package cn.v1.unionc_user.ui.home.health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.PickerUI.PickerUI;
import cn.v1.unionc_user.ui.home.PickerUI.PickerUISettings;

/**
 * Created by An4 on 2015/10/30.
 */
public class DossierWheelViewActivity extends BaseActivity {

    @BindView(R.id.picker_ui_view)
    PickerUI mPickerUI;
    @BindView(R.id.desc_tv)
    TextView tv_desc;
    @OnClick(R.id.yes_tv)
    void doneChoose(){
        Intent intent = new Intent();
        intent.putExtra("firstIndex",firstColumIndex);
        intent.putExtra("firstColum",firstColum);
        if(type == TYPE_TWO){
            intent.putExtra("seconeIndex",secondColumIndex);
            intent.putExtra("secondColum",firstColum2);
        }
        if(type == TYPE_THREE||type == TYPE_DATE){
            intent.putExtra("secondIndex",secondColumIndex);
            intent.putExtra("secondColum",firstColum2);
            intent.putExtra("thirdIndex",thirdColumIndex);
            intent.putExtra("thirdColum",firstColum3);
        }
        setResult(RESULT_OK,intent);
        finish();
    }
    @OnClick(R.id.cancle_tv)
    void cancleChoose(){
        finish();
    }

private Unbinder unbinder;

    private List<String> options1 = new ArrayList<>();
    private List<String> options2 = new ArrayList<>();
    private List<String> options3 = new ArrayList<>();

    private String firstColum;
    private String firstColum2;
    private String firstColum3;
    private String desc = "";

    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<String> mData2 = new ArrayList<>();
    private ArrayList<String> mData3 = new ArrayList<>();

    private int firstColumIndex, secondColumIndex, thirdColumIndex;

    public static final String KEY_DESC = "desc";
    public static final String KEY_LISTONE = "list1";
    public static final String KEY_LISTTWO = "list2";
    public static final String KEY_LISTTHREE = "list3";
    public static final String KEY_TYPE = "type";
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_DATE = 4;
    public static final String KEY_CURRENTYEAR= "key_currentyear";
    public static final String KEY_CURRENTMONTH = "key_currentmonth";
    public static final String KEY_CURRENTDAY = "key_currentday";
    public static final String KEY_DEFAULT_FIRST = "key_default_first";
    public static final String KEY_DEFAULT_SECOND = "key_default_second";
    public static final String KEY_DEFAULT_THIRD = "key_default_third";
    public int type = TYPE_ONE;
    public int currentYear,currentMonth,currentDay,first_default = 0,second_default = 0,third_default = 0;

