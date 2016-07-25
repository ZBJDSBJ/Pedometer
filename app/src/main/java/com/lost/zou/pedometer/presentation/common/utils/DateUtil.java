package com.lost.zou.pedometer.presentation.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by chaoziliang on 16/1/13.
 */
public class DateUtil {


    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    //年月日 转换成 月日
    public static String toChangeTimeFormat(String strDate){
        String result = StringToDate(strDate, "yyyy-MM-dd", "MM-dd");

        return result;
    }

    public static String StringToDate(String dateStr, String dateFormatStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try{
            date = sdf.parse(dateStr);

        } catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat s = new SimpleDateFormat(formatStr);

        return s.format(date);
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @return
     */
    public static String getStrFromTime(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm");//初始化Formatter的转换格式。
        String dateString = formatter.format(time);

        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param
     * @return
     */
    public static String getStrOtherFromTime(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//初始化Formatter的转换格式。
        String dateString = formatter.format(time);

        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param
     * @return
     */
    public static String getHoursFromTime(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//初始化Formatter的转换格式。
        String dateString = formatter.format(time);

        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm
     *
     * @param
     * @return
     */
    public static String getYearDateFromTime(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//初始化Formatter的转换格式。
        String dateString = formatter.format(time);

        return dateString;
    }

    public static String getTotalTime(Integer time) {
        long sportTime = time - TimeZone.getDefault().getRawOffset();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        String hms = formatter.format(sportTime);
        return hms;
    }

    //获取时分秒分隔值
    public static String[] getTotalTimeDivide(Integer time) {
        String[] timeDivides = new String[3];
        long sportTime = time - TimeZone.getDefault().getRawOffset();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");//初始化Formatter的转换格式。
        String hms = formatter.format(sportTime);
        timeDivides[0] = hms;
        formatter = new SimpleDateFormat("mm");
        String hms2 = formatter.format(sportTime);
        timeDivides[1] = hms2;
        formatter = new SimpleDateFormat("ss");
        String hms3 = formatter.format(sportTime);
        timeDivides[2] = hms3;
        return timeDivides;
    }

    //获取两位数的字符串
    public static String getStringFromDouble(double value){
        return String.format("%.02f", value);
    }

    //获取1位数的字符串
    public static String getOneStringFromDouble(double value){

        return String.format("%.01f", value);
    }

    /**
     * 小数点处理，保留一个小数点
     */
    public static float decimalProcess(double data) {
        return Math.round(data * 10.0f) / 10.0f;
    }


    // 通过毫秒获取 获取是日期 4/15
    public static String getDay(Long endTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        return month+"/"+day;
    }

}
