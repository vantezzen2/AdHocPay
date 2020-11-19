package io.vantezzen.adhocpay.application;

import android.app.Activity;
import android.content.SharedPreferences;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPApplication;
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;

import java.io.IOException;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.activities.BaseActivity;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.controllers.ControllerManagerImpl;
import io.vantezzen.adhocpay.logger.AndroidLogger;
import io.vantezzen.adhocpay.logger.Logger;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.ASAPCommunication;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

/**
 * Implementation eines AdHoc Pay Managers für die App
 */
public class ManagerImpl implements Manager {
    // Aktuelle Instanzen
    private final ASAPApplication application;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ControllerManager controllerManager;
    private final Logger logger;
    private final NetworkCommunicator communicator;

    // Statische Daten für ASAP
    public static final String ASAP_APPNAME = "application/x-AdHocPay";
    public static final String DEFAULT_URI = "adhocpay://data";

    // State
    private boolean isAsapSetup = false;

    public ManagerImpl(ASAPApplication application) {
        // Setze benötigte Instanzen auf
        this.application = application;
        this.userRepository = new UserRepository();
        this.transactionRepository = new TransactionRepository();
        this.controllerManager = new ControllerManagerImpl(this);
        this.logger = new AndroidLogger();
        this.communicator = new ASAPCommunication(this, controllerManager);
    }

    @Override
    public void registerASAPListener(ASAPMessageReceivedListener listener) {
        application.addASAPMessageReceivedListener(ASAP_APPNAME, listener);
    }

    @Override
    public Activity getActivity() {
        return application.getActivity();
    }

    @Override
    public void refreshView() {
        Activity activity = application.getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).onDataChange();
        }
    }

    @Override
    public String getAppName() {
        return ASAP_APPNAME;
    }

    @Override
    public String getDefaultUri() {
        return DEFAULT_URI;
    }

    @Override
    public String getApplicationRootFolder(String app) {
        return application.getApplicationRootFolder(app);
    }

    @Override
    public String getOwnerId() {
        return (String) application.getOwnerID();
    }

    @Override
    public ASAPStorage getAsapStorage(String format) throws IOException, ASAPException {
        return application.getASAPStorage(format);
    }

    @Override
    public NetworkCommunicator getNetwork() {
        return communicator;
    }

    @Override
    public void setupNetwork() {
        if (!isAsapSetup) {
            communicator.setup();
            isAsapSetup = true;
        }
    }

    @Override
    public void storeSetting(String key, String value) {
        SharedPreferences.Editor editor = application.getActivity().getSharedPreferences("ADHOCPAY", 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getSetting(String key) {
        SharedPreferences settings = application.getActivity().getSharedPreferences("ADHOCPAY", 0);
        String data = settings.getString(key, null);

        return data;
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public User getMe() {
        if (getSetting("username") == null) {
            return null;
        }

        return userRepository.getUserByName(getSetting("username"));
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    @Override
    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    @Override
    public void log(String start, String message) {
        logger.log(start, message);
    }
}
