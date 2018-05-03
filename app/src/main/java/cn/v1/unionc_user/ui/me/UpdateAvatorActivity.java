package cn.v1.unionc_user.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.UploadAvatarUtil;
import cn.v1.unionc_user.view.CircleImageView;

public class UpdateAvatorActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.img_update_avator)
    CircleImageView imgAvator;
    @BindView(R.id.tv_album)
    TextView tvAlbum;
    @BindView(R.id.tv_camera)
    TextView tvCamera;

    private String urlpath = "";
    private String avator = "";

    private File photoFile;
    private static final String JPEG_FILE_SUFFIX = ".png";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String CAMERA_DIR = "/v1/";
    private static final String albumName ="CameraSample";

    private String mHeadCachePath;
    private File mHeadCacheFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avator);
        ButterKnife.bind(this);
        initData();
    }


    @OnClick({R.id.img_back, R.id.tv_right, R.id.tv_album, R.id.tv_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                //保存
                uploadImage();
                break;
            case R.id.tv_album:
                //从相册中选择
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_camera:


//                File file = UploadAvatarUtil.getFile(imgUrl, imageFileNameTemp);
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
//                //拍照
//                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                //下面这句指定调用相机拍照后的照片存储的路径
//                // 指定照片保存路径（SD卡）
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上使用FileProvider获取Uri
//                    intent2.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    String authority =  getPackageName()+".provider";
//                    Uri contentUri = FileProvider.getUriForFile(context, authority,photoFile);
//                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//                } else {
//                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                }
////                intent2.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(photoFile));
//                startActivityForResult(intent2, 2);


//                //拍照
//                Intent intent2 = new Intent(
//                        MediaStore.ACTION_IMAGE_CAPTURE);
//                //下面这句指定调用相机拍照后的照片存储的路径
//                Uri uri=Uri.fromFile(new File(Environment
//                                .getExternalStorageDirectory(),
//                                "qmavator.jpg"));
//                Log.d("linshi","fileurl:"+uri);
//                intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(intent2, 2);
                break;
        }
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


    private void initData() {
        if (getIntent().hasExtra("avator")) {
            avator = getIntent().getStringExtra("avator");
            Glide.with(context).load(avator)
                    .placeholder(R.drawable.icon_default_avator).dontAnimate()
                    .error(R.drawable.icon_default_avator)
                    .into(imgAvator);
        }

        try {
            photoFile = createFile();
        } catch (IOException e) {
            e.printStackTrace();
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
        Glide.with(context).load( urlpath).into(imgAvator);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);
    }


    /**
     * 头像上传
     */
    private void uploadImage() {
        Log.d("linshi","uploadImage:"+urlpath);
        showDialog("头像上传中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.uploadImge(token, new File(mHeadCacheFile.getAbsolutePath())), new BaseObserver<UpdateFileData>(context) {
            @Override
            public void onResponse(UpdateFileData data) {
                Log.d("linshi","UpdateFileData:"+ new Gson().toJson(data));
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    updateUserInfo(data.getPath() + "");
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
     * 修改用户信息
     */
    private void updateUserInfo(String avator) {
        showDialog("修改用户资料...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.updateUserInfo(token, "", "", avator, "", ""),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(urlpath)) {
                                Intent intent = new Intent();
                                intent.putExtra("avator", urlpath + "");
                                setResult(Activity.RESULT_OK, intent);
//                                setResult(AVATOR, intent);
                            }
                            finish();
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

    //获得文件路径,这里以public为例

    private File getPhotoDir(){
        File storDirPrivate = null;
        File storDirPublic = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

            //private,只有本应用可访问
            storDirPrivate = new File (
                    Environment.getExternalStorageDirectory()
                            + CAMERA_DIR
                            + albumName
            );

            //public 所有应用均可访问
            storDirPublic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    albumName);

            if (storDirPrivate != null) {
                if (! storDirPrivate.mkdirs()) {
                    if (! storDirPrivate.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
        }else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storDirPublic;//或者return storDirPrivate;

    }
}
