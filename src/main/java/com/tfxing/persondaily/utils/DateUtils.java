package com.tfxing.persondaily.utils;

import org.assertj.core.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * 计算指定当前距离指定日期相差的天数
     * @param date
     * @return
     */
    public static Long subDayCount(Date date) {
        Date star = DateUtil.now();//开始时间
        Long starTime=star.getTime();
        Long endTime=date.getTime();
        Long num=endTime-starTime;//时间戳相差的毫秒数
        long dayCount = num / 24 / 60 / 60 / 1000;
        return dayCount;
    }

    /**
     * 计算指定时间到距离时间的小时差
     * @return
     */
    public static Integer subHoursCount() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long from = 0;
        long to = 0;
        try {
            from = DateUtil.now().getTime();
            to = simpleFormat.parse("2023-05-11 23:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer hours = (int) ((to - from)/(1000 * 60 * 60*1.0));
        return hours;
    }

    /**
     * 日期字符串转日期格式
     * @param dateStr
     * @param patten
     * @return
     */
    public static Date str2Date(String dateStr,String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }
    public static Date getDateYearMonthByDate(Date datefm) throws ParseException {
        String dateStr = parseDateToStr("yyyy-MM", datefm);
        return new SimpleDateFormat("yyyy-MM").parse(dateStr);
    }

    /**
     * 获取一天的结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getEndOfDay(Date date) {
        // 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        // 设置日期为指定日期
        calendar.setTime(date);
        // 设置时间为最后一秒
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        // 获取结束时间
        return calendar.getTime();
    }

    /**
     * 获取date所属月的第一天的日期
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取date所属月的最后一天的日期
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
}
