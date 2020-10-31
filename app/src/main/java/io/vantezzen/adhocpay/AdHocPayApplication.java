package io.vantezzen.adhocpay;

import android.app.Activity;

import net.sharksystem.asap.android.apps.ASAPApplication;

import java.util.ArrayList;
import java.util.Collection;

import io.vantezzen.adhocpay.network.ASAPCommunication;
import io.vantezzen.adhocpay.network.NetworkCommunicator;
import io.vantezzen.adhocpay.application.Manager;
import io.vantezzen.adhocpay.application.ManagerImpl;

/**
 * AdHocPayApplication: Dies ist die Basisklasse von AdHoc Pay.
 * Sie setzt den Manager und die ASAPCommunication Verbindung auf, damit die Applikation starten kann
 */
public class AdHocPayApplication extends ASAPApplication {
    private static AdHocPayApplication instance;
    private Manager manager;
    private NetworkCommunicator communicator;

    private boolean isAsapSetup = false;

    /**
     * Erstelle eine AdHocPayApplication instanz.
     * Diese Methode wird von initializeApplication aufgerufen
     *
     * @param supportedFormats Unterstützte Formate der ASAP App
     * @param initialActivity Erste Activity
     */
    protected AdHocPayApplication(Collection<CharSequence> supportedFormats, Activity initialActivity) {
        super(supportedFormats, initialActivity);

        manager = new ManagerImpl(this);
    }

    /**
     * Initialize the Application
     *
     * @param initialActivity
     * @return Current Application instance
     */
    public static AdHocPayApplication initializeApplication(Activity initialActivity) {
        if (AdHocPayApplication.instance == null) {
            Collection<CharSequence> formats = new ArrayList<>();
            formats.add(ManagerImpl.ASAP_APPNAME);

            new AdHocPayApplication(formats, initialActivity);
            AdHocPayApplication.instance.startASAPApplication();
        }

        return AdHocPayApplication.instance;
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
     * Liefert die aktuelle Manager instanz für diese App
     *
     * @return Manager
     */
    public Manager getManager() {
        return manager;
    }
}
