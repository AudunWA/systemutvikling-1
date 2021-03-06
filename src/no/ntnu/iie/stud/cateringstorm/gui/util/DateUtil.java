package no.ntnu.iie.stud.cateringstorm.gui.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Contains utility methods for date and time..
 */
public final class DateUtil {
    public static String[] WEEKDAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * Converts a java.util.Date to java.time.ZonedDateTime.
     *
     * @param date The Date input
     * @return A ZonedDateTime representing the time
     */
    public static ZonedDateTime convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * Creates a string representation of a weekday.
     *
     * @param weekday The day (Monday = 0)
     * @return A String containing the text representation of the day
     */
    public static String formatWeekday(int weekday) {
        if (weekday < 0 || weekday >= WEEKDAYS.length) {
            throw new IllegalArgumentException("weekday has to be between 0 and 6, is " + weekday);
        }

        return WEEKDAYS[weekday];
    }

    /**
     * Creates a Integer representation of the input day.
     *
     * @param weekdayFormatted The day (Monday = 0)
     * @return A Integer representation of the formatted day, or -1 if invalid.
     */
    public static int parseWeekday(String weekdayFormatted) {
        for (int i = 0; i < WEEKDAYS.length; i++) {
            if (WEEKDAYS[i].equals(weekdayFormatted)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Formats our own relative time format to hh:ss.
     *
     * @param relativeTime Seconds after midnight.
     * @return A String containing the formatted time.
     */
    public static String formatRelativeTime(int relativeTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        return formatter.format(Timestamp.valueOf(convertRelativeTime(relativeTime)));
    }

    /**
     * Converts our own relative time format to a LocalDateTime object.
     * The date part is the current date.
     *
     * @param relativeTime Seconds after midnight.
     * @return A LocalDateTime for today, at the given time.
     */
    public static LocalDateTime convertRelativeTime(int relativeTime) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.ofSecondOfDay(relativeTime));
        // int hours = relativeTime/3600;
        // relativeTime -= (hours * 3600);
        // int minutes = relativeTime/60;
        // relativeTime -= (minutes * 60);
        // int seconds = relativeTime;

        // return LocalDate.now().atTime(hours, minutes, seconds);
    }

    /**
     * @param time
     * @return int From Localtime(Hour,Minute,Second) to seconds
     */
    public static int convertToRelativeTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        return hour * 3600 + minute * 60 + second;
    }

    /**
     * Calculates which date the given weekday occurs next time.
     *
     * @param day The weekday to find.
     * @return The next occurrence of the weekday.
     */
    public static LocalDate getNextDateOfDay(DayOfWeek day) {
        return LocalDate.now().with(TemporalAdjusters.next(day));
    }
}
