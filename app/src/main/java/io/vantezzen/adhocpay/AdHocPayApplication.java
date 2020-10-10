package io.vantezzen.adhocpay;

import android.app.Activity;
import android.util.Log;

import net.sharksystem.asap.ASAP;
import net.sharksystem.asap.ASAPChannel;
import net.sharksystem.asap.ASAPEngineFS;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPMessages;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import io.vantezzen.adhocpay.Integrations.ASAPCommunication;
import io.vantezzen.adhocpay.Integrations.Storage;

/**
 * AdHocPayApplication: This class is AdHoc Pay's heart.
 * It manages communication between different part of the app and provides global data
 * and information across all activities.
 */
public class AdHocPayApplication extends ASAPApplication {
    // ASAP Configuration values
    public static final String ASAP_APPNAME = "application/x-AdHocPay";
    public static final String USER_STORAGE = "AHP:USER";
    public static final String DEFAULT_URI = "adhocpay://data";

    // Current running instances
    private static AdHocPayApplication instance = null;
    private ASAPCommunication asap = null;
    private Storage storage = null;
    private ASAPStorage asapStorage = null;

    // Client data
    private CharSequence id = null;

    /**
     * Create a new AdHocPayApplication.
     * This method will never need to be called - the initializeApplication method is doing that for you
     *
     * @param supportedFormats
     * @param initialActivity
     */
    protected AdHocPayApplication(Collection<CharSequence> supportedFormats, Activity initialActivity) {
        super(supportedFormats, initialActivity);

        this.asap = new ASAPCommunication(this);
        this.storage = new Storage(this);
        this.id = ASAP.createUniqueID();

        // Set up ASAP folder
        String folder = getApplicationRootFolder(ASAP_APPNAME);
        try {
            asapStorage = ASAPEngineFS.getASAPStorage(
                    (String) getOwnerId(),
                    folder,
                    ASAP_APPNAME
            );
        } catch (IOException e) {
            // TODO: Handle these
            e.printStackTrace();
        } catch (ASAPException e) {
            e.printStackTrace();
        }

        if (asapStorage == null) {
            try {
                asapStorage = getASAPStorage(ASAP_APPNAME);
            } catch (IOException e) {
                // TODO: Handle these
                e.printStackTrace();
            } catch (ASAPException e) {
                e.printStackTrace();
            }
        }

        restoreData();
    }

    /**
     * Restore data that we stored from previous runs
     */
    private void restoreData() {
        ASAPChannel channel;
        ASAPMessages messages;
        try {
            channel = asapStorage.getChannel(DEFAULT_URI);
            messages = channel.getMessages();
        } catch (ASAPException e) {
            // TODO: Handle these
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            Log.d("AdHocPayApplication", "Restoring " + messages.size() + " messages");
        } catch (IOException e) {}
        asap.asapMessagesReceived(messages);
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
            formats.add(ASAP_APPNAME);

            AdHocPayApplication.instance = new AdHocPayApplication(formats, initialActivity);
            AdHocPayApplication.instance.startASAPApplication();
        }

        return AdHocPayApplication.instance;
    }

    /**
     * Update our saved username
     *
     * @param username
     */
    public void setUsername(String username) {
        storage.set(USER_STORAGE, "username", username);
    }

    /**
     * Get the user's current username
     * This will return null if no username is set
     *
     * @return Username or null
     */
    public String getUsername() {
        return storage.get(USER_STORAGE, "username");
    }

    public CharSequence getOwnerId() {
        return this.id;
    }

    /**
     * Get the information about if the app is set up.
     * This is used to determine if the Setup screen should be opened
     *
     * @return Boolean
     */
    public boolean isSetup() {
        return getUsername() != null;
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
     * Get the current ASAPCommunication instance
     *
     * @return Instance
     */
    public ASAPCommunication getASAPCommunication() {
        return asap;
    }

    /**
     * Get the current storage instance
     *
     * @return Instance
     */
    public Storage getStorage() {
        return storage;
    }
}
