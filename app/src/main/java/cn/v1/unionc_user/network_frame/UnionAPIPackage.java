package cn.v1.unionc_user.network_frame;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.ClinicActivityData;
import cn.v1.unionc_user.model.ClinicInfoData;
import cn.v1.unionc_user.model.DoctorAnswerDetailData;
import cn.v1.unionc_user.model.DoctorEvaluateData;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.model.DoctorInfoIdentifierData;
import cn.v1.unionc_user.model.DoctorOrClinicData;
import cn.v1.unionc_user.model.DoctorScheduleData;
import cn.v1.unionc_user.model.GetGuardianshipInfoData;
import cn.v1.unionc_user.model.GetRongTokenData;
import cn.v1.unionc_user.model.HeartHistoryData;
import cn.v1.unionc_user.model.HeartHistoryListData;
import cn.v1.unionc_user.model.HeartIndicationData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.HomeToHomeData;
import cn.v1.unionc_user.model.IsDoctorData;
import cn.v1.unionc_user.model.IsDoctorSignData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.MapClinicData;
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.model.MyDutyDoctorsData;
import cn.v1.unionc_user.model.MyRecommenDoctorsData;
import cn.v1.unionc_user.model.OMLHistoryData;
import cn.v1.unionc_user.model.RecommendDoctorsData;
import cn.v1.unionc_user.model.TIMSigData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.model.WatchingActivityData;
import cn.v1.unionc_user.model.WeiXinQRcodeData;
import cn.v1.unionc_user.utils.MobileConfigUtil;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by qy on 2018/1/31.
 */

public class UnionAPIPackage {


    private static Gson gson = new Gson();

    /**
     * 数据处理
     *
     * @param params 传递的参数
     * @return
     */
    private static Map<String, Object> dataProcess(Map<String, String> params) {
        HashMap<String, Object> processData = new HashMap<>();
        processData.put("data", gson.toJson(params).toString());
        processData.put("encryption", false);
        Logger.d(gson.toJson(processData).toString());
        return processData;
    }

    /**
     * 验证码下发
     *
     * @param userMobile 手机号
     * @return
     */
    public static Observable<BaseData> getAuthCode(String userMobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userMobile", userMobile);
        return ConnectHttp.getUnionAPI().getAuthCode(dataProcess(params));
    }

    /**
     * 修改用户地址
     *
     * @return
     */
    public static Observable<BaseData> updateAdd(String token,String add, String la,String lo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("address", add);
        params.put("latitude", la);
        params.put("longitude", lo);
        return ConnectHttp.getUnionAPI().updateAdd(dataProcess(params));
    }
    /**
     * 登录
     *
     * @param userMobile 手机号
     * @param authCode   验证码
     * @return
     */
    public static Observable<LoginData> login(String userMobile, String authCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userMobile", userMobile);
        params.put("authCode", authCode);
        params.put("imei", MobileConfigUtil.getMacCode());
        Log.d("linshi","imei"+MobileConfigUtil.getMacCode());
        return ConnectHttp.getUnionAPI().login(dataProcess(params));
    }

    /**
     * 获取TIM sig
     *
     * @return
     */
    public static Observable<TIMSigData> getTIMSig(String token, String identifier) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("identifier", identifier);
        return ConnectHttp.getUnionAPI().getTIMSig(dataProcess(params));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static Observable<UserInfoData> getUserInfo(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionAPI().getUserInfo(dataProcess(params));
    }


