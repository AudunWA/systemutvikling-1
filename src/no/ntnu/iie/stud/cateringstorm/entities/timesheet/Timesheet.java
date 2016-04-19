package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import java.sql.Timestamp;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class Timesheet {
    private int timesheetId;
    private int employeeId;
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean active;
    public Timesheet(int timesheetId, int employeeId, Timestamp startTime, Timestamp endTime, boolean active){
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public int getTimesheetId() {
        return timesheetId;
    }
    public int getEmployeeId() {
        return employeeId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    @Override
    public String toString() {
        return "Timesheet{" +
                "timesheetId=" + timesheetId +
                ", employeeId=" + employeeId +
                ", fromTime=" + startTime +
                ", toTime=" + endTime +
                ", active=" + active +
                '}';
    }
}
