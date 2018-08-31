package cn.v1.unionc_user.ui.me.oldregister;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.OldmanInfoData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.UploadAvatarUtil;

public class OldRegisterActivity3 extends BaseActivity {
    private String ElderlyUserId;
    private String pictureid;
    private boolean edit=false;
    @BindView(R.id.img_back)
    ImageView bakc;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.im_img)
    ImageView im_img;
    @BindView(R.id.im_top)
    ImageView im_top;
    @BindView(R.id.im_status)
    ImageView im_status;
    @BindView(R.id.bt_next)
    Button bt_next;
    @BindView(R.id.bt_photo)
    Button bt_photo;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @OnClick(R.id.bt_next)
    void next(){

        Intent intent=new Intent(OldRegisterActivity3.this,OldRegisterActivity4.class);
        intent.putExtra("ElderlyUserId",ElderlyUserId);
        intent.putExtra("edit",true);
        startActivity(intent);
//       goNewActivity(OldRegisterActivity3.class);
    }
    @OnClick(R.id.bt_photo)
    void photo(){
        photoDialog();
    }


    private String urlpath = "";
    private String avator = "";

    private File photoFile;
    private static final String JPEG_FILE_SUFFIX = ".png";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String CAMERA_DIR = "/v1/";
    private static final String albumName ="CameraSample";

    private String mHeadCachePath;
    private File mHeadCacheFile;




    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_old_register2);
        unbinder= ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        im_top.setBackgroundResource(R.drawable.find_register3);
        im_status.setVisibility(View.INVISIBLE);
        if(edit){
            bt_next.setClickable(true);
            bt_next.setBackgroundResource(R.drawable.blue_btn_bg);
        }else {
            bt_next.setClickable(false);
            bt_next.setBackgroundResource(R.drawable.bg_gray_btn);
        }


        try {
            photoFile = createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private File createFile() throws IOException {
        photoFile = null;
        Date dt= new Date();
        Long time= dt.getTime();
        if (UploadAvatarUtil.hasSdcard()) {
            mHeadCachePath = Environment.getExternalStorageDirectory().getPath() + File.separator +  "headCache";
        } else {
            mHeadCachePath =  "headCache";
        }
        Log.d("linshi","mHeadCachePath"+mHeadCachePath);
        File parentPath = new File(mHeadCachePath);
        if (!parentPath.exists()) {
            parentPath.mkdirs();
        }
        mHeadCacheFile = new File(parentPath, "head"+"_"+time+".png");
        if (!mHeadCacheFile.exists()) {
            try {
                mHeadCacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String fileName;
        //通过时间戳区别文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        fileName = JPEG_FILE_PREFIX+timeStamp+"_";
        fileName = "head";

        photoFile = File.createTempFile(fileName,JPEG_FILE_SUFFIX,mHeadCacheFile);

        return photoFile;
    }
    private void initData(){
        Intent intent=getIntent();
        if(intent.hasExtra("ElderlyUserId")){
            ElderlyUserId=intent.getStringExtra("ElderlyUserId");
            Log.d("linshi","ElderlyUserId:"+ElderlyUserId);
        }
        if(intent.hasExtra("edit")){
            edit=true;
            getOldman();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void photoDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_photo, null);
        bottomDialog.setContentView(contentView);
        TextView tvphtot=contentView.findViewById(R.id.tv_photo);
        TextView tvalbum=contentView.findViewById(R.id.tv_album);
        TextView tvCancel=contentView.findViewById(R.id.tv_cancel);
        tvphtot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开相机
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri contentUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //FileUriExposedException这个异常只会在Android 7.0 + 出现，当app使用file:// url 共享给其他app时， 会抛出这个异常。
                    //因为在android 6.0 + 权限需要 在运行时候检查， 其他app 可能没有读写文件的权限， 所以google在7.0的时候加上了这个限制。官方推荐使用 FileProvider 解决这个问题。
                    intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION ); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    contentUri = FileProvider.getUriForFile(context, "cn.v1.unionc_user.fileprovider", mHeadCacheFile);//通过FileProvider创建一个content类型的Uri
                } else {
                    contentUri = Uri.fromFile(mHeadCacheFile);
                }
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);//将拍取的照片保存到指定URI
                startActivityForResult(intentFromCapture, 2);

                bottomDialog.dismiss();
            }
        });
        tvalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从相册中选择
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent1, 1);

                bottomDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case 1:
