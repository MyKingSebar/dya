package cn.v1.unionc_user.ui.home.health;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class CaiboSetting {

    public static final String SETTING_FILE = "com.vodone.caibo.setting";

    public static final String Key_AlertMessage = "mMessageAlert";


    // 最后登陆用户
    public static final String KEY_LAST_LOGINNAME = "lastAccout_loginname";
    //用户是否已实名过
    public static final String KEY_NOTAUTHENICTION = "autheniction";


    //标识用户是用新浪微博登录的还是用我们自己的账号登陆的(新浪账号登陆的标识为1,否则为0)
    public static final String KEY_LOGINTYPE = "logintype";
    public static final String TYPE_OUR = "0";
    public static final String TYPE_SINA = "1";
    public static final String TYPE_QQWEIBO = "2";
    public static final String TYPE_QQ = "3";
    public static final String TYPE_ALIPAY = "4";
    public static final String TYPE_WEIXIN = "5";

    public static final String KEY_USERID = "userid";
    public static final String KEY_ISBINDBANDCARD = "isbindbandcard";
    public static final String KEY_ACCESSTOKEN = "userpasswd";
    public static final String KEY_SESSIONDID = "sessionId";
    public static final String KEY_UUID = "uuid";
    public static final String KEY_USERAGE = "userAge";
    public static final String KEY_APATCH_VERSION="bugVersion";

    //充值列表显示  优惠码方式   数组
    public static final String KEY_GOUCAIZHONGZHIARRAY = "goucaichongzhiarray";
    public static final String KEY_ORDERSOUND = "ordersound";
    public static final String KEY_SESSIONRECONNECTION = "SESSIONRECONNECTION";
    public static final String KEY_SEARCHHISTORY = "SEARCHHISTORY";

    public static final String KEY_CITY = "CITY";
    public static final String KEY_CITYCODE = "CITYCODE";

    public static final String KEY_LONGTITUDE = "LONGTITUDE";
    public static final String KEY_LATITUDE = "LATITUDE";
    public static final String KEY_MERCHANTSWITCH = "merchantswitch";

    public static final String KEY_ISDUMMY = "isDummy";
    public static final String KEY_ISFIRSTUSE = "isFirstUse";
    public static final String KEY_ISVERSIONUPDATE = "isVersionUpdate";
    public static final String KEY_HASEUPDATE = "hasupdate";
    public static final String KEY_ISDIALOG = "isDialog";
    public static final String KEY_ISHOSPITALBEDDIALOG = "ishospitaldialog";
    public static final String KEY_ISCREATESHAREDIALOG = "iscreatesharedialog";
    public static final String KEY_PAYTIPDIALOG = "paytipdialog";
    public static final String KEY_ISNEWANSWER = "key_isnewanswer";//0是有新回复
    public static final String KEY_ISNEWANSWERREFRESH = "key_newanswerrefresh";//是否刷新问诊接口
    public  static final String EMPLOYEEFLAG = "employeeFlag";//1是集团员工
    public  static final String EMPLOYEENAME = "employeeName"; //员工姓名
    /**
     * 消息通知
     */
    public static final String KEY_MESSAGENOTIFY = "messagenotify";

    public static final String KEY_REGISTERREDPACKET = "redpacket_register";
    public static final String KEY_REGISTERACTIVITYID = "redpacket_registerid";

    public static final String KEY_FIRSTINPEDOMETER = "first_in_pedometer";
    public static final String KEY_WXPAYORDERID = "wx_payorderid";
    public static final String KEY_WXPAYCHARGEORDERID = "wx_chargepayorderid";
    //广告位ID
    public static final String ADVERTISEMENTPAGEID = "advertisementPageId";

    //美白针 保险提示
    public static final String KEY_ISBAOXIANTIP = "isbaoxiantip";
    //强制升级 和 普通升级的版本 普通升级点忽略 存当前忽略的版本
    public static final String KEY_ISIGNOREVERSION="isignoreversion";

    public static final String KEY_SERVICEMOBILE = "serviceMobile";//客服电话
    public static final String KEY_REGISTERMOBLE = "registerMoble";//挂号客服专线

    public static final String KEY_GHTIPMESSAGE = "ghtipmessage";//挂号提示信息


    public static final String KEY_GOSETTINGPS = "gosettingps";//是否提示设置密码

    public static final String KEY_NETSTATUS = "netStatus";//是否提示设置密码

    //转诊陪诊搜索历史记录
    public static final String KEY_PZHISTORYLIST = "pzhistorylist";

    public static final String KEY_HOMESEARCHSERVICE = "homesearchservice";//首页搜索历史记录

    public static final String KEY_ADDEDPAYPRICEMAX = "addedPayPriceMax";//added_pay_price_max  最大金额 挂号打赏金额范围
    public static final String KEY_ADDEDPAYPRICEMIN = "addedPayPriceMin"; //added_pay_price_min  最小金额
    public static final String KYE_CHANGEDATEMESSAGE = "changedatemessage";//改变挂号时间
    public static final String KEY_ONLINEGUAHAOKFURL = "onlineGuahaoKfUrl";//转诊陪诊在线客服H5

    //到家客服
    public static final String KEY_ONLINEYIHUKFURL = "onlineYihuKfUrl";//到家在线客服
    //陪诊服务搜索医院历史记录
    public static final String KEY_PZHOSPITAL_HISTORYLIST = "pzhospitalhistorylist";

    public static final String KEY_ISSHOWGROUPWELFAREDIALOG = "siShowGroupWelfareDialog";//是否弹企业员工的弹窗
    public static final String KEY_ISSHOWGROUPWELFARENEWFLAG = "siShowGroupWelfareNewFlag";//是否显示new标识

    public static final String KEY_UNPAY = "key_unpay";//是否打开客户端

    public static final String KEY_MHEADURL= "mey_mheadurl";//聊天头像
    public static final String KEY_MUSERNAME= "mey_musername";//聊天名称

    /**
     * 设置整形属性
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setIntAttr(Context context, String key, int value) {
        SharedPreferences sp = getCaiboSettingShared(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 获得整形属性
     *
     * @param context
     * @param key
     * @return
     */
    public static int getIntAttr(Context context, String key) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getInt(key, -1);
    }


    /**
     * 获得整形属性
     *
     * @param context
     * @param key
     * @return
     */
    public static int getIntAttr(Context context, String key, int defaultValue) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getInt(key, defaultValue);
    }


    /**
     * 高置布尔属性
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setBooleanAttr(Context context, String key, boolean value) {
        SharedPreferences sp = getCaiboSettingShared(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 获得布尔属性
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanAttr(Context context, String key) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getBoolean(key, true);
    }


    /**
     * 获得布尔属性
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanAttr(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 设置字串属性
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setStringAttr(Context context, String key, String value) {
        SharedPreferences sp = getCaiboSettingShared(context);
        SharedPreferences.Editor edit = sp.edit();
        if (value == null) {
            edit.remove(key);
        } else {
            edit.putString(key, value);
        }

        edit.commit();
    }

    /**
     * 获得字串属性
     *
     * @param context
     * @param key
     * @return
     */
    public static String getStringAttr(Context context, String key) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getString(key, null);
