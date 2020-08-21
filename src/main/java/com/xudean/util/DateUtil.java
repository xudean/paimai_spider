package com.xudean.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期帮助类
 *
 * @author xuda.it@outlook.com
 */
@Slf4j
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private static final String FULL_DATE_FORMATE_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String UTC_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String FULL_DATE_FORMATE_STR_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String RFC5424_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * yyyy-MM-dd
     */
    private static final String SIMPLE_DATE_FORMAT_STR = "yyyy-MM-dd";

    public static String formatDate(Date date) {
        return new SimpleDateFormat(FULL_DATE_FORMATE_STR).format(date);
    }

    public static String formatDateInSimpleFormat(Date date) {
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT_STR).format(date);
    }


    public static Date parseDate(String dateStr) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat(FULL_DATE_FORMATE_STR).parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return date;
    }

    public static Date parseDateMileSecond(String dateStr) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat(FULL_DATE_FORMATE_STR_SSS).parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return date;
    }

    public static String getUtcFormatStr(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(UTC_FORMAT_STR);
        formater.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return formater.format(date);
    }

    public static String getRfc5424FormatStr(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(RFC5424_FORMAT_STR);
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formater.format(date);
    }

    public static Date parseUtcDate(String dateStr) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat(UTC_FORMAT_STR).parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return date;
    }

    /**
     * 指定时间添加指定天数
     *
     * @param curDate 待添加天数的时间
     * @param day     增加的天数
     * @return 添加完毕后的时间
     */
    public static Date dateAddDay(Date curDate, int day) {
        return DateUtils.addDays(curDate, day);
    }

    /**
     * 获取指定日期前指定天数的日期
     * @param curDate
     * @param day
     * @return
     */
    public static Date dateSubDay(Date curDate, int day) {
        long subMilles = DateUtils.MILLIS_PER_DAY * day;
        long curDateTime = curDate.getTime();
        return new Date(curDateTime-subMilles);
    }


    /**
     * 时间相减获取天数
     *
     * @param notBefore 开始时间
     * @param notAfter  结束时间
     * @return 结束时间-开始时间
     */
    public static int dateSubDay(Date notBefore, Date notAfter) {
        long time1 = notBefore.getTime();

        long time2 = notAfter.getTime();

        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取当前时间
     *
     * @return
     * @see DateUtil#formatDate(Date)
     */
    public static String currentTime() {
        return formatDate(new Date());
    }

    public static Date getDate(Object date) {
        if (date == null) {
            return null;
        }
        try {
            return DateUtil.parseDate(date.toString());
        } catch (ParseException e) {
            String usapTimeType = "yyyyMMddHHmmssZZZZ";
            try {
                SimpleDateFormat format = new SimpleDateFormat(usapTimeType);
                return format.parse(date.toString());
            } catch (ParseException e1) {
                long time = Long.valueOf(date.toString());
                return new Date(time);
            }
        }
    }

    public static Date parseBJTime(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = sdf.parse(date);
        return parsedDate;
    }

    public static int getIntDayTime(int year, int month, int day) {
        String strMonth = String.valueOf(month).length() < 2 ? "0" + String.valueOf(month) : String.valueOf(month);
        String strDay = String.valueOf(day).length() < 2 ? "0" + String.valueOf(day) : String.valueOf(day);
        return Integer.valueOf(String.format("%s%s%s", year, strMonth, strDay));
    }


    /**
     * 证书有效期开始时间应为当天00::00:00
     *
     * @return
     */
    public static Date getNewCertNotBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 返回对应日期的00:00:00作为证书生效时间
     *
     * @param date
     * @return
     */
    public static Date formatDateAsCertNotBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 字符串转换为正确格式的生效时间
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date formatDateAsCertNotBefore(String dateStr) throws ParseException {
        Date date = parseDate(dateStr);
        return formatDateAsCertNotBefore(date);
    }


    /**
     * 字符串转换为正确格式的失效时间
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date formatDateAsCertNotAfter(String dateStr) throws ParseException {
        Date date = parseDate(dateStr);
        return formatDateAsCertNotAfter(date);
    }

    /**
     * 返回对应日的23:59:59作为证书失效时间
     *
     * @param date
     * @return
     */
    public static Date formatDateAsCertNotAfter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 根据生效时间和有效期天数获取失效时间
     *
     * @param notBefore
     * @param validDays
     * @return
     */
    public static Date getNewNotAfter(Date notBefore, Integer validDays) {
        return formatDateAsCertNotAfter(dateAddDay(notBefore, validDays));
    }

}
