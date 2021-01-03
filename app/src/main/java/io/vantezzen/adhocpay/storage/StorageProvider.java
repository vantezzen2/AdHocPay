package io.vantezzen.adhocpay.storage;

/**
 * The interface Storage provider.
 */
public interface StorageProvider {
    /**
     * Setzt einen Wert in den Einstellungen
     *
     * @param key   Schlüssel, für die Daten
     * @param value Daten
     */
    void set(String key, String value);

    /**
     * Liefert den Wert eines Schlüssels in den Einstellungen
     *
     * @param key Schlüssel
     * @return Wert string
     */
    String get(String key);
}
