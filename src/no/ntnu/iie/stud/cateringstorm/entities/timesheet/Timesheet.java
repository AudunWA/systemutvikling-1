package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/* Expected salaries:
* One year is 1695 hrs
* CEO: 277.29 /hr | 470 000 /yr
* Secretary: 206.49 /hr | 350 000 /yr
* Salesperson: 106.19 /hr | 180 000 + 11% comm / yr
* Chef : 235.99 /hr | 400 000 / yr
* */

/**
 * Java representation of database entity "timesheet".
 */
public class Timesheet {

    private int timesheetId;
    private int employeeId;
    private Timestamp fromTime;
    private Timestamp toTime;
    private boolean active;

    /**
     * Constructs and initializes a time sheet with the specified details
     * @param timesheetId The ID of this time sheet.
     * @param employeeId The ID of the employee owning the time sheet.
     * @param fromTime The start time of the time sheet.
     * @param toTime The end time of the time sheet.
     * @param active Whether the time sheet is active or inactive.
     */
    public Timesheet(int timesheetId, int employeeId, Timestamp fromTime, Timestamp toTime, boolean active) {
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = active;
    }

    /**
     * Returns the id of this time sheet.
     * @return the id of this time sheet.
     */
    public int getTimesheetId() {
        return timesheetId;
    }

    /**
     * Returns the id of the employee.
     * @return the id of the employee.
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns the start time of this time sheet.
     * @return the start time of this time sheet.
     */
    public Timestamp getFromTime() {
        return fromTime;
    }

    /**
     * Sets the start time of the work day.
     * @param fromTime The new start time.
     */
    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * Returns the end time of this time sheet.
     * @return the end time of this time sheet.
     */
    public Timestamp getToTime() {
        return toTime;
    }

    /**
     * Sets the end time of this time sheet.
     * @param toTime The new end time.
     */
    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    /**
     * Returns a string representation of the start time.
     * @return a string representation of the start time.
     */
    public String getFromTimeString() {
        Timestamp time = getFromTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    /**
     * Returns a string representation of the end time.
     * @return a string representation of the end time.
     */
    public String getToTimeString() {
        Timestamp time = getToTime();
        if (time != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(time);
        }
        return "Not registered yet";
    }

    /**
     * Determines whether the employee is active. An inactive employee is practically considered a deleted one.
     * @return true if the employee is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates or inactivates this employee. An inactive employee is practically considered a deleted one.
     * @param active If true, this employee is active, otherwise this employee is inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns a string representation of this time sheet.
     * @return a string representation of this time sheet.
     */
    @Override
    public String toString() {
        return "Timesheet{" +
                "timesheetId=" + timesheetId +
                ", employeeId=" + employeeId +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", active=" + active +
                '}';
    }
}
