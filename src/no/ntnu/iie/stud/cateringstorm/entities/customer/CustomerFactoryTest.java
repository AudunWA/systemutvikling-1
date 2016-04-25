package no.ntnu.iie.stud.cateringstorm.entities.customer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * All methods: @throws Exception
 */
public class CustomerFactoryTest {

    @Test
    public void testViewSingleCustomer() throws Exception {
        Customer customer = CustomerFactory.createCustomer("Knut", "Kirkhorn", "Hornindal", true, "999999", "noot@hotmail.com"),
                newCustomer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertNotNull(newCustomer);
        Assert.assertEquals(customer.getCustomerId(), newCustomer.getCustomerId());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        ArrayList<Customer> list = CustomerFactory.getAllCustomers();
        Assert.assertNotNull(list);
    }

    @Test
    public void testGetCustomersByQuery() throws Exception {
        ArrayList<Customer> list = CustomerFactory.getCustomersByQuery("test");
        Assert.assertNotNull(list);
    }

    @Test
    public void testGetActiveCustomers() throws Exception {
        ArrayList<Customer> list = CustomerFactory.getActiveCustomers();
        Assert.assertNotNull(list);
        for (int i = 0; i < list.size(); i++) {
            Assert.assertTrue(list.get(i).isActive());
        }
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(1, "Knut", "Kirkhorn", "Hornindal", true, "999999", "noot@hotmail.com");
        Customer testCustomer = CustomerFactory.createCustomer(customer.getForename(), customer.getSurname(), customer.getAddress(), customer.isActive(), customer.getPhone(), customer.getEmail());
        Assert.assertNotNull(testCustomer);
        Assert.assertEquals(customer.getForename(), testCustomer.getForename());
        Assert.assertEquals(customer.getEmail(), testCustomer.getEmail());
    }

    @Test
    public void testEditCustomerStatus() throws Exception {
        Customer customer = CustomerFactory.createCustomer("Knut Sr.", "Kirkhorn", "Hornindal", true, "999999", "noot@hotmail.com");
        CustomerFactory.editCustomerStatus(customer.getCustomerId(), false);
        customer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertFalse(customer.isActive());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String text = "Knut Jr.";
        Customer customer = CustomerFactory.createCustomer("Knut Sr.", "Kirkhorn", "Hornindal", true, "999999", "noot@hotmail.com");
        customer.setForename(text);
        CustomerFactory.updateCustomer(customer);
        customer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertEquals(customer.getForename(), text);

    }
}