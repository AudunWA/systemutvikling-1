package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.encryption.PasswordUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Audun on 11.03.2016.
 */
public class EmployeeFactoryTest {


    @Test
    public void testNewEmployee() throws Exception {
        // Existing employee
        Employee employee = EmployeeFactory.getEmployee("nootnoot");
        Assert.assertNotNull(employee);
        System.out.println(employee);

        // Non-existing employee
        employee = EmployeeFactory.getEmployee("nonexisting");
        Assert.assertNull(employee);
        System.out.println(employee);
    }

    @Test
    public void testLogin() throws Exception {
        // Valid credentials
        Employee employee = EmployeeFactory.getEmployee("nootnoot", "testPassword123");
        Assert.assertNotNull(employee);
        System.out.println(employee);

        // Invalid credentials
        employee = EmployeeFactory.getEmployee("nootnoot", "invalidPassword");
        Assert.assertNull(employee);
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        ArrayList<Employee> employees = EmployeeFactory.getAllEmployees();
        Assert.assertNotNull(employees);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    /**
     * Generates a new employee with a random username
     *
     * @throws Exception
     */
    @Test
    public void testCreateEmployee() throws Exception {
        //Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Mo", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT);
        //Assert.assertNotNull(test);
    }

    /*@Test
    public void testEditEmployeeStatus() throws Exception {
        Test is initially active
        Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Mo", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT);

        int result = EmployeeFactory.editEmployeeStatus(test.getEmployeeId(), false);
        Assert.assertEquals(result, 1);
        test = EmployeeFactory.getEmployee(test.getUsername());
        Assert.assertTrue(!test.isActive());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Testaddress", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT);
        String newAddress = "New testaddress 9";
        test = new Employee(test.getEmployeeId(), test.getUsername(), test.getForename(), test.getSurname(), newAddress, test.getPhoneNumber(), test.getEmail(), test.getEmployeeType(), test.isActive());
        int result = EmployeeFactory.updateEmployee(test);
        Assert.assertEquals(1, result);
        test = EmployeeFactory.getEmployee(test.getUsername());
        Assert.assertEquals(test.getAddress(), newAddress);
    }*/
}