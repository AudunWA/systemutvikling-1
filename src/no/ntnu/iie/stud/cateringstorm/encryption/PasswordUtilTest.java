package no.ntnu.iie.stud.cateringstorm.encryption;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Audun on 10.03.2016.
 * All methods: @throws Exception
 */
public class PasswordUtilTest {

    @Test
    public void testGenerateSalt() throws Exception {
        Assert.assertEquals(PasswordUtil.generateSalt().length(), 32);
    }

    @Test
    public void testGeneratePasswordHash() throws Exception {
        // All valid
        Assert.assertEquals(PasswordUtil.generatePasswordHash("testPassword123", "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh"), "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e");
        // Wrong password
        Assert.assertNotEquals(PasswordUtil.generatePasswordHash("testpassword123", "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh"), "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e");
        // Wrong salt
        Assert.assertNotEquals(PasswordUtil.generatePasswordHash("testPassword123", "AWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh"), "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e");
    }

    @Test
    public void testVerifyPassword() throws Exception {
        // All valid
        Assert.assertTrue(PasswordUtil.verifyPassword("testPassword123", "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh", "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e"));
        // Wrong password
        Assert.assertFalse(PasswordUtil.verifyPassword("testpassword123", "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh", "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e"));
        // Wrong salt
        Assert.assertFalse(PasswordUtil.verifyPassword("testPassword123", "AWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh", "dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e"));
    }
}