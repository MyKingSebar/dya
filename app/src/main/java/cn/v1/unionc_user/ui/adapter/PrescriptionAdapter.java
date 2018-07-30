package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.NetCouldPullData;
import cn.v1.unionc_user.model.PrescriptionInfoData;

/**
 * Created by qy on 2018/2/7.
 */

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionInfoData.DataData.PrescriptionData.MedicinesData> datas = new ArrayList<>();

    public PrescriptionAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PrescriptionInfoData.DataData.PrescriptionData.MedicinesData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public PrescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_find_live_list, null));
    }


    @Override
    public void onBindViewHolder(final PrescriptionAdapter.ViewHolder holder, final int position) {
        final PrescriptionInfoData.DataData.PrescriptionData.MedicinesData livesData = datas.get(position);
        Log.d("linshi", "doctorsdata:" + livesData.toString());

        holder.Title.setText(livesData.getTitle() + "");
        holder.Packaging.setText(livesData.getPackaging()+"");
        holder.Num.setText(livesData.getNum() + "");
        holder.Usages.setText(livesData.getUsages() + "");
        holder.SalePrice.setText(livesData.getSalePrice() + "");

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll)
        LinearLayout ll;

        @BindView(R.id.Title)
        TextView Title;
        @BindView(R.id.Packaging)
        TextView Packaging;
        @BindView(R.id.Num)
        TextView Num;
        @BindView(R.id.Usages)
        TextView Usages;
        @BindView(R.id.SalePrice)
        TextView SalePrice;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
