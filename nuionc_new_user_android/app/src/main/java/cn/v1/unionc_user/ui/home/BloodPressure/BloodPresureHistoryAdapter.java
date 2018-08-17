package cn.v1.unionc_user.ui.home.BloodPressure;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresureHistoryData;


/**
 * Created by qy on 2018/5/11.
 */

public class BloodPresureHistoryAdapter extends RecyclerView.Adapter<BloodPresureHistoryAdapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private Context context;
    private List<BloodPresureHistoryData.DataData.BloodPressureData> listDatas = new ArrayList<>();
    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    public BloodPresureHistoryAdapter(Context context) {
        this.context = context;
        this.mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
    }

    public void setData(List<BloodPresureHistoryData.DataData.BloodPressureData> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bloodpresure_history, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.sbtView.setSlidingButtonListener(BloodPresureHistoryAdapter.this);

        final BloodPresureHistoryData.DataData.BloodPressureData data = listDatas.get(position);
        if (listDatas.get(position).isHead()) {
            holder.tvMeasureDate.setVisibility(View.VISIBLE);
            holder.tvMeasureDate.setText(listDatas.get(position).getHeadText() + "");
        } else {
            holder.tvMeasureDate.setVisibility(View.GONE);
        }
        holder.tvBloodPresure.setText(data.getHighPressure() + "/" + data.getLowPressure());
        holder.tvHeartRate.setText(data.getPluseRate() + "");
        holder.tvMeasureTime.setText(data.getMeasureTime() + "");
        if (TextUtils.equals("0", data.getMeasureWay())) {
            //设备测量
            holder.tvMeasureWay.setText("血压计测量");
        }
        if (TextUtils.equals("1", data.getMeasureWay())) {
            //手动测量
            holder.tvMeasureWay.setText("手动输入");
        }
        holder.llHistoryRecord.getLayoutParams().width = UnioncApp.getScreenWidth(context);
        holder.llHistoryRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    Intent intent = new Intent(context, BloodPresureHistoryRecordDetailActivity.class);
                    intent.putExtra("historyItemData", data);
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