    /**
     * 获取首页列表
     *
     * @return
     */
    public static Observable<HomeListData> getHomeList(String token, String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("imei", MobileConfigUtil.getMacCode());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return ConnectHttp.getUnionAPI().getHomeList(dataProcess(params));
    }
    /**
     * 获取我的/关注/医生 列表
     *
     * @return
     */
    public static Observable<MeWatchingDoctorListData> getMeWatchingDoctorList(String token, String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("attentType", "1");//查询类型（1：查询关注的医生 2关注的医院)
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return ConnectHttp.getUnionAPI().getMeWatchingDoctorList(dataProcess(params));
    }
    /**
     * 获取我的/关注/医院 列表
     *
     * @return
     */
    public static Observable<MeWatchingHospitalListData> getMeWatchingHospitalList(String token, String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("attentType", "2");//查询类型（1：查询关注的医生 2关注的医院)
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        Log.d("linshi","getHospitalList:"+latitude+","+longitude);
        return ConnectHttp.getUnionAPI().getMeWatchingHospitalList(dataProcess(params));
    }
    /**
     * 获取我的/关注/活动 列表
     *
     * @return
     */
    public static Observable<WatchingActivityData> getMeWatchingActivityList(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        return ConnectHttp.getUnionAPI().getMeWatchingActivityList(dataProcess(params));
    }
    /**
     * 获取周边活动 列表
     *
     * @return
     */
    public static Observable<WatchingActivityData> getFindActivityList(boolean hastoken,String token) {
        HashMap<String, String> params = new HashMap<>();
        if(hastoken){

            params.put("token", token);
        }
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        return ConnectHttp.getUnionAPI().getFindActivityList(dataProcess(params));
    }
    /**
     * 获取我的/关注/报名活动 列表
     *
     * @return
     */
    public static Observable<WatchingActivityData> getMeApplyActivityList(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        return ConnectHttp.getUnionAPI().getMeApplyActivityList(dataProcess(params));
    }


    /**
     * 获取活动弹窗
     *
     * @return
     */
    public static Observable<HomeListData> getPushList(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("imei", MobileConfigUtil.getMacCode());
        params.put("token", token);
        return ConnectHttp.getUnionAPI().getPushList(dataProcess(params));
    }

    /**
     * 8.6.	获取首页地图诊所列表
     *
     * @return
     */
    public static Observable<MapClinicData> getMapClinic(String token,String type, String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("type", type);
        params.put("imei",MobileConfigUtil.getMacCode());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return ConnectHttp.getUnionAPI().getMapClinic(dataProcess(params));
    }
    /**
     * 获取诊所详细信息
     *
     * @return
     */
    public static Observable<ClinicInfoData> getClinicInfo(String token, String clinicId, String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("clinicId", clinicId);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return ConnectHttp.getUnionAPI().getClinicInfo(dataProcess(params));
    }

