package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

/**
 * Created by An4 on 2016/10/12.
 */
public class DossierPhotoActivity extends BaseActivity {

    @BindView(R.id.myviewpager)
    ViewPager mViewPager;
    @BindView(R.id.dossier_photo_dots_ll)
    LinearLayout mDotsLinearLayout;
    private List<Fragment> list = new ArrayList<>();
    MyPagerAdapter mAdapter;
    int nowIndex = 0;
    ArrayList<String> piclocallis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossierphoto);
        initData();
    }

    private void initData() {
        FragmentManager manager = getSupportFragmentManager();
        piclocallis.addAll(getIntent().getStringArrayListExtra("picLocalList"));
        nowIndex = getIntent().getIntExtra("nowIndex",0);
        for (int i = 0 ; i < piclocallis.size() ; i++ ) {
            DossierPhotoFragment fragment = DossierPhotoFragment.newInstance(piclocallis.get(i));
            list.add(fragment);
        }
        initcoverflowpoint(piclocallis.size());
        mAdapter = new MyPagerAdapter(manager,list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mAdapter.getCount() == 0)
                    return;
                int realPosition = (position) % (mAdapter.getCount());
                if (mAdapter != null && piclocallis.size() > 0) {
                    coverflowpointSelected(realPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(piclocallis.size() >= nowIndex+1){
            coverflowpointSelected(nowIndex);
            mViewPager.setCurrentItem(nowIndex);
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager manager = null;
        private List<Fragment> list = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.manager = fm;
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    public void initcoverflowpoint(int length) {
        mDotsLinearLayout.removeAllViews();
        for (int i = length; i > 0; i--) {
            ImageView pointImg = new ImageView(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            p.gravity = Gravity.CENTER;
            p.rightMargin = (int) (5 * getResources().getDisplayMetrics().density);
            pointImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_pager_unselect));
            mDotsLinearLayout.addView(pointImg, p);
        }
    }

    public void coverflowpointSelected(int index) {
        coverflowpointNormal();
        ImageView secectPoint = (ImageView) mDotsLinearLayout.getChildAt(index);
        secectPoint.setImageDrawable(getResources().getDrawable(R.drawable.icon_pager_select));
    }

    public void coverflowpointNormal() {
        for (int i = 0; i < mDotsLinearLayout.getChildCount(); i++) {
            ImageView secectPoint = (ImageView) mDotsLinearLayout.getChildAt(i);
            secectPoint.setImageDrawable(getResources().getDrawable(R.drawable.icon_pager_unselect));
        }

    }

}
