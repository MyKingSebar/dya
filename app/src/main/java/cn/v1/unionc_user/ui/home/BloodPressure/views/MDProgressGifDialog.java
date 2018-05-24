package cn.v1.unionc_user.ui.home.BloodPressure.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import cn.v1.unionc_user.R;

/**
 * Created by lawrence on 17/10/12.
 */
public class MDProgressGifDialog extends AlertDialog {

    private GifView gifView;
    private Context context;

    public MDProgressGifDialog(Context context) {
        super(context, R.style.loading_gif_dialog);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressgifdialog_layout);
        gifView = (GifView) findViewById(R.id.img_gif);
        gifView.setMovieResource(R.raw.loading_gif);
    }


}
