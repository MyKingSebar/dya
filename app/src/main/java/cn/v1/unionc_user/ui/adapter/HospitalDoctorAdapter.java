package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.ClinicInfoData;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/23.
 */

public class HospitalDoctorAdapter extends NewBaseAdapter<List<ClinicInfoData.DataData.DoctorsData>> {

    public HospitalDoctorAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseHolder getHolder(Context context) {
        return new HeheHolder();
    }

    @Override
    protected int getView() {
        return R.layout.item_me_watching_doctor_list;
    }



    @Override
    public int getCount() {
        if(mData==null){
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class HeheHolder extends BaseHolder<List<ClinicInfoData.DataData.DoctorsData>> {

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
        @Bind(R.id.ll)
        LinearLayout ll;





        @Override
        public void setData(final int position, final List<ClinicInfoData.DataData.DoctorsData> data) {
            if(TextUtils.isEmpty(data.get(position).getImagePath())){
imgMessageAvator.setImageResource(R.drawable.me_watching_hospital);
            }else{
                Glide.with(context)
                        .load(data.get(position).getImagePath())
                        .placeholder(R.drawable.me_watching_hospital)
                        .error(R.drawable.me_watching_hospital)
                        .into(imgMessageAvator);

            }
            tv_doctorname.setText(data.get(position).getDoctorName());
            tvRole.setText(data.get(position).getDepartName());
            tvIdentity.setText(data.get(position).getProfessLevel());
            tvDescribe.setText(data.get(position).getClinicName());
            tvMajor.setText(data.get(position).getMajor());
ll.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DoctorDetailActivity.class);
        intent.putExtra("doctorId", data.get(position).getDoctId());
        context.startActivity(intent);
    }
});

            notifyDataSetChanged();
        }
    }





}
