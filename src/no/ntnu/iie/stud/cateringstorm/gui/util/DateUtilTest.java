package no.ntnu.iie.stud.cateringstorm.gui.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Audun on 21.04.2016.
 */
public class DateUtilTest {
    @Test
    public void testFormatRelativeTime() throws Exception {
        int testData = 13 * 3600 + 42 * 60 + 54; // 13:42
        String output = DateUtil.formatRelativeTime(testData);
        Assert.assertEquals("13:42", output);
    }

    @Test
    public void testConvertRelativeTime() throws Exception {
        int testData = 13 * 3600 + 42 * 60 + 54; // 13:42:54
        LocalDateTime time = DateUtil.convertRelativeTime(testData);
        Assert.assertEquals(13, time.getHour());
        Assert.assertEquals(42, time.getMinute());
        Assert.assertEquals(54, time.getSecond());
    }

    @Test
    public void testConvertToRelativeTime() throws Exception {
        int testData = 13 * 3600 + 42 * 60 + 54; // 13:42:54

        LocalTime time = LocalTime.ofSecondOfDay(testData);
        Assert.assertEquals(testData, DateUtil.convertToRelativeTime(time));
    }
}