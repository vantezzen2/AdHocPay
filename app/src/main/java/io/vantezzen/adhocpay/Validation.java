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
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void greaterThanZero(float a) throws IllegalArgumentException {
        if (a <= 0) {
            throw new IllegalArgumentException("Argument muss größer als 0 sein");
        }
    }

    /**
     * Prüfe, ob der angegebene Nutzername gültig ist.
     * Achtung: Dies liefert einen boolean zurück und wirft nicht direkt eine Exception!
     * Ein gültiger Nutzername darf hierbei nur aus den Buchstaben a-z und Zahlen bestehen
     *
     * @param username Nutzername
     * @return Ist der Name gültig?
     */
    public static boolean isValidUsername(String username) {
        return username.matches("[A-Za-z0-9_]+");
    }
}
