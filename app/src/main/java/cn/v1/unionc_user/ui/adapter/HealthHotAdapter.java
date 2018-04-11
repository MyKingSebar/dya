package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.HealthClassDetailActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/11.
 */

public class HealthHotAdapter extends RecyclerView.Adapter<HealthHotAdapter.ViewHolder> {

    private Context context;

    public HealthHotAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_health_class, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HealthClassDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_point_avator)
        CircleImageView imgPointAvator;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_hospital_name)
        TextView tvHospitalName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_readed)
        TextView tvReaded;
        @BindView(R.id.tv_follow)
        TextView tvFollow;
        @BindView(R.id.img_big)
        ImageView imgBig;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}


