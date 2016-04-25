package no.ntnu.iie.stud.cateringstorm.util;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;

import java.io.IOException;
import java.util.List;
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
     * Validates an email address.
     *
     * @param input The email address to validate.
     * @return true if valid email address, false otherwise.
     */
    public static boolean isValidEmail(String input) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Validates a phone number.
     *
     * @param input The phone number to validate.
     * @return true if valid phone number, false otherwise.
     */
    public static boolean isValidPhoneNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Uses the Google Geocoder API to validate a street address.
     * @param input The street address to validate. Trondheim, Norway is added automatically at the end.
     * @return true if valid street address or API problems, false otherwise.
     */
    public static boolean isValidStreetAddress(String input) {
        Geocoder geocoder = new Geocoder();
        GeocoderRequest geoRequest = new GeocoderRequestBuilder().setAddress(input).getGeocoderRequest();
        GeocodeResponse geocodeResponse;

        try {
            geocodeResponse = geocoder.geocode(geoRequest);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        switch (geocodeResponse.getStatus()) {
            case ERROR:
                return true;
            case INVALID_REQUEST:
                return false;
            case OK:
                return true;
            case OVER_QUERY_LIMIT:
                return true;
            case REQUEST_DENIED:
                return true;
            case UNKNOWN_ERROR:
                return true;
            case ZERO_RESULTS:
                return false;
            default:
                return false;
        }
    }
}
