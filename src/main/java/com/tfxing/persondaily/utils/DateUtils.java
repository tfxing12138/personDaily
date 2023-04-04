package com.tfxing.persondaily.utils;

import org.assertj.core.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
