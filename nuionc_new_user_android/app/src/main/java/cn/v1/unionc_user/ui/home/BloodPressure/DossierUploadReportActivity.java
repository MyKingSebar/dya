package cn.v1.unionc_user.ui.home.BloodPressure;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.health.ImageUtils;

/**
 * Created by An4 on 2016/10/8.
 */
public class DossierUploadReportActivity extends BaseActivity {

    @BindView(R.id.upload_ll1)
    LinearLayout upLoadLl1;
    @BindView(R.id.upload_ll2)
    LinearLayout upLoadLl2;
    @BindViews({R.id.upload_img1, R.id.upload_img2, R.id.upload_img3,
            R.id.upload_img4, R.id.upload_img5, R.id.upload_img6})
    ImageView[] mImageViewList;
    @BindViews({R.id.upload_rl1, R.id.upload_rl2, R.id.upload_rl3,
            R.id.upload_rl4, R.id.upload_rl5, R.id.upload_rl6})
    RelativeLayout[] mRelativeLayoutList;
    @BindViews({R.id.delete_img1, R.id.delete_img2, R.id.delete_img3,
            R.id.delete_img4, R.id.delete_img5, R.id.delete_img6})
    ImageView[] mImageViewDeleteList;

    @OnClick({R.id.delete_img1, R.id.delete_img2, R.id.delete_img3,
            R.id.delete_img4, R.id.delete_img5, R.id.delete_img6})
    void delePic1(ImageView delImg) {
        int tagIndex = Integer.parseInt(delImg.getTag().toString());
        deleteImg(tagIndex);
    }

    @OnClick({R.id.upload_img1, R.id.upload_img2, R.id.upload_img3, R.id.upload_img4,
            R.id.upload_img5, R.id.upload_img6})
    void choosePic(ImageView imageView) {
        int i = 0;
        for (ImageView mImageView : mImageViewList) {
            if (mImageView == imageView) {
                if (bitmals.get(i) == null) {
                    showChoosePicDialog();
                } else {
                    showPic(i);
                }
                break;
            }
            i++;
        }
    }

    @OnClick(R.id.upload_save_btn)
    void savePic() {
        uploadNum = 0;
        if (picLocalList.size() == 0) {
            doSaveData();
        } else {
            showDialog("上传中...");
            for (int i = 0; i < picLocalList.size(); i++) {
                final int finalI = i;
                if (picLocalList.get(i).startsWith("http://")) {
                    uploadNum++;
                    picUrlList.set(finalI, picLocalList.get(i));
                    if (uploadNum == picLocalList.size()) {
                        doSaveData();
                    }
                } else {
//                    bindObservable(mAppClient.uploadPicNew(picLocalList.get(i), UpLoadServiceEnmu.UPLOADJIANCHAZILIAO.getName(), UpLoadServiceEnmu.UPLOADJIANCHAZILIAO.getId()), new Action1<UploadPicData>() {
//                        @Override
//                        public void call(UploadPicData uploadPicData) {
//                            uploadNum++;
//                            String picUrls = "";
//                            if (!StringUtil.checkNull(uploadPicData.getUrl())) {
//                                picUrls = uploadPicData.getUrl();
//                            }
//                            picUrlList.set(finalI, picUrls);
//                            if (uploadNum == picLocalList.size()) {
//                                doSaveData();
//                            }
//                        }
//                    }, new ErrorAction(this) {
//                        @Override
//                        public void call(Throwable throwable) {
//                            uploadNum++;
//                            if (uploadNum == picLocalList.size()) {
//                                doSaveData();
//                            }
//                        }
//                    });
                }
            }
        }
    }

