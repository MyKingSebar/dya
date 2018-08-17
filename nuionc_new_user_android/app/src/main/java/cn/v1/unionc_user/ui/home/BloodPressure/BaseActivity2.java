package cn.v1.unionc_user.ui.home.BloodPressure;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.BloodPressure.views.MDProgressGifDialog;

public class BaseActivity2 extends AppCompatActivity{
    private MDProgressGifDialog mDialog;
    private Toolbar mActionBarToolbar;
    private Toast mToast;


    public Toolbar getactionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null)
                setSupportActionBar(mActionBarToolbar);
        }
        return mActionBarToolbar;
    }


    public void showToast(String info) {
        if (!TextUtils.isEmpty(info)) {
            if (mToast == null)
                mToast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
            mToast.setText(info);
            mToast.show();
        }
    }

    public void showDialog(String message) {
        if(mDialog == null){
            mDialog = new MDProgressGifDialog(this);
        }
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.show();
//        if (!TextUtils.isEmpty(message)) {
//            if (mDialog == null) {
//                mDialog = new MDProgressDialog(this);
//            }
//            mDialog.setMessage(message);
//            mDialog.setCanceledOnTouchOutside(true);
//            mDialog.setCancelable(true);
//            mDialog.show();
//        }
    }
}
