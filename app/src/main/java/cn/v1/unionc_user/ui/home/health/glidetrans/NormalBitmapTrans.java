package cn.v1.unionc_user.ui.home.health.glidetrans;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lawrence on 14/11/18.
 */
public class NormalBitmapTrans extends BitmapTransformation {

    public NormalBitmapTrans(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return toTransform;
    }

    @Override
    public String getId() {
        return "NormalBitmapTrans";
    }
}
