package io.vantezzen.adhocpay.application;

import android.app.Activity;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.controllers.ControllerManagerImpl;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.MockCommunicator;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

/**
 * Manager, welcher für Mocking genutzt werden kann
 */
public class ManagerMock implements Manager {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ControllerManagerImpl controllerManager;

    private final NetworkCommunicator communicator;
    private Map<String, String> settings = new HashMap<>();

    public ManagerMock() {
        // Setze benötigte Instanzen auf
        // Wir können die echten Repositories nutzen, da diese angekapselt sind
        this.userRepository = new UserRepository();
        this.transactionRepository = new TransactionRepository();

        this.controllerManager = new ControllerManagerImpl(this);
        this.communicator = new MockCommunicator();
    }

    @Override
    public void registerASAPListener(ASAPMessageReceivedListener listener) {
        // Tue nichts
    }

    @Override
    public Activity getActivity() {
        // Wir haben keine Activity
        return null;
    }

    @Override
    public void refreshView() {
        // Tue nichts
    }

    @Override
    public String getAppName() {
        return "application/x-AdHocPay-Mock";
    }

    @Override
    public String getDefaultUri() {
        return "adhocpay://mock";
    }

    @Override
    public String getApplicationRootFolder(String app) {
        return app;
    }

    @Override
    public String getOwnerId() {
        return "1";
    }

    @Override
    public ASAPStorage getAsapStorage(String format) throws IOException, ASAPException {
        return null;
    }

    @Override
    public NetworkCommunicator getNetwork() {
        return this.communicator;
    }

    @Override
    public void setupNetwork() {
        // Tue nichts
    }

    @Override
    public void storeSetting(String key, String value) {
        settings.put(key, value);
    }

    @Override
    public String getSetting(String key) {
        return settings.get(key);
    }

    @Override
    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    @Override
    public User getMe() {
        return this.userRepository.getUserByName("mock");
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }

    @Override
    public ControllerManager getControllerManager() {
        return null;
    }

    @Override
    public void log(String start, String message) {
        System.out.println("Log: " + start + " - " + message);
    }

    /**
     * Liefert den aktuellen MockCommunicator
     *
     * @return Communicator
     */
    public NetworkCommunicator getCommunicator() {
        return communicator;
    }
}