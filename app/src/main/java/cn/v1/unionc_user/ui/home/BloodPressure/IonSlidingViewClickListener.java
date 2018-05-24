package cn.v1.unionc_user.ui.home.BloodPressure;

import android.view.View;

/**
 * Created by dell on 2015/12/25.
 */
public interface IonSlidingViewClickListener {
    void onItemClick(View view, int position);
    void onDeleteBtnCilck(View view, int position);
    void onDeleteDevice(String Bda);
}
