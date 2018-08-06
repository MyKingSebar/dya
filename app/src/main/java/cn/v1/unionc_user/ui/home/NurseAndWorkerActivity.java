package cn.v1.unionc_user.ui.home;

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

public class NurseAndWorkerActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cb_had_register)
    CheckBox cbHadRegister;
    @BindView(R.id.cb_collect)
    CheckBox cbCollect;
    @BindView(R.id.ll_fragment_container)
    LinearLayout linearLayout;

    private Fragment mCurrentfragment;//记录选中的fragment
    private NurseListFragment nurseListFragment;
    private NurseWorkerListFragment nurseWorkerListFragment;
    private final String DOCTOR = "doctor";
    private final String HOSPITAL = "hospital";
    private String[] tags = new String[]{DOCTOR, HOSPITAL};

    private int type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    private void initData() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type",1);
        }
    }

    private void initView() {
        linearLayout.setFocusable(false);
        Log.d("linshi","myinitView:");
        tvTitle.setText("护理上门");
        stateCheck(outState);
        switch (type){
            case Common.NURSE:
                cbHadRegister.setChecked(true);
                if (null == nurseListFragment) {
                    nurseListFragment = new NurseListFragment();
                }
                switchContent(nurseListFragment, 0);
                break;
            case Common.WORKER:
                cbCollect.setChecked(true);
                if (null == nurseWorkerListFragment) {
                    nurseWorkerListFragment = new NurseWorkerListFragment();
                }
                switchContent(nurseWorkerListFragment, 1);
                break;
        }
        Log.d("linshi","myinitViewtype:"+type);
        cbHadRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("linshi","isChecked:");
                    cbHadRegister.setClickable(false);
                    cbCollect.setChecked(false);
                    if (null == nurseListFragment) {
                        nurseListFragment = new NurseListFragment();
                    }
                    switchContent(nurseListFragment, 0);
                } else {
                    Log.d("linshi","noChecked:");
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
                    if (null == nurseWorkerListFragment) {
                        nurseWorkerListFragment = new NurseWorkerListFragment();
                    }
                    switchContent(nurseWorkerListFragment, 1);
                } else {
                    cbCollect.setClickable(true);
                }
            }
        });


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
            NurseListFragment watchingDoctorFragment = (NurseListFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            NurseWorkerListFragment watchingHospitalFragment = (NurseWorkerListFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(watchingDoctorFragment)
                    .hide(watchingHospitalFragment)
                    .commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (null == nurseListFragment) {
                nurseListFragment = new NurseListFragment();
            }
            transaction.add(R.id.ll_fragment_container, nurseListFragment, tags[0]).commit();
        }
        mCurrentfragment = nurseListFragment;
    }
}
