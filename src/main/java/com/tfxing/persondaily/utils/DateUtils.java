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
