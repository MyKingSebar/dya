package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.view.CircleImageView;
import io.rong.callkit.RongCallKit;

/**
 * Created by qy on 2018/2/7.
 */

public class MeguardianshipListAdapter extends RecyclerView.Adapter<MeguardianshipListAdapter.ViewHolder> {

    private Context context;
    private List<MeguardianshipData.DataData.GuardianshipData> datas = new ArrayList<>();

    public MeguardianshipListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MeguardianshipData.DataData.GuardianshipData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MeguardianshipListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_guardianship_list, null));
    }


    @Override
    public void onBindViewHolder(final MeguardianshipListAdapter.ViewHolder holder, final int position) {
        final MeguardianshipData.DataData.GuardianshipData doctorData = datas.get(position);
        if (TextUtils.isEmpty(doctorData.getGuardianHeadImage())) {

            holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
        } else {
            Glide.with(context)
                    .load(doctorData.getGuardianHeadImage())
                    .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                    .error(R.drawable.icon_doctor_default)
                    .into(holder.imgMessageAvator);

        }
        holder.tv_name.setText(doctorData.getGuardianName());
        holder.tv_relation.setText(doctorData.getGuardianRoleName());
        holder.tv_describe.setText(doctorData.getDoctName());
        holder.bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(doctorData.getGuardianIdentifier())) {
                    RongCallKit.startSingleCall(context, doctorData.getGuardianIdentifier(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                } else {
                    Toast.makeText(context, "暂时无法连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.bt_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.bt_serve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_relation)
        TextView tv_relation;
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.bt_unbind)
        TextView bt_unbind;
        @BindView(R.id.bt_serve)
        TextView bt_serve;
        @BindView(R.id.bt_video)
        TextView bt_video;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
