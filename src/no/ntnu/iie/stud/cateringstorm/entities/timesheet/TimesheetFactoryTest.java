package no.ntnu.iie.stud.cateringstorm.entities.timesheet;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class TimesheetFactoryTest {

    @Test
    public void testGetAllTimesheets() throws Exception {
        ArrayList<Timesheet> sheets = TimesheetFactory.getAllTimesheets();
        org.junit.Assert.assertNotNull(sheets);
    }

    @Test
    public void testGetTimesheetsByEmployee() throws Exception {
        ArrayList<Timesheet> sheets = TimesheetFactory.getTimesheetsByEmployee(3);
        org.junit.Assert.assertNotNull(sheets);
    }

    @Test
    public void testGetActiveTimesheetsByEmployee() throws Exception {

    }

    @Test
    public void testCreateTimesheet() throws Exception {

    }

    @Test
    public void testEditTimesheetStatus() throws Exception {

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