//        return sp.getString(key, "");
    }

    /**
     * 获得字串属性
     *
     * @param context
     * @param key
     * @return
     */
    public static String getStringAttr(Context context, String key, String defult) {
        SharedPreferences sp = getCaiboSettingShared(context);
        return sp.getString(key, defult);
    }

    /**
     * 设置对象属性
     */
    public static void setObject(Context context, Object object, String key) {
        SharedPreferences sp = getCaiboSettingShareds(context);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(object);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos
                    .toByteArray()));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, oAuth_Base64);

            editor.commit();
        } catch (IOException e) {
            // TODO Auto-generated
        }
    }

    /**
     * 获取对象属性
     */
    public static Object getObject(Context context, Object object , String key) {
        SharedPreferences preferences = CaiboSetting.getCaiboSettingShareds(context);
        String productBase64 = preferences.getString(key, "");

        //读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes());

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                object = bis.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 设置对象属性
     */
    public static void setObject(Context context, Nurse nurse) {
        SharedPreferences sp = getCaiboSettingShareds(context);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(nurse);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos
                    .toByteArray()));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("inquiry_nurse", oAuth_Base64);

            editor.commit();
        } catch (IOException e) {
            // TODO Auto-generated
        }
    }

    /**
     * 获取对象属性
     */
    public static Nurse getObject(Context context) {
        Nurse nurse = null;
        SharedPreferences preferences = CaiboSetting.getCaiboSettingShareds(context);
        String productBase64 = preferences.getString("inquiry_nurse", "");

        //读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes());

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                nurse = (Nurse) bis.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nurse;
    }

    /**
     * 获取推送间隔
     *
     * @param context
     * @return
     */

    public static int getRefreshTime(Context context) {

        int alertTime = getCaiboSettingShared(context).getInt(Key_AlertMessage, -1);
        if (alertTime != -1) {
            if (alertTime == 0) {
                //隔两分钟请求推送
                return 120;
            } else if (alertTime == 1) {
                //隔10分钟请求推送
                return 600;
            } else {
                return 120;
            }
        } else {
            CaiboSetting.setIntAttr(context, Key_AlertMessage, 0);
            return 120;
        }

    }

    private static SharedPreferences getCaiboSettingShared(Context context) {
        return context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getCaiboSettingShareds(Context context) {
        return context.getSharedPreferences("base64", Context.MODE_PRIVATE);
    }

    public static void deleteKey(Context context, String key){
        SharedPreferences sp = getCaiboSettingShared(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

}
