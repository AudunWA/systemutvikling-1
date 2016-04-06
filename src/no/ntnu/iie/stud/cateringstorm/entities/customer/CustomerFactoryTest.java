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

    @Test
    public void testGetAllCustomers() throws Exception {

        for (Customer customer : CustomerFactory.getAllCustomers()){

            System.out.println(customer);

        }
    }

    @Test
    public void testCreateCustomer() throws Exception {

        Customer knut = new Customer(0, "Kirkhorn", "Knut",  "Hornindal", true, 2, "999999","noot@hotmail.com");

        CustomerFactory.createCustomer(knut);

    }

    @Test
    public void testEditCustomer() throws Exception {

        CustomerFactory.editCustomer(9,false);

    }
}