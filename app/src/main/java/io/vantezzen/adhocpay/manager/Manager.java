package io.vantezzen.adhocpay.manager;

import android.app.Activity;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPApplication;
import net.sharksystem.asap.apps.ASAPMessageReceivedListener;

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
     * Liefert die aktuelle Activity
     *
     * @return Activity activity
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
     * @return URI default uri
     */
    String getDefaultUri();

    // Netzwerk
    /**
     * Liefert das Netzwerk
     *
     * @return Netzwerkkommunikation network
     */
    NetworkCommunicator getNetwork();

    /**
     * Setze das Netzwerk auf
     * Dies setzt bei ASAP vorraus, dass wir uns in einer ASAPActivity befinden, deswegen
     * muss dieser Schritt unabhängig vom Konstruktor geschehen
     * <p>
     * Achtung: Diese Methode kann im Laufe des App Lifecycles mehrmals aufgerufen werden.
     * Sie sollte daher selbstständig in Erinnerung halten, ob das Netzwerk nicht bereits
     * aufgesetzt wurde.
     */
    void setupNetwork();

    // Einstellungen

    /**
     * Speichere eine Einstellung
     *
     * @param key   Schlüssel
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
     * Diese Funktion sollte null liefern, falls der Nutzer noch nicht registriert ist
     *
     * @return Nutzer me
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

    /**
     * Liefert den aktuellen Network Communicator
     *
     * @return Network Communicator
     */
    NetworkCommunicator getNetworkCommunicator();

    // Logger

    /**
     * Logge eine Nachricht
     *
     * @param start   Start der Nachricht. Dies sollte i.d.R. der aktuelle Klassenname sein
     * @param message Nachricht
     */
    void log(String start, String message);
}
