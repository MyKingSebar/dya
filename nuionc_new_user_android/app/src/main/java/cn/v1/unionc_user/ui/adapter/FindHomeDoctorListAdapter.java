package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MyRecommenDoctorsData;
import cn.v1.unionc_user.model.RecommendDoctorsData;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class FindHomeDoctorListAdapter extends RecyclerView.Adapter<FindHomeDoctorListAdapter.ViewHolder> {

    private Context context;
    private List<MyRecommenDoctorsData.DataData.DoctorsData> datas = new ArrayList<>();

    public FindHomeDoctorListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MyRecommenDoctorsData.DataData.DoctorsData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public FindHomeDoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_find_home_doctor_list, null));
    }


    @Override
    public void onBindViewHolder(final FindHomeDoctorListAdapter.ViewHolder holder, final int position) {
        final MyRecommenDoctorsData.DataData.DoctorsData doctorData = datas.get(position);
            if(TextUtils.isEmpty(doctorData.getImagePath())){

                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            }else{
                Glide.with(context)
                        .load(doctorData.getImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);

            }
            holder.tv_doctorname.setText(doctorData.getDoctorName());
            holder.distance.setText(doctorData.getDistance()+"km");
            holder.commendnum.setText(doctorData.getAtteCount()+"关注");
            holder.tvRole.setText(doctorData.getDepartName());
            holder.tvIdentity.setText(doctorData.getProfessLevel());
            holder.tvDescribe.setText(doctorData.getClinicName());
            holder.tvMajor.setText("擅长："+doctorData.getMajor());
        holder.sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("doctorId", doctorData.getDoctId());
                context.startActivity(intent);
            }
        });
            if(TextUtils.equals(doctorData.getIsSigned(),"0")){
                holder.sign.setText("签约");
                holder.sign.setClickable(true);
            }else{
                holder.sign.setVisibility(View.INVISIBLE);
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, DoctorDetailActivity.class);
                    intent.putExtra("doctorId", doctorData.getDoctId());
                    context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        CircleImageView imgMessageAvator;

        @BindView(R.id.tv_message_name)
        TextView tv_doctorname;
        @BindView(R.id.tv_role)
        TextView tvRole;
        @BindView(R.id.tv_identity)
        TextView tvIdentity;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        @BindView(R.id.tv_major)
        TextView tvMajor;
        @BindView(R.id.sign)
        TextView sign;
        @BindView(R.id.commendnum)
        TextView commendnum;
        @BindView(R.id.distance)
        TextView distance;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
