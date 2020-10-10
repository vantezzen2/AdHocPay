package io.vantezzen.adhocpay.Integrations;

import android.content.SharedPreferences;

import io.vantezzen.adhocpay.AdHocPayApplication;

/**
 * Storage: Persistently store data using the "shared preferences" feature
 */
public class Storage {
    private AdHocPayApplication application = null;

    public Storage(AdHocPayApplication application) {
        this.application = application;
    }

    /**
     * Set a value
     *
     * @param bucket Bucket to choose
     * @param key Key
     * @param value Value
     */
    public void set(String bucket, String key, String value) {
        SharedPreferences.Editor editor = application.getActivity().getSharedPreferences(bucket, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get a value from the storage
     *
     * @param bucket Bucket to choose
     * @param key Key of the information
     * @param defaultValue Default value. This value will be returned if the key doesn't exist
     * @return Data
     */
    public String get(String bucket, String key, String defaultValue) {
        SharedPreferences settings = application.getActivity().getSharedPreferences(bucket, 0);
        String data = settings.getString(key, defaultValue);

        return data;
    }

    /**
     * Get a value from the storage.
     * This will return null if the key doesn't exist
     *
     * @param bucket Bucket to choose
     * @param key Key of the information
     * @return Data
     */
    public String get(String bucket, String key) {
        return get(bucket, key, null);
    }
}
