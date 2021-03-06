package io.vantezzen.adhocpay.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.activities.BaseActivity;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.controllers.ControllerManagerImpl;
import io.vantezzen.adhocpay.logger.AndroidLogger;
import io.vantezzen.adhocpay.logger.Logger;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.ASAPCommunication;
import io.vantezzen.adhocpay.network.HttpCommunication;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

/**
 * Implementation eines AdHoc Pay Managers für die App
 */
public class ManagerImpl implements Manager {
    // Aktuelle Instanzen
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ControllerManager controllerManager;
    private final Logger logger;
    private final NetworkCommunicator communicator;

    /**
     * The constant ASAP_APPNAME.
     */
// Statische Daten für ASAP
    public static final String ASAP_APPNAME = "application/x-AdHocPay";
    /**
     * The constant DEFAULT_URI.
     */
    public static final String DEFAULT_URI = "adhocpay://data";

    // State
    private boolean isAsapSetup = false;

    /**
     * Instantiates a new Manager.
     *
     * @throws NullPointerException the null pointer exception
     */
    public ManagerImpl() throws NullPointerException {
        // Setze benötigte Instanzen auf
        this.userRepository = new UserRepository();
        this.transactionRepository = new TransactionRepository(this.userRepository, false);
        this.controllerManager = new ControllerManagerImpl(this);
        this.logger = new AndroidLogger();
        this.communicator = new ASAPCommunication(this, controllerManager);
        //this.communicator = new HttpCommunication(this);
    }

    @Override
    public Activity getActivity() {
        return AdHocPayApplication.getActivity();
    }

    @Override
    public void refreshView() {
        Activity activity = AdHocPayApplication.getActivity();
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
    public void storeSetting(String key, String value) throws NullPointerException {
        Validation.notNull(key);
        Validation.notNull(value);

        SharedPreferences.Editor editor = AdHocPayApplication.getActivity().getSharedPreferences("ADHOCPAY", 0).edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String getSetting(String key) throws NullPointerException {
        Validation.notNull(key);

        SharedPreferences settings = AdHocPayApplication.getActivity().getSharedPreferences("ADHOCPAY", 0);
        return settings.getString(key, null);
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
    public NetworkCommunicator getNetworkCommunicator() {
        return communicator;
    }

    @Override
    public void log(String start, String message) {
        logger.log(start, message);
    }
}
