package no.ntnu.iie.stud.cateringstorm.entities.customer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Chris on 17.03.2016.
 */
public class CustomerFactoryTest {

    @Test
    public void testViewSingleCustomer() throws Exception {
        Customer customer = CustomerFactory.createCustomer("Kirkhorn", "Knut",  "Hornindal", true, "999999","noot@hotmail.com"),
        newCustomer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertNotNull(newCustomer);
        Assert.assertEquals(customer.getCustomerId(),newCustomer.getCustomerId());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        ArrayList<Customer> list = CustomerFactory.getAllCustomers();
        Assert.assertNotNull(list);
        for (Customer customer : list){

            System.out.println(customer);

        }
    }
    @Test
    public void testGetCustomersByQuery()throws Exception {
        ArrayList<Customer> list = CustomerFactory.getCustomersByQuery("test");
        Assert.assertNotNull(list);
        for(Customer c: list){
            System.out.println(c);
        }
    }
    @Test
    public void testGetActiveCustomers() throws Exception {
        ArrayList<Customer> list = CustomerFactory.getActiveCustomers();
        Assert.assertNotNull(list);
        for(Customer c : list){
            System.out.println(c);
        }
    }
    @Test
    public void testCreateCustomer() throws Exception {

       Customer customer= CustomerFactory.createCustomer("Kirkhorn", "Knut",  "Hornindal", true, "999999","noot@hotmail.com");
        Assert.assertNotNull(customer);
    }

    @Test
    public void testEditCustomerStatus() throws Exception {
        Customer customer = CustomerFactory.createCustomer("Kirkhorn", "Knut Sr.",  "Hornindal", true, "999999","noot@hotmail.com");
        CustomerFactory.editCustomerStatus(customer.getCustomerId(),false);
        customer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertEquals(customer.isActive(), false);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String text = "Knut Jr.";
        Customer customer = CustomerFactory.createCustomer("Kirkhorn", "Knut Sr.",  "Hornindal", true, "999999","noot@hotmail.com");
        customer.setForename(text);
        CustomerFactory.updateCustomer(customer);
        customer = CustomerFactory.getCustomer(customer.getCustomerId());
        Assert.assertEquals(customer.getForename(), text);

    }
}