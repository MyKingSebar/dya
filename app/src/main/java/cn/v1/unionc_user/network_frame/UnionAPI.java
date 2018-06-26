package cn.v1.unionc_user.network_frame;

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
import cn.v1.unionc_user.model.IsBindJianhurenData;
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
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by qy on 2018/1/31.
 */

public interface UnionAPI {
    /**
     * 验证码发送
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("authcode-send")
    Observable<BaseData> getAuthCode(@FieldMap Map<String, Object> params);

    /**
     * 修改用户地址
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/update-user-address")
    Observable<BaseData> updateAdd(@FieldMap Map<String, Object> params);

    /**
     * 登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginData>
    login(@FieldMap Map<String, Object> params);

    /**
     * 获取TIM sig
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("instant-msg")
    Observable<TIMSigData> getTIMSig(@FieldMap Map<String, Object> params);


    /**
     * 获取用户信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-info")
    Observable<UserInfoData> getUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 获取首页信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/home-page2")
    Observable<HomeListData> getHomeList(@FieldMap Map<String, Object> params);

    /**
     * 获取我的/关注/医生 列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-attention")
    Observable<MeWatchingDoctorListData> getMeWatchingDoctorList(@FieldMap Map<String, Object> params);

    /**
     * 获取我的/关注/医生 列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-attention")
    Observable<MeWatchingHospitalListData> getMeWatchingHospitalList(@FieldMap Map<String, Object> params);

    /**
     * 获取我的/关注/活动 列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/user-attention-activities")
    Observable<WatchingActivityData> getMeWatchingActivityList(@FieldMap Map<String, Object> params);
    /**
     * 获取周边活动 列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/activities2")
    Observable<WatchingActivityData> getFindActivityList(@FieldMap Map<String, Object> params);
    /**
     * 获取我的/报名/活动 列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/user-signup-activities")
    Observable<WatchingActivityData> getMeApplyActivityList(@FieldMap Map<String, Object> params);

    /**
     * 获取活动弹窗
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/activities_pop_up")
    Observable<HomeListData> getPushList(@FieldMap Map<String, Object> params);


    /**
     * 8.6.	获取首页地图诊所列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/home-page")
    Observable<MapClinicData> getMapClinic(@FieldMap Map<String, Object> params);
    /**
     * 获取诊所详细信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/clinic-info")
    Observable<ClinicInfoData> getClinicInfo(@FieldMap Map<String, Object> params);


    /**
     * 获取医生详细信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/doctor-info")
    Observable<DoctorInfoData> getDoctorInfo(@FieldMap Map<String, Object> params);

    /**
     * 获取医生详细信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/doctor-info-by-param")
    Observable<DoctorInfoIdentifierData> doctorInfoByParam(@FieldMap Map<String, Object> params);
    /**
     * 通过identy获取 医院/医生  /医院id
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/jpuser-type")
    Observable<DoctorOrClinicData> doctorOrclinicByParam(@FieldMap Map<String, Object> params);


    /**
     * 上传照片
     *
     * @param data
     * @param encryption
     * @param imgs
     * @return
     */
    @Multipart
    @POST("upload/upload-image")
    Observable<UpdateFileData> uploadImge(@Query("data") String data,
                                          @Query("encryption") boolean encryption,
                                          @Part MultipartBody.Part imgs);

