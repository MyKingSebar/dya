package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.ClinicServerListData;
import cn.v1.unionc_user.model.NearbyClinicData;
import cn.v1.unionc_user.ui.me.guardianship.ServiceInfoActivity;
import cn.v1.unionc_user.ui.me.guardianship.ServiceListActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class MeguardianshipClinicServiceListAdapter extends RecyclerView.Adapter<MeguardianshipClinicServiceListAdapter.ViewHolder> {
    private int type=0;
    private Context context;
    private String elderlyId;

    public void setElderlyId(String elderlyId) {
        this.elderlyId = elderlyId;
    }

    private List<NearbyClinicData.DataData.DataDataData> datas = new ArrayList<>();

    public MeguardianshipClinicServiceListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NearbyClinicData.DataData.DataDataData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MeguardianshipClinicServiceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_guardianship_clinicservice_list, null));
    }


    @Override
    public void onBindViewHolder(final MeguardianshipClinicServiceListAdapter.ViewHolder holder, final int position) {
        final NearbyClinicData.DataData.DataDataData data = datas.get(position);
        if(TextUtils.isEmpty(data.getImagePath())){
            holder.img.setImageResource(R.drawable.icon_hospital_zh);
        }else{
            Glide.with(context)
                    .load(data.getImagePath())
                    .placeholder(R.drawable.icon_hospital_zh).dontAnimate()
                    .error(R.drawable.icon_hospital_zh)
                    .into(holder.img);
        }
        
        
        holder.tv_name.setText(data.getClinicName());
        holder.tv_des.setText(data.getAUniress());
        holder.tv_km.setText(data.getDistance()+"km");
if(TextUtils.equals("0",data.getStatus())|TextUtils.equals("2",data.getStatus())){
    //Status": "状态：0:初始化 1：审核中 2：审核通
    holder.tv_go.setBackgroundResource(R.drawable.bg_button_blue);
    holder.tv_go.setText("服务");
holder.tv_go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,ServiceListActivity.class);
        intent.putExtra("clinicId",data.getClinicId());
        intent.putExtra("elderlyId",elderlyId);
        context.startActivity(intent);
    }
});
    holder.tv_go.setClickable(true);
}else if(TextUtils.equals("1",data.getStatus())){
    //Status": "状态：0:初始化 1：审核中 2：审核通
    holder.tv_go.setBackgroundResource(R.drawable.bg_button_ffa654);
    holder.tv_go.setText("评估中");
    holder.tv_go.setClickable(false);
}


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_des)
        TextView tv_des;
        @BindView(R.id.tv_km)
        TextView tv_km;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_go)
        TextView tv_go;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
