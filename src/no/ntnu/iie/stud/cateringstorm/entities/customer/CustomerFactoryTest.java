package no.ntnu.iie.stud.cateringstorm.entities.customer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 17.03.2016.
 */
public class CustomerFactoryTest {

    @Test
    public void testViewSingleCustomer() throws Exception {

        System.out.println(CustomerFactory.viewSingleCustomer(1));

    }
}