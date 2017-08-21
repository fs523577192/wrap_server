package org.firas.common.helper;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * java.util.Date
 * http://docs.oracle.com/javase/8/docs/api/java/util/Date.html
 *
 * java.util.Calendar
 * http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html
 */
public class DateTimeHelper {

    public static final String YEAR_REGEX = "(\\d{4})",
            SHORT_MONTH_REGEX = "([1-9]|1[012])",
            MONTH_REGEX = "(0?[1-9]|1[012])",
            FULL_MONTH_REGEX = "(0[1-9]|1[012])",
            SHORT_DATE_REGEX = "([1-9]|[12]\\d|3[01])",
            DATE_REGEX = "(0?[1-9]|[12]\\d|3[01])",
            FULL_DATE_REGEX = "(0[1-9]|[12]\\d|3[01])",
            SHORT_HOUR_REGEX = "(1?\\d|2[0-3])",
            HOUR_REGEX = "([01]?\\d|2[0-3])",
            FULL_HOUR_REGEX = "([01]\\d|2[0-3])",
            MINUTE_OR_SECOND_REGEX = "([0-5]\\d)",
            ISO_DATE_REGEX = YEAR_REGEX + '-' + FULL_MONTH_REGEX + '-' + FULL_DATE_REGEX,
            ISO_TIME_REGEX = FULL_HOUR_REGEX + ':' + MINUTE_OR_SECOND_REGEX + ':' + MINUTE_OR_SECOND_REGEX;

    public static final String DATE_PATTERN = "yyyy-MM-dd",
            TIME_PATTERN = "HH:mm:ss",
            DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN,
            DATETIME_PATTERN_WITH_ZONE = DATE_PATTERN + " " + TIME_PATTERN + "XXX",
            DATETIME_PATTERN_WITH_T = DATE_PATTERN + "'T'" + TIME_PATTERN,
            DATETIME_PATTERN_WITH_T_AND_ZONE = DATE_PATTERN + "'T'" + TIME_PATTERN + "XXX";

    public static DateFormat getDateFormatter() {
        return new SimpleDateFormat(DATE_PATTERN);
    }
    public static DateFormat getTimeFormatter() {
        return new SimpleDateFormat(TIME_PATTERN);
    }
    public static DateFormat getDateTimeFormatter() {
        return new SimpleDateFormat(DATETIME_PATTERN);
    }
    public static DateFormat getDateTimeFormatterWithZone() {
        return new SimpleDateFormat(DATETIME_PATTERN_WITH_ZONE);
    }
    public static DateFormat getDateTimeFormatterWithT() {
        return new SimpleDateFormat(DATETIME_PATTERN_WITH_T);
    }
    public static DateFormat getDateTimeFormatterWithTAndZone() {
        return new SimpleDateFormat(DATETIME_PATTERN_WITH_T_AND_ZONE);
    }


    public static Calendar cloneCalendar(Calendar c) {
        return (Calendar)c.clone();
    }

    public static Date cloneDate(Date d) {
        return (Date)d.clone();
    }

    public static Calendar getCalendarFromTime(long time) {
        return (new Calendar.Builder()).setInstant(time).build();
    }

    public static Calendar getCalendarFromDate(Date date) {
        return (new Calendar.Builder()).setInstant(date).build();
    }


    public static Calendar dayBegin(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.HOUR_OF_DAY, time.getActualMinimum(
                Calendar.HOUR_OF_DAY));
        time.set(Calendar.MINUTE, time.getActualMinimum(
                Calendar.MINUTE));
        time.set(Calendar.SECOND, time.getActualMinimum(
                Calendar.SECOND));
        time.set(Calendar.MILLISECOND, time.getActualMinimum(
                Calendar.MILLISECOND));
        return time;
    }

    public static Calendar dayBegin(Date time) {
        if (null != time) return dayBegin(Calendar.getInstance());
        return dayBegin(getCalendarFromDate(time));
    }

    public static Calendar dayBegin(Long time) {
        if (null != time) return dayBegin(Calendar.getInstance());
        return dayBegin(getCalendarFromTime(time));
    }


    public static Calendar dayEnd(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.HOUR_OF_DAY, time.getActualMaximum(
                Calendar.HOUR_OF_DAY));
        time.set(Calendar.MINUTE, time.getActualMaximum(
                Calendar.MINUTE));
        time.set(Calendar.SECOND, time.getActualMaximum(
                Calendar.SECOND));
        time.set(Calendar.MILLISECOND, time.getActualMaximum(
                Calendar.MILLISECOND));
        return time;
    }

    public static Calendar dayEnd(Date time) {
        if (null == time) return dayEnd(Calendar.getInstance());
        return dayEnd(getCalendarFromDate(time));
    }

    public static Calendar dayEnd(Long time) {
        if (null == time) return dayEnd(Calendar.getInstance());
        return dayEnd(getCalendarFromTime(time));
    }


    public static Calendar weekBegin(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.DAY_OF_WEEK, time.getActualMinimum(
                Calendar.DAY_OF_WEEK));
        return dayBegin(time);
    }

    public static Calendar weekBegin(Date time) {
        if (null == time) return weekBegin(Calendar.getInstance());
        return weekBegin(getCalendarFromDate(time));
    }

    public static Calendar weekBegin(Long time) {
        if (null == time) return weekBegin(Calendar.getInstance());
        return weekBegin(getCalendarFromTime(time));
    }


    public static Calendar weekEnd(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.DAY_OF_WEEK, time.getActualMaximum(
                Calendar.DAY_OF_WEEK));
        return dayEnd(time);
    }

    public static Calendar weekEnd(Date time) {
        if (null == time) return weekEnd(Calendar.getInstance());
        return weekEnd(getCalendarFromDate(time));
    }

    public static Calendar weekEnd(Long time) {
        if (null == time) return weekEnd(Calendar.getInstance());
        return weekEnd(getCalendarFromTime(time));
    }


    public static Calendar monthBegin(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.DAY_OF_MONTH, time.getActualMinimum(
                Calendar.DAY_OF_MONTH));
        return dayBegin(time);
    }

    public static Calendar monthBegin(Date time) {
        if (null == time) return monthBegin(Calendar.getInstance());
        return monthBegin(getCalendarFromDate(time));
    }

    public static Calendar monthBegin(Long time) {
        if (null == time) return monthBegin(Calendar.getInstance());
        return monthBegin(getCalendarFromTime(time));
    }


    public static Calendar monthEnd(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.DAY_OF_MONTH, time.getActualMaximum(
                Calendar.DAY_OF_MONTH));
        return dayEnd(time);
    }

    public static Calendar monthEnd(Date time) {
        if (null == time) return monthEnd(Calendar.getInstance());
        return monthEnd(getCalendarFromDate(time));
    }

    public static Calendar monthEnd(Long time) {
        if (null == time) return monthEnd(Calendar.getInstance());
        return monthEnd(getCalendarFromTime(time));
    }


    public static Calendar yearBegin(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.MONTH, time.getActualMinimum(
                Calendar.MONTH));
        return monthBegin(time);
    }

    public static Calendar yearBegin(Date time) {
        if (null == time) return yearBegin(Calendar.getInstance());
        return yearBegin(getCalendarFromDate(time));
    }

    public static Calendar yearBegin(Long time) {
        if (null == time) return yearBegin(Calendar.getInstance());
        return yearBegin(getCalendarFromTime(time));
    }


    public static Calendar yearEnd(Calendar time) {
        if (null == time) time = Calendar.getInstance();
        else time = cloneCalendar(time);
        time.set(Calendar.MONTH, time.getActualMaximum(
                Calendar.MONTH));
        return monthEnd(time);
    }

    public static Calendar yearEnd(Date time) {
        if (null == time) return yearEnd(Calendar.getInstance());
        return yearEnd(getCalendarFromDate(time));
    }

    public static Calendar yearEnd(Long time) {
        if (null == time) return yearEnd(Calendar.getInstance());
        return yearEnd(getCalendarFromTime(time));
    }
}
