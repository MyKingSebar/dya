//package cn.v1.unionc_user.ui.home.health;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.vodone.cp365.R;
//import com.vodone.cp365.caibodata.HeartRateBuyTimesData;
//import com.vodone.cp365.caibodata.HeartRatePackageData;
//import com.vodone.cp365.customview.MGGridViewInScrollView;
//import com.vodone.cp365.network.ErrorAction;
//import com.vodone.cp365.util.ActivityMangerUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.v1.unionc_user.ui.base.BaseActivity;
//import rx.functions.Action1;
//
///**
// * 医生读图购买次数
// */
//public class DossierHertRateBuyTimesActivity extends BaseActivity {
//
//    @Bind(R.id.rv_times_type)
//    MGGridViewInScrollView rvTimesType;
//    @Bind(R.id.tv_pay_detial)
//    TextView tvPayDetial;
//    @Bind(R.id.tv_pay)
//    TextView tvPay;
//    private HeartRateTimesAdapter heartRateTimesAdapter;
//
//    @OnClick(R.id.tv_pay)
//    public void onClick() {
//        for(int i = 0;i < list.size();i++){
//            if(list.get(i).isSelect()){
//                String payAmount = list.get(i).getPackPrice();
//                String packageType = list.get(i).getPackId();
//                saveOrder(payAmount,packageType);
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dossier_hert_rate_buy_times);
//        ActivityMangerUtil.getInstance().addActivity(this);
//        initRecycleView();
//        initData();
//    }
//
//    private List<HeartRatePackageData.DataData.HeartratePackagesData> list = new ArrayList<>();
//
//    private void initRecycleView() {
//        heartRateTimesAdapter = new HeartRateTimesAdapter(this, list);
//        rvTimesType.setAdapter(heartRateTimesAdapter);
//        rvTimesType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                for(int i = 0;i<list.size();i++){
//                    if(i == position){
//                        list.get(position).setSelect(true);
//                    }else{
//                        list.get(i).setSelect(false);
//                    }
//                }
//                heartRateTimesAdapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    private void saveOrder(final String payAmount, String packageType){
//
//        showDialog("正在下单");
//        bindObservable(mAppClient.saveOrder(payAmount, packageType), new Action1<HeartRateBuyTimesData>() {
//            @Override
//            public void call(HeartRateBuyTimesData data) {
//                if(TextUtils.equals("0000",data.getCode())){
////                    Intent intent = TzOrderPaymentActivity.getTzOrderPaymentIntent(DossierHertRateBuyTimesActivity.this, payAmount,data.getData().getOrderId(),
////                            "", "", "","1",false);
////                    startActivity(intent);
////
////                    Intent intent = new Intent(DossierHertRateBuyTimesActivity.this,TzOrderPaymentActivity.class);
////                    intent.putExtra("orderId",data.getData().getOrderId());
////                    intent.putExtra("amount",payAmount);
////                    intent.putExtra("orderType",4+"");
////                    intent.putExtra("fromwhere",0+"");
////                    startActivity(intent);
//                }else{
//                    showToast(data.getMessage());
//                }
//                closeDialog();
//            }
//        },new ErrorAction(this){
//            @Override
//            public void call(Throwable throwable) {
//                closeDialog();
//                super.call(throwable);
//            }
//        });
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//
//        bindObservable(mAppClient.getHeartratePackage(), new Action1<HeartRatePackageData>() {
//            @Override
//            public void call(HeartRatePackageData data) {
//                if (TextUtils.equals("0000", data.getCode())) {
//                    list.addAll(data.getData().getHeartratePackages());
//                    heartRateTimesAdapter.notifyDataSetChanged();
//                } else {
//                    showToast(data.getMessage());
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//            }
//        });
//
//    }
//
//
//    class HeartRateTimesAdapter extends BaseAdapter {
//
//        private Context context;
//        private List<HeartRatePackageData.DataData.HeartratePackagesData> list = new ArrayList<>();
//
//        public HeartRateTimesAdapter(Context context, List<HeartRatePackageData.DataData.HeartratePackagesData> list) {
//            this.context = context;
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHower = null;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(
//                        R.layout.item_heart_rate_times, null);
//                viewHower = new ViewHolder(convertView);
//                convertView.setTag(viewHower);
//            } else {
//                viewHower = (ViewHolder) convertView.getTag();
//            }
//            viewHower.tvTimes.setText(list.get(position).getPackTime() + "次");
//            viewHower.tvPrice.setText("售价：" + list.get(position).getPackPrice() + "元");
//            if(list.get(position).isSelect()){
//                convertView.setBackgroundResource(R.drawable.green_four_radius_bg);
//                viewHower.tvTimes.setTextColor(getResources().getColor(R.color.allcolor));
//                viewHower.tvPrice.setTextColor(getResources().getColor(R.color.allcolor));
//            }else{
//                convertView.setBackgroundResource(R.drawable.selector_heart_rate_times);
//                viewHower.tvTimes.setTextColor(getResources().getColorStateList(R.color.selector_heart_rate_times_text));
//                viewHower.tvPrice.setTextColor(getResources().getColorStateList(R.color.selector_heart_rate_times_price_text));
//            }
//            return convertView;
//        }
//
//        class ViewHolder {
//            @Bind(R.id.tv_times)
//            TextView tvTimes;
//            @Bind(R.id.tv_price)
//            TextView tvPrice;
//
//            ViewHolder(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }
//    }
//
//
//}
