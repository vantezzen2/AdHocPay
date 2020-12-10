package io.vantezzen.adhocpay.activities;

import android.content.Intent;
import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.runner.RunWith;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import java.time.LocalDateTime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.RecyclerViewItemCountAssertion;
import io.vantezzen.adhocpay.manager.ManagerMock;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.UserRepository;

import static io.vantezzen.adhocpay.Utils.atPositionOnView;
import static org.junit.Assert.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void prepareApp() {
        Log.d("Test", "Setting up App");
        AdHocPayApplication.useTestApplication();
    }

    @Test
    public void testCanShowData() {
        // Zeige Credits an
        onView(withId(R.id.credits)).check(matches(isDisplayed()));
        onView(withId(R.id.credits)).check(matches(withText("50.0€")));

        // Zeigt Transaktionen an
        onView(withId(R.id.transactionlist)).check(matches(isDisplayed()));

        // Muss die richtige Anzahl an Transaktionen haben
        onView(withId(R.id.transactionlist)).check(new RecyclerViewItemCountAssertion(4));

        // Teste den Inhalt der ersten Transaktion
        onView(withId(R.id.transactionlist)).check(matches(atPositionOnView(0, withText("heinrich 👉 marko"), R.id.sender_receiver)));
        onView(withId(R.id.transactionlist)).check(matches(atPositionOnView(0, withText("100.0€"), R.id.amount)));
        onView(withId(R.id.transactionlist)).check(matches(atPositionOnView(0, withText("0 minutes ago"), R.id.time_ago)));
    }
}