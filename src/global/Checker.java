package global;

/**
 * Created by Steiner on 01.05.2016.
 */
public class Checker {

    public static boolean checkPasswordForValidity(String u, String p) {
        return (u.matches("[a-zA-Z]+[0-9]*") && p != null);
    }

    public static boolean checkNewPersonnel(String s, String f, String p) {
        return (s.matches("[A-Z][a-z]+") && f.matches("[A-Z][a-z]+") && p.matches("[A-Z][a-z]+"));
    }

    public static boolean checkNewAutoCode(String s) {
        return (s.matches("^[ABCEHKMOPTX][0-9]{3}[ABCEHKMOPTX]{2}[0-9]{2,3}$"));
    }
}
