package cn.v1.unionc_user.ui.home.health.glidetrans;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.home.health.ImageUtils;

/**
 * Created by lawrence on 14/11/17.
 */
public class HeadPicTransform extends BitmapTransformation {

    public static final String PREVIEW_ID = "glide_headPic_id";
    int mContainerViewHeight;
    public HeadPicTransform(Context context, int viewHeight) {
        super(context);
        this.mContainerViewHeight = viewHeight;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        float scaleX = UnioncApp.getInstance().displayMetrics.widthPixels/toTransform.getWidth();
        float scaleY = mContainerViewHeight /toTransform.getHeight();

        return ImageUtils.getScaleBitmap(toTransform,scaleX,scaleY);
    }

    @Override
    public String getId() {
        return PREVIEW_ID;
    }
}
