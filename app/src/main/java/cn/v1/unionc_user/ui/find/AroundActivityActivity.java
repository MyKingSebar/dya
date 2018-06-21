package cn.v1.unionc_user.ui.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.me.MyActivity1Fragment;
import cn.v1.unionc_user.ui.me.WatchingDoctorFragment;
import cn.v1.unionc_user.ui.me.WatchingHospitalFragment;

public class AroundActivityActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.recycleView)
//    RecyclerView recycleView;
@BindView(R.id.ll_fragment_container)
LinearLayout linearLayout;

    private Fragment mCurrentfragment;//记录选中的fragment
    MyActivity1Fragment  applyfragment;
    private final String APPLY = "apply";
    private final String COLLECT = "collect";
    private String[] tags = new String[]{APPLY, COLLECT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroundactivity);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private void initView() {
        tvTitle.setText("社区活动");
        stateCheck(outState);
        if (null == applyfragment) {
            applyfragment = new MyActivity1Fragment().newInstance(Common.AROUNDACTIVITY);
            Log.d("linshi","applyfragment==null"+(applyfragment==null));
        }
        switchContent(applyfragment, 0);

//        recycleView.setLayoutManager(new LinearLayoutManager(context));
//        ActivityAdapter activityAdapter = new ActivityAdapter(context);
//        recycleView.setAdapter(activityAdapter);
    }

    /**
     * fragment 切换
     *
     * @param to
     */
    public void switchContent(Fragment to, int position) {
        Log.d("linshi","switchContent:"+position+","+to.toString());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentfragment != to) {
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mCurrentfragment)
                        .add(R.id.ll_fragment_container, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mCurrentfragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
        mCurrentfragment = to;
    }

    /**
     * 状态检测 用于内存不足时的时候保证fragment不会重叠
     */
    private void stateCheck(Bundle saveInstanceState) {
        Logger.i(new Gson().toJson(saveInstanceState));
        if (null != saveInstanceState) {
            //通过tag找回失去引用但是存在内存中的fragment.id相同
            WatchingDoctorFragment watchingDoctorFragment = (WatchingDoctorFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            WatchingHospitalFragment watchingHospitalFragment = (WatchingHospitalFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(watchingDoctorFragment)
                    .hide(watchingHospitalFragment)
                    .commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (null == applyfragment) {
                applyfragment = new MyActivity1Fragment().newInstance(Common.AROUNDACTIVITY);
            }
            transaction.add(R.id.ll_fragment_container, applyfragment, tags[0]).commit();
        }
        mCurrentfragment = applyfragment;
    }
}
