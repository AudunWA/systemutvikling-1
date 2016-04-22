package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class Timesheet {
    private int timesheetId;
    private int employeeId;
    private Timestamp fromTime;
    private Timestamp toTime;
    private boolean active;

    public Timesheet(int timesheetId, int employeeId, Timestamp fromTime, Timestamp toTime, boolean active) {
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.active = active;
    }

    public int getTimesheetId() {
        return timesheetId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    public String getFromTimeString() {
        Timestamp time = getFromTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    public String getToTimeString() {
        Timestamp time = getToTime();
        if (time != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(time);
        }
        return "Not registered yet";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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
