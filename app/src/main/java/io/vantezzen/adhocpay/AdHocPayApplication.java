package io.vantezzen.adhocpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import io.vantezzen.adhocpay.manager.ManagerMock;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.manager.ManagerImpl;

/**
 * AdHocPayApplication: Dies ist die Basisklasse von AdHoc Pay.
 * Sie setzt den Manager und die ASAPCommunication Verbindung auf, damit die Applikation starten kann
 */
public class AdHocPayApplication {
    private static Manager manager;
    private static boolean isTesting = false;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;

    /**
     * Initialize application ad hoc pay application.
     */
    public static void initializeApplication() {
        Log.d("AHP", "Starte App");

        if (manager != null) {
            Log.d("AHP", "App mehrmals gestartet - ignoriere");
            return;
        }
        manager = new ManagerImpl();
    }

    /**
     * Use test application.
     *
     * @param addTestdata the add testdata
     */
    public static void useTestApplication(boolean addTestdata) {
        System.out.println(
                "ACHTUNG: AdHoc Pay wurde im Testmodus gestartet. Dieser ist nur für Software Tests gedacht."
        );

        isTesting = true;
        manager = new ManagerMock(addTestdata);
    }

    /**
     * Use test application.
     */
    public static void useTestApplication() {
        useTestApplication(true);
    }

    /**
     * get the current Application Manager
     *
     * @return Manager manager
     */
    public static Manager getManager() {
        if (manager == null) {
            Log.d("AHP", "Es existiert kein Manager - erstelle einen");
            initializeApplication();
        }
        return manager;
    }

    /**
     * Find out of the Application is currently started in test mode
     *
     * @return Test mode activated?
     */
    public static boolean isTesting() {
        return isTesting;
    }


    /**
     * Gets activity.
     *
     * @return the activity
     */
    public static Activity getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public static void setActivity(Activity activity) {
        AdHocPayApplication.activity = activity;
    }
}
