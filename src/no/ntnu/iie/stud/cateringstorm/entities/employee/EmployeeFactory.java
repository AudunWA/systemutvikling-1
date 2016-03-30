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
                        if (correct) {
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
     * @return Employee
     */
    public static Employee createEmployee(String username, String password, String forename,
                                          String surname, String address, String phoneNumber,
                                          String email, EmployeeType type) {
        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.generatePasswordHash(password, salt);

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO employee VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, TRUE, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, forename);
                statement.setString(3, surname);
                statement.setString(4, address);
                statement.setString(5, phoneNumber);
                statement.setString(6, email);
                statement.setInt(7, type.getType());
                statement.setString(8, hashedPassword);
                statement.setString(9, salt);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return null; // No rows inserted
                }

                int generatedID;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedID = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }

                Employee employee = new Employee(generatedID, username, forename, surname, address, phoneNumber, email, type);
                return employee;
            }
        } catch (SQLException e) {
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
            try (PreparedStatement statement = connection.prepareStatement("UPDATE employee SET active = ? WHERE employee.employee_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, employeeId);

                statement.execute();
                return employeeId;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
