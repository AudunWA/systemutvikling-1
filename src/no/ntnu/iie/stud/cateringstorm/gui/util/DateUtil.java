package no.ntnu.iie.stud.cateringstorm.gui.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Audun on 14.04.2016.
 */
public final class DateUtil {
    /**
     Converts a java.util.Date to java.time.LocalDate
     * @param date The Date input
     * @return A LocalDate representing the Date
     */
    public static final LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