    /**
     * 获取医生详细信息
     *
     * @return
     */
    public static Observable<DoctorInfoData> getDoctorInfo(String token, String doctId, String longitude, String latitude, String source) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("pageNo", "1");
        params.put("source", source);
        return ConnectHttp.getUnionAPI().getDoctorInfo(dataProcess(params));
    }



    /**
     * 获取医生详细信息
     *
     * @return
     */
    public static Observable<DoctorInfoIdentifierData> doctorInfoByParam(String token, String identifier) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("identifier", identifier);
        return ConnectHttp.getUnionAPI().doctorInfoByParam(dataProcess(params));
    }

    /**
     * 通过identy获取 医院/医生  /医院id
     *
     * @return
     */
    public static Observable<DoctorOrClinicData> doctorOrclinicByParam(String token, String identifier) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("identifier", identifier);
        return ConnectHttp.getUnionAPI().doctorOrclinicByParam(dataProcess(params));
    }


    /**
     * 上传照片
     *
     * @return
     */
    public static Observable<UpdateFileData> uploadImge(String token, File file) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        String data = gson.toJson(params).toString();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part 和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", file.getName(), requestFile);
        return ConnectHttp.getUnionAPI().uploadImge(data, false, body);
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    public static Observable<BaseData> updateUserInfo(String token, String userName,
                                                      String cardNo, String headImage,
                                                      String gender, String birthday) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("userName", userName);
        params.put("cardNo", cardNo);
        params.put("headImage", headImage);
        params.put("gender", gender);
        params.put("birthday", birthday);
        return ConnectHttp.getUnionAPI().updateUserInfo(dataProcess(params));
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    public static Observable<DoctorScheduleData> doctorSchedule(String token, String doctId,
                                                                String pageNo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        params.put("pageNo", pageNo);
        return ConnectHttp.getUnionAPI().doctorSchedule(dataProcess(params));
    }


    /**
     * 实名认证
     *
     * @return
     */
    public static Observable<BaseData> certification(String token, String realName,
                                                     String gender, String birthday,
                                                     String telphone,String cardNo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("realName", realName);
        params.put("gender", gender);
        params.put("birthday", birthday);
        params.put("cardNo", cardNo);
        params.put("cardImagePath", "");
        params.put("telphone", telphone);
        return ConnectHttp.getUnionAPI().certification(dataProcess(params));
    }

    /**
     * 实名认证
     *
     * @return
     */
    public static Observable<BaseData> isCertification(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionAPI().isCertification(dataProcess(params));
    }


    /**
     * 医生回答的问题
     *
     * @return
     */
    public static Observable<DoctorAnswerDetailData> getDoctorAnswer(String token, String questionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("questionId", questionId);
        return ConnectHttp.getUnionAPI().getDoctorAnswer(dataProcess(params));
    }

    /**
     * 医生是否签约
     *
     * @return
     */
    public static Observable<IsDoctorSignData> isDoctorSign(String token, String doctId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        return ConnectHttp.getUnionAPI().isDoctorSign(dataProcess(params));
    }

    /**
     * 签约医生
     *
     * @return
     */
    public static Observable<BaseData> signDoctor(String token, String doctId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        return ConnectHttp.getUnionAPI().signDoctor(dataProcess(params));
    }

    /**
     * 推荐和不推荐诊所
     *
     * @param token
     * @param clinicId
     * @param starCount : 1 不推荐   5 推荐
     * @return
     */
    public static Observable<BaseData> evaluateClinic(String token, String clinicId, String starCount) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("imei",MobileConfigUtil.getMacCode() );
        params.put("clinicId", clinicId);
        params.put("starCount", starCount);
        return ConnectHttp.getUnionAPI().evaluateClinic(dataProcess(params));
    }
    /**
     * 推荐和不推荐医生
     *
     * @param token
     * @param doctId
     * @param starCount : 1 不推荐   5 推荐
     * @return
     */
    public static Observable<BaseData> evaluateDoctor(String token, String doctId, String starCount) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        params.put("starCount", starCount);
        return ConnectHttp.getUnionAPI().evaluateDoctor(dataProcess(params));
    }

    /**
     * 关注和取消医生
     *
     * @param token
     * @param Id
     * @param attentFlag : 0：关注 1：取消关注
     * @param attentType ：（1：医生 2：医院，3：义诊活动）
     * @return
     */
    public static Observable<BaseData> attentionDoctor(String token,
                                                       String attentType,
                                                       String Id,
                                                       String attentFlag) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("attentType", attentType);
        switch (Integer.parseInt(attentType)){
            case 1:
                params.put("doctId", Id);
                break;
            case 2:
                params.put("clinicId", Id);
                break;
            case 3:
                params.put("activityId", Id);
                break;
        }
        params.put("attentFlag", attentFlag);
        return ConnectHttp.getUnionAPI().attention(dataProcess(params));
    }

    /**
     * 获取医生评价
     *
     * @param token
     * @param evaType  （1：医生评价 2：医院评价）
     * @param pageNo
     * @param pageSize
     * @param doctId
     * @return
     */
    public static Observable<DoctorEvaluateData> doctorevaluates(String token,
                                                                 String evaType,
                                                                 String pageNo,
                                                                 String pageSize,
                                                                 String doctId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("evaType", evaType);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        switch (Integer.parseInt(evaType)){
            case 1:
                params.put("doctId", doctId);
                break;
            case 2:
                params.put("clinicId", doctId);
                break;
        }
        return ConnectHttp.getUnionAPI().evaluates(dataProcess(params));
    }

    /**
     * 保存医院评价
     *
     * @param token
     * @param clinicId
     * @param content
     * @return
     */
    public static Observable<BaseData> saveClinicEvaluate(String token,
                                                          String clinicId,
                                                          String content
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("imei", MobileConfigUtil.getMacCode());
        params.put("clinicId", clinicId);
        params.put("content", content);
        return ConnectHttp.getUnionAPI().saveClinicEvaluate(dataProcess(params));
    }
    /**
     * 保存医生评价
     *
     * @param token
     * @param doctId
     * @param content
     * @return
     */
    public static Observable<BaseData> saveDoctorEvaluate(String token,
                                                          String doctId,
                                                          String content
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        params.put("content", content);
        return ConnectHttp.getUnionAPI().saveDoctorEvaluate(dataProcess(params));
    }

    /**
     * 医院活动
     *
     * @param clinicId
     * @param token
     * @return
     */
    public static Observable<ClinicActivityData> clinicActivities(String clinicId,
                                                                  String token, String source
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("clinicId", clinicId);
        params.put("token", token);
        params.put("source", source);
        return ConnectHttp.getUnionAPI().clinicActivities(dataProcess(params));
    }

    /**
     * 签约医院活动
     *
     * @param activityIds
     * @param token
     * @return
     */
    public static Observable<BaseData> signActivities(String activityIds,
                                                        String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("activityIds", activityIds);
        params.put("token", token);
        return ConnectHttp.getUnionAPI().signActivities(dataProcess(params));
    }
    /**
     * 获取推荐医生列表
     *
     * @return
     */
    public static Observable<RecommendDoctorsData> recommenddoctors(String longitude,
                                                                     String latitude,String pageSize,String pageNo
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("pageSize", pageSize);
        params.put("pageNo", pageNo);
        return ConnectHttp.getUnionAPI().recommenddoctors(dataProcess(params));
    }
    /**
     * 获取家庭医生列表
     *
     * @return
     */
    public static Observable<MyRecommenDoctorsData> myrecommenddoctors(String longitude,
                                                                       String latitude, String pageSize, String pageNo, String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("pageSize", pageSize);
        params.put("pageNo", pageNo);
        params.put("token", token);
        return ConnectHttp.getUnionAPI().myrecommenddoctors(dataProcess(params));
    }
    /**
     * 获取值班医生列表
     *
     * @return
     */
    public static Observable<MyDutyDoctorsData> mydutydoctors(String longitude,
                                                              String latitude, String pageSize, String pageNo
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("pageSize", pageSize);
        params.put("pageNo", pageNo);
        return ConnectHttp.getUnionAPI().mydutydoctors(dataProcess(params));
    }

    /**
     * 获取医护到家网址
     *
     * @param token
     * @return
     */
    public static Observable<HomeToHomeData> getyihu(String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionappAPI().getyihu(dataProcess(params));
    }
    /**
     * 获取送药到家网址
     *
     * @param token
     * @return
     */
    public static Observable<HomeToHomeData> getsongyao(String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionappAPI().getsongyao(dataProcess(params));
    }


    /**
     * 根据类型查询字典数据（type ：001-医生级别 002-客服电话 003-不适应症 004-心脏病类型）
     *
     * @return
     */
    public static Observable<HeartIndicationData> getIntelligentHardwareIndication(String type
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        return ConnectHttp.getUnionAPI().getIntelligentHardwareIndication(dataProcess(params));
    }
    /**
     * 微信二维码相关查询
     *
     * @return
     */
    public static Observable<WeiXinQRcodeData> getWeiXinQRcode(String qrCodeContentCode
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("qrCodeContentCode", qrCodeContentCode);
        return ConnectHttp.getUnionAPI().getWeiXinQRcode(dataProcess(params));
    }
    /**
     * 添加用户健康数据（monitorId：1-心率）
     *
     * @return
     */
    public static Observable<BaseData> saveHealthData(String token,String monitorId,String monitorDate,String heartRate,String heartRateImage,String cureMedicine,String diabetesType,
                                                              String disorder,String reason) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("monitorDate", monitorDate);
        params.put("heartRate", heartRate);
        params.put("heartRateImage", heartRateImage);
        params.put("cureMedicine", cureMedicine);
        params.put("diabetesType", diabetesType);
        params.put("disorder", disorder);
        params.put("reason", reason);
        return ConnectHttp.getUnionAPI().saveHealthData(dataProcess(params));
    }
    /**
     * 用户健康信息列表（monitorId：1-心率）
     *
     * @return
     */
    public static Observable<HeartHistoryListData> getHeartListData(String token, String monitorId, String pageNo, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return ConnectHttp.getUnionAPI().getHeartListData(dataProcess(params));
    }
    /**
     *
     用户健康信息详细（monitorId：1-心率）
     *
     * @return
     */
    public static Observable<HeartHistoryData> getHeartData(String token, String monitorId, String dataId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("dataId", dataId);
        return ConnectHttp.getUnionAPI().getHeartData(dataProcess(params));
    }
    /**
     *
     删除数据（monitorId：2-oml）
     *
     * @return
     */
    public static Observable<BaseData> deleteOMLData(String token, String monitorId, String dataId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("dataId", dataId);
        return ConnectHttp.getUnionAPI().deleteOMLData(dataProcess(params));
    }
    /**
     *
     保存血压数据
     *token:"yGGSVMEtTZdpdw8zSJJlpzhUgzeBRuKAyrOmdTw4OdQ\u003d",
     * monitorId:"2",
     * heartRate:"65",
     * monitorDate:"2018-05-29 10:38:00",
     * avgMeasure:"0",
     * bdaCode:"resgresgresres",
     * deviceModel:"欧姆龙血压仪",
     * highPressure:"100",
     * lowPressure:"65",
     * deviceType:"0",
     * measureWay:"0",
     * patientInfoId:""
     *
     * type:1-用户自己测量，2-通过扫码
     clinicId:医院id
     * @return
     */
    public static Observable<BaseData> saveOMLData(String token, String monitorId, String heartRate,
                                                   String monitorDate,String avgMeasure,String bdaCode,
                                                   String deviceModel,String highPressure,String lowPressure,
                                                   String deviceType,String measureWay,String patientInfoId,String type,String clinicId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("heartRate", heartRate);
        params.put("monitorDate", monitorDate);
        params.put("avgMeasure", avgMeasure);
        params.put("bdaCode", bdaCode);
        params.put("deviceModel", deviceModel);
        params.put("highPressure", highPressure);
        params.put("lowPressure", lowPressure);
        params.put("deviceType", deviceType);
        params.put("measureWay", measureWay);
        params.put("patientInfoId", patientInfoId);
        params.put("type", type);
        params.put("clinicId", clinicId);
        return ConnectHttp.getUnionAPI().saveOMLData(dataProcess(params));
    }
    /**
     *
     获取血压数据
     *token:"yGGSVMEtTZdpdw8zSJJlpzhUgzeBRuKAyrOmdTw4OdQ\u003d",monitorId:"2",pageNo:"1",pageSize:"10"
     */
    public static Observable<OMLHistoryData> getOMLData(String token, String monitorId, String pageNo,
                                                        String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("monitorId", monitorId);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return ConnectHttp.getUnionAPI().getOMLData(dataProcess(params));
    }
    /**
     *
     是否是医护
     *localhost:8080/unionWeb/doctor/is-medical?data={token:"tiLJBvWdRjTaq7VPJcswTThUgzeBRuKAyrOmdTw4OdQ\u003d"}&encryption=false
     */
    public static Observable<IsDoctorData> getIsDoctor(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionAPI().getIsDoctor(dataProcess(params));
    }
    /**
     *
     保存咨询记录
     *localhost:8080/unionWeb/user/save-doctor-conversation?data={token:"yGGSVMEtTZdpdw8zSJJlpzhUgzeBRuKAyrOmdTw4OdQ\u003d",doctId:"30"}&encryption=false
     */
    public static Observable<BaseData> savetalk(String token,String doctId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("doctId", doctId);
        return ConnectHttp.getUnionAPI().savetalk(dataProcess(params));
    }

    /**
     * 获取融云token
     */
    public static Observable<GetRongTokenData> getRongToken(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);

        return ConnectHttp.getUnionAPI().getRongToken(dataProcess(params));
    }
    /**
     * 获取融云Info
     */
    public static Observable<GetGuardianshipInfoData> GetGuardianshipInfo(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);

        return ConnectHttp.getUnionAPI().GetGuardianshipInfo(dataProcess(params));
    }
    /**
     * 获取亲情监护列表
     */
    public static Observable<MeguardianshipData> GetGuardianshipListInfo(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);

        return ConnectHttp.getUnionAPI().GetGuardianshipListInfo(dataProcess(params));
    }
    /**
     * 绑定监护人
     */
    public static Observable<BaseData> BindGuardianship(String token,String elderlyUserId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("elderlyUserId", elderlyUserId);

        return ConnectHttp.getUnionAPI().BindGuardianship(dataProcess(params));
    }
    /**
     * 保存关系
     */
    public static Observable<BaseData> SaveGuardianship(String token,String elderlyUserId,String roleName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("elderlyUserId", elderlyUserId);
        params.put("roleName", roleName);

        return ConnectHttp.getUnionAPI().SaveGuardianship(dataProcess(params));
    }
    /**
     * 解绑
     */
    public static Observable<BaseData> UnbindGuardianship(String token,String elderlyUserId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("elderlyUserId", elderlyUserId);

        return ConnectHttp.getUnionAPI().UnbindGuardianship(dataProcess(params));
    }

}
