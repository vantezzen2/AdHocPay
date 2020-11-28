package io.vantezzen.adhocpay.manager;

import android.app.Activity;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;

import java.io.IOException;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

/**
 * Application Manager: Verwalte die einzelnen Komponenten der Applikation und sorgt für
 * einfache Kommunikation zwischen den Komponenten
 */
public interface Manager {
    // Weiterleitungen an die Application
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

    /**
     * Sendet die Nachricht an die aktuelle View, dass die angezeigten Daten aktualisiert
     * werden müssen.
     * Dies tritt in der Regel auf, wenn eine neue Transaktion empfangen wird
     */
    void refreshView();

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
     * Liefert die ASAP Storage Instanz
     *
     * @param format Format des Storage
     * @return Storage
     * @throws IOException Falls nicht auf die IO zugegriffen werden kann
     * @throws ASAPException Bei anderen Exceptions
     */
    ASAPStorage getAsapStorage(String format) throws IOException, ASAPException;

    /**
     * Liefert das Netzwerk
     *
     * @return Netzwerkkommunikation
     */
    NetworkCommunicator getNetwork();

    /**
     * Setze das Netzwerk auf
     * Dies setzt bei ASAP vorraus, dass wir uns in einer ASAPActivity befinden, deswegen
     * muss dieser Schritt unabhängig vom Konstruktor geschehen
     *
     * Achtung: Diese Methode kann im Laufe des App Lifecycles mehrmals aufgerufen werden.
     * Sie sollte daher selbstständig in Erinnerung halten, ob das Netzwerk nicht bereits
     * aufgesetzt wurde.
     */
    void setupNetwork();

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

    // Controller

    /**
     * Liefert den aktuellen Controller Manager
     *
     * @return Controller Manager
     */
    ControllerManager getControllerManager();

    // Logger
    /**
     * Logge eine Nachricht
     *
     * @param start Start der Nachricht. Dies sollte i.d.R. der aktuelle Klassenname sein
     * @param message Nachricht
     */
    void log(String start, String message);
}
