package com.demo.account.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bill on 2017/9/27.
 * 日期格式化工具类
 */

public class DateFormat {

    private DateFormat() {
    }

    /**
     * 根据特定格式 格式化指定日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format){
        if (date == null && TextUtils.isEmpty(format)){
            return null;
        }
        return new SimpleDateFormat(format, Locale.ENGLISH).format(date);
    }


    /**
     * 获取当月结束时间
     *
     * @param date
     * @return
     */
    public static Date getSameMonthEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当月开始时间
     *
     * @param date
     * @return
     */
    public static Date getSameMonthStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据指定日期，获取指定月后的日期。
     */
    public static Date getDateNxtMonth(Date date, int next){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, next);
        return calendar.getTime();
    }





}
