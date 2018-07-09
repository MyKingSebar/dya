//package cn.v1.unionc_user.ui.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import cn.v1.unionc_user.R;
//import cn.v1.unionc_user.model.ClinicServerListData;
//import cn.v1.unionc_user.ui.me.guardianship.ServiceInfoActivity;
//
///**
// * Created by qy on 2018/2/7.
// */
//
//public class ServiceClinicListAdapter extends RecyclerView.Adapter<ServiceClinicListAdapter.ViewHolder> {
//    private int type=0;
//    private Context context;
//    private List<ClinicServerListData.DataData.DataDataData> datas = new ArrayList<>();
//
//    public ServiceClinicListAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setData(List<ClinicServerListData.DataData.DataDataData> datas) {
//        this.datas = datas;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public ServiceClinicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_me_guardianship_service_list, null));
//    }
//
//
//    @Override
//    public void onBindViewHolder(final ServiceClinicListAdapter.ViewHolder holder, final int position) {
//        final ClinicServerListData.DataData.DataDataData data = datas.get(position);
//
//        holder.tv_name.setText(data.getServiceName());
//        holder.tv_des.setText(data.getContent());
//        holder.tv_price.setText(data.getCharge());
//if(TextUtils.equals("0",data.getIsOpenUp())){
//    //未开通
//    holder.tv_ok.setVisibility(View.GONE);
//    holder.tv_price.setVisibility(View.VISIBLE);
//    holder.tv_go.setVisibility(View.VISIBLE);
//holder.tv_go.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent intent=new Intent(context,ServiceInfoActivity.class);
//        context.startActivity(intent);
//    }
//});
//
//}else if(TextUtils.equals("1",data.getIsOpenUp())){
//    //已开通
//    holder.tv_ok.setVisibility(View.VISIBLE);
//    holder.tv_price.setVisibility(View.GONE);
//    holder.tv_go.setVisibility(View.GONE);
//}
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return datas.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.tv_name)
//        TextView tv_name;
//        @BindView(R.id.tv_des)
//        TextView tv_des;
//        @BindView(R.id.tv_price)
//        TextView tv_price;
//        @BindView(R.id.tv_ok)
//        TextView tv_ok;
//        @BindView(R.id.tv_go)
//        TextView tv_go;
//
//
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//
//}
