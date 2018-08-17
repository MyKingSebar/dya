package cn.v1.unionc_user.ui.home.BloodPressure.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class BloodPresure {


    /**
     *
     * @Title: computeRate
     * @Description:    根据计算得出数据正常或偏高或偏低，属于哪个区间，就往哪个方向走，
    六种文字提示：低血压；正常血压；正常高值血压；轻度高血压；中度高血压；重度高血压
    计算方法如右图：
    高压<90或低压<70,属于低血压
    90=<高压<120或70=<低压<80，属于正常
    120=<高压<140或80<=低压<90，属于正常高值
    140=<高压<160或90=<低压<100，属于轻度高血压
    160=<高压<180或且100=<低压<110，属于中度高血压
    180=<高压且100=<低压，属于重度高血压

    此处注意，如果存在高低压所在所在区间不同时的判断依据‘有高就是高；有低就是低；正常血压取高值’：
    1.一个在绿色区，一个在蓝色区，属于低血压
    2.一个在绿色区，一个在红色区，属于高血压
    3.一个在蓝色区，一个在红色区，属于高血压
    4.两个都在同一绿色区间属于正常血压，如果任意一个在正常高值区间内，属于正常高值。
    不会存在高压比低压还低的情况，手动输入时如存在会弹出气泡提示
     * @param map
     * @param highPressure
     * @param lowPressure
     * @return
     * @return Map<String,Object>
     * @throws
     */
    public static Map<String,Object> computeRate( String highPressure, String lowPressure){
        Map<String,Object> map=new HashMap<>();
        if(!TextUtils.isEmpty(highPressure) && !TextUtils.isEmpty(lowPressure)){
            int high = Integer.parseInt(highPressure);
            int low = Integer.parseInt(lowPressure);
            String[] rateName = {"低血压","正常血压","正常高值血压","轻度高血压","中度高血压","重度高血压"};
            if(180 <= high || 110 <= low){
                map.put("rate", 6);
                map.put("rateName", rateName[5]);
                return map;
            }else if((160 <= high && high <180) || (100 <= low && low < 110)){
                map.put("rate", 5);
                map.put("rateName", rateName[4]);
                return map;
            }else if((140 <= high && high <160) || (90 <= low && low < 100)){
                map.put("rate", 4);
                map.put("rateName", rateName[3]);
                return map;
            }else if(high < 90 || low < 60){
                map.put("rate", 1);
                map.put("rateName", rateName[0]);
                return map;
            }else if((130 <= high && high <140) || (85 <= low && low < 90)){
                map.put("rate", 3);
                map.put("rateName", rateName[2]);
                return map;
            }else if((90 <= high && high <130) || (60 <= low && low < 85)){
                map.put("rate", 2);
                map.put("rateName", rateName[1]);
                return map;
            }
        }
        return map;
    }
}
