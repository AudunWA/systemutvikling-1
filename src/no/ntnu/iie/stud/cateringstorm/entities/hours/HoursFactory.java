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
    /**
     * Inserts a time sheet into the SQL table hours. Takes an hours object as arguement
     * @param employeeId,fromTime,toTime,active
     * @return Hours
     */
    public static Hours createTimesheet(int employeeId,Timestamp fromTime, Timestamp toTime, boolean active){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES(DEFAULT,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1,employeeId);
                statement.setTimestamp(2,fromTime);
                statement.setTimestamp(3,toTime);
                statement.setBoolean(4,active);
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


                //statement.execute();
                return new Hours(generatedId,employeeId,fromTime ,toTime,active);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Edits the status of a customer (if the customer is active or not)
     * @param hoursId,employeeId,active
     * @return int
     */
    public static int editTimesheetStatus(int hoursId,int employeeId, boolean active){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE hours SET active = ? WHERE employee_id= ? AND hours_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, employeeId);
                statement.setInt(3,hoursId);
                statement.execute();
                return employeeId;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     *
     * @param timesheet
     * @return statement.executeUpdate
     */
    public static int updateHours(Hours timesheet){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE hours SET from_time = ?, to_time = ?, active = ? WHERE employee_id = ? AND hours_id = ?")) {
                statement.setTimestamp(1,timesheet.getStartTime());
                statement.setTimestamp(2,timesheet.getEndTime());
                statement.setBoolean(3,timesheet.isActive());
                statement.setInt(4,timesheet.getEmployeeId());
                statement.setInt(5,timesheet.getHoursId());
                return statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
