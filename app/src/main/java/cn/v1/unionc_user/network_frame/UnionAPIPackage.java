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
import cn.v1.unionc_user.model.DoctorScheduleData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.HomeSongYaoData;
import cn.v1.unionc_user.model.IsDoctorSignData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.MapClinicData;
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.model.TIMSigData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.model.UserInfoData;
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
    public static Observable<MeWatchingDoctorListData> getMeWatchingDoctorList(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("attentType", "1");//查询类型（1：查询关注的医生 2关注的医院)
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        return ConnectHttp.getUnionAPI().getMeWatchingDoctorList(dataProcess(params));
    }
    /**
     * 获取我的/关注/医院 列表
     *
     * @return
     */
    public static Observable<MeWatchingHospitalListData> getMeWatchingHospitalList(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("attentType", "2");//查询类型（1：查询关注的医生 2关注的医院)
        params.put("pageNo", "1");
        params.put("pageSize", "100");
        return ConnectHttp.getUnionAPI().getMeWatchingHospitalList(dataProcess(params));
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
                                                     String telphone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("realName", realName);
        params.put("gender", gender);
        params.put("birthday", birthday);
        params.put("cardNo", "");
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
                                                                  String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("clinicId", clinicId);
        params.put("token", token);
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
     * 获取送药到家网址
     *
     * @param token
     * @return
     */
    public static Observable<HomeSongYaoData> getsongyao(String token
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        return ConnectHttp.getUnionappAPI().getsongyao(dataProcess(params));
    }
}
