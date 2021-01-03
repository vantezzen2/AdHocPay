package io.vantezzen.adhocpay.network.asap;

import android.app.Activity;
import android.util.Log;

import net.sharksystem.asap.ASAP;
import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.android.apps.ASAPApplication;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.manager.ManagerImpl;
import io.vantezzen.adhocpay.manager.ManagerMock;

/**
 * The type Asap app.
 */
public class ASAPApp extends ASAPApplication {
    private static ASAPApp instance;

    private ASAPApp(Collection<CharSequence> supportedFormats, Activity initialActivity) {
        super(supportedFormats, initialActivity);
    }

    /**
     * Initialize the ASAP Application
     *
     * @param initialActivity the initial activity
     * @return Current Application instance
     * @throws NullPointerException the null pointer exception
     */
    public static ASAPApp initializeApplication(Activity initialActivity) throws NullPointerException {
        Log.d("ASAPApp", "Erstelle App");
        if (ASAPApp.instance == null) {
            if (AdHocPayApplication.isTesting()) {
                // Erstelle eine Mockinstanz von ASAPApplication
                Log.d("ASAPApp", "Nutze Mock Modus");
                ASAPApp.instance = Mockito.mock(ASAPApp.class);
            } else {
                Validation.notNull(initialActivity);

                Collection<CharSequence> formats = new ArrayList<>();
                formats.add(ManagerImpl.ASAP_APPNAME);

                ASAPApp.instance = new ASAPApp(formats, initialActivity);
                ASAPApp.instance.startASAPApplication();
            }
        }

        return ASAPApp.instance;
    }

    /**
     * Get the current App instance
     *
     * @return Instance instance
     */
    public static ASAPApp getInstance() {
        return instance;
    }
}
