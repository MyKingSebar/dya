package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Environment;

/**
 * Created by lawrence on 14/12/10.
 */
public class CommonContract {
    public static final int TIMELINE_PAGE_SIZE = 20;
    public static final int TIMELINE_LISTTYPE_PLAZA = 1;
    public static final int LOADERID_TIMELINE_PLAZA = 2;
    public static final int LOADERID_JCFOOTBALLHEAD = -1;

    //居家养老检测类型
    public static final String DOSSIERJIANCE_DIABETES = "85";
    public static final String DOSSIERJIANCE_HYPERTENSION = "86";
    public static final String DOSSIERJIANCE_BRAINVESSELS = "87";
    public static final String DOSSIERJIANCE_HEARTDISEASE = "88";


    public static final String KEY_PARENTNAME = "parentname";

    public static final String KEY_FILEPATH = Environment.getExternalStorageDirectory()+"/vodone/yihu";

    public static final String KEY_INQUIRY = "key_from_inquiry";

    //支付对应的 gate_id值  当payType是2
    //医护到家 1  ，  名医挂号 2  ，  打针输液(护士到家) 3  ，  爱美之心(美容美白针) 4  ， 挂号网 5  ，  送药到家  6
    //奶牛妈咪 7  ，  蜻蜓心理 8  ，  挂号160  9  ，  挂号网官网 10  ，  114挂号网  11
    //南京挂号网  12  ，  广州挂号网  13  ，  医联挂号网  14  ，  浙江挂号网  15  ，  医联挂号平台  16
    //17:家庭护士平台     18:护士上门平台      19:好护士平台       20:护士医院平台
    public static final String GATE_ID_HEALTH_CARE = "1";
    public static final String GATE_ID_GUAHAO_MINGYI = "2";
    public static final String GATE_ID_HUSHIDAOJIA = "3";
    public static final String GATE_ID_MEIRONGMEIBAIZHEN = "4";
    public static final String GATE_ID_GUAHAO_GUAHAOWANG = "5";
    public static final String GATE_ID_MEDICINEONLINE = "6";
    public static final String GATE_ID_NAINIUMM = "7";
    public static final String GATE_ID_PSY = "8";
    public static final String GATE_ID_GUAHAO_160 = "9";
    public static final String GATE_ID_GUAHAO_GUANWANG = "10";
    public static final String GATE_ID_GUAHAO_114 = "11";
    public static final String GATE_ID_GUAHAO_NANJING = "12";
    public static final String GATE_ID_GUAHAO_GUANGZHOU = "13";
    public static final String GATE_ID_GUAHAO_DOCTORUNION = "14";
    public static final String GATE_ID_GUAHAO_ZHEJIANG = "15";
    public static final String GATE_ID_GUAHAO_YILIANPINGTAI = "16";
    public static final String GATE_ID_GUAHAO_NURSEHOME = "17";
    public static final String GATE_ID_GUAHAO_NURSEINDOOR = "18";
    public static final String GATE_ID_GUAHAO_NURSEHAO = "19";
    public static final String GATE_ID_GUAHAO_NURSEHOSPITAL = "20";


    /**   角色code*/
    /** 医生 */
    public static String role_code_doctor = "001";//医生
    /** 护士 */
    public static String role_code_nurse = "002";//护士
    /** 就医助理 */
    public static String role_code_accompany = "003";//就医助理
    /** 护工 */
    public static String role_code_workers = "004";//护工
    /** 月嫂 */
    public static String role_code_maternity_matron = "005";//月嫂
    /** 催乳师 */
    public static String role_code_prolactin_division = "006";//催乳师
    /** 驻外专家 */
    public static String role_code_prolactin_overseas = "007";//驻外专家
    /** 药店 */
    public static String role_code_pharmacy = "009";//药店
    /** 送药员 */
    public static String role_code_deliveryman = "008";//送药员
    /** 咨询师 */
    public static String role_code_counselor = "010";//咨询师
    /** 体检师 */
    public static String role_code_examination = "011";//体检师

    /** 公共营养师 */
    public static String role_code_publicNutritionist = "012";//公共营养师

    /** 保健按摩师 */
    public static String role_code_massage = "013";//保健按摩师

    /** 健康管理师 */
    public static String role_code_healthManage = "014";//健康管理师

    /** 康复治疗师*/
    public static String role_code_therapist = "015";//康复治疗师

    /** 美容师*/
    public static String role_code_cosmetologist = "016";

    /** 整形医生*/
    public static String role_code_plasticSurgeon = "017";
    /** 健康计划*/
    public final static String role_code_healthplan = "018";
    /** 护士陪诊*/
    public final static String role_code_nurse_accompany = "019";


}
