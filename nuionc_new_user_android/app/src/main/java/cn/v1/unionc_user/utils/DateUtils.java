package cn.v1.unionc_user.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 主要用于活动弹窗活动时间处理
 * Created by qy on 2018/3/20.
 */

public class DateUtils {

public static Calendar stringToCalendar(String str, String format){
    Calendar calendar=null;
    SimpleDateFormat sdf= new SimpleDateFormat(format);
    try {
        Date date =sdf.parse(str);
        calendar= Calendar.getInstance();

        calendar.setTime(date);

//        Log.d("linshi", "date:" + calendar.get(Calendar.YEAR) + "," + calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.MINUTE));
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return calendar;
}
public static String getMandD(Calendar calendar){
    return calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.DAY_OF_MONTH);
}

public String getTandD(){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    String time = format.format(Calendar.getInstance().getTime());
    return time;
}
public String getDate(){
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    String time2 = format2.format(Calendar.getInstance().getTime());
    return time2;
}
public static  String getTime(){
    SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
    String time3 = format3.format(Calendar.getInstance().getTime());
    return time3;
}

public static String getStartandEnd(String start,String end){
   String start1= getMandD(stringToCalendar(start,"yyyy-MM-dd hh:mm:ss"));
   String end1= getMandD(stringToCalendar(end,"yyyy-MM-dd hh:mm:ss"));
   if(TextUtils.equals(start1,end1)){
       return start1;
    }else{
       return start1+"-"+end1;
   }

}

}
