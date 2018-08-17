package cn.v1.unionc_user.ui.home.health;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cn.v1.unionc_user.R;

/**
 * Created by qy on 2017/7/3.
 */

public class HeartRateContinueMeasureDialog {

    private Context context;
    private MyDilogListener myDilogListener;
    private Dialog dialog ;

    public HeartRateContinueMeasureDialog(Context context, MyDilogListener myDilogListener) {
        this.context = context;
        this.myDilogListener = myDilogListener;
        show();
    }


    public void show() {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_heart_rate_continue_measure, null);
        //对话框
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(dialogView);

        Button btnConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDilogListener.btnConfirm(dialog);
            }
        });
    }

    public interface MyDilogListener{
        void btnConfirm(Dialog dialog);
    }


}
