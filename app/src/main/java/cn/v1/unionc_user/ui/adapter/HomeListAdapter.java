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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private Context context;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();

    public HomeListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeListData.DataData.HomeData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_list, null));
    }


    @Override
    public void onBindViewHolder(final HomeListAdapter.ViewHolder holder, final int position) {
        final HomeListData.DataData.HomeData homeData = datas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = datas.get(position).getType();
                String doctorId = datas.get(position).getDoctId() + "";
                String identifier = datas.get(position).getIdentifier() + "";
                if (TextUtils.equals(type, Common.RECOMMEND_DOCTOR) ||
                        TextUtils.equals(type, Common.SIGNED_DOCTROS) ||
                        TextUtils.equals(type, Common.ATTENDING_DOCTORS)) {
                    Intent intent = new Intent(context, DoctorDetailActivity.class);
                    intent.putExtra("doctorId", doctorId);
                    context.startActivity(intent);
                }
                if (TextUtils.equals(type, Common.CONVERSATIONS)) {
                    DoctorInfo doctorInfo = new DoctorInfo();
                    doctorInfo.setDoctorName(homeData.getDoctorName() + "");
                    doctorInfo.setIdentifier(homeData.getIdentifier() + "");
                    doctorInfo.setImagePath(homeData.getImagePath() + "");
                    Log.d("linshi","homeData.getImagePath:"+homeData.getImagePath());
                    TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
                }
                if (TextUtils.equals(type, Common.ACTIVITY_PUSH)) {
                    //
                    Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("activityid", homeData.getActivityId());
                    Log.d("linshi","activityid"+homeData.getActivityId());
                    context.startActivity(intent);
                }
            }
        });

        if (TextUtils.equals(homeData.getType(), Common.INQUIRY_RECORD)) {
            Glide.with(context).load(homeData.getImagePath()).into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvDescribe.setText(homeData.getClinicName() + "\n" +"擅长："+
                    homeData.getMajor());
            holder.tvRole.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals(homeData.getType(), Common.ATTENDING_DOCTORS)) {
            if (TextUtils.isEmpty(homeData.getImagePath())) {

                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(homeData.getImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);

            }
            holder.tvRole.setVisibility(View.VISIBLE);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText("（主治医生）");
            holder.tvDescribe.setText("最近的聊天记录");
            holder.tvDescribe.setVisibility(View.VISIBLE);
            holder.tvEndTime.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
        }
        if (TextUtils.equals(homeData.getType(), Common.SIGNED_DOCTROS)) {
            if (TextUtils.isEmpty(homeData.getImagePath())) {
                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(homeData.getImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);
            }
            holder.tvRole.setVisibility(View.VISIBLE);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText("（家庭医生）");
            holder.tvDescribe.setText("最近的聊天记录");
            holder.tvDescribe.setVisibility(View.VISIBLE);
            holder.tvEndTime.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
        }
        if (TextUtils.equals(homeData.getType(), Common.RECOMMEND_DOCTOR)) {
            if (TextUtils.isEmpty(homeData.getImagePath())) {
                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(homeData.getImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);
            }
            holder.tvRole.setVisibility(View.VISIBLE);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText(homeData.getDepartName() + "  " +
                    homeData.getProfessLevel());
            holder.tvDescribe.setLineSpacing(10,1);
            holder.tvDescribe.setText(homeData.getClinicName() + "\n" +"擅长："+
                    homeData.getMajor());
            holder.tvDescribe.setVisibility(View.VISIBLE);
            holder.tvEndTime.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
        }
        if (TextUtils.equals(homeData.getType(), Common.CONVERSATIONS)) {
            try {
                //待获取用户资料的用户列表
                List<String> users = new ArrayList<String>();
                users.add(homeData.getIdentifier());

                /**
                 * 处理im标题有时为空的问题
                 */
                holder.itemView.setClickable(false);

                //获取用户资料
                TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int code, String desc) {
                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code列表请参见错误码表
                        Log.e("tim", "getUsersProfile failed: " + code + " desc");
                        holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
                        holder.tvMessageName.setText(homeData.getIdentifier() + "");
                        holder.itemView.setClickable(true);
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> result) {
                        Log.e("tim", "getUsersProfile succ");
                        for (TIMUserProfile res : result) {
                            Log.e("tim", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                                    + " remark: " + res.getRemark() + "faceurl" + res.getFaceUrl());
                            if (!TextUtils.isEmpty(res.getNickName())) {
                                holder.tvMessageName.setText(res.getNickName() + "");
                                homeData.setDoctorName(res.getNickName() + "");
                                Log.d("linshi","homeData.setDoctorName:"+res.getNickName());
                            } else {
                                holder.tvMessageName.setText(homeData.getIdentifier() + "");
                            }
                            Log.d("linshi","TextUtils.isEmpty(res.getFaceUrl()"+TextUtils.isEmpty(res.getFaceUrl()));
                            if (!TextUtils.isEmpty(res.getFaceUrl())) {
                                Glide.with(context)
                                        .load(res.getFaceUrl())
                                        .placeholder(R.drawable.icon_doctor_default).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model,
                                                               Target<GlideDrawable> target,
                                                               boolean isFirstResource) {
                                        Log.d("linshi","onException:"+e.toString()+",model:"+model+",target:"+target+",isFirstResource:"+isFirstResource);
                                        // 可替换成进度条
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                                   Target<GlideDrawable> target,
                                                                   boolean isFromMemoryCache,
                                                                   boolean isFirstResource) {
                                        // 图片加载完成，取消进度条
                                        return false;
                                    }
                                }).error(R.drawable.icon_doctor_default)
                                        .into(holder.imgMessageAvator);
                                homeData.setImagePath(res.getFaceUrl() + "");
                            } else {
                                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
                            }
                        }
                        holder.itemView.setClickable(true);
                    }
                });

                holder.tvDescribe.setText(homeData.getLastMessage().getSummary() + "");
                holder.tvDescribe.setVisibility(View.VISIBLE);
                holder.tvTime.setText(homeData.getLasttime() + "");
                holder.tvEndTime.setVisibility(View.GONE);
                holder.tvAddress.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(homeData.getUnReadMessage())) {
                    holder.tvUnread.setVisibility(View.VISIBLE);
                    holder.tvUnread.setText(homeData.getUnReadMessage() + "");
                } else {
                    holder.tvUnread.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
        }
        if (TextUtils.equals(homeData.getType(), Common.ACTIVITY_PUSH)) {
            if (TextUtils.isEmpty(homeData.getImagePath())) {

                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
            } else {
                Glide.with(context)
                        .load(homeData.getImagePath2())
                        .placeholder(R.drawable.icon_push).dontAnimate()
                        .error(R.drawable.icon_push)
                        .into(holder.imgMessageAvator);

            }
            holder.tvEndTime.setVisibility(View.VISIBLE);
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvDescribe.setVisibility(View.GONE);
            holder.tvRole.setVisibility(View.GONE);
//            holder.tvEndTime.setText(DateUtils.getStartandEnd(homeData.getStartTime(), homeData.getEndTime()));
            holder.tvEndTime.setText(homeData.getStartTime() + "-" + homeData.getEndTime());
//            holder.tvEndTime.setText(homeData.getEndTime());
            holder.tvAddress.setText(homeData.getAddress());
            holder.tvMessageName.setText(homeData.getName() + "  ");
            holder.tvTime.setText(homeData.getCreatedTime());
//            holder.tvTime.setText(homeData.getCreatedTime());
//            holder.tvRole.setText("（主治医生）");
//            holder.tvDescribe.setText("最近的聊天记录");
        }



    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        ImageView imgMessageAvator;
        @BindView(R.id.tv_message_name)
        TextView tvMessageName;
        @BindView(R.id.tv_role)
        TextView tvRole;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_unread)
        TextView tvUnread;
        @BindView(R.id.tv_endtime)
        TextView tvEndTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
