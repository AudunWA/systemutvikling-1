package no.ntnu.iie.stud.cateringstorm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for validating user input.
 */
public final class InputUtil {
    /**
     * The regex pattern for matching email addresses.
     */
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


    /**
     Validates an email address.
     * @param input The email address to validate.
     * @return true if valid email address, false otherwise.
     */
    public static boolean isValidEmail(String input) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     Validates a phone number.
     * @param input The phone number to validate.
     * @return true if valid phone number, false otherwise.
     */
    public static boolean isValidPhoneNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
