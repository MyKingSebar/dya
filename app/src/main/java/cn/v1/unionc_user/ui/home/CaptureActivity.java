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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.ActivityListReturnEventData;
import cn.v1.unionc_user.model.ClinicActivityData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

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
                if (rawResult.getText().contains("http://www.yibashi.cn/page/scan.html")) {


                    if (rawResult.getText().contains("clinicId=")) {
                        //医院二维码
                        try {
                            String clinicId;
                            String[] splitText1 = text.split("clinicId=");
                            Logger.d(Arrays.toString(splitText1));
                            if (splitText1[1].contains("&")) {
                                String[] splitText2 = splitText1[1].split("&");
                                clinicId = splitText2[0];
                            } else {
                                clinicId = splitText1[1];
                            }
                            if (clinicId.contains("\"")) {
                                clinicId = clinicId.replaceAll("\"", "");
                            }

                            clinicActivities(clinicId);
//                        Intent intent = new Intent(context, SignactivityActivity.class);
//                        intent.putExtra("clinicId", clinicId);
//                        startActivityForResult(intent,1);
//                        finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (rawResult.getText().contains("doctId=")) {
                        //医生二维码
                        try {
                            String doctId;
                            String[] splitText1 = text.split("doctId=");
                            Logger.d(Arrays.toString(splitText1));
                            if (splitText1[1].contains("&")) {
                                String[] splitText2 = splitText1[1].split("&");
                                doctId = splitText2[0];
                            } else {
                                doctId = splitText1[1];
                            }
                            if (doctId.contains("\"")) {
                                doctId = doctId.replaceAll("\"", "");
                            }
                            Intent intent = new Intent(context, DoctorDetailActivity.class);
                            intent.putExtra("doctorId", doctId);
                            intent.putExtra("source", 1 + "");
                            startActivityForResult(intent,2);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        if(resultCode==1){
            finish();
        }
    }


    /**
     * 查询医院活动
     */
    private void clinicActivities(final String clinicId) {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.clinicActivities(clinicId, token,"1"), new BaseObserver<ClinicActivityData>(context) {
            @Override
            public void onResponse(ClinicActivityData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    if(data.getData().getActivities().size()>0){

                        Intent intent = new Intent(context, SignactivityActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("activities",(Serializable) data.getData().getActivities());
                        intent.putExtra("clinicId", clinicId);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,1);
                        finish();
                    }else{
                        finish();
                        Log.d("linshi","ActivityListReturnEventData"+clinicId);
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


}
