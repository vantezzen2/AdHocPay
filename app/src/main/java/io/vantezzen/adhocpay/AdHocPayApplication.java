package io.vantezzen.adhocpay;

import android.app.Activity;
import android.util.Log;

import io.vantezzen.adhocpay.manager.ManagerMock;
import io.vantezzen.adhocpay.network.NetworkCommunicator;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.manager.ManagerImpl;

/**
 * AdHocPayApplication: Dies ist die Basisklasse von AdHoc Pay.
 * Sie setzt den Manager und die ASAPCommunication Verbindung auf, damit die Applikation starten kann
 */
public class AdHocPayApplication {
    private static AdHocPayApplication instance = null;
    private static Manager manager;
    private NetworkCommunicator communicator;
    private static boolean isTesting = false;


    private static Activity activity;

    /**
     * Erstelle eine AdHocPayApplication instanz.
     * Diese Methode wird von initializeApplication aufgerufen
     */
    protected AdHocPayApplication() {
        if (AdHocPayApplication.instance != null) {
            throw new IllegalStateException("Singleton kann nicht erneut initialisiert werden!");
        }

        manager = new ManagerImpl(this);
    }

    public static AdHocPayApplication initializeApplication() {
        Log.d("AHP", "Starte App");

        if (instance != null) {
            Log.d("AHP", "App mehrmals gestartet - ignoriere");
            return instance;
        }

        instance = new AdHocPayApplication();
        manager = new ManagerImpl(instance);
        return instance;
    }

    public static void useTestApplication(boolean addTestdata) {
        System.out.println(
                "ACHTUNG: AdHoc Pay wurde im Testmodus gestartet. Dieser ist nur für Software Tests gedacht."
        );

        isTesting = true;
        manager = new ManagerMock(addTestdata);
    }

    public static void useTestApplication() {
        useTestApplication(true);
    }

    /**
     * Get the current AdHocPayApplication instance
     *
     * @return Instance
     */
    public static AdHocPayApplication getInstance() {
        return instance;
    }

    /**
     * get the current Application Manager
     *
     * @return Manager
     */
    public static Manager getManager() {
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


    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        AdHocPayApplication.activity = activity;
    }
}
