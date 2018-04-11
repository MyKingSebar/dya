package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.ClinicActivityData;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;

/**
 * Created by qy on 2018/3/15.
 */

public class Capture_activityActivityAdapter extends RecyclerView.Adapter<Capture_activityActivityAdapter.ViewHolder> {


    private Context context;
    private List<ClinicActivityData.DataData.ActivitiesData> activities = new ArrayList<>();


    public Capture_activityActivityAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ClinicActivityData.DataData.ActivitiesData> activities) {
        this.activities = activities;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_capture_activity, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ClinicActivityData.DataData.ActivitiesData activitiesData = activities.get(position);
        holder.tvActivityTitle.setText(activitiesData.getTitle() + "");
        holder.tvActivityContent.setText(activitiesData.getPresentDesc() + "  " + activitiesData.getDigest());
        holder.tvTime.setText(activitiesData.getStartTime() + "-" + activitiesData.getEndTime());
        holder.tvAddress.setText(activitiesData.getActionAddr() + "");
        if (TextUtils.isEmpty(activitiesData.getImagePath())) {
            holder.imgPreview.setImageResource(R.drawable.icon_activity);
        } else {
            Glide.with(context)
                    .load(activitiesData.getImagePath())
                    .placeholder(R.drawable.icon_activity).dontAnimate()
                    .error(R.drawable.icon_activity)
                    .into(holder.imgPreview);
        }


        if(TextUtils.equals("0",activitiesData.getIsSignIn())){
            holder.cbSelect.setChecked(false);
        }
        if(TextUtils.equals("1",activitiesData.getIsSignIn())){
            holder.cbSelect.setChecked(true);
        }
        holder.imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("activityid", activitiesData.getActivityId());
                context.startActivity(intent);
            }
        });
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    activitiesData.setIsSignIn("1");
                } else {
                    activitiesData.setIsSignIn("0");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.img_preview)
        ImageView imgPreview;
        @BindView(R.id.tv_activity_title)
        TextView tvActivityTitle;
        @BindView(R.id.tv_activity_content)
        TextView tvActivityContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
