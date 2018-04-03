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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.WatchingActivityData;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;

/**
 * Created by qy on 2018/3/12.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private Context context;
    private List<WatchingActivityData.DataData.ActivitiesData> datas=new ArrayList<>();

    public ActivityAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<WatchingActivityData.DataData.ActivitiesData> datas) {
        this.datas = datas;
        Log.d("linshi","ActivityAdapter.data:"+datas);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_activity, null));
    }

    @Override
    public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position) {
        final WatchingActivityData.DataData.ActivitiesData activitiesData=datas.get(position);
//        Log.d("linshi","activitiesData.getIsSignUp():"+activitiesData.getIsSignUp());
//        Log.d("linshi","activitiesData.getStatus():"+activitiesData.getStatus());
//        Log.d("linshi","activitiesData.tvActivityTitle():"+activitiesData.getTitle());
//        Log.d("linshi","activitiesData.getDigest():"+activitiesData.getDigest());
//        Log.d("linshi","activitiesData.getSignConut():"+activitiesData.getSignCount());
//        Log.d("linshi","activitiesData.getActionAddr():"+activitiesData.getActionAddr());
//        Log.d("linshi","activitiesData.getIsSigninPresent():"+activitiesData.getIsSigninPresent());
        if (TextUtils.equals(activitiesData.getIsSignUp(), "1")) {
            holder.tvRegis.setClickable(false);
            holder.tvRegis.setText("已报名");
            holder.tvRegis.setTextColor( context.getResources().getColor(R.color.detail_text));
            holder.tvRegis.setBackground(context.getResources().getDrawable(R.drawable.bg_button_grey_line));

        }else if(TextUtils.equals(activitiesData.getIsSignUp(), "0")){
            holder.tvRegis.setClickable(true);
            holder.tvRegis.setText("立即报名");
            holder.tvRegis.setTextColor( context.getResources().getColor(R.color.qm_blue));
            holder.tvRegis.setBackground(context.getResources().getDrawable(R.drawable.bg_button_blue_line_thin));
        }

        if (TextUtils.equals(activitiesData.getStatus(), "0")) {
            holder.tvActivityState.setText("未开始");
            holder.tvActivityState.setTextColor( context.getResources().getColor(R.color.qm_blue));
        }else if(TextUtils.equals(activitiesData.getStatus(), "1")){
            holder.tvActivityState.setText("已开始");
            holder.tvActivityState.setTextColor( context.getResources().getColor(R.color.red_rz));
        }else if(TextUtils.equals(activitiesData.getStatus(), "2")){
            holder.tvActivityState.setText("已结束");
            holder.tvActivityState.setTextColor( context.getResources().getColor(R.color.detail_text));
        }else if(TextUtils.equals(activitiesData.getStatus(), "3")){
            holder.tvActivityState.setText("已作废");
            holder.tvActivityState.setTextColor( context.getResources().getColor(R.color.detail_text));
        }else if(TextUtils.equals(activitiesData.getStatus(), "4")){
            holder.tvActivityState.setText("未发布");
            holder.tvActivityState.setTextColor( context.getResources().getColor(R.color.detail_text));
        }

        if (TextUtils.isEmpty(activitiesData.getImagePath())) {
            holder.im.setImageResource(R.drawable.icon_activity);
        } else {
            Glide.with(context)
                    .load(activitiesData.getImagePath())
                    .placeholder(R.drawable.icon_activity)
                    .error(R.drawable.icon_activity)
                    .into(holder.im);
        }
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("activityid", activitiesData.getActivityId());
                context.startActivity(intent);
            }
        });
        holder.tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("activityid", activitiesData.getActivityId());
                context.startActivity(intent);
            }
        });
        holder.tvActivityTitle.setText(activitiesData.getTitle()+"");
        holder.tvActivityContent.setText(activitiesData.getDigest()+"");

        holder.tvHadRegiste.setText(activitiesData.getSignCount()+"人已报名");
        holder.tvTime.setText(activitiesData.getStartTime()+"-"+activitiesData.getEndTime());
        holder.tvAddress.setText(activitiesData.getActionAddr()+"");
if(TextUtils.equals(activitiesData.getIsSigninPresent(), "1")){
    holder. present.setVisibility(View.VISIBLE);
}else if(TextUtils.equals(activitiesData.getIsSigninPresent(), "0")){
    holder. present.setVisibility(View.INVISIBLE);
}

    }

    @Override
    public int getItemCount() {
        Log.d("linshi","datas.size():"+datas.size());
        return datas.size();
    }


     class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.present)
        TextView present;
        @Bind(R.id.im)
        ImageView im;
        //title
        @Bind(R.id.tv_activity_title)
        TextView tvActivityTitle;
        //标签des
        @Bind(R.id.tv_activity_content)
        TextView tvActivityContent;
        @Bind(R.id.tv_time)
        TextView tvTime;

         @Bind(R.id.tv_address)
         TextView tvAddress;
         //进行中 Status 0-未开始，1-已开始，2-已结束，3-已作废,4-未发布
         @Bind(R.id.tv_activity_state)
        TextView tvActivityState;
         //多少人已报名 sign
        @Bind(R.id.tv_had_registe)
        TextView tvHadRegiste;
        //立刻报名  issign
        @Bind(R.id.tv_regis)
        TextView tvRegis;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
