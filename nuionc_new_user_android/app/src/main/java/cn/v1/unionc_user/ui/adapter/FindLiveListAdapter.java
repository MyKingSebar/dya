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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import cn.v1.unionc_user.model.NetCouldPullData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class FindLiveListAdapter extends RecyclerView.Adapter<FindLiveListAdapter.ViewHolder> {
    private onClickMyTextView onClickMyTextView;
    private Context context;
    private List<NetCouldPullData.DataData.datadatadata> datas = new ArrayList<>();

    public FindLiveListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NetCouldPullData.DataData.datadatadata> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public FindLiveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_find_live_list, null));
    }


    @Override
    public void onBindViewHolder(final FindLiveListAdapter.ViewHolder holder, final int position) {
        final NetCouldPullData.DataData.datadatadata livesData = datas.get(position);
        Log.d("linshi", "doctorsdata:" + livesData.toString());
        if (TextUtils.isEmpty(livesData.getBanner())) {

            Glide.with(context)
                    .load(livesData.getBanner())
                    .into(holder.img_banner);
        } else {
            Glide.with(context)
                    .load(livesData.getBanner())
                    .placeholder(R.drawable.icon_hospital_zh).dontAnimate()
                    .error(R.drawable.icon_hospital_zh)
                    .into(holder.img_banner);

        }
        if (TextUtils.isEmpty(livesData.getClinicImagePath())) {

            Glide.with(context)
                    .load(livesData.getBanner())
                    .into(holder.tv_clinic_img);
        } else {
            Glide.with(context)
                    .load(livesData.getClinicImagePath())
                    .placeholder(R.drawable.icon_hospital_zh).dontAnimate()
                    .error(R.drawable.icon_hospital_zh)
                    .into(holder.tv_clinic_img);

        }

        holder.tv_clinic_name.setText(livesData.getClinicName() + "");
        holder.tv_name.setText(livesData.getSpeaker() + "   " + livesData.getSpeakerIntro());
        holder.tv_livename.setText(livesData.getLiveTitle() + "");
        if (TextUtils.equals(livesData.getLiveStatus(), "1")) {
//            holder.tv_righttop.setText("未开始");
            holder.tv_righttop.setBackgroundResource(R.drawable.find_live_tip_before);
            holder.tv_play.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals(livesData.getLiveStatus(), "2")) {
//            holder.tv_righttop.setText("直播中");
            holder.tv_righttop.setBackgroundResource(R.drawable.find_live_tip_on);
            holder.tv_play.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals(livesData.getLiveStatus(), "3")) {
//            holder.tv_righttop.setText("已结束");
            holder.tv_righttop.setBackgroundResource(R.drawable.find_live_tip_again);
            holder.tv_play.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(livesData.getHttpPullUrl())){

                    onClickMyTextView.myTextViewClick(livesData.getHttpPullUrl());
                }

//                if (isLogin()) {
//
//                    goIm(livesData);
//                } else {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.img_banner)
        ImageView img_banner;

        @BindView(R.id.tv_righttop)
        TextView tv_righttop;
        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_num)
        TextView tv_num;


        @BindView(R.id.tv_play)
        TextView tv_play;
        @BindView(R.id.tv_livename)
        TextView tv_livename;
        @BindView(R.id.tv_clinic_img)
        ImageView tv_clinic_img;
        @BindView(R.id.tv_clinic_name)
        TextView tv_clinic_name;


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

    public interface onClickMyTextView{
        public void myTextViewClick(String url);
    }
    public void setOnClickMyTextView(onClickMyTextView onClickMyTextView) {
        this.onClickMyTextView = onClickMyTextView;
    }


}
