package cn.v1.unionc_user.view;

import android.support.v4.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.v1.unionc_user.R;

public class FullDialog2 extends DialogFragment {
    TextView num;
    int n_num = 4;

    private FullScrreenDialog.OnFinishListener OnFinishListener;

    public FullScrreenDialog.OnFinishListener getOnFinishListener() {
        return OnFinishListener;
    }

    public void setOnFinishListener(FullScrreenDialog.OnFinishListener onFinishListener) {
        OnFinishListener = onFinishListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.full_screen_dialog, container, false);
        num = v.findViewById(R.id.num);
        num.setText(n_num+"");
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        handler.sendEmptyMessageAtTime(0, 1000);
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                    handler.removeMessages(0);
                    n_num--;
                    num.setText(n_num + "");
                    // app的功能逻辑处理
                    // 再次发出msg，循环更新
                    if (n_num > 0) {

                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        if (OnFinishListener != null) {
                            OnFinishListener.onFinish();
                        }
                        dismiss();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public interface OnFinishListener {
        void onFinish();


    }
}