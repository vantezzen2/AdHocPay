package io.vantezzen.adhocpay.storage;

public interface StorageProvider {
    /**
     * Setzt einen Wert in den Einstellungen
     *
     * @param key Schlüssel, für die Daten
     * @param value Daten
     */
    void set(String key, String value);

    /**
     * Liefert den Wert eines Schlüssels in den Einstellungen
     *
     * @param key Schlüssel
     * @return Wert
     */
    String get(String key);
}
