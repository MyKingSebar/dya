package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.ActivityListReturnEventData;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.ClinicActivityData;
import cn.v1.unionc_user.model.IsBindJianhurenData;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.model.WeiXinQRcodeData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.BloodPresureHistoryRecordDetailActivity2;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresuresaveData;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.BloodPresure;
import cn.v1.unionc_user.ui.home.health.StringUtil;
import cn.v1.unionc_user.ui.me.guardianship.BindguardianshipActivity;

public class CaptureActivity extends BaseActivity {

    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_light)
    TextView tvLight;
    @BindView(R.id.tv_sao)
    TextView tvSao;
    @BindView(R.id.tv_input)
    TextView tvInput;

    private boolean lightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        mScannerView.restartPreviewAfterDelay(1000);
        super.onResume();
    }


    private void init() {
        mScannerView.setScannerOptions(new ScannerOptions.Builder()
                .setLaserLineColor(R.color.qm_blue)
                .setFrameCornerColor(R.color.qm_blue)
                .setFrameCornerWidth(5)
                .setMediaResId(R.raw.beep).build());
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                Logger.d(new Gson().toJson(rawResult));
                Logger.d(new Gson().toJson(parsedResult));
                Logger.d(new Gson().toJson(barcode));
                String text = rawResult.getText();
                Log.d("linshi", "rawResult.getText():" + rawResult.getText());
                if (rawResult.getText().contains("weixin.qq.com/")) {
                    if (rawResult.getText().contains("/q/")) {
                        try {
                            String qrCodeContentCode;
                            String[] splitText1 = text.split("q/");
                            Logger.d(Arrays.toString(splitText1));
                            if (!TextUtils.isEmpty(splitText1[1])) {
                                qrCodeContentCode = splitText1[1];
                                getweiXin(qrCodeContentCode);
                            } else {
                                Log.d("linshi", "TextUtils.isEmpty(splitText1[1])");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

//                    if (rawResult.getText().contains("clinicId=")) {
//                        //医院二维码
//                        try {
//                            String clinicId;
//                            String[] splitText1 = text.split("clinicId=");
//                            Logger.d(Arrays.toString(splitText1));
//                            if (splitText1[1].contains("&")) {
//                                String[] splitText2 = splitText1[1].split("&");
//                                clinicId = splitText2[0];
//                            } else {
//                                clinicId = splitText1[1];
//                            }
//                            if (clinicId.contains("\"")) {
//                                clinicId = clinicId.replaceAll("\"", "");
//                            }
//
//                            clinicActivities(clinicId);
////                        Intent intent = new Intent(context, SignactivityActivity.class);
////                        intent.putExtra("clinicId", clinicId);
////                        startActivityForResult(intent,1);
////                        finish();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    if (rawResult.getText().contains("doctId=")) {
//                        //医生二维码
//                        try {
//                            String doctId;
//                            String[] splitText1 = text.split("doctId=");
//                            Logger.d(Arrays.toString(splitText1));
//                            if (splitText1[1].contains("&")) {
//                                String[] splitText2 = splitText1[1].split("&");
//                                doctId = splitText2[0];
//                            } else {
//                                doctId = splitText1[1];
//                            }
//                            if (doctId.contains("\"")) {
//                                doctId = doctId.replaceAll("\"", "");
//                            }
//                            Intent intent = new Intent(context, DoctorDetailActivity.class);
//                            intent.putExtra("doctorId", doctId);
//                            intent.putExtra("source", 1 + "");
//                            startActivityForResult(intent,2);
//                            finish();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
                /**
                 * "医巴士血压二维码"+heartrate+","+lowPresure+","+heartrate+","+savedata.getRate()+","+savedata.getRateName()+","+savedata.getMeasureDate() + " " + savedata.getMeasureTime()
                 */
                else if (rawResult.getText().contains("医巴士血压二维码")) {
                    try {
                        String qrCodeContentCode;
                        String[] splitText1 = text.split("医巴士血压二维码");
                        Logger.d(Arrays.toString(splitText1));
                        if (!TextUtils.isEmpty(splitText1[1])) {
                            qrCodeContentCode = splitText1[1];
                            String[] splitText2 = qrCodeContentCode.split(",");
                            if (splitText2.length == 7) {

                                getOMLqr(splitText2);
                            } else {
                                Log.d("linshi", "splitText2:" + splitText2.toString());
                            }
                        } else {
                            Log.d("linshi", "TextUtils.isEmpty(splitText1[1])");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /**
                 * Pad二维码+elderlyUserId
                 */
                else if (rawResult.getText().contains("Pad二维码")) {
                    try {
                        String[] splitText1 = text.split("Pad二维码");
                        Logger.d(Arrays.toString(splitText1));
                        if (!TextUtils.isEmpty(splitText1[1])) {
                            checkbind2(splitText1[1]);
                        } else {
                            showTost("Pad二维码无效");
                            Log.d("linshi", "TextUtils.isEmpty(splitText1[1])");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                mScannerView.restartPreviewAfterDelay(1000);
            }
//            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
//                Logger.d(new Gson().toJson(rawResult));
//                Logger.d(new Gson().toJson(parsedResult));
//                Logger.d(new Gson().toJson(barcode));
//                String text = rawResult.getText();
//                if (rawResult.getText().contains("unionWeb/activity/clinic-activities")) {
//                    //医院二维码
//                    try {
//                        String[] splitText1 = text.split("clinicId=");
//                        Logger.d(Arrays.toString(splitText1));
//                        String clinicId = "";
//                        if (splitText1[splitText1.length - 1].contains("}")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split("\\}");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else if (splitText1[splitText1.length - 1].contains(",")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split(",");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else if (splitText1[splitText1.length - 1].contains("&")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split("&");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else {
//                            clinicId = splitText1[splitText1.length - 1];
//                        }
//                        if (clinicId.contains("\"")) {
//                            clinicId = clinicId.replaceAll("\"", "");
//                        }
//                        clinicActivities(clinicId);
////                        Intent intent = new Intent(context, SignactivityActivity.class);
////                        intent.putExtra("clinicId", clinicId);
////                        startActivityForResult(intent,1);
////                        finish();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
////                if (rawResult.getText().contains("unionWeb/scanDoctQRCode.jsp")) {
//                if (rawResult.getText().contains("page/scan.html")) {
//                    //医生二维码
//                    try {
//                        String doctId;
//                        String[] splitText1 = text.split("doctId=");
//                        Logger.d(Arrays.toString(splitText1));
//                        if (splitText1[1].contains("&")) {
//                            String[] splitText2 = splitText1[1].split("&");
//                            doctId = splitText2[0];
//                        } else {
//                            doctId = splitText1[1];
//                        }
//                        if (doctId.contains("\"")) {
//                            doctId = doctId.replaceAll("\"", "");
//                        }
//                        Intent intent = new Intent(context, DoctorDetailActivity.class);
//                        intent.putExtra("doctorId", doctId);
//                        intent.putExtra("source", 1 + "");
//                        startActivityForResult(intent,2);
//                        finish();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                mScannerView.restartPreviewAfterDelay(1000);
//            }
        });
    }


    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @OnClick({R.id.img_back, R.id.tv_light, R.id.tv_sao, R.id.tv_input})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_light:
                if (lightOn) {
                    mScannerView.toggleLight(false);
                    lightOn = false;
                } else {
                    mScannerView.toggleLight(true);
                    lightOn = true;
                }
                break;
            case R.id.tv_sao:
                break;
            case R.id.tv_input:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            finish();
        }
    }


    /**
     * 查询医院活动
     */
    private void clinicActivities(final String clinicId) {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.clinicActivities(clinicId, token, "1"), new BaseObserver<ClinicActivityData>(context) {
            @Override
            public void onResponse(ClinicActivityData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    if (data.getData().getActivities().size() > 0) {

                        Intent intent = new Intent(context, SignactivityActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("activities", (Serializable) data.getData().getActivities());
                        intent.putExtra("clinicId", clinicId);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);
                        finish();
                    } else {
                        finish();
                        Log.d("linshi", "ActivityListReturnEventData" + clinicId);
                        ActivityListReturnEventData eventData = new ActivityListReturnEventData(clinicId);
                        BusProvider.getInstance().post(eventData);
                    }
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    /**
     * 查询微信二维码
     */
    private void getweiXin(final String qrCodeContentCode) {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getWeiXinQRcode(qrCodeContentCode), new BaseObserver<WeiXinQRcodeData>(context) {
            @Override
            public void onResponse(WeiXinQRcodeData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    if (TextUtils.equals(data.getData().getType(), "0")) {
                        //医院
                        if (!TextUtils.isEmpty(data.getData().getId())) {
                            if(isLogin()){

                                clinicActivities(data.getData().getId());
                            }else{
                                showTost("请先登录");
                                goNewActivity(LoginActivity.class);
                                finish();
                            }
                        }
                    } else if (TextUtils.equals(data.getData().getType(), "1")) {
                        //医生
                        if (!TextUtils.isEmpty(data.getData().getId())) {
                            Intent intent = new Intent(context, DoctorDetailActivity.class);
                            intent.putExtra("doctorId", data.getData().getId());
                            intent.putExtra("source", 1 + "");
                            startActivityForResult(intent, 2);
                            finish();
                        }

                    }

                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    /**
     * 欧姆龙分享
     *
     * @param qrCodeContentCode
     */
    private void getOMLqr(final String[] qrCodeContentCode) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            showTost("请先登录！");
            finish();
            return;
        }
        Intent intent = new Intent(this, BloodPresureHistoryRecordDetailActivity2.class);
        BloodPresuresaveData data = new BloodPresuresaveData();
        data.setHighPressure(qrCodeContentCode[0]);
        data.setLowPressure(qrCodeContentCode[1]);
        data.setPluseRate(qrCodeContentCode[2]);
        data.setMeasureDate(qrCodeContentCode[5]);
        Map map = BloodPresure.computeRate(qrCodeContentCode[0], qrCodeContentCode[1]);
        data.setRate((int) map.get("rate"));
        data.setRateName((String) map.get("rateName"));
        intent.putExtra("savedata", data);
        intent.putExtra("first", "first");
        intent.putExtra("saoma", qrCodeContentCode[6]);
        startActivity(intent);
        finish();

//        ConnectHttp.connect(UnionAPIPackage.saveOMLData(token, "2", qrCodeContentCode[2], savedata.getMeasureTime(),"0",savedata.getBdaCode(),"欧姆龙血压仪", qrCodeContentCode[0], qrCodeContentCode[1],
//                "0","0",""), new BaseObserver<BaseData>(context) {
//
//            @Override
//            public void onResponse(BaseData data) {
//                if (TextUtils.equals("4000", data.getCode())) {
//                    showTost("保存成功");
//                } else {
//                    showTost(data.getMessage());
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                showTost("保存失败");
//            }
//        });
    }

private void checkbind(String elderlyUserId){
        final String id=elderlyUserId;
    showDialog("加载中...");
    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
    ConnectHttp.connect(UnionAPIPackage.BindGuardianship( token, elderlyUserId,""), new BaseObserver<BaseData>(context) {
        @Override
        public void onResponse(BaseData data) {
            closeDialog();
            if (TextUtils.equals("4000", data.getCode())) {

                    Intent intent = new Intent(context, BindguardianshipActivity.class);
                    intent.putExtra("elderlyUserId", id);
                    startActivity(intent);
                    finish();
            } else {
                showTost(data.getMessage() + "");
            }
        }

        @Override
        public void onFail(Throwable e) {
            closeDialog();
        }
    });
}

private void checkbind2(String elderlyUserId){
        final String id=elderlyUserId;
    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
    ConnectHttp.connect(UnionAPIPackage.GetGuardianshipListInfo(token,""), new BaseObserver<MeguardianshipData>(context) {
        @Override
        public void onResponse(MeguardianshipData data) {

            if (TextUtils.equals("4000", data.getCode())) {
                if(null==data.getData().getGuardian()){
                    Intent intent = new Intent(context, BindguardianshipActivity.class);
                    intent.putExtra("elderlyUserId", id);
                    startActivity(intent);
                    finish();
                    return;
                }
                if (data.getData().getGuardian().size() > 0) {
                    showTost("您已绑定监护人");
                    finish();
                }else{
                    Intent intent = new Intent(context, BindguardianshipActivity.class);
                    intent.putExtra("elderlyUserId", id);
                    startActivity(intent);
                    finish();
                }
                closeDialog();
            } else {
                showTost(data.getMessage());
            }
        }

        @Override
        public void onFail(Throwable e) {
            closeDialog();
        }
    });




}

}
