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
     * @param timesheetId
     * @param employeeId
     * @param fromTime
     * @param toTime
     * @param active
     */
    public Timesheet(int timesheetId, int employeeId, Timestamp fromTime, Timestamp toTime, boolean active) {
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = active;
    }

    /**
     *
     * @return
     */
    public int getTimesheetId() {
        return timesheetId;
    }

    /**
     *
     * @return
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @return
     */
    public Timestamp getFromTime() {
        return fromTime;
    }

    /**
     *
     * @param fromTime
     */
    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    /**
     *
     * @return
     */
    public Timestamp getToTime() {
        return toTime;
    }

    /**
     *
     * @param toTime
     */
    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    /**
     *
     * @return
     */
    public String getFromTimeString() {
        Timestamp time = getFromTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
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
