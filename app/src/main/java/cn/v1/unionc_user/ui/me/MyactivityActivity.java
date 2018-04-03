package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.ui.adapter.ActivityAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class MyactivityActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cb_had_register)
    CheckBox cbHadRegister;
    @Bind(R.id.cb_collect)
    CheckBox cbCollect;
//    @Bind(R.id.recycleView)
//    RecyclerView recycleView;
@Bind(R.id.ll_fragment_container)
LinearLayout linearLayout;

    private Fragment mCurrentfragment;//记录选中的fragment
    MyActivity1Fragment  applyfragment;
    MyActivity1Fragment  collectfragment;
    private final String APPLY = "apply";
    private final String COLLECT = "collect";
    private String[] tags = new String[]{APPLY, COLLECT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private void initView() {
        tvTitle.setText("我的活动");
        stateCheck(outState);
        if (null == applyfragment) {
            applyfragment = new MyActivity1Fragment().newInstance(Common.APPLYACTIVITY);
            Log.d("linshi","applyfragment==null"+(applyfragment==null));
        }
        switchContent(applyfragment, 0);
        cbHadRegister.setChecked(true);
        cbHadRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbHadRegister.setClickable(false);
                    cbCollect.setChecked(false);
                    if (null == applyfragment) {
                        applyfragment = new MyActivity1Fragment().newInstance(Common.APPLYACTIVITY);
                    }
                    switchContent(applyfragment, 0);
                } else {
                    cbHadRegister.setClickable(true);
                }
            }
        });
        cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbCollect.setClickable(false);
                    cbHadRegister.setChecked(false);
                    if (null == collectfragment) {
                        collectfragment =new MyActivity1Fragment().newInstance(Common.COLLECTACTIVITY);
                    }
                    switchContent(collectfragment, 1);
                } else {
                    cbCollect.setClickable(true);
                }
            }
        });
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
                applyfragment = new MyActivity1Fragment().newInstance(Common.APPLYACTIVITY);
            }
            transaction.add(R.id.ll_fragment_container, applyfragment, tags[0]).commit();
        }
        mCurrentfragment = applyfragment;
    }
}
