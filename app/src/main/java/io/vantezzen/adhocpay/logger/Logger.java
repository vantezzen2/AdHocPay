package io.vantezzen.adhocpay.logger;

/**
 * Logger: Einfacher Logger, welcher Ausgaben auf einer beliebigen Platform erzeugt
 */
public interface Logger {
    /**
     * Logge eine Nachricht
     *
     * @param start   Start der Nachricht. Dies sollte i.d.R. der aktuelle Klassenname sein
     * @param message Nachricht
     */
    void log(String start, String message);
}
