package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.HeartHistoryListData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;
import cn.v1.unionc_user.ui.home.health.DossierHeartRateAutoMeasureActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class HeartHistoryListAdapter extends RecyclerView.Adapter<HeartHistoryListAdapter.ViewHolder> {

    private Context context;
    private List<HeartHistoryListData.DataData.Heartdata> datas = new ArrayList<>();

    public HeartHistoryListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HeartHistoryListData.DataData.Heartdata> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public HeartHistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_heart_history_list, null));
    }


    @Override
    public void onBindViewHolder(final HeartHistoryListAdapter.ViewHolder holder, final int position) {
        final HeartHistoryListData.DataData.Heartdata homeData = datas.get(position);
        holder.num.setText(homeData.getHeartRate());
        holder.time.setText(homeData.getMonitorDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DossierHeartRateAutoMeasureActivity.class);
                intent.putExtra("dataId",homeData.getDataId());
                context.startActivity(intent);
            }
        });


        }





    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.time)
        TextView time;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
