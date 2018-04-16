package cn.v1.unionc_user.ui.home.health;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.view.View;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.v1.unionc_user.R;

import static cn.v1.unionc_user.ui.home.health.LogUtils.LOGD;
import static cn.v1.unionc_user.ui.home.health.LogUtils.makeLogTag;

/**
 * Created by lawrence on 14/11/3.
 */
public class Util {

    public static final String TAG = makeLogTag(Util.class);

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isExistsTheNum(int num,int ...nums){
        if(nums!=null){
            int l=nums.length;
            for (int i = 0; i < l; i++) {
                if(nums[i]==num)return true;
            }
        }
        return false;
    }

    public static boolean isAfterHoneyComb(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }


    public static String getMobileID(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wfm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        String imsi = manager.getSubscriberId();
        String imei = manager.getDeviceId();
        String macCode = wfm.getConnectionInfo().getMacAddress();
        if (null != imsi && !imsi.equals("")) {
            return imsi;
        } else if (null != macCode && !macCode.equals("")) {
            return macCode;
        } else if (null != imei && !imei.equals("")) {
            return imei;
        } else {
            return null;
        }
//		if(manager != null)
//			return manager.getDeviceId();
		/*android requirement zhangyang @modify 20111229 end*/
//		return null;
    }

    public static int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    public static int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    public static String saveBitmapToFile(String fileNmame, Bitmap bitmap){
        if(isExternalStorageWritable()){
            File dir = getPicDirectory("vodone");
            if(!dir.exists())
                dir.mkdirs();
            File file = new File(dir,fileNmame);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
                fos.flush();
                fos.close();
                return file.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public static void saveStringToClipboard(String text, Context context){
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
//            ClipData clip = ClipData.newPlainText("caibo",text);
//            clipboard.setPrimaryClip(clip);
//        }
    }

    public static File getPicDirectory(String albumName) {
        File storageDir = null;
        if (isExternalStorageWritable()) {
            storageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    albumName
            );
            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        LOGD(TAG, "failed to create directory");
                        return null;
                    }else{
                        storageDir = new File(Environment.getExternalStorageDirectory(),albumName);
                        if(!storageDir.mkdirs()){
                            if(!storageDir.exists()){
                                LOGD(TAG, "failed to create directory");
                                return null;
                            }
                        }
                    }
                }
            }
        } else {
            LOGD(TAG, "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }

    /**
     * 上传图片统一压缩到720p
     * @param fileName
     * @return
     */
    public static ByteArrayOutputStream resizeUploadFile(String fileName, int degree){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName,options);
        int targetW = 720;
        int targetH = 1280;
        int photoW = options.outWidth;
        int photoH = options.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.max(photoW / targetW, photoH / targetH);
        }
        options.inJustDecodeBounds = false;
        if(scaleFactor < 2){
            scaleFactor = 1;
        }
        options.inSampleSize = scaleFactor;
		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
        Bitmap bm = ImageUtils.rotateBitmapByDegree(bitmap, degree);
        File destination = new File(fileName);
        LOGD("Util", "original:size = " + FileUtils.sizeOf(destination)/1024);
        return compressImage(bm);
    }

    private static ByteArrayOutputStream compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，50标示压缩质量，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>500) {  //循环判断如果压缩后图片是否大于500k,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        LOGD("Util", "compressed:size = " + baos.toByteArray().length / 1024);
        return baos;
    }


    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor;
        if(Build.VERSION.SDK_INT >19){
            try{
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(uri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{ id }, null);
            }catch (Exception e){
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
            }
        }else{
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try{
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        }catch(NullPointerException e) {

        }
        return path;
    }


    public static Intent getGalleryIntent(){
        return Intent.createChooser(
                new Intent(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"), "Choose an image");
    }

    public static String toMd5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // 将加密后的字节以十六进制形式字符串返回
            return toHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转成16进制字符串 打印
     *
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789abcdef";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static boolean isSameToUsername(String username, String password) {
        if (username.equals(password)) {
            return true;
        }
        return false;
    }
    public static int doValidPassword(String password) {
        // 密码不能为相同数字
        String regex = "([0-9])\\1{" + (password.length() - 1) + "}";
        /*if (password.matches(regex)) {
            return R.string.password_same_number;
        }

        regex = ".*[A-Z].*";
        if (password.matches(regex)) {
            return R.string.password_upper_case;
        }
        regex = "[0-9]*";
        if (password.matches(regex)) {
            return R.string.password_allnum;
        }*/

        // 密码只能是小写字母，数字,下划线
        regex = "[A-Za-z0-9_]+";
        if (!password.matches(regex)) {
            return R.string.password_char_limit;
        }

        // 密码不能为连续数字
//        if (isConsecutiveNum(password, -1) || isConsecutiveNum(password, 1)) {
//            return R.string.password_consecutive_number;
//
//        }

        //密码长度
        if (password.length() < 6 || password.length() > 16) {
            return R.string.password_length_limit;
        }
        return 0;
    }

    private static boolean isConsecutiveNum(String str, int step) {
        char c = str.charAt(0);
        int num = 0;
        if (c >= '0' && c <= '9') {
            num = c - '0';
        } else {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                int n = c - '0';
                if (n == num + step) {
                    num = n;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;

    }

    public static  void showDailDialog(final Context context, String title, final String[] items) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("拨打电话", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                // 直接连接打电话
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + items[0]));
                context.startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static boolean getNetStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null){
            return info.isAvailable();
        }
        return false;
    }

}
