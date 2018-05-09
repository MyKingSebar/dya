package cn.v1.unionc_user.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.v1.unionc_user.R;

public class FullScrreenDialog extends Dialog {
    TextView num;
    int n_num = 3;


    private OnFinishListener OnFinishListener;

    public FullScrreenDialog.OnFinishListener getOnFinishListener() {
        return OnFinishListener;
    }

    public void setOnFinishListener(FullScrreenDialog.OnFinishListener onFinishListener) {
        OnFinishListener = onFinishListener;
    }

    public FullScrreenDialog(Context context) {
        super(context,R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.full_screen_dialog, null);
        setContentView(view);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        num = view.findViewById(R.id.num);
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