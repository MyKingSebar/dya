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
public class PreviewTransform extends BitmapTransformation {

    public static final String PREVIEW_ID = "glide_preview_id";
    public PreviewTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        float scale = UnioncApp.getInstance().displayMetrics.widthPixels/toTransform.getWidth();
        return ImageUtils.getScaleBitmap(toTransform,scale);
    }

    @Override
    public String getId() {
        return PREVIEW_ID;
    }
}
