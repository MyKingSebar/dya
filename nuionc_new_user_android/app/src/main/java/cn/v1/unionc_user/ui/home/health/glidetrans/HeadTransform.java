package cn.v1.unionc_user.ui.home.health.glidetrans;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.health.ImageUtils;

/**
  * Created by lawrence on 14/11/17.
  */
 public class HeadTransform extends BitmapTransformation {

    public static final String TRANS_ID = "glide_head_transid";
    float headWidth;
    public HeadTransform(Context context) {
        super(context);
        headWidth = context.getResources().getDimension(R.dimen.header_width);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap resource, int outWidth, int outHeight) {
//        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
//        // If no matching Bitmap is in the pool, obtain will return null, so we should allocate.
//        if (result == null) {
//            // Use ARGB_8888 since we're going to add alpha to the image.
//            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
//        }
//        // Create a Canvas backed by the result Bitmap.
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        // Draw the original Bitmap onto the result Bitmap with a transformation.
//        canvas.drawBitmap(resource, 0, 0, paint);
        float scale = headWidth/resource.getWidth();
        return ImageUtils.getCircularBitmapImage(ImageUtils.getScaleBitmap(resource, scale));
    }

    @Override
    public String getId() {
        return TRANS_ID;
    }
}
