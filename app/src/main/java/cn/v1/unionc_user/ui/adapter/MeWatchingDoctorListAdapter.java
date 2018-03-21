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
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.utils.DateUtils;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class MeWatchingDoctorListAdapter extends RecyclerView.Adapter<MeWatchingDoctorListAdapter.ViewHolder> {

    private Context context;
    private List<MeWatchingDoctorListData.DataData.DoctorData> datas = new ArrayList<>();

    public MeWatchingDoctorListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MeWatchingDoctorListData.DataData.DoctorData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MeWatchingDoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_watching_doctor_list, null));
    }


    @Override
    public void onBindViewHolder(final MeWatchingDoctorListAdapter.ViewHolder holder, final int position) {
        final MeWatchingDoctorListData.DataData.DoctorData doctorData = datas.get(position);
            if(TextUtils.isEmpty(doctorData.getImagePath())){

                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            }else{
                Glide.with(context)
                        .load(doctorData.getImagePath())
                        .placeholder(R.drawable.icon_doctor_default)
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);

            }
            holder.tv_doctorname.setText(doctorData.getDoctorName());
            holder.tvRole.setText(doctorData.getDepartName());
            holder.tvIdentity.setText(doctorData.getProfessLevel());
            holder.tvDescribe.setText(doctorData.getClinicName());
            holder.tvMajor.setText(doctorData.getMajor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String type = datas.get(position).getType();
//                String doctorId = datas.get(position).getDoctId() + "";
//                String identifier = datas.get(position).getIdentifier() + "";
//                if (TextUtils.equals(type, Common.RECOMMEND_DOCTOR) ||
//                        TextUtils.equals(type, Common.SIGNED_DOCTROS) ||
//                        TextUtils.equals(type, Common.ATTENDING_DOCTORS)) {
//                    Intent intent = new Intent(context, DoctorDetailActivity.class);
//                    intent.putExtra("doctorId", doctorId);
//                    context.startActivity(intent);
//                }
//                if (TextUtils.equals(type, Common.CONVERSATIONS)) {
//                    DoctorInfo doctorInfo = new DoctorInfo();
//                    doctorInfo.setDoctorName(doctorData.getDoctorName() + "");
//                    doctorInfo.setIdentifier(doctorData.getIdentifier() + "");
//                    doctorInfo.setImagePath(doctorData.getImagePath() + "");
//                    TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_message_avator)
        CircleImageView imgMessageAvator;

        @Bind(R.id.tv_message_name)
        TextView tv_doctorname;
        @Bind(R.id.tv_role)
        TextView tvRole;
        @Bind(R.id.tv_identity)
        TextView tvIdentity;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.tv_major)
        TextView tvMajor;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
