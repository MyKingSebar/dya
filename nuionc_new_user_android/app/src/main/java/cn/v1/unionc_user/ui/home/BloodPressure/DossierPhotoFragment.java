package cn.v1.unionc_user.ui.home.BloodPressure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.health.GlideUtil;

/**
 * Created by An4 on 2016/10/12.
 */
public class DossierPhotoFragment extends BaseFragment {

    @BindView(R.id.dossier_photo_img)
    ImageView mImageView;

    String picUrl = "";

    public static DossierPhotoFragment newInstance(String picUrl) {
        Bundle args = new Bundle();
        args.putString("picUrl",picUrl);
        DossierPhotoFragment fragment = new DossierPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picUrl = getArguments().getString("picUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dossierphoto, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(picUrl.startsWith("http://")) {
            GlideUtil.setNormalImage(getActivity(), picUrl, mImageView, -1, -1);
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(picUrl);
            mImageView.setImageBitmap(bitmap);
        }
    }


}
