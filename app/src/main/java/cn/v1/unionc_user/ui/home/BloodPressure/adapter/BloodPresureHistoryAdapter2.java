package cn.v1.unionc_user.ui.home.BloodPressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.model.OMLHistoryData;
import cn.v1.unionc_user.ui.home.BloodPressure.BloodPresureHistoryRecordDetailActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.BloodPresureHistoryRecordDetailActivity2;
import cn.v1.unionc_user.ui.home.BloodPressure.IonSlidingViewClickListener;
import cn.v1.unionc_user.ui.home.BloodPressure.SlidingButtonView;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresureHistoryData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresuresaveData;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.BloodPresure;


/**
 * Created by qy on 2018/5/11.
 */

public class BloodPresureHistoryAdapter2 extends RecyclerView.Adapter<BloodPresureHistoryAdapter2.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private Context context;
    private List<OMLHistoryData.DataData.OMLdatta> listDatas = new ArrayList<>();
    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    public BloodPresureHistoryAdapter2(Context context) {
        this.context = context;
        this.mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
    }

    public void setData(List<OMLHistoryData.DataData.OMLdatta> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bloodpresure_history, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.sbtView.setSlidingButtonListener(BloodPresureHistoryAdapter2.this);

        final OMLHistoryData.DataData.OMLdatta data = listDatas.get(position);
        if (TextUtils.equals(listDatas.get(position).getIsHead(),"1")) {
            holder.tvMeasureDate.setVisibility(View.VISIBLE);
            holder.tvMeasureDate.setText(listDatas.get(position).getMonitorDate() + "");
        } else {
            holder.tvMeasureDate.setVisibility(View.GONE);
        }
        holder.tvBloodPresure.setText(data.getHighPressure() + "/" + data.getLowPressure());
        holder.tvHeartRate.setText(data.getHeartRate() + "");
        holder.tvMeasureTime.setText(data.getMonitorTime() + "");
//        if (TextUtils.equals("0", data.getMeasureWay())) {
//            //设备测量
//            holder.tvMeasureWay.setText("血压计测量");
//        }
//        if (TextUtils.equals("1", data.getMeasureWay())) {
//            //手动测量
//            holder.tvMeasureWay.setText("手动输入");
//        }
        holder.llHistoryRecord.getLayoutParams().width = UnioncApp.getScreenWidth(context);
        holder.llHistoryRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    Intent intent=new Intent(context,BloodPresureHistoryRecordDetailActivity2.class);
                    BloodPresuresaveData data2=new BloodPresuresaveData();
                    data2.setHighPressure(data.getHighPressure());
                    data2.setLowPressure(data.getLowPressure());
                    data2.setPluseRate(data.getHeartRate());
                    data2.setMeasureDate(data.getMonitorDate()+" "+data.getMonitorTime());
                    Map map=  BloodPresure.computeRate(data.getHighPressure(),data.getLowPressure());
                    data2.setRate((int)map.get("rate"));
                    data2.setRateName((String)map.get("rateName"));
                    intent.putExtra("savedata",data2);
                    context.startActivity(intent);
                }

            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_blood_presure)
        TextView tvBloodPresure;
        @BindView(R.id.tv_heart_rate)
        TextView tvHeartRate;
        @BindView(R.id.tv_measure_time)
        TextView tvMeasureTime;
        @BindView(R.id.tv_measure_way)
        TextView tvMeasureWay;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.ll_history_record)
        LinearLayout llHistoryRecord;
        @BindView(R.id.tv_measure_date)
        TextView tvMeasureDate;
        @BindView(R.id.sbt_view)
        SlidingButtonView sbtView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

}
