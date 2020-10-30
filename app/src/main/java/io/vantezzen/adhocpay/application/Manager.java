package io.vantezzen.adhocpay.application;

import android.app.Activity;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;

import java.io.IOException;

import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;

/**
 * Application Manager: Verwalte die einzelnen Komponenten der Applikation
 */
public interface Manager {
    /**
     * Füge einen ASAP Message Listener zur aktuellen ASAPApplication hinzu
     *
     * @param listener ASAP Listener Klassen-Instanz
     */
    void registerASAPListener(ASAPMessageReceivedListener listener);

    /**
     * Liefert die aktuelle Activity
     *
     * @return Activity
     */
    Activity getActivity();

    // ASAP Einstellungen
    /**
     * Liefert die App name, welche für ASAP benutzt werden sollte
     *
     * @return App name
     */
    String getAppName();

    /**
     * Liefert die Standart-URI, welche für ASAP genutzt wird
     *
     * @return URI
     */
    String getDefaultUri();

    // ASAP Komponenten
    /**
     * Liefert den Pfad zum root Verzeichnis der App durch ASAP
     *
     * @param app App Name
     * @return Pfad
     */
    String getApplicationRootFolder(String app);

    /**
     * Liefert die ASAP Owner ID
     *
     * @return Owner ID
     */
    String getOwnerId();

    /**
     * Liefert die ASAP Storage
     *
     * @param format Format des Storage
     * @return Storage
     * @throws IOException Falls nicht auf die IO zugegriffen werden kann
     * @throws ASAPException Bei anderen Exceptions
     */
    ASAPStorage getAsapStorage(String format) throws IOException, ASAPException;

    // Einstellungen
    /**
     * Speichere eine Einstellung
     *
     * @param key Schlüssel
     * @param value Wert der Einstellung
     */
    void storeSetting(String key, String value);

    /**
     * Liefert den Wert der Einstellung oder null, wenn die Einstellung nicht definiert ist
     *
     * @param key Schlüssel
     * @return Wert oder null
     */
    String getSetting(String key);

    // Models
    /**
     * Liefert die User Repository
     *
     * @return User Repository
     */
    UserRepository getUserRepository();

    /**
     * Liefert den aktuellen Nutzer
     *
     * @return Nutzer
     */
    User getMe();

    /**
     * Liefert die Transaction Repository
     *
     * @return Transaction Repository
     */
    TransactionRepository getTransactionRepository();
}
