package io.vantezzen.adhocpay.e2e;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.RecyclerViewItemCountAssertion;
import io.vantezzen.adhocpay.Utils;
import io.vantezzen.adhocpay.activities.StartupActivity;
import io.vantezzen.adhocpay.network.ASAPCommunication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Teste das Zusammenspiel der kompletten App
 */
@RunWith(AndroidJUnit4.class)
public class EndToEndTest {
    @Rule
    public ActivityTestRule<StartupActivity> rule  = new  ActivityTestRule<>(StartupActivity.class);

    /**
     * Lösche gespeicherte Daten und starte die App nei
     */
    private void cleanAppData() throws IOException {
        // Lösche shared preferences
        rule.getActivity().getSharedPreferences("ADHOCPAY", 0).edit().clear().apply();

        // Lösche ASAP Daten
        ((ASAPCommunication)AdHocPayApplication.getManager().getNetwork()).getAsapStorage().removeChannel(AdHocPayApplication.getManager().getDefaultUri());

        // Bereinige Repositories
        AdHocPayApplication.getManager().getUserRepository().clear();
        AdHocPayApplication.getManager().getTransactionRepository().clear();

        Intent start = new Intent(
                rule.getActivity(),
                StartupActivity.class
        );
        rule.launchActivity(start);
    }

    @Test
    public void testAppWorks() throws InterruptedException, IOException {
        cleanAppData();

        // Warte, bis ASAP gestartet wurde
        Thread.sleep(100);

        // Stelle sicher, dass wir auf der richtigen Activity sind
        onView(withId(R.id.registerWelcomeText)).check(matches(isDisplayed()));

        /////// REGISTRIERUNG //////
        onView(withId(R.id.nutzernameInput))
                .perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.confirmRegisterButton)).perform(click());

        Thread.sleep(100);

        /////// STARTSEITE ///////
        // Stelle sicher, dass wir nun auf der Startseite sind
        onView(withId(R.id.credits)).check(matches(isDisplayed()));

        // Stelle sicher, dass wir die richtigen Inhalte anzeigen
        onView(withId(R.id.credits)).check(matches(Utils.hasValueEqualTo("50.0€")));
        onView(withId(R.id.transactionlist)).check(new RecyclerViewItemCountAssertion(0));

        ////// SENDE EINE TRANSAKTION //////
        onView(withId(R.id.sendCreditsButton)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.wertInput)).check(matches(isDisplayed()));

        // Sende Formular
        onView(withId(R.id.nutzernameInput)).perform(typeText("endtoend"), closeSoftKeyboard());
        onView(withId(R.id.wertInput)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.confirmRegisterButton)).perform(click());

        Thread.sleep(100);

        /////// STARTSEITE ///////
        // Stelle sicher, dass wir nun auf der Startseite sind
        onView(withId(R.id.credits)).check(matches(isDisplayed()));

        // Stelle sicher, dass wir die richtigen Inhalte anzeigen
        onView(withId(R.id.credits)).check(matches(Utils.hasValueEqualTo("45.0€")));
        onView(withId(R.id.transactionlist)).check(new RecyclerViewItemCountAssertion(1));
    }
}
