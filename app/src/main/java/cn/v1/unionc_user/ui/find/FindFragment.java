package cn.v1.unionc_user.ui.find;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.me.oldregister.OldRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.tv_my_healthcircle)
    TextView tvHealthcircle;
    @BindView(R.id.tv_homedoc)
    TextView tvHomedoc;
    @BindView(R.id.tv_zbdoc)
    TextView tvZbdoc;
    @BindView(R.id.tv_activity)
    TextView tv_activity;
    @BindView(R.id.tv_healthlive)
    TextView tv_healthlive;


    private Intent intent;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.tv_my_healthcircle, R.id.tv_homedoc, R.id.tv_zbdoc,R.id.tv_healthlive,R.id.tv_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_healthcircle:
                //健康圈
                goNewActivity(OldRegisterActivity.class);
                break;
            case R.id.tv_homedoc:
                //家庭医生
                goNewActivity(HomeDocActivity.class);
                break;
            case R.id.tv_zbdoc:
                //值班医生
                goNewActivity(DutyDocActivity.class);
//                if (isLogin()) {
//                    goNewActivity(DutyDocActivity.class);
//                } else {
//                    goNewActivity(LoginActivity.class);
//                }

                break;
            case R.id.tv_healthlive:
                //健康直播
//goNewActivity(NEMainActivity.class);
goNewActivity(LiveListActivity.class);
                break;
            case R.id.tv_activity:
                //社区活动
goNewActivity(AroundActivityActivity.class);
                break;


        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