//                    String replace = data.getData().toString().replace("content", "file");
                    Log.d("linshi","data.getData():"+data.getData().toString());
//                    Log.d("linshi","replace:"+replace);
                    startPhotoZoom(Uri.parse(data.getData().toString()));
//                    startPhotoZoom(data.getData());
                    break;
                // 如果是调用相机拍照时
                case 2:
//                    Log.d("linshi","data.getData2():"+data.getData().toString());
                    startPhotoZoom(Uri.fromFile(mHeadCacheFile));
//                    File temp = new File(Environment.getExternalStorageDirectory()
//                            + "/qmavator.jpg");
//                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                // 取得裁剪后的图片
                case 3:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        if (UploadAvatarUtil.hasSdcard()) {
            mHeadCachePath = Environment.getExternalStorageDirectory().getPath() + File.separator +  "headCache";
        } else {
            mHeadCachePath =  "headCache";
        }
        Log.d("linshi","mHeadCachePath"+mHeadCachePath);
        File parentPath = new File(mHeadCachePath);
        if (!parentPath.exists()) {
            parentPath.mkdirs();
        }
        Date dt= new Date();
        Long time= dt.getTime();
        mHeadCacheFile = new File(parentPath, "head"+"_"+time+".png");
        if (!mHeadCacheFile.exists()) {
            try {
                mHeadCacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mHeadCachePath = mHeadCacheFile.getAbsolutePath();
        Log.d("linshi","mHeadCachePath2:"+mHeadCachePath);
        Uri uriPath = Uri.parse("file://" + mHeadCacheFile.getAbsolutePath());
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        //intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent,3);
//        File temp = new File(Environment.getExternalStorageDirectory()
//                + "/qmavator.jpg");
//
////        mHeadCachePath = temp.getAbsolutePath();
//
//        Uri uriPath = Uri.parse("file://" + temp.getAbsolutePath());
//        //将裁剪好的图输出到所建文件中
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        //intent.putExtra("return-data", true);
//        intent.putExtra("return-data", false);
//        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
//        Log.d("linshi","setPicToView:"+new Gson().toJson(picdata));
//        Bundle extras = picdata.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            //图片路径
////            urlpath = PictureFileUtil.saveFile(context, "temphead.jpg", photo);
//            urlpath = "file://" + mHeadCacheFile.getAbsolutePath();
//            Log.d("linshi","urlpath:"+urlpath);
//            Glide.with(context).load("file://" + urlpath).into(imgAvator);
//        }
        urlpath = "file://" + mHeadCacheFile.getAbsolutePath();
        Log.d("linshi","urlpath:"+urlpath);
        Glide.get(context).clearMemory();
        Glide.with(context).load( urlpath).into(im_img);
//        uploadImage();
        updateUserInfo(new File(mHeadCacheFile.getAbsolutePath()));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);
    }

