package no.ntnu.iie.stud.cateringstorm.entities.employee;

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
        Employee employee = EmployeeFactory.newEmployee("nootnoot");
        Assert.assertNotNull(employee);
        System.out.println(employee);

        // Non-existing employee
        employee = EmployeeFactory.newEmployee("nonexisting");
        Assert.assertNull(employee);
        System.out.println(employee);
    }

    @Test
    public void testLogin() throws Exception {
        // Valid credentials
        Employee employee = EmployeeFactory.newEmployee("nootnoot", "testPassword123");
        Assert.assertNotNull(employee);
        System.out.println(employee);

        // Invalid credentials
        employee = EmployeeFactory.newEmployee("nootnoot", "invalidPassword");
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

    @Test
    public void testCreateEmployee() throws Exception {

        Employee test = new Employee(0, "KersjnRobaat", "kristoffer", "aas", "Mo", "99999999", "Kersjn@robåtmail.com", EmployeeType.NUTRITION_EXPERT);

        EmployeeFactory.createEmployee(test);

    }

    @Test
    public void testEditEmployeeStatus() throws Exception {

        EmployeeFactory.editEmployeeStatus(1, false);
    }
}