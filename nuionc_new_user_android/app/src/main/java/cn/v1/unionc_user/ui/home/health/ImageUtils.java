package cn.v1.unionc_user.ui.home.health;

//import android.annotation.TargetApi;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v8.renderscript.Allocation;

import java.io.IOException;

import cn.v1.unionc_user.UnioncApp;

//import android.os.Build;

public class ImageUtils {
    public static Bitmap getCircularBitmapImage(Bitmap source, boolean needRecycle) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source && needRecycle) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }
    public static Bitmap getCircularBitmapImage(Bitmap source) {
        return getCircularBitmapImage(source,true);
    }

    public static Drawable getRoundedBitmap(float conorradius, Bitmap source) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(UnioncApp.getInstance().getResources(), source);
        roundedBitmapDrawable.setCornerRadius(conorradius);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }

    public static Bitmap getScaleBitmap(Bitmap resource, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap result = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
        if (result != resource)
            resource.recycle();
        return result;
    }

    public static Bitmap getScaleBitmap(Bitmap resource, float scaleX, float ScaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, ScaleY);
        Bitmap result = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
        if (result != resource)
            resource.recycle();
        return result;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurBitmap(Bitmap bitmap, boolean needRecycle){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        android.support.v8.renderscript.RenderScript rs = android.support.v8.renderscript.RenderScript.create(UnioncApp.getContext());

        //Create an Intrinsic Blur Script using the Renderscript
        android.support.v8.renderscript.ScriptIntrinsicBlur blurScript = android.support.v8.renderscript.ScriptIntrinsicBlur.create(rs, android.support.v8.renderscript.Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        if (needRecycle) {
            bitmap.recycle();
        }

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }

    public static Bitmap blurBitmap(Bitmap bitmap){
        return blurBitmap(bitmap,true);
    }

}