    /**
     * 修改用户信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/update-user-info")
    Observable<BaseData> updateUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 实名认证
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/certification")
    Observable<BaseData> certification(@FieldMap Map<String, Object> params);

    /**
     * 实名认证
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/is-certification")
    Observable<BaseData> isCertification(@FieldMap Map<String, Object> params);

    /**
     * 查询医生排班
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/doctor-schedule")
    Observable<DoctorScheduleData> doctorSchedule(@FieldMap Map<String, Object> params);

    /**
     * 查询医生回答
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/question-info")
    Observable<DoctorAnswerDetailData> getDoctorAnswer(@FieldMap Map<String, Object> params);

    /**
     * 查询医生是否签约
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/is-signed")
    Observable<IsDoctorSignData> isDoctorSign(@FieldMap Map<String, Object> params);

    /**
     * 签约医生
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/sign-family-doctor")
    Observable<BaseData> signDoctor(@FieldMap Map<String, Object> params);

    /**
     * 推荐和不推荐医生
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-doctor-evaluate-star")
    Observable<BaseData> evaluateDoctor(@FieldMap Map<String, Object> params);

    /**
     * 推荐和不推荐诊所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-clinic-evaluate-star")
    Observable<BaseData> evaluateClinic(@FieldMap Map<String, Object> params);

    /**
     * 关注取消接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/attention")
    Observable<BaseData> attention(@FieldMap Map<String, Object> params);

    /**
     * 医生和医院评价
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/evaluates")
    Observable<DoctorEvaluateData> evaluates(@FieldMap Map<String, Object> params);

    /**
     * 保存医生评价
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-doctor-evaluate")
    Observable<BaseData> saveDoctorEvaluate(@FieldMap Map<String, Object> params);
    /**
     * 保存医院评价
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-clinic-evaluate")
    Observable<BaseData> saveClinicEvaluate(@FieldMap Map<String, Object> params);

    /**
     * 查询医院活动
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/clinic-activities")
    Observable<ClinicActivityData> clinicActivities(@FieldMap Map<String, Object> params);


    /**
     * 簽約醫院活動
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("activity/sign-activities")
    Observable<BaseData> signActivities(@FieldMap Map<String, Object> params);
    /**
     * 获取推荐医生列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/recommend-doctors")
    Observable<RecommendDoctorsData> recommenddoctors(@FieldMap Map<String, Object> params);

    /**
     * 获取推荐家庭医生列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/my-recommend-doctors")
    Observable<MyRecommenDoctorsData> myrecommenddoctors(@FieldMap Map<String, Object> params);
    /**
     * 获取推荐值班医生列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/duty-doctor-list")
    Observable<MyDutyDoctorsData> mydutydoctors(@FieldMap Map<String, Object> params);


    /**
     * 获取医护到家网址
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("yh_h5/yhpage")
    Observable<HomeToHomeData> getyihu(@FieldMap Map<String, Object> params);

    /**
     * 获取送药到家网址
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("dd_h5/ddpage")
    Observable<HomeToHomeData> getsongyao(@FieldMap Map<String, Object> params);



    //智能硬件相关

    /**
     * 根据类型查询字典数据（type ：001-医生级别 002-客服电话 003-不适应症 004-心脏病类型）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("basic/basic-dict")
    Observable<HeartIndicationData> getIntelligentHardwareIndication(@FieldMap Map<String, Object> params);



    /**
     * 微信二维码相关查询
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/query-qrcode-type")
    Observable<WeiXinQRcodeData> getWeiXinQRcode(@FieldMap Map<String, Object> params);

    /**
     * 添加用户健康数据（monitorId：1-心率）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/save-health-data")
    Observable<BaseData> saveHealthData(@FieldMap Map<String, Object> params);
    /**
     * 用户健康信息列表（monitorId：1-心率）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-health-datas")
    Observable<HeartHistoryListData> getHeartListData(@FieldMap Map<String, Object> params);
    /**
     *
     用户健康信息详细（monitorId：1-心率）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-health-data-info")
    Observable<HeartHistoryData> getHeartData(@FieldMap Map<String, Object> params);

    /**
     * 删除数据（monitorId：2-oml）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/delete-user-health-data")
    Observable<BaseData> deleteOMLData(@FieldMap Map<String, Object> params);
    /**
     * 保存血压数据
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/save-health-data")
    Observable<BaseData> saveOMLData(@FieldMap Map<String, Object> params);
    /**
     * 获取血压数据
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-health-datas")
    Observable<OMLHistoryData> getOMLData(@FieldMap Map<String, Object> params);
    /**
     * 获取血压数据
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/is-medical")
    Observable<IsDoctorData> getIsDoctor(@FieldMap Map<String, Object> params);
    /**
     * 保存咨询记录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/save-doctor-conversation")
    Observable<BaseData> savetalk(@FieldMap Map<String, Object> params);
    /**
     * 获取融云token
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("rongyun/rongyun_token")
    Observable<GetRongTokenData> getRongToken(@FieldMap Map<String, Object> params);
    /**
     * 获取一键呼叫监护人信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("rongyun/chat_object_info")
    Observable<GetGuardianshipInfoData> GetGuardianshipInfo(@FieldMap Map<String, Object> params);
    /**
     *获取亲情监护列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("old-man/guardians")
    Observable<MeguardianshipData> GetGuardianshipListInfo(@FieldMap Map<String, Object> params);
    /**
     *绑定监护人
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("old-man/guardian-bound")
    Observable<BaseData>BindGuardianship(@FieldMap Map<String, Object> params);
    /**
     *保存关系
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("old-man/guardian-old-man-relation")
    Observable<BaseData> SaveGuardianship(@FieldMap Map<String, Object> params);
    /**
     *解绑监护人
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("old-man/guardian-unbound")
    Observable<BaseData> UnbindGuardianship(@FieldMap Map<String, Object> params);


    /**
     * 是否绑定监护人
     *
     * @return
     */
    @FormUrlEncoded
    @POST("old-man/has-guardian")
    Observable<IsBindJianhurenData> ishasguardian(@FieldMap Map<String, Object> params);
}
