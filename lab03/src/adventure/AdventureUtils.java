package adventure;

public class AdventureUtils {

    /** Returns whether the given string is a valid int. */
    static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
