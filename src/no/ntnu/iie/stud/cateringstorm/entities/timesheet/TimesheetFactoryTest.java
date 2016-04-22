package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        for (int i = 0; i < sheets.size(); i++) {
            Assert.assertTrue(sheets.get(i).isActive());
        }
    }

    @Test
    public void testCreateTimesheet() throws Exception {
        Timesheet sheet = new Timesheet(100, 3, new Timestamp(System.currentTimeMillis() - 10), new Timestamp(System.currentTimeMillis()), true);
        Timesheet createdSheet = TimesheetFactory.createTimesheet(sheet.getEmployeeId(), sheet.getFromTime(), sheet.getToTime(), sheet.isActive());
        Assert.assertNotNull(createdSheet);
        //Compare sheets
        Assert.assertEquals(createdSheet.getFromTime(), sheet.getFromTime());
        Assert.assertEquals(createdSheet.getEmployeeId(), sheet.getEmployeeId());
    }

    @Test
    public void testEditTimesheetStatus() throws Exception {
        Timesheet sheet = new Timesheet(100, 3, new Timestamp(System.currentTimeMillis() - 10), new Timestamp(System.currentTimeMillis()), true);
        sheet = TimesheetFactory.createTimesheet(sheet.getEmployeeId(), sheet.getFromTime(), sheet.getToTime(), sheet.isActive());
        int result = TimesheetFactory.editTimesheetStatus(sheet.getTimesheetId(), sheet.getEmployeeId(), false);
        Assert.assertEquals(1, result);
    }

    /*@Test
    public void testGetUnfinishedTimeSheet() throws Exception {
        Timesheet sheet = TimesheetFactory.createTimesheet(3,new Timestamp(System.currentTimeMillis()-10),true),
        unfinishedSheet = TimesheetFactory.getUnfinishedTimeSheet(sheet.getEmployeeId());
        Assert.assertNotNull(unfinishedSheet);
        Assert.assertEquals(sheet.getTimesheetId(),unfinishedSheet.getTimesheetId());
        Assert.assertEquals(sheet.getEmployeeId(),unfinishedSheet.getEmployeeId());
    }*/

    @Test
    public void testGetLatestTimeSheet() throws Exception {
        LocalDateTime testDate = LocalDateTime.now().withNano(0);
        Timesheet sheet = TimesheetFactory.createTimesheet(3, Timestamp.valueOf(testDate), Timestamp.valueOf(testDate.plusSeconds(1)), true),
                latestTimesheet = TimesheetFactory.getLatestTimeSheet(sheet.getEmployeeId());
        Assert.assertNotNull(latestTimesheet);
        Assert.assertEquals(sheet.getEmployeeId(), latestTimesheet.getEmployeeId());
        Assert.assertEquals(sheet.getFromTime(), latestTimesheet.getFromTime());
    }

    @Test
    public void testUpdateTimesheet() throws Exception {
        Timesheet sheet = TimesheetFactory.createTimesheet(3, new Timestamp(System.currentTimeMillis() - 1), new Timestamp(System.currentTimeMillis()), true);
        sheet = TimesheetFactory.getLatestTimeSheet(sheet.getEmployeeId());
        Timestamp newFromTime = new Timestamp(System.currentTimeMillis());
        sheet.setFromTime(newFromTime);
        int result = TimesheetFactory.updateTimesheet(sheet);
        Assert.assertEquals(1, result);
        sheet = TimesheetFactory.getLatestTimeSheet(sheet.getEmployeeId());
        Assert.assertNotEquals(newFromTime, sheet.getFromTime());
    }
}