    ArrayList<String> picUrlList = new ArrayList<>();
    ArrayList<String> picLocalList = new ArrayList<>();
    ArrayList<Bitmap> bitmals = new ArrayList<>();
    ArrayList<Bitmap> bitmalsTemp = new ArrayList<>();
    String fileName1 = "";
    private TextView mTakePicButton, mGalleryButton, photodialog_cancle_btn;
    AlertDialog photoDialog;
    private final static int REQEST_CODE_CAMERA = 100; //拍照
    private final static int REQEST_CODE_ALBUM = 101; //相册
    int mWidths, mHeights;
    Bitmap defaultBit;
    int uploadNum = 0;
    String pic1 = "", pic2 = "", pic3 = "", pic4 = "", pic5 = "", pic6 = "";
    String patientInfoId = "", monitorId = "", dateStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossieruploadreport);
        initView();
        initData();
    }

    private void initView() {
        defaultBit = BitmapFactory.decodeResource(getResources(), R.drawable.upload_report_bg);
        mWidths = defaultBit.getWidth();
        mHeights = defaultBit.getHeight();
        bitmals.add(0, null);
        bitmals.add(1, null);
        bitmals.add(2, null);
        bitmals.add(3, null);
        bitmals.add(4, null);
        bitmals.add(5, null);
        picUrlList.add(0, "");
        picUrlList.add(1, "");
        picUrlList.add(2, "");
        picUrlList.add(3, "");
        picUrlList.add(4, "");
        picUrlList.add(5, "");
    }

    private void initData() {
        if (getIntent().hasExtra("piclist")) {
            picLocalList.addAll(getIntent().getStringArrayListExtra("piclist"));
            for (int i = 0; i < picLocalList.size(); i++) {
                showNetImg(picLocalList.get(i), mImageViewList[i], i);
            }
        }
        if (getIntent().hasExtra("monitorId"))
            monitorId = getIntent().getStringExtra("monitorId");
        if (getIntent().hasExtra("patientInfoId"))
            patientInfoId = getIntent().getStringExtra("patientInfoId");
        if (getIntent().hasExtra("dateStr"))
            dateStr = getIntent().getStringExtra("dateStr");
    }

    public void showChoosePicDialog() {
        photoDialog = new AlertDialog.Builder(this).show();
        photoDialog.getWindow().setContentView(R.layout.include_choosepic_layout);
        WindowManager.LayoutParams layoutParams = photoDialog.getWindow().getAttributes();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        photoDialog.getWindow().setAttributes(layoutParams);
        photoDialog.setCanceledOnTouchOutside(false);
        mTakePicButton = (TextView) photoDialog.findViewById(R.id.takephoto);
        mGalleryButton = (TextView) photoDialog.findViewById(R.id.gallery);
        photodialog_cancle_btn = (TextView) photoDialog.findViewById(R.id.photodialog_cancle_btn);
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, REQEST_CODE_ALBUM);
                photoDialog.dismiss();
            }
        });
        mTakePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName1 = System.currentTimeMillis() + "_yihu.jpg";
                File file = new File(CommonContract.KEY_FILEPATH, fileName1);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(file));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intent, REQEST_CODE_CAMERA);
                photoDialog.dismiss();
            }
        });
        photodialog_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQEST_CODE_CAMERA && resultCode == RESULT_OK) {
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonContract.KEY_FILEPATH + "/" + fileName1, ops);
            int destWidth = (int) mWidths;
            int destHeight = (int) mWidths;
            int sourceWidth = ops.outWidth;
            int sourceHeight = ops.outHeight;

            int scale = Math.max(sourceWidth / destWidth, sourceHeight / destHeight);
            if (scale < 1) {
                scale = 1;
            }
            ops.inJustDecodeBounds = false;
            ops.inSampleSize = scale;
            //bm.recycle();
            Bitmap bm = BitmapFactory.decodeFile(CommonContract.KEY_FILEPATH + "/" + fileName1, ops);
            int degree = ImageUtils.getBitmapDegree(CommonContract.KEY_FILEPATH + "/" + fileName1);
            Bitmap bitmap = ImageUtils.rotateBitmapByDegree(bm, degree);
            picLocalList.add(CommonContract.KEY_FILEPATH + "/" + fileName1);
            bitmals.set(picLocalList.size() - 1, bitmap);
            updateImg();
        } else if (requestCode == REQEST_CODE_ALBUM && resultCode == RESULT_OK) {
            System.out.println("data2-->" + data);
            String fileName2 = null;
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            String projection[] = {MediaStore.Images.ImageColumns.DATA,};
            Cursor cursor = cr.query(uri, projection, null, null, null);
            if (cursor == null) {
                showTost("图片未找到！");
                return;
            }
            if (cursor.moveToFirst()) {
                do {
                    fileName2 = cursor.getString(cursor.getColumnIndex("_data"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            if (uri == null) {
                Bundle b = data.getExtras();
                if (b != null) {
                    Bitmap bm = (Bitmap) b.get("data");
                    picLocalList.add(fileName2);
                    bitmals.set(picLocalList.size() - 1, bm);
                    updateImg();
                }

            } else {
                try {
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(cr.openInputStream(uri), null, ops);
                    int destWidth = (int) mWidths;
                    int destHeight = (int) mHeights;
                    int sourceWidth = ops.outWidth;
                    int sourceHeight = ops.outHeight;

                    int scale = Math.max(sourceWidth / destWidth, sourceHeight / destHeight);
                    if (scale < 1) {
                        scale = 1;
                    }
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = scale;
                    Rect rect = new Rect(0, 0, (int) mWidths, (int) mHeights);
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), rect, ops);
                    int degree = ImageUtils.getBitmapDegree(fileName2);
                    Bitmap bm = ImageUtils.rotateBitmapByDegree(bitmap, degree);
                    picLocalList.add(fileName2);
                    bitmals.set(picLocalList.size() - 1, bm);
                    updateImg();
                } catch (FileNotFoundException e) {
                    //Log.e("Exception", e.getMessage(),e);
                }
            }

        }
    }

    public void deleteImg(int index) {
        bitmals.set(index, null);
        picLocalList.remove(index);
        updateImg();
    }

    public void updateImg() {
        bitmalsTemp.clear();
        for (Bitmap bitmap : bitmals) {
            if (bitmap != null) {
                bitmalsTemp.add(bitmap);
            }
        }
        if (bitmalsTemp.size() > 0) {
            for (int i = 0; i < 6; i++) {
                if (i <= bitmalsTemp.size() - 1) {
                    bitmals.set(i, bitmalsTemp.get(i));
                } else {
                    bitmals.set(i, null);
                }
            }
        }
        for (int j = 0; j < 6; j++) {
            mImageViewList[j].setImageBitmap(bitmals.get(j) == null ? defaultBit : bitmals.get(j));
        }
        ShowOrHideView();
    }

    private void ShowOrHideView() {
        if (picLocalList.size() > 2) {
            upLoadLl2.setVisibility(View.VISIBLE);
        } else {
            upLoadLl2.setVisibility(View.GONE);
        }
        for (int i = 0; i < 6; i++) {
            if (i <= picLocalList.size()) {
                mRelativeLayoutList[i].setVisibility(View.VISIBLE);
                if (bitmals.get(i) != null)
                    mImageViewDeleteList[i].setVisibility(View.VISIBLE);
                else
                    mImageViewDeleteList[i].setVisibility(View.INVISIBLE);
            } else {
                mRelativeLayoutList[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showNetImg(String url, final ImageView mImgView, final int index) {
        Glide.with(this).load(url).asBitmap().placeholder(R.drawable.ic_default_for_list1).error(-1).listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                bitmals.set(index, resource);
                ShowOrHideView();
                return false;
            }
        }).into(mImgView);
    }

    void showPic(int index) {
        Intent intent = new Intent(this, DossierPhotoActivity.class);
        intent.putStringArrayListExtra("picLocalList", picLocalList);
        intent.putExtra("nowIndex", index);
        startActivity(intent);
    }

    public void doSaveData() {
        closeDialog();
        String picurlDatas = "";
        if (picUrlList.size() > 0) {
            for (int i = 0; i < picUrlList.size(); i++) {
                if (!TextUtils.isEmpty(picUrlList.get(i))) {
                    picurlDatas = picurlDatas + "," + picUrlList.get(i);
                }
            }
            picurlDatas = picurlDatas.replaceFirst(",", "");
        }
        Log.e("ceshi", picurlDatas);
        Intent intent = new Intent();
        intent.putExtra("picUrlList", picurlDatas);
        setResult(RESULT_OK, intent);
        finish();
//        pic1 = picUrlList.get(0);
//        pic2 = picUrlList.get(1);
//        pic3 = picUrlList.get(2);
//        pic4 = picUrlList.get(3);
//        pic5 = picUrlList.get(4);
//        pic6 = picUrlList.get(5);
//        updateHealthOtherData("", "", monitorId,dateStr ,"" , "", "", "", "", "", "", "", pic1, pic2, pic3, pic4, pic5, pic6, "");
    }

    public void updateHealthOtherData(String id, String cureMedicine, String monitorId, String monitorDate,
                                      String data1, String data2, String data3, String data4, String data5,
                                      String data6, String data7, String data8, String pic1, String pic2, String pic3, String pic4, String pic5,
                                      String pic6, String diabetesType) {
//        bindObservable(mAppClient.saveHealthData(id, cureMedicine, monitorId, patientInfoId, monitorDate, data1, data2, data3, data4, data5, data6,
//                data7, data8, pic1, pic2, pic3, pic4, pic5, pic6, diabetesType), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData baseData) {
//                showToast(baseData.getMessage());
//                if (baseData.getCode().equals("0000")) {
//                    setResult(RESULT_OK);
//                    DossierUploadReportActivity.this.finish();
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//            }
//        });
    }

}
