package no.ntnu.iie.stud.cateringstorm.entities.hours;

import java.sql.Timestamp;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class Hours {
    private int hoursId;
    private int employeeId;
    private Timestamp startTime;
    private Timestamp endTime;

    public Hours(int hoursId,int employeeId, Timestamp startTime, Timestamp endTime){
        this.hoursId = hoursId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getHoursId() {
        return hoursId;
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
}
