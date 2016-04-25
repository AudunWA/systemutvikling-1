package no.ntnu.iie.stud.cateringstorm.util;

import jdk.internal.util.xml.impl.Input;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contains unit tests for InputUtil.
 */
public class InputUtilTest {
    @Test
    public void testIsValidEmail() throws Exception {
        Assert.assertTrue(InputUtil.isValidEmail("test@gmail.com"));
        Assert.assertFalse(InputUtil.isValidEmail("test.gmail.com"));
    }

    @Test
    public void testIsValidPhoneNumber() throws Exception {
        Assert.assertTrue(InputUtil.isValidPhoneNumber("12345678"));
        Assert.assertFalse(InputUtil.isValidPhoneNumber("a12345678"));
    }

    @Test
    public void testIsValidStreetAddress() throws Exception {
        Assert.assertTrue(InputUtil.isValidStreetAddress("Kongens gate 1"));
        Assert.assertFalse(InputUtil.isValidStreetAddress("Definitely not valid address!"));
    }
}