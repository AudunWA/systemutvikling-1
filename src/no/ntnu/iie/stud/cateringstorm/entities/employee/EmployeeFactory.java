package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.encryption.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles all loading and generation of employees
 * Created by Audun on 10.03.2016.
 */
public final class EmployeeFactory {

    /**
     * Finds an employee given username and password.
     * @param username
     * @param password
     * @return Employee. if matching username and password
     */
    public static Employee newEmployee(String username, String password) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE username = ?")) {
                statement.setString(1, username);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        String correctPassword = result.getString("password");
                        String salt = result.getString("salt");

                        // Check if the password is correct
                        Boolean correct = PasswordUtil.verifyPassword(password, salt, correctPassword);
                        if(correct) {
                            return createEmployeeFromResultSet(result);
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Shows an employee given a username
     * @param username
     * @return Employee
     */
    public static Employee newEmployee(String username) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE username = ?")) {
                statement.setString(1, username);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        return createEmployeeFromResultSet(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an arraylist containing all employees from the SQL table employee
     * @return Arraylist<Employee>
     */
    public static ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        employees.add(createEmployeeFromResultSet(result));
                    }
                }
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an employee from result
     * @param result
     * @return Employee
     * @throws SQLException
     */
    private static Employee createEmployeeFromResultSet(ResultSet result) throws SQLException {
        int employeeId = result.getInt("employee_id");
        int employeeTypeId = result.getInt("e_type_id");
        String username = result.getString("username");
        String forename = result.getString("forename");
        String surname = result.getString("surname");
        String address = result.getString("address");
        String phone = result.getString("phone");
        String email = result.getString("email");
        EmployeeType employeeType = EmployeeType.getEmployeeType(employeeTypeId);
        return new Employee(employeeId, username, forename, surname, address, phone, email, employeeType);
    }

    /**
     * Inserts an employee into the SQL table employee given an employee object
     * @param newEmployee
     * @return Employee
     */
    public static Employee createEmployee(Employee newEmployee){

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, 1, ' ', ' ')")) {

                statement.setInt(1, newEmployee.getEmployeeId());
                statement.setString(2, newEmployee.getUsername());
                statement.setString(3, newEmployee.getForename());
                statement.setString(4, newEmployee.getSurname());
                statement.setString(5, newEmployee.getAddress());
                statement.setString(6, newEmployee.getPhoneNumber());
                statement.setString(7, newEmployee.getEmail());
                statement.setInt(8, newEmployee.getEmployeeType().getType());

                statement.execute();
                return newEmployee;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Edits the status of an employee given the employeeID (if the employee is active or not)
     * @param employeeId
     * @param active
     * @return int
     */
    public static int editEmployeeStatus(int employeeId, boolean active){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE g_tdat1006_t6.employee SET active = ? WHERE employee.employee_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, employeeId);

                statement.execute();
                return employeeId;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
