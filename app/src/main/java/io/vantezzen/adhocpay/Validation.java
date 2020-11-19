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
}
