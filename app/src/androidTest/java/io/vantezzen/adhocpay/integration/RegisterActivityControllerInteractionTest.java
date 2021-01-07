package io.vantezzen.adhocpay.integration;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.Utils;
import io.vantezzen.adhocpay.activities.MainActivity;
import io.vantezzen.adhocpay.activities.SetupActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests zwischen Activities und den Controllern
 */
@RunWith(AndroidJUnit4.class)
public class RegisterActivityControllerInteractionTest {
    @Rule
    public ActivityTestRule<SetupActivity> rule  = new  ActivityTestRule<>(SetupActivity.class);

    @BeforeClass
    public static void prepareApp() {
        Log.d("Test", "Setting up App");
        AdHocPayApplication.useTestApplication();
    }

    @Test
    public void testRegisterScreenInteractsWithUserControllerInTheRightWay() {
        // Stelle sicher, dass wir auf der richtigen Activity sind
        onView(withId(R.id.registerWelcomeText)).check(matches(isDisplayed()));

        // Registriere einen Nutzer
        onView(withId(R.id.nutzernameInput))
                .perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.confirmRegisterButton)).perform(click());

        // Stelle sicher, dass der Nutzer registriert wurde
        assertEquals("testUser", AdHocPayApplication.getManager().getSetting("username"));
    }

    @Test
    public void testRegisterScreenFailsWhenUserControllerReturnsFalse() {
        // Stelle sicher, dass wir auf der richtigen Activity sind
        onView(withId(R.id.registerWelcomeText)).check(matches(isDisplayed()));

        // Erstelle den Nutzer in der Repository, so dass dieser nicht mehr registriert werden kann
        AdHocPayApplication.getManager().getUserRepository().getUserByName("testUser");

        // Registriere den Nutzer
        onView(withId(R.id.nutzernameInput))
                .perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.confirmRegisterButton)).perform(click());

        System.out.println(AdHocPayApplication.getManager().getSetting("username"));

        // Stelle sicher, dass ein Fehler gezeigt wird
        assertNotEquals("testUser", AdHocPayApplication.getManager().getSetting("username"));
        onView(withId(R.id.errorMessage))
            .check(
                    matches(
                            Utils.hasValueEqualTo(
                                AdHocPayApplication.getActivity().getString(R.string.register_user_exists)
                            )
                    )
            );
    }
}
