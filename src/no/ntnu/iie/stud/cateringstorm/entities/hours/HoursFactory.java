package no.ntnu.iie.stud.cateringstorm.entities.hours;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public final class HoursFactory {
    private Employee thisEmployee = GlobalStorage.getLoggedInEmployee(); //Access to the respective employee

    /**
     * Creates customer from result.
     *
     * @param result
     * @return Hours
     * @throws SQLException
     */
    private static Hours createTimesheetFromResultSet(ResultSet result) throws SQLException {

        int hoursId = result.getInt("hours_id");
        int employeeId = result.getInt("employee_id");
        Timestamp fromTime = result.getTimestamp("from_time");
        Timestamp toTime = result.getTimestamp("to_time");
        boolean active = result.getBoolean("active");
        return new Hours(hoursId, employeeId, fromTime, toTime, active);

    }

    /**
     * Creates an arraylist containing Hours
     *
     * @return ArrayList<Hours>
     */
    public static ArrayList<Hours> getAllHours() {
        // TODO: Implement method gathering all hours from table, only where employee ID equals this ID
        ArrayList<Hours> hoursList = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM hours")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        hoursList.add(createTimesheetFromResultSet(result));
                    }
                    return hoursList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        /*ArrayList<Hours> dummy = new ArrayList<>();
        dummy.add(new Hours(1,1,new Timestamp(1460713371),new Timestamp(1460714371),true));
        return dummy;*/
    }

    /**
     * Gets all hours from a single employee
     *
     * @param employeeId
     * @return Customer
     */
    public static ArrayList<Hours> getHoursByEmployee(int employeeId) {

        ArrayList<Hours> hoursList = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM hours WHERE employee_id = ?")) {
                statement.setInt(1, employeeId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        hoursList.add(createTimesheetFromResultSet(result));
                    }
                    return hoursList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Returns customers with column "Active" = true, on one employee
     * @return ArrayList<Customer>
     */
    public static ArrayList<Hours> getActiveHoursByEmployee(int employeeId){
        ArrayList<Hours> hours = new ArrayList<>();
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM hours WHERE active LIKE true AND employee_id = ?")){
                statement.setInt(1,employeeId);
                statement.executeQuery();
                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){

                        hours.add(createTimesheetFromResultSet(result));
                    }
                    return hours;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
