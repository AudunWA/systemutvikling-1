package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public final class TimesheetFactory {
    private Employee thisEmployee = GlobalStorage.getLoggedInEmployee(); //Access to the respective employee

    /**
     * Creates customer from result.
     *
     * @param result
     * @return Timesheet
     * @throws SQLException
     */
    private static Timesheet createTimesheetFromResultSet(ResultSet result) throws SQLException {

        int timesheetId = result.getInt("timesheet_id");
        int employeeId = result.getInt("employee_id");
        Timestamp fromTime = result.getTimestamp("from_time");
        Timestamp toTime = result.getTimestamp("to_time");
        boolean active = result.getBoolean("active");
        return new Timesheet(timesheetId, employeeId, fromTime, toTime, active);

    }

    /**
     * Creates an arraylist containing Timesheet
     *
     * @return ArrayList<Timesheet>
     */
    public static ArrayList<Timesheet> getAllTimesheets() {
        // TODO: Implement method gathering all timesheet from table, only where employee ID equals this ID
        ArrayList<Timesheet> timesheetList = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM timesheet ORDER BY from_time")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        timesheetList.add(createTimesheetFromResultSet(result));
                    }
                    return timesheetList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all timesheet from a single employee
     *
     * @param employeeId
     * @return Customer
     */
    public static ArrayList<Timesheet> getTimesheetsByEmployee(int employeeId) {

        ArrayList<Timesheet> timesheetList = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM timesheet WHERE employee_id = ? ORDER BY from_time")) {
                statement.setInt(1, employeeId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        timesheetList.add(createTimesheetFromResultSet(result));
                    }
                    return timesheetList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Returns customers with column "Active" = true, on one employee
     * @return ArrayList<Timesheet>
     */
    public static ArrayList<Timesheet> getActiveTimesheetsByEmployee(int employeeId){
        ArrayList<Timesheet> hours = new ArrayList<>();
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM timesheet WHERE active LIKE true AND employee_id = ? ORDER BY from_time")){
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
     * Inserts a time sheet into the SQL table timesheet. Takes an timesheet object as arguement
     * @param employeeId,fromTime,toTime,active
     * @return Timesheet
     */
    public static Timesheet createTimesheet(int employeeId, Timestamp fromTime,  boolean active){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO timesheet VALUES(DEFAULT,?,?,NULL,?)", PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1,employeeId);
                statement.setTimestamp(2,fromTime);
                statement.setBoolean(3,active);
                statement.execute();

                int generatedId;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }
                //statement.execute();
                return new Timesheet(generatedId,employeeId,fromTime ,null,active);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Inserts a time sheet into the SQL table timesheet. Takes an timesheet object as arguement
     * @param employeeId,fromTime,toTime,active
     * @return Timesheet
     */
    public static Timesheet createTimesheet(int employeeId, Timestamp fromTime,  Timestamp toTime, boolean active){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO timesheet VALUES(DEFAULT,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1,employeeId);
                statement.setTimestamp(2,fromTime);
                statement.setTimestamp(3,toTime);
                statement.setBoolean(4,active);
                statement.execute();

                int generatedId;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }
                //statement.execute();
                return new Timesheet(generatedId,employeeId,fromTime ,null,active);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Edits the status of a customer (if the customer is active or not)
     * @param timesheetId,employeeId,active
     * @return int
     */
    public static int editTimesheetStatus(int timesheetId,int employeeId, boolean active){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE timesheet SET active = ? WHERE employee_id= ? AND timesheet_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, employeeId);
                statement.setInt(3,timesheetId);
                statement.execute();
                return employeeId;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public static Timesheet getUnfinishedTimeSheet(int employeeId){
        Timesheet sheet;
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM timesheet WHERE active LIKE true AND employee_id = ? AND to_time IS NULL ORDER BY from_time")){
                statement.setInt(1,employeeId);
                statement.executeQuery();
                try (ResultSet result = statement.getResultSet()){
                    if(result.next()) {
                        sheet = createTimesheetFromResultSet(result);
                        return sheet;
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Timesheet getLatestTimeSheet(int employeeId){
        Timesheet sheet;
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM timesheet WHERE active LIKE true AND employee_id = ? ORDER BY from_time DESC LIMIT 1")){
                statement.setInt(1,employeeId);
                statement.executeQuery();
                try (ResultSet result = statement.getResultSet()){
                    if(result.next()) {
                        sheet = createTimesheetFromResultSet(result);
                        return sheet;
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param timesheet
     * @return statement.executeUpdate
     */
    public static int updateTimesheet(Timesheet timesheet){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE timesheet SET from_time = ?, to_time = ?, active = ? WHERE employee_id = ? AND timesheet_id = ?")) {
                statement.setTimestamp(1,timesheet.getFromTime());
                statement.setTimestamp(2,timesheet.getToTime());
                statement.setBoolean(3,timesheet.isActive());
                statement.setInt(4,timesheet.getEmployeeId());
                statement.setInt(5,timesheet.getTimesheetId());
                return statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
