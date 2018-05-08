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
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.RecommendDoctorsData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class RecommendDoctorsAdapter extends RecyclerView.Adapter<RecommendDoctorsAdapter.ViewHolder> {

    private Context context;
    private List<RecommendDoctorsData.DataData.DoctorsData> datas = new ArrayList<>();

    public RecommendDoctorsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RecommendDoctorsData.DataData.DoctorsData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecommendDoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_map_recommend, null));
    }


    @Override
    public void onBindViewHolder(final RecommendDoctorsAdapter.ViewHolder holder, final int position) {
        final RecommendDoctorsData.DataData.DoctorsData homeData = datas.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("doctorId", homeData.getDoctId());
                context.startActivity(intent);
            }
        });
        if (TextUtils.isEmpty(homeData.getImagePath())) {

            holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
        } else {
            Glide.with(context)
                    .load(homeData.getImagePath())
                    .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                    .error(R.drawable.icon_doctor_default)
                    .into(holder.imgMessageAvator);

        }
        holder.tvName.setText(homeData.getDoctName());
        holder.tvProfesslevel.setText(homeData.getProfessLevel());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        CircleImageView imgMessageAvator;

        @BindView(R.id.tv_message_name)
        TextView tvName;

        @BindView(R.id.tv_professlevel)
        TextView tvProfesslevel;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
