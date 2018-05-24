package cn.v1.unionc_user.ui.home.BloodPressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.BloodPressure.OMRONBannerActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DeviceData;

/**
 * Created by qy on 2018/5/11.
 */

public class BlueToothClassfyAdapter extends RecyclerView.Adapter<BlueToothClassfyAdapter.ViewHolder> {

    private Context context;
    private List<DeviceData> devicedatas = new ArrayList<>();

    public BlueToothClassfyAdapter(Context context, List<DeviceData> devicedatas) {
        this.context = context;
        this.devicedatas = devicedatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bluetooth_device, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imgDeviceIcon.setImageResource(devicedatas.get(position).getDeviceImg());
        holder.tvDeviceName.setText(devicedatas.get(position).getDeviceName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.equals("1",devicedatas.get(position).getDevice())){
                    Intent intent = new Intent(context,OMRONBannerActivity.class);
                    intent.putExtra("deviceName",devicedatas.get(position).getDeviceName());
                    context.startActivity(intent);
                }
//                if(TextUtils.equals("2",devicedatas.get(position).getDevice())){
//                    Intent intent = new Intent(context,DossiersListActviity.class);
//                    context.startActivity(intent);
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return devicedatas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_device_icon)
        ImageView imgDeviceIcon;
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
