package io.vantezzen.adhocpay;

/**
 * Validation: Einfache Validationen für Objekte
 */
public class Validation {
    /**
     * Prüfe, dass das Objekt nicht null ist, sonst werfe eine NullPointerException
     *
     * @param o Beliebiges Objekt
     * @throws NullPointerException Wenn o = null
     */
    public static void notNull(Object o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException("Argument darf nicht null sein");
        }
    }

    /**
     * Prüfe, dass der Float Wert größer als 0 ist (0 NICHT eingeschlossen)
     *
     * @param a Zahl
     */
    public static void greaterThanZero(float a) throws IllegalArgumentException {
        if (a <= 0) {
            throw new IllegalArgumentException("Argument muss größer als 0 sein");
        }
    }
}
