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
}
