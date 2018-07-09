package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.view.CircleImageView;
import io.rong.callkit.RongCallKit;

/**
 * Created by qy on 2018/2/7.
 */

public class MeguardianshipListAdapterServerType extends RecyclerView.Adapter<MeguardianshipListAdapterServerType.ViewHolder> {
    private int type = 0;
    private Context context;
    private List<MeguardianshipData.DataData.GuardianshipData> datas = new ArrayList<>();

    public MeguardianshipListAdapterServerType(Context context) {
        this.context = context;
    }

    public void setData(List<MeguardianshipData.DataData.GuardianshipData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MeguardianshipListAdapterServerType.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_guardianship_list_servertype, null));
    }


    @Override
    public void onBindViewHolder(final MeguardianshipListAdapterServerType.ViewHolder holder, final int position) {
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
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                datas.get(position).setHaschecked(isChecked);
            }
        });
        if (TextUtils.equals("0", doctorData.getIsOpenUp())) {
            holder.cb.setEnabled(true);
            holder.tv_has.setVisibility(View.INVISIBLE);
        } else {
            holder.cb.setEnabled(false);
            holder.tv_has.setVisibility(View.VISIBLE);
        }

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

        @BindView(R.id.tv_has)
        TextView tv_has;
        @BindView(R.id.cb)
        CheckBox cb;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public String getelders() {
        String elders = "";
        for (MeguardianshipData.DataData.GuardianshipData data : datas) {
            if (data.getHaschecked()) {
                elders += data.getElderlyId() + ",";
            }
        }
        if (elders.length() > 0) {
            elders = elders.substring(0, elders.length() - 1);
        }
        Log.d("linshi", "elders:" + elders);
        return elders;
    }
}
