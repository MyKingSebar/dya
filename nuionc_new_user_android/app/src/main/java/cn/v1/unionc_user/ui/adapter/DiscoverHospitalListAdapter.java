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
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.model.NearbyClinicData;
import cn.v1.unionc_user.ui.home.HospitalDetailActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class DiscoverHospitalListAdapter extends RecyclerView.Adapter<DiscoverHospitalListAdapter.ViewHolder> {

    private Context context;
    private List<NearbyClinicData.DataData.DataDataData> datas = new ArrayList<>();

    public DiscoverHospitalListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NearbyClinicData.DataData.DataDataData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public DiscoverHospitalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_watching_hospital_list, null));
    }


    @Override
    public void onBindViewHolder(final DiscoverHospitalListAdapter.ViewHolder holder, final int position) {
        final NearbyClinicData.DataData.DataDataData hospitalData = datas.get(position);
            if(TextUtils.isEmpty(hospitalData.getImagePath())){

                holder.imgMessageAvator.setImageResource(R.drawable.me_watching_hospital);
            }else{
                Glide.with(context)
                        .load(hospitalData.getImagePath())
                        .placeholder(R.drawable.me_watching_hospital).dontAnimate()
                        .error(R.drawable.me_watching_hospital)
                        .into(holder.imgMessageAvator);

            }
            holder.tvHospitalname.setText(hospitalData.getClinicName());
            String[] tips=hospitalData.getTips().split(",");
            if(tips.length>2){
                holder.tvTag1.setText(tips[0]);
                holder.tvTag2.setText(tips[1]);
                holder.tvTag3.setText(tips[2]);
                if(TextUtils.isEmpty(holder.tvTag1.getText())){
                    holder.tvTag1.setVisibility(View.GONE);
                }
                if(TextUtils.isEmpty(holder.tvTag2.getText())){
                    holder.tvTag2.setVisibility(View.GONE);
                }
                if(TextUtils.isEmpty(holder.tvTag3.getText())){
                    holder.tvTag3.setVisibility(View.GONE);
                }
            }else{
                holder.tvTag1.setVisibility(View.GONE);
                holder.tvTag2.setVisibility(View.GONE);
                holder.tvTag3.setVisibility(View.GONE);
            }

            holder.tvAddress.setText(hospitalData.getAUniress());
            holder.tvKm.setText(hospitalData.getDistance()+"km");
            holder.imCall.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HospitalDetailActivity.class);
                intent.putExtra("clinicId", hospitalData.getClinicId());
                context.startActivity(intent);
//                String type = datas.get(position).getType();
//                String HospitalId = datas.get(position).getDoctId() + "";
//                String identifier = datas.get(position).getIdentifier() + "";
//                if (TextUtils.equals(type, Common.RECOMMEND_Hospital) ||
//                        TextUtils.equals(type, Common.SIGNED_DOCTROS) ||
//                        TextUtils.equals(type, Common.ATTENDING_HospitalS)) {
//                    Intent intent = new Intent(context, HospitalDetailActivity.class);
//                    intent.putExtra("HospitalId", HospitalId);
//                    context.startActivity(intent);
//                }
//                if (TextUtils.equals(type, Common.CONVERSATIONS)) {
//                    HospitalInfo HospitalInfo = new HospitalInfo();
//                    HospitalInfo.setHospitalName(HospitalData.getHospitalName() + "");
//                    HospitalInfo.setIdentifier(HospitalData.getIdentifier() + "");
//                    HospitalInfo.setImagePath(HospitalData.getImagePath() + "");
//                    TIMChatActivity.navToChat(context, HospitalInfo, TIMConversationType.C2C);
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        ImageView imgMessageAvator;
        @BindView(R.id.im_call)
        ImageView imCall;

        @BindView(R.id.tv_message_name)
        TextView tvHospitalname;
        @BindView(R.id.tv_tag1)
        TextView tvTag1;
        @BindView(R.id.tv_tag2)
        TextView tvTag2;
        @BindView(R.id.tv_tag3)
        TextView tvTag3;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_km)
        TextView tvKm;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
