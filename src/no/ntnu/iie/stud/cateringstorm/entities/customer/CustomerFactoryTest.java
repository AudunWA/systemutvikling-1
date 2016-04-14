package no.ntnu.iie.stud.cateringstorm.entities.customer;

import org.junit.Test;

/**
 * Created by Chris on 17.03.2016.
 */
public class CustomerFactoryTest {

    @Test
    public void testViewSingleCustomer() throws Exception {

        System.out.println(CustomerFactory.getCustomer(1).getForename());

    }

    @Test
    public void testGetAllCustomers() throws Exception {

        for (Customer customer : CustomerFactory.getAllCustomers()){

            System.out.println(customer);

        }
    }
    @Test
    public void testGetActiveCustomers() throws Exception {
        for(Customer c :CustomerFactory.getActiveCustomers()){
            System.out.println(c);
        }
    }
    @Test
    public void testCreateCustomer() throws Exception {

        CustomerFactory.createCustomer("Kirkhorn", "Knut",  "Hornindal", true, "999999","noot@hotmail.com");

    }

    @Test
    public void testEditCustomerStatus() throws Exception {

        CustomerFactory.editCustomerStatus(9,false);

    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerFactory.updateCustomer(new Customer(0, "Kirkhorn", "Knut",  "Hornindal", true, "999999","noot@hotmail.com"));
    }
}