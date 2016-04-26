package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.encryption.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class handling database interaction, loading and generating Employee entity objects.
 */
public final class EmployeeFactory {

    /**
     * Finds an employee given username and password.
     *
     * @param username
     * @param password
     * @return Employee. if matching username and password
     */
    public static Employee getEmployee(String username, String password) {
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
     *
     * @param username
     * @return Employee
     */
    public static Employee getEmployee(String username) {
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
     *
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
     * Creates an arraylist containing all active employees from the SQL table employee
     *
     * @return Arraylist<Employee>
     */
    public static ArrayList<Employee> getActiveEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE active LIKE TRUE")) {
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
     *
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
        boolean active = result.getBoolean("active");
        double salary = result.getDouble("salary");
        int commission = result.getInt("commission");
        EmployeeType employeeType = EmployeeType.getEmployeeType(employeeTypeId);
        return new Employee(employeeId, username, forename, surname, address, phone, email, employeeType, active,salary,commission);
    }

    /**
     * Inserts an employee into the SQL table employee given an employee object.
     *
     * @return Employee.
     */
    public static Employee createEmployee(String username, String password, String forename,
                                          String surname, String address, String phoneNumber,
                                          String email, EmployeeType type,double salary,int commission) {
        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.generatePasswordHash(password, salt);

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO employee VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, TRUE, ?, ?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, forename);
                statement.setString(3, surname);
                statement.setString(4, address);
                statement.setString(5, phoneNumber);
                statement.setString(6, email);
                statement.setInt(7, type.getType());
                statement.setString(8, hashedPassword);
                statement.setString(9, salt);
                statement.setDouble(10,salary);
                statement.setInt(11,commission);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return null; // No rows inserted
                }

                int generatedId;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }

                Employee employee = new Employee(generatedId, username, forename, surname, address, phoneNumber, email, type, true,salary,commission);
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Edits the status of an employee given the employeeID (if the employee is active or not)
     *
     * @param employeeId
     * @param active
     * @return int
     */
    public static int editEmployeeStatus(int employeeId, boolean active) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE employee SET active = ? WHERE employee.employee_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, employeeId);

                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Returns salary so far selected year for a selected employee.
     * @param employeeId The id of the selected employee
     * @return double
     */
    public static double getSalarySoFar(int employeeId, java.sql.Date year){
        /*SELECT employee_id, SUM(HOUR(TIMEDIFF(DATE_ADD(to_time, INTERVAL 30 MINUTE), from_time))) sum FROM timesheet WHERE year(from_time) = year(CURDATE())
            GROUP BY employee_id;*/

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT employee_id, SUM(HOUR(TIMEDIFF(DATE_ADD(to_time, INTERVAL 30 MINUTE), from_time))) sum " +
                    "FROM timesheet WHERE employee_id = ? AND YEAR(from_time) = YEAR(?)\n" +
                    "GROUP BY employee_id")) {
                statement.setInt(1,employeeId);
                statement.setDate(2,year);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    double paymentSoFar;
                    if(result.next()) {
                        return result.getDouble("sum");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1.;
    }
    /**
     * @param employee The selected employee.
     * @return int.
     */
    public static int updateEmployee(Employee employee) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE employee SET username = ?, forename = ?, surname = ?,address = ?,phone = ?,email = ?, e_type_id = ?, active = ? WHERE employee_id = ?")) {
                statement.setString(1, employee.getUsername());
                statement.setString(2, employee.getForename());
                statement.setString(3, employee.getSurname());
                statement.setString(4, employee.getAddress());
                statement.setString(5, employee.getPhoneNumber());
                statement.setString(6, employee.getEmail());
                statement.setInt(7, employee.getEmployeeType().getType());
                statement.setBoolean(8, employee.isActive());
                statement.setInt(9, employee.getEmployeeId());
                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*SELECT employee_id, COUNT(*) sum FROM _order o JOIN employee e ON(o.salesperson_id = e.employee_id) WHERE year(o.delivery_time) = year(CURDATE()) AND o.status = 2
 GROUP BY employee_id DESC;*/
    /*SELECT employee_id, COUNT(*) sum FROM _order o JOIN employee e ON(o.salesperson_id = e.employee_id) WHERE year(o.delivery_time) = year(CURDATE()) AND o.status = 2 AND employee_id = ?
 GROUP BY employee_id DESC;*/

    /**
     * Returns sales this year by a selected employee. Only completed(delivered) sales
     * are registered. Year can be freely selected.
     * @param employeeId The id of the selected employee.
     * @return int.
     */
    public static int getSalesThisYear(int employeeId, java.sql.Date year){
        int sales = 0;
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT employee_id, COUNT(*) sum FROM _order o JOIN employee e ON(o.salesperson_id = e.employee_id)" +
                    " WHERE year(o.delivery_time) = year(?)" +
                    " AND o.status = 2 AND employee_id = ?\n" +
                    " GROUP BY employee_id DESC LIMIT 1")) {
                statement.setDate(1,year);
                statement.setInt(2, employeeId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        sales = result.getInt("sum");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return sales;
    }
    public static int getCommissionByType(int employeeType){
        int commission = 0;
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT commission FROM employee_type WHERE e_type_id = ?")) {
                statement.setInt(1, employeeType);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        commission = result.getInt("commission");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return commission;
    }

    public static double getSalaryByType(int employeeType){
        double salary = 0.0;
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee_type WHERE e_type_id = ?")) {
                statement.setInt(1, employeeType);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        salary = result.getDouble("salary");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1.;
        }
        return salary;
    }
}
