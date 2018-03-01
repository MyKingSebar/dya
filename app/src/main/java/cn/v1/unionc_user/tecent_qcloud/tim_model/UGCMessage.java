package cn.v1.unionc_user.tecent_qcloud.tim_model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.ugc.TIMUGCCover;
import com.tencent.imsdk.ext.ugc.TIMUGCElem;
import com.tencent.imsdk.ext.ugc.TIMUGCVideo;

import java.io.File;

import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.tecent_qcloud.ChatAdapter;
import cn.v1.unionc_user.tecent_qcloud.VideoActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TIMFileUtil;

/**
 * 小视频消息
 */

public class UGCMessage extends Message {
    private static final String TAG = "UGCMessage";


    public UGCMessage(TIMMessage message) {
        this.message = message;
    }


    public UGCMessage(String filePath, String coverPath, long duration) {
        message = new TIMMessage();

        TIMUGCElem elem = new TIMUGCElem();
        TIMUGCCover cover = new TIMUGCCover();
        File file = new File(coverPath);
        int height = 0, width = 0;
        if (file.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(coverPath, options);
            height = options.outHeight;
            width = options.outWidth;
        }
        cover.setHeight(height);
        cover.setWidth(width);
        cover.setType("PNG");
        TIMUGCVideo video = new TIMUGCVideo();
        video.setType("MP4");
        video.setDuration(duration);
        elem.setCover(cover);
        elem.setVideo(video);

        elem.setVideoPath(filePath);
        elem.setCoverPath(coverPath);
        message.addElement(elem);
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(final ChatAdapter.ViewHolder viewHolder, final Context context) {
        clearView(viewHolder);
        if (checkRevoke(viewHolder)) return;
        final TIMUGCElem e = (TIMUGCElem) message.getElement(0);
        switch (message.status()) {
            case Sending:
                showSnapshot(viewHolder, BitmapFactory.decodeFile(e.getCoverPath(), new BitmapFactory.Options()));
                break;
            case SendSucc:

                final TIMUGCCover snapshot = e.getCover();
                if (TIMFileUtil.isCacheFileExist(e.getFileId())) {
                    showSnapshot(viewHolder, BitmapFactory.decodeFile(TIMFileUtil.getCacheFilePath(e.getFileId()), new BitmapFactory.Options()));
                } else {
                    snapshot.getImage(TIMFileUtil.getCacheFilePath(e.getFileId()), new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "get snapshot failed. code: " + i + " errmsg: " + s);
                        }

                        @Override
                        public void onSuccess() {
                            showSnapshot(viewHolder, BitmapFactory.decodeFile(TIMFileUtil.getCacheFilePath(e.getFileId()), new BitmapFactory.Options()));
                        }
                    });
                }
                final String fileName = e.getFileId() + "_video";
                if (!TIMFileUtil.isCacheFileExist(fileName)) {
                    e.getVideo().getVideo(TIMFileUtil.getCacheFilePath(fileName), new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "get video failed. code: " + i + " errmsg: " + s);
                        }

                        @Override
                        public void onSuccess() {
                            setVideoEvent(viewHolder, fileName, context);
                        }
                    });
                } else {
                    setVideoEvent(viewHolder, fileName, context);
                }
                break;
        }
        showStatus(viewHolder);
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        String str = getRevokeSummary();
        if (str != null) return str;
        return "[小视频]";
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {

    }


    /**
     * 显示缩略图
     */
    private void showSnapshot(final ChatAdapter.ViewHolder viewHolder, final Bitmap bitmap) {
        if (bitmap == null) return;
        ImageView imageView = new ImageView(UnioncApp.getInstance());
        imageView.setImageBitmap(bitmap);
        getBubbleView(viewHolder).addView(imageView);
    }

    private void showVideo(String path, Context context) {
        if (context == null) return;
        final TIMUGCElem e = (TIMUGCElem) message.getElement(0);
        File file = new File(path);
//        Log.d(TAG, "file size " + file.length() + " ucg size " + e.getVideo().getSize());
//        QLog.d(TAG, QLog.USR, "file size " + file.length() + " ucg size " + e.getVideo().getSize());
        if (file.length() < e.getVideo().getSize()) return;
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    private void setVideoEvent(final ChatAdapter.ViewHolder viewHolder, final String fileName, final Context context) {
        getBubbleView(viewHolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo(TIMFileUtil.getCacheFilePath(fileName), context);
            }
        });
    }


}