//    /**
//     * 头像上传
//     */
//    private void uploadImage() {
//        Log.d("linshi","uploadImage:"+urlpath);
//        showDialog("头像上传中...");
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        ConnectHttp.connect(UnionAPIPackage.uploadImge(token, new File(mHeadCacheFile.getAbsolutePath())), new BaseObserver<UpdateFileData>(context) {
//            @Override
//            public void onResponse(UpdateFileData data) {
//                Log.d("linshi","UpdateFileData:"+ new Gson().toJson(data));
//                closeDialog();
//                if (TextUtils.equals("4000", data.getCode())) {
//                    if(TextUtils.isEmpty(pictureid)){
//
//                        updateUserInfo(data.getPath() + "");
//                    }else{
//                        updateOldmanPhoto(data.getPath() + "");
//                    }
//                } else {
//                    showTost(data.getMessage() + "");
//                    onPhotoFail();
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                closeDialog();
//                onPhotoFail();
//            }
//        });
//
//
//    }
    private void updateUserInfo(File file) {
        Log.d("linshi","uploadImage:"+urlpath);
        showDialog("头像上传中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect((UnionAPIPackage.oldaddphoto(token,ElderlyUserId,file)), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    bt_next.setClickable(true);
                    bt_next.setBackgroundResource(R.drawable.blue_btn_bg);
                    bt_photo.setVisibility(View.INVISIBLE);
                    im_status.setBackgroundResource(R.drawable.icon_upload_success);

                } else {
                    showTost(data.getMessage() + "");
                    onPhotoFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                onPhotoFail();
            }
        });


    }
//    private void updateUserInfo(String url) {
//        Log.d("linshi","uploadImage:"+urlpath);
//        showDialog("头像上传中...");
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        ConnectHttp.connect((UnionAPIPackage.oldaddphoto(token,ElderlyUserId,url)), new BaseObserver<BaseData>(context) {
//            @Override
//            public void onResponse(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("4000", data.getCode())) {
//                    bt_next.setClickable(true);
//                    bt_next.setBackgroundResource(R.drawable.blue_btn_bg);
//                    bt_photo.setVisibility(View.INVISIBLE);
//                    im_status.setBackgroundResource(R.drawable.icon_upload_success);
//
//                } else {
//                    showTost(data.getMessage() + "");
//                    onPhotoFail();
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                closeDialog();
//                onPhotoFail();
//            }
//        });
//
//
//    }
    private void updateOldmanPhoto(String url) {
        Log.d("linshi","uploadImage:"+urlpath);
        showDialog("头像上传中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect((UnionAPIPackage.updateOldmanPhoto(token,ElderlyUserId,url,pictureid)), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    bt_next.setClickable(true);
                    bt_next.setBackgroundResource(R.drawable.blue_btn_bg);
                    bt_photo.setVisibility(View.INVISIBLE);
                    im_status.setBackgroundResource(R.drawable.icon_upload_success);

                } else {
                    showTost(data.getMessage() + "");
                    onPhotoFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                onPhotoFail();
            }
        });


    }
    protected void getOldman() {
        showDialog("请稍侯...");
        ConnectHttp.connect(UnionAPIPackage.getOldmanInfo(getToken(),ElderlyUserId), new BaseObserver<OldmanInfoData>(context) {

            @Override
            public void onResponse(OldmanInfoData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    List<OldmanInfoData.DataData.DataDataData.ImageInfo> images=data.getData().getElderLyInfo().getImages();
                    if(images.size()>1){
                        if(!TextUtils.isEmpty(images.get(1).getImageId())){
                            pictureid=images.get(1).getImageId();
                        }
                        if (TextUtils.isEmpty(images.get(1).getImagePath())) {

                            im_img.setImageResource(R.drawable.user_default);
                        } else {
                            Glide.with(context)
                                    .load(images.get(1).getImagePath())
                                    .placeholder(R.drawable.user_default).dontAnimate()
                                    .error(R.drawable.user_default)
                                    .into(im_img);

                        }
                    }
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                showTost("保存失败");
            }
        });
    }


    private void onPhotoFail(){
        if(edit){
            im_status.setBackgroundResource(R.drawable.icon_upload_fail);
        }else {
            bt_next.setClickable(false);
            bt_next.setBackgroundResource(R.drawable.bg_gray_btn);
            bt_photo.setVisibility(View.VISIBLE);
            im_status.setBackgroundResource(R.drawable.icon_upload_fail);
        }

    }
}
