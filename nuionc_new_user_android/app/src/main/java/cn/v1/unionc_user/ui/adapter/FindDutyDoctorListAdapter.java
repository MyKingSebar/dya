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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.MyDutyDoctorsData;
import cn.v1.unionc_user.model.MyRecommenDoctorsData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.view.CircleImageView;
import cn.v1.unionc_user.view.ProgressDialog;

/**
 * Created by qy on 2018/2/7.
 */

public class FindDutyDoctorListAdapter extends RecyclerView.Adapter<FindDutyDoctorListAdapter.ViewHolder> {

    private Context context;
    private List<MyDutyDoctorsData.DataData.DoctorsData> datas = new ArrayList<>();

    public FindDutyDoctorListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MyDutyDoctorsData.DataData.DoctorsData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public FindDutyDoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_find_duty_doctor_list, null));
    }


    @Override
    public void onBindViewHolder(final FindDutyDoctorListAdapter.ViewHolder holder, final int position) {
        final MyDutyDoctorsData.DataData.DoctorsData doctorData = datas.get(position);
        Log.d("linshi","doctorsdata:"+doctorData.toString());
            if(TextUtils.isEmpty(doctorData.getClinicImagePath())){

                holder.imgHospitalAvator.setImageResource(R.drawable.icon_hospital_zh);
            }else{
                Glide.with(context)
                        .load(doctorData.getClinicImagePath())
                        .placeholder(R.drawable.icon_hospital_zh).dontAnimate()
                        .error(R.drawable.icon_hospital_zh)
                        .into(holder.imgHospitalAvator);

            }
            if(TextUtils.isEmpty(doctorData.getDoctImagePath())){

                holder.imgHospitalAvator.setImageResource(R.drawable.icon_doctor_default);
            }else{
                Glide.with(context)
                        .load(doctorData.getDoctImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.img_avator);

            }
            holder.clinicname.setText(doctorData.getClinicName()+"");
            holder.distance.setText(doctorData.getDistance()+"km");
            holder.progressBar1.setProgress((int)(Float.parseFloat(doctorData.getDifferenceTime())*100));
            holder.time.setText(doctorData.getStartTime()+"-"+doctorData.getEndTime());
            holder.dutyname.setText("值班医生："+doctorData.getDoctName());
        holder.sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin()){

                    goIm(doctorData);
                }else {
                    Intent intent =new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin()){

                    goIm(doctorData);
                }else {
                    Intent intent =new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }
private void goIm(MyDutyDoctorsData.DataData.DoctorsData doctorData){
    DoctorInfo doctorInfo = new DoctorInfo();
    doctorInfo.setDoctorName( ""+doctorData.getDoctName());
    doctorInfo.setIdentifier(""+doctorData.getIdentifier());
    doctorInfo.setImagePath(""+doctorData.getDoctImagePath());
    doctorInfo.setId(""+doctorData.getDoctId());
    Log.d("linshi","homeData.getIdentifier:"+doctorData.getIdentifier());
    TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
}
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        ImageView imgHospitalAvator;
        @BindView(R.id.img_avator)
        CircleImageView img_avator;
        @BindView(R.id.distance)
        TextView distance;

        @BindView(R.id.tv_message_name)
        TextView clinicname;


        @BindView(R.id.sign)
        TextView sign;


        @BindView(R.id.dutyname)
        TextView dutyname;


        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.progressBar1)
        ProgressBar progressBar1;





        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 是否登录
     */
    protected boolean isLogin() {
        return SPUtil.contains(context, Common.USER_TOKEN);
    }
}
