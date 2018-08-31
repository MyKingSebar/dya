package cn.v1.unionc_user.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.base.BaseFragment;

public class TestFragment extends BaseFragment {
    private String id;
    private String type;
    private View mView;
    private TimePickerView pvTime;
    private FrameLayout mFrameLayout;
    private Unbinder unbinder;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.et_address)
    EditText address;
    @OnClick(R.id.bt_next)
    void next(){
        pvTime.returnData();


    }

    public static  TestFragment newInstance(String id,String type){
        TestFragment fragmentOne = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
        //fragment保存参数，传入一个Bundle对象
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_test, null);
        unbinder= ButterKnife.bind(this,mView);
        if(getArguments()!=null){
            //取出保存的值
           this.id=getArguments().getString("id");
           this.type=getArguments().getString("type");
        }
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrameLayout = (FrameLayout) mView.findViewById(R.id.fragmen_fragment);
        initTimePicker();
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                if(TextUtils.isEmpty(phone.getText().toString())){
                    showTost("请输入电话号码");
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    showTost("请输入姓名");
                    return;
                }
                if(TextUtils.isEmpty(address.getText().toString())){
                    showTost("请输入地址");
                    return;
                }
                pickeduptime(getTime(date), id);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
//                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
//                .setRangDate(startDate, selectedDate)
                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void pickeduptime(String time, String id) {
        if (!isLogin()) {
            showTost("请先登录");
            return;
        }
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        //serviceType：1-护士，2-护工  :
        //userType
        //1-手机用户，2-老人
        String la = (String) SPUtil.get(context, Common.LATITUDE, "");
        String lo = (String) SPUtil.get(context, Common.LONGITUDE, "");
        Log.d("linshi","la2:"+la + "," + lo);
        ConnectHttp.connect(UnionAPIPackage.subscribenurses(token, id, "1", time, type, name.getText().toString(), address.getText().toString(), phone.getText().toString(),"" , "2",lo,la), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("预约成功");
                    getActivity().finish();
                } else {
                    showTost(data.getMessage() + "");
                }

            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }
}
