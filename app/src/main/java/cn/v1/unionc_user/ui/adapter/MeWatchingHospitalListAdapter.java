package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class MeWatchingHospitalListAdapter extends RecyclerView.Adapter<MeWatchingHospitalListAdapter.ViewHolder> {

    private Context context;
    private List<MeWatchingHospitalListData.DataData.HospitalData> datas = new ArrayList<>();

    public MeWatchingHospitalListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MeWatchingHospitalListData.DataData.HospitalData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MeWatchingHospitalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_watching_hospital_list, null));
    }


    @Override
    public void onBindViewHolder(final MeWatchingHospitalListAdapter.ViewHolder holder, final int position) {
        final MeWatchingHospitalListData.DataData.HospitalData HospitalData = datas.get(position);
            if(TextUtils.isEmpty(HospitalData.getImagePath())){

                holder.imgMessageAvator.setImageResource(R.drawable.me_watching_hospital);
            }else{
                Glide.with(context)
                        .load(HospitalData.getImagePath())
                        .placeholder(R.drawable.me_watching_hospital)
                        .error(R.drawable.me_watching_hospital)
                        .into(holder.imgMessageAvator);

            }
            holder.tvHospitalname.setText(HospitalData.getClinicName());
            if(HospitalData.getTips().size()>2){
                holder.tvTag1.setText(HospitalData.getTips().get(0));
                holder.tvTag2.setText(HospitalData.getTips().get(1));
                holder.tvTag3.setText(HospitalData.getTips().get(2));
            }

            holder.tvAddress.setText(HospitalData.getAddress());
            holder.tvKm.setText(HospitalData.getDistance()+"km");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        @Bind(R.id.img_message_avator)
        ImageView imgMessageAvator;
        @Bind(R.id.im_call)
        ImageView imCall;

        @Bind(R.id.tv_message_name)
        TextView tvHospitalname;
        @Bind(R.id.tv_tag1)
        TextView tvTag1;
        @Bind(R.id.tv_tag2)
        TextView tvTag2;
        @Bind(R.id.tv_tag3)
        TextView tvTag3;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_km)
        TextView tvKm;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
