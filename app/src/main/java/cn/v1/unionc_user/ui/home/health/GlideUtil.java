package cn.v1.unionc_user.ui.home.health;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.home.health.glidetrans.NormalBitmapTrans;

/**
 * Created by lawrence on 14/11/12.
 */
public class GlideUtil {
    public static final String IMAGE_HOST = "http://t.diyicai.com";
    public static final String APP_DIR = "/vodone/caibo";
    public static final String APP_CACHE_DIR = "/cache";

    /**
     * 默认显示图片原来的大小
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setNormalImage(Context context, String url, final ImageView img, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                img.setImageDrawable(resource);
            }
        });
    }

    public static void setNormalImageSign(Context context, String url, final ImageView img, StringSignature upDateTimeMillions, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).
                load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).
                placeholder(defaultId).
                error(errorId).
                signature(upDateTimeMillions);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                img.setImageDrawable(resource);
            }
        });
    }

    /**
     * 默认显示图片原来的大小  解决 首页 图片颜色变绿
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setHomeNormalImage(Context context, String url, final ImageView img, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                img.setImageDrawable(resource);
            }
        });
    }

    /**
     * 默认显示图片原来的大小
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setHomeCacheNormalImage(Context context, String url, final ImageView img, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                img.setImageDrawable(resource);
            }
        });
    }

    /**
     * 设置override，下载图片大小
     * @param context
     * @param url
     * @param img
     * @param width 设置图片宽度
     * @param height 设置图片高度
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setCircleOverrideImage(Context context, String url, ImageView img, int width, int height, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        if(width>0 && height > 0){
            builder.override((int) (context.getResources().getDisplayMetrics().density*width+ 0.5f),
                    (int) (context.getResources().getDisplayMetrics().density*height+ 0.5f)).centerCrop();
//            builder.override(width,height).centerCrop();
        }
        builder.into(img);
    }

    /**
     * 设置override，下载图片大小
     * @param context
     * @param url
     * @param img
     * @param width 设置图片宽度
     * @param height 设置图片高度
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setHomeOverrideImage(Context context, String url, ImageView img, int width, int height, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        if(width>0 && height > 0){
//            builder.override((int) (context.getResources().getDisplayMetrics().density*width+ 0.5f),
//                    (int) (context.getResources().getDisplayMetrics().density*height+ 0.5f)).centerCrop();
            builder.override(width,height).centerCrop();
        }
        builder.into(img);
    }

    public static void setHomeBannerOverrideImage(Context context, String url, ImageView img, int width, int height, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        if(width>0 && height > 0){
//            builder.override((int) (context.getResources().getDisplayMetrics().density*width+ 0.5f),
//                    (int) (context.getResources().getDisplayMetrics().density*height+ 0.5f)).centerCrop();
            builder.override(width,height);
        }
        builder.into(img);
    }


    /**
     * 默认显示图片—本地
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setLocalImage(Context context, String url, ImageView img, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(img);
    }

    /**
     * 设置override，下载图片大小
     * @param context
     * @param url
     * @param img
     * @param width 设置图片宽度
     * @param height 设置图片高度
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setOverrideImage(Context context, String url, ImageView img, int width, int height, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        if(width>0 && height > 0){
            builder.override(width,height).centerCrop();
        }
        builder.into(img);
    }

    /**
     * 默认将图片撑满view
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     * @param trans
     */
    public static void setMatchImage(Context context, String url, ImageView img, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).placeholder(defaultId).error(errorId);
        if(trans.length > 0)
            builder.transform(trans);
        builder.into(img);
    }

    /**
     * 图片预览用到，会将图片适应到全屏，同时通过photoview可以手动缩放
     * @param context
     * @param url
     * @param photoView
     * @param defaultId
     * @param errorId
     */
    public static void setPhotoViewImg(Context context, String url, final PhotoView photoView, int defaultId, int errorId){
        Glide.with(context.getApplicationContext()).load(url).asBitmap().placeholder(defaultId).error(errorId).listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(final Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                photoView.post(new Runnable() {
                    @Override
                    public void run() {
                        float scale = UnioncApp.getInstance().getResources().getDisplayMetrics().widthPixels * 1.0f / (UnioncApp.getInstance().getResources().getDisplayMetrics().heightPixels * 1.0f / resource.getHeight() * resource.getWidth());
                        photoView.setScale(scale, 0, 0, false);
                    }
                });
                return false;
            }
        }).into(photoView);
    }

    /**
     * 设置头像
     * @param context
     * @param url
     * @param img
     * @param defaultId
     * @param errorId
     */
    public static void setHeadImage(Context context, String url, final ImageView img, int defaultId, int errorId){
        if(context == null || url == null) {
            return;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        Glide.with(context.getApplicationContext()).load(url).placeholder(defaultId).error(errorId).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                img.setImageDrawable(resource);
            }
        });
    }


    public static void setLocalImageHead(Context context, String fileName, ImageView img, int defaultId, int errorId){
        Glide.with(context.getApplicationContext()).load(fileName).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId).into(img);

    }

    public static void setLocalImageHeadSignture(Context context, String fileName, ImageView img, StringSignature stringSignature, int defaultId, int errorId){
        Glide.with(context.getApplicationContext()).load(fileName).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).signature(stringSignature).error(errorId).into(img);

    }

    synchronized public static File getAppCacheStoragePath(Context context) {

        File appdir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            File storage = Environment.getExternalStorageDirectory();

            appdir = new File(storage, APP_DIR);
            if (!appdir.exists()) {
                appdir.mkdirs();
            }
        } else {
            appdir = context.getCacheDir();
        }

        File cache = new File(appdir,APP_CACHE_DIR);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;

    }
    /**
     * 获得截图的存储目录
     *
     * @param context
     * @return
     */
    synchronized public static File getAppShotStoragePath(Context context) {

        File appdir = null;
        File cache = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            appdir = Environment.getExternalStorageDirectory();
            cache = new File(appdir, APP_CACHE_DIR);
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileName = "yhshot"+formate.format(new Date())+".txt";
            try {
                File shotfile = new File(cache, fileName);
                if(shotfile.exists())
                    shotfile.delete();
            } catch (IllegalStateException e) {
                cache = new File(context.getCacheDir(), APP_CACHE_DIR);
            }
        } else {
            appdir = context.getCacheDir();
            cache = new File(appdir, APP_CACHE_DIR);
        }
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

    public static void setLinearLayoutImage(Context context, String url, final LinearLayout ll, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                ll.setBackgroundDrawable(resource);
            }
        });
    }

    public static void setLinearLayoutImage(Context context, String url, final LinearLayout ll, int width, int height, int defaultId, int errorId, BitmapTransformation... trans){
        if(context == null || url == null)
            return;
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = IMAGE_HOST+url;
        }
        DrawableRequestBuilder builder = Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultId).error(errorId);
        if(trans.length == 0){
            trans = new BitmapTransformation[1];
            trans[0] = new NormalBitmapTrans(context.getApplicationContext());
        }
        builder.transform(trans);
        if(width>0 && height > 0){
//            builder.override((int) (context.getResources().getDisplayMetrics().density*width+ 0.5f),
//                    (int) (context.getResources().getDisplayMetrics().density*height+ 0.5f)).centerCrop();
            builder.override(width,height).centerCrop();
        }
        builder.into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                ll.setBackgroundDrawable(resource);
            }
        });
    }

}
