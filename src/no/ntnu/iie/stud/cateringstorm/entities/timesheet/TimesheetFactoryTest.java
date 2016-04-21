package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class TimesheetFactoryTest {

    @Test
    public void testGetAllTimesheets() throws Exception {
        ArrayList<Timesheet> sheets = TimesheetFactory.getAllTimesheets();
        Assert.assertNotNull(sheets);
    }

    @Test
    public void testGetTimesheetsByEmployee() throws Exception {
        ArrayList<Timesheet> sheets = TimesheetFactory.getTimesheetsByEmployee(3);
        Assert.assertNotNull(sheets);
    }

    @Test
    public void testGetActiveTimesheetsByEmployee() throws Exception {
        ArrayList<Timesheet> sheets = TimesheetFactory.getActiveTimesheetsByEmployee(3);
        Assert.assertNotNull(sheets);
        for (int i = 0; i < sheets.size() ; i++) {
            Assert.assertEquals(sheets.get(i).isActive(),true);
        }
    }

    @Test
    public void testCreateTimesheet() throws Exception {
        Timesheet sheet = new Timesheet(100,3,new Timestamp(System.currentTimeMillis()-10),new Timestamp(System.currentTimeMillis()),true);
        Timesheet createdSheet = TimesheetFactory.createTimesheet(sheet.getEmployeeId(),sheet.getFromTime(),sheet.getToTime(),sheet.isActive());
        Assert.assertNotNull(createdSheet);
        //Compare sheets
        Assert.assertEquals(createdSheet.getFromTime(),sheet.getFromTime());
        Assert.assertEquals(createdSheet.getEmployeeId(),sheet.getEmployeeId());
    }

    @Test
    public void testEditTimesheetStatus() throws Exception {
        Timesheet sheet = new Timesheet(100,3,new Timestamp(System.currentTimeMillis()-10),new Timestamp(System.currentTimeMillis()),true);

    }

    @Test
    public void testGetUnfinishedTimeSheet() throws Exception {

    }

    @Test
    public void testGetLatestTimeSheet() throws Exception {

    }

    @Test
    public void testUpdateTimesheet() throws Exception {

    }
}
