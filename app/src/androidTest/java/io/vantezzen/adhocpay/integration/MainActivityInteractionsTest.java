package io.vantezzen.adhocpay.integration;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.RecyclerViewItemCountAssertion;
import io.vantezzen.adhocpay.Utils;
import io.vantezzen.adhocpay.activities.MainActivity;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.user.UserRepository;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Teste die Interaktionen von MainActivity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInteractionsTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void prepareApp() {
        Log.d("Test", "Setting up App");
        AdHocPayApplication.useTestApplication(false);
    }

    @Test
    public void testWillUpdateViewWhenNewTransactionComesIn() {
        // Stelle sicher, dass noch keine Transaktionen angezeigt werden
        onView(withId(R.id.transactionlist)).check(matches(isDisplayed()));
        onView(withId(R.id.transactionlist)).check(new RecyclerViewItemCountAssertion(0));

        // Füge eine Transaktion hinzu
        UserRepository repo = AdHocPayApplication.getManager().getUserRepository();
        new Transaction(
            repo.getUserByName("one"),
            repo.getUserByName("mock"),
            15.99f,
            LocalDateTime.now(),
            AdHocPayApplication.getManager().getTransactionRepository()
        );
        AdHocPayApplication.getManager().getControllerManager().onDataChange();

        // Stelle sicher, dass die Liste aktualisiert wurde
        onView(withId(R.id.transactionlist)).check(matches(isDisplayed()));
        onView(withId(R.id.transactionlist)).check(new RecyclerViewItemCountAssertion(1));
    }
}
