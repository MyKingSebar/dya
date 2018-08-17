package cn.v1.unionc_user.ui.home.BloodPressure.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qy on 2017/8/14.
 */

public class DateUtil {

    /**
     * 根据日期判断是今天还是昨天
     * @param currentDate
     * @param formatDate
     * @return
     */
    public  static String getTimeTodayOrYesterday(String currentDate , String formatDate){
        String timeStr = "" ;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long currentTime = format.parse(currentDate).getTime();
            long  formatTime = format.parse(formatDate).getTime();
            if(currentTime/1000 - formatTime/1000 <= 3600*24){
                timeStr = "今天";
            }else if(currentTime/1000 - formatTime/1000 > 3600*24 && currentTime/1000 - formatTime/1000 <= 3600*48){
                timeStr = "昨天";
            }else{
                timeStr = formatDate;
            }
        } catch (ParseException e) {
            timeStr = formatDate;
            e.printStackTrace();
        }
//        try {
//            if(Integer.parseInt(currentDate.split("-")[0]) == Integer.parseInt(formatDate.split("-")[0])
//                    && Integer.parseInt(currentDate.split("-")[1]) == Integer.parseInt(formatDate.split("-")[1])){
//                if(Integer.parseInt(currentDate.split("-")[2]) == Integer.parseInt(formatDate.split("-")[2])){
//                    timeStr = "今天";
//                }else if(Integer.parseInt(currentDate.split("-")[2]) - Integer.parseInt(formatDate.split("-")[2]) == 1){
//                    timeStr = "昨天";
//                }else{
//                    timeStr = formatDate;
//                }
//            }else{
//                timeStr = formatDate;
//            }
//        }catch (Exception e){
//            timeStr = formatDate;
//            e.printStackTrace();
//        }
        return  timeStr;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
     *

     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getDateOfWeek(String pTime){
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "周天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 将时间格式为2017-08-03 17:00:00 转换为 8月3日(周几)20:00
     * @param formatTime  设置的需要判断的时间  //格式如2012-09-08
     * @return
     */

    public static String dateFormat(String formatTime){
        String formatDate = "";
        String date = formatTime.split(" ")[0];
        String time = formatTime.split(" ")[1];
        try {
            formatDate = deleteStart0(date.split("-")[1]) + "月" + deleteStart0(date.split("-")[2]) + "日" + "(" + getDateOfWeek(date) + ")" +
                    time.split(":")[0] + ":" + time.split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return  formatDate;
    }

    private static String deleteStart0(String str){
        String deletedStart0Str = "";
        try {
            deletedStart0Str = Integer.parseInt(str)+"";
        }catch (Exception e){
            deletedStart0Str = str;
            e.printStackTrace();
        }
        return  deletedStart0Str;
    }
    /**
     * 时间比较
     */
    public static boolean timeCompare(String compareTime) {
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTime = System.currentTimeMillis();
        boolean compareTimeBeforNow = false;
//        String date1="2015-01-25 09:12:09";
//        String date2="2015-01-29 09:12:11";
        try {

            // Date beginTime=CurrentTime.parse(nowTime);
            Date endTime = CurrentTime.parse(compareTime);
            //判断比较的时间是否大于当前时间
            if ((endTime.getTime() - nowTime) > 0) {
                compareTimeBeforNow = true;
            } else {
                compareTimeBeforNow = false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  compareTimeBeforNow;
    }


}
