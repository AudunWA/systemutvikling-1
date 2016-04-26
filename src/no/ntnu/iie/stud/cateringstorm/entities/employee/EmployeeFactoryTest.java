package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.encryption.PasswordUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

/**
 * JUnit Test class for EmployeeFactory.
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
        Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Mo", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT, 235.99, 0);
        Assert.assertNotNull(test);

    }

    @Test
    public void testEditEmployeeStatus() throws Exception {
        //Test is initially active
        Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Mo", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT, 235.99, 0);


        int result = EmployeeFactory.editEmployeeStatus(test.getEmployeeId(), false);
        Assert.assertEquals(1, result);
        test = EmployeeFactory.getEmployee(test.getUsername());
        Assert.assertTrue(!test.isActive());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee test = EmployeeFactory.createEmployee(PasswordUtil.generateSalt().substring(0, 30), "testPassword123", "Unit", "Test", "Testaddress", "99999999", "unit@test.com", EmployeeType.NUTRITION_EXPERT, 235.99, 0);
        String newAddress = "New testaddress 9";
        test = new Employee(test.getEmployeeId(), test.getUsername(), test.getForename(), test.getSurname(), newAddress, test.getPhoneNumber(), test.getEmail(), test.getEmployeeType(), test.isActive(), test.getSalary(), test.getCommission());
        int result = EmployeeFactory.updateEmployee(test);
        Assert.assertEquals(1, result);
        test = EmployeeFactory.getEmployee(test.getUsername());
        Assert.assertEquals(newAddress, test.getAddress());
    }

    @Test
    public void testGetSalarySoFar() throws Exception {
        Employee employee = EmployeeFactory.getEmployee("chechter");
        double salary = EmployeeFactory.getSalarySoFar(employee.getEmployeeId(), new Date(System.currentTimeMillis()));
        Assert.assertNotNull(salary);
    }

    //Valid data must be inserted to database. This test fails unless valid orders exist.
    @Test
    public void testGetSalesThisYear() throws Exception {
        Employee employee = EmployeeFactory.getEmployee("drammen");
        int sales = EmployeeFactory.getSalesThisYear(employee.getEmployeeId(), new Date(System.currentTimeMillis()));
        Assert.assertNotNull(sales);
        Assert.assertNotEquals(-1, sales);
    }
}