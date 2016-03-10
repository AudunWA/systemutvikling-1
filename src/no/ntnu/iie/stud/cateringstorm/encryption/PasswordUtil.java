package no.ntnu.iie.stud.cateringstorm.encryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * Utility class for generating and verifying passwords.
 * Created by Audun on 10.03.2016.
 */
public final class PasswordUtil {
    private static final int SALT_SIZE = 24;

    // No need to create new instance every time
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateSalt() {
        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);
        return Base64.encodeBase64String(salt);
    }

    public static String generatePasswordHash(String password, String salt) {
        return DigestUtils.sha256Hex(salt + password);
    }

    public static boolean verifyPassword(String userInput, String salt, String hash) {
        String inputHashed = generatePasswordHash(userInput, salt);
        return inputHashed.equals(hash);
    }
}
