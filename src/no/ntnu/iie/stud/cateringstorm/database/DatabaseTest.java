package no.ntnu.iie.stud.cateringstorm.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Audun on 10.03.2016.
 */
public class DatabaseTest {

    @Test
    public void testGetConnection() throws Exception {
        Database.getConnection();
    }
}