    public static Intent getPickViewActivityOne(Context context, int type, String desc, ArrayList<String> mData1) {
        Intent intent = new Intent(context, DossierWheelViewActivity.class);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_TYPE,type);
        intent.putStringArrayListExtra(KEY_LISTONE,mData1);
        return intent;
    }

    public static Intent getPickViewActivityTwo(Context context, int type, String desc, ArrayList<String> mData1, ArrayList<String> mData2,
                                                int first_default, int second_default) {
        Intent intent = new Intent(context, DossierWheelViewActivity.class);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_TYPE,type);
        intent.putStringArrayListExtra(KEY_LISTONE,mData1);
        intent.putStringArrayListExtra(KEY_LISTTWO,mData2);
        intent.putExtra(KEY_DEFAULT_FIRST,first_default);
        intent.putExtra(KEY_DEFAULT_SECOND,second_default);
        return intent;
    }

    public static Intent getPickViewActivityThree(Context context, int type, String desc, ArrayList<String> mData1, ArrayList<String> mData2, ArrayList<String> mData3) {
        Intent intent = new Intent(context, DossierWheelViewActivity.class);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_TYPE,type);
        intent.putStringArrayListExtra(KEY_LISTONE,mData1);
        intent.putStringArrayListExtra(KEY_LISTTWO,mData2);
        intent.putStringArrayListExtra(KEY_LISTTHREE,mData3);
        return intent;
    }

    public static Intent getPickViewActivityDate(Context context, int type, String desc, ArrayList<String> mData1, ArrayList<String> mData2, ArrayList<String> mData3,
                                                 int currentYear, int currentMonth, int currentDay) {
        Intent intent = new Intent(context, DossierWheelViewActivity.class);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_TYPE,type);
        intent.putStringArrayListExtra(KEY_LISTONE,mData1);
        intent.putStringArrayListExtra(KEY_LISTTWO,mData2);
        intent.putStringArrayListExtra(KEY_LISTTHREE,mData3);
        intent.putExtra(KEY_CURRENTYEAR,currentYear);
        intent.putExtra(KEY_CURRENTMONTH,currentMonth);
        intent.putExtra(KEY_CURRENTDAY,currentDay);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dossier_wheel_view_layout);
        unbinder=ButterKnife.bind(this);
        initpickui();
    }

    private void initpickui() {
        type = getIntent().getIntExtra(KEY_TYPE,1);
        desc = getIntent().getStringExtra(KEY_DESC);
        mData = getIntent().getStringArrayListExtra(KEY_LISTONE);
        tv_desc.setText(desc);
        if(type == TYPE_DATE){
            currentYear = getIntent().getIntExtra(KEY_CURRENTYEAR,1);
            currentMonth = getIntent().getIntExtra(KEY_CURRENTMONTH,1);
            currentDay = getIntent().getIntExtra(KEY_CURRENTDAY,1);
            options1.addAll(mData);
            firstColum = options1.get(0);
            mData2 = getIntent().getStringArrayListExtra(KEY_LISTTWO);
            options2.addAll(mData2);
            firstColum2 = options2.get(currentMonth);
            mData3 = getIntent().getStringArrayListExtra(KEY_LISTTHREE);
            options3.addAll(mData3);
            firstColum3 = options3.get(currentDay);
            mPickerUI.setItems(DossierWheelViewActivity.this, options1, 1);
            mPickerUI.setItems(DossierWheelViewActivity.this, options2, 2);
            mPickerUI.setItems(DossierWheelViewActivity.this, options3, 3);
            mPickerUI.setColumn(3);
            firstColumIndex = 0;
            secondColumIndex = currentMonth;
            thirdColumIndex = currentDay;
        }else {
            options1.addAll(mData);
            firstColum = options1.get(0);
            mPickerUI.setItems(DossierWheelViewActivity.this, options1, 1);
            mPickerUI.setColumn(1);
            if (type == TYPE_TWO) {
                mData2 = getIntent().getStringArrayListExtra(KEY_LISTTWO);
                options2.addAll(mData2);
                firstColum2 = options2.get(0);
                mPickerUI.setItems(DossierWheelViewActivity.this, options2, 2);
                mPickerUI.setColumn(2);
            }
            if (type == TYPE_THREE) {
                mData2 = getIntent().getStringArrayListExtra(KEY_LISTTWO);
                options2.addAll(mData2);
                firstColum2 = options2.get(0);
                mData3 = getIntent().getStringArrayListExtra(KEY_LISTTHREE);
                options3.addAll(mData3);
                firstColum3 = options3.get(0);
                mPickerUI.setItems(DossierWheelViewActivity.this, options2, 2);
                mPickerUI.setItems(DossierWheelViewActivity.this, options3, 3);
                mPickerUI.setColumn(3);
            }
        }
        mPickerUI.setItemsClickables(false);
        mPickerUI.setAutoDismiss(false);
        PickerUISettings pickerUISettings =
                new PickerUISettings.Builder()
                        .withAutoDismiss(false)
                        .build();
        mPickerUI.slide(0);
        mPickerUI.setOnClickItemPickerUIListener(
                new PickerUI.PickerUIItemClickListener() {

                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        firstColumIndex = position;
                        firstColum = valueResult;
                        offsetData();
                    }
                }, new PickerUI.PickerUIItemClickListener() {

                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        secondColumIndex = position;
                        firstColum2 = valueResult;
                        offsetData();
                    }
                }, new PickerUI.PickerUIItemClickListener() {

                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        thirdColumIndex = position;
                        firstColum3 = valueResult;
                    }
                });
        mPickerUI.setSettings(pickerUISettings);
        mPickerUI.setColorTextCenter(R.color.black_87);
        mPickerUI.setColorTextNoCenter(R.color.black_26);
        mPickerUI.setBackgroundColorPanel(R.color.white);
        mPickerUI.setLinesColor(getResources().getColor(R.color.black_26));

        if(type == TYPE_TWO){
            first_default = getIntent().getIntExtra(KEY_DEFAULT_FIRST,0);
            second_default = getIntent().getIntExtra(KEY_DEFAULT_SECOND,0);
            firstColumIndex = first_default;
            secondColumIndex = second_default;
            firstColum = options1.get(first_default);
            firstColum2 = options2.get(second_default);
            newHandler.sendEmptyMessageDelayed(0,500);
        }

        if(type == TYPE_DATE){
            newHandler.sendEmptyMessageDelayed(1,500);
        }

    }

    Handler newHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                mPickerUI.mPickerUIListView.getPickerUIAdapter().handleSelectEvent(first_default + 2);
                mPickerUI.mPickerUIListView.smoothScrollToPosition(first_default);
                mPickerUI.mPickerUIListView.setSelection(first_default);
                mPickerUI.mPickerUIListView2.getPickerUIAdapter().handleSelectEvent(second_default + 2);
                mPickerUI.mPickerUIListView2.smoothScrollToPosition(second_default);
                mPickerUI.mPickerUIListView2.setSelection(second_default);
