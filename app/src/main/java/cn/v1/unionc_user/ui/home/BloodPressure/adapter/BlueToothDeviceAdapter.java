package cn.v1.unionc_user.ui.home.BloodPressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import cn.v1.unionc_user.ui.home.BloodPressure.BlueToothClassfyListActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DeviceData;

/**
 * Created by qy on 2018/5/11.
 */

public class BlueToothDeviceAdapter extends RecyclerView.Adapter<BlueToothDeviceAdapter.ViewHolder> {

    private Context context;
    private List<DeviceData> devicedatas = new ArrayList<>();

    public BlueToothDeviceAdapter(Context context, List<DeviceData> devicedatas) {
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
                Intent intent = new Intent(context,BlueToothClassfyListActivity.class);
                intent.putExtra("device",devicedatas.get(position).getDevice());
                context.startActivity(intent);
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