//                mPickerUI.mPickerUIListView2.getPickerUIAdapter().notifyDataSetChanged();
            }else if(msg.what == 1){
                mPickerUI.mPickerUIListView.getPickerUIAdapter().handleSelectEvent(currentYear + 2);
                mPickerUI.mPickerUIListView.smoothScrollToPosition(currentYear);
                mPickerUI.mPickerUIListView.setSelection(currentYear);
                mPickerUI.mPickerUIListView2.getPickerUIAdapter().handleSelectEvent(currentMonth + 2);
                mPickerUI.mPickerUIListView2.smoothScrollToPosition(currentMonth);
                mPickerUI.mPickerUIListView2.setSelection(currentMonth);
                mPickerUI.mPickerUIListView3.getPickerUIAdapter().handleSelectEvent(currentDay + 2);
                mPickerUI.mPickerUIListView3.smoothScrollToPosition(currentDay);
                mPickerUI.mPickerUIListView3.setSelection(currentDay);

            }
        }
    };

    public void offsetData(){
        if(type == TYPE_DATE) {
            mData3.clear();
            int year = Integer.parseInt(firstColum);
            int month = Integer.parseInt(firstColum2);
            int day = 0;
            boolean leayyear = false;
            if (year % 4 == 0 && year % 100 != 0|| year%400 == 0) {
                leayyear = true;
            } else {
                leayyear = false;
            }
            switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        day = 31;
                        break;
                    case 2:
                        if (leayyear) {
                            day = 29;
                        } else {
                            day = 28;
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        day = 30;
                        break;
            }
            for (int i = 1; i <= day ;i++){
                mData3.add(i+"");
            }
            options3.clear();
            options3.addAll(mData3);
            mPickerUI.setItems(DossierWheelViewActivity.this, options3, 3);
            thirdColumIndex = 0;
            firstColum3 = options3.get(thirdColumIndex);
            mPickerUI.mPickerUIListView3.getPickerUIAdapter().handleSelectEvent(thirdColumIndex+2);
            mPickerUI.mPickerUIListView3.smoothScrollToPosition(thirdColumIndex);
            mPickerUI.mPickerUIListView3.setSelection(thirdColumIndex);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
