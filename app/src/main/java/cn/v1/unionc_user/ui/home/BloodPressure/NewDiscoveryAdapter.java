//package cn.v1.unionc_user.ui.home.BloodPressure;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.vodone.cp365.R;
//import com.vodone.cp365.caibodata.HomePageListData;
//import com.vodone.cp365.caibodata.SearchMatchDoctorData;
//import com.vodone.cp365.customview.CircleImageView;
//import com.vodone.cp365.ui.activity.BaseActivity;
//import com.vodone.cp365.ui.activity.BaseHomePageNewActivity;
//import com.vodone.cp365.ui.activity.MGServiceListActivityFactory;
//import com.vodone.cp365.util.GlideUtil;
//import com.vodone.cp365.util.StringUtil;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import cn.v1.unionc_user.R;
//import cn.v1.unionc_user.ui.home.BloodPressure.data.HomePageListData;
//import cn.v1.unionc_user.ui.home.BloodPressure.data.SearchMatchDoctorData;
//import cn.v1.unionc_user.ui.home.health.GlideUtil;
//
///**
// * Created by cp on 2017/5/31.
// */
//
//public class NewDiscoveryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    public final int TITLE_TYPE = 1; //title标题  不可点击
//    public final int ONE_TYPE = 2;      //默认显示
//    public final int GRID_TYPE = 3; //GridView显示
//    public final int FOOD_TYPE = 4; //fooderview
//    public final int DIVIDE_TYPE = 5; //fooderview
//
//    private Context mContext;
//    private ArrayList<HomePageListData.PageItem> pageServicesList = new ArrayList();
//    private ArrayList<SearchMatchDoctorData.RetListEntity> discoveryNurseList = new ArrayList();
//
//    private View footView;
//
//    public NewDiscoveryAdapter(Context context, ArrayList<HomePageListData.PageItem> pageServicesList,
//                               ArrayList<SearchMatchDoctorData.RetListEntity> discoveryNurseList){
//        this.mContext = context;
//        this.pageServicesList = pageServicesList;
//        this.discoveryNurseList = discoveryNurseList;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType){
//            case TITLE_TYPE:
//                return new TitleDiscoveryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_discovery_title_view,parent,false));
//            case ONE_TYPE:
//                return new NomalDiscoveryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_discovery_list,parent,false));
//            case GRID_TYPE:
//                return new GridDiscoveryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_discovery_header_view,parent,false));
//            case DIVIDE_TYPE:
//                return new DivideDiscoveryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_discovery_divide_view,parent,false));
//            case FOOD_TYPE:
//                return new TitleDiscoveryViewHolder(footView);
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////        int itemViewType = getItemViewType(position);
//        if (holder instanceof TitleDiscoveryViewHolder){
//            BindTitleDiscoveryHolder((TitleDiscoveryViewHolder)holder,position);
//        }else if (holder instanceof NomalDiscoveryViewHolder){
//            BindNomalDiscoveryHolder((NomalDiscoveryViewHolder)holder,position);
//        }else if (holder instanceof GridDiscoveryViewHolder){
//            BindGridDiscoveryHolder((GridDiscoveryViewHolder)holder,position);
//        }else if (holder instanceof DivideDiscoveryViewHolder){
////            BindGridDiscoveryHolder((DivideDiscoveryViewHolder)holder,position);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
////        return 1 + pageServicesList.size() + 2 + discoveryNurseList.size();
//        return 2 + discoveryNurseList.size();
//    }
//
//    public void addFooterView(View view){
//        this.footView = view;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
////            return super.getItemViewType(position);
////        if(0 == position){
////            return DIVIDE_TYPE;
////        }else if(0 < position && position <= pageServicesList.size()){
////            return GRID_TYPE;
////        }else if (position == pageServicesList.size() + 1){
////            return TITLE_TYPE;
////        }else if (pageServicesList.size() + 1 < position && position <= pageServicesList.size() + discoveryNurseList.size() + 1){
////            return ONE_TYPE;
////        }else {
////            return FOOD_TYPE;
////        }
//        if(0 == position){
//            return TITLE_TYPE;
//        }else if (0 < position && position < discoveryNurseList.size() + 1){
//            return ONE_TYPE;
//        }else {
//            return FOOD_TYPE;
//        }
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager){
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    switch (type){
//                        case DIVIDE_TYPE:
//                        case TITLE_TYPE:
//                        case ONE_TYPE:
//                        case FOOD_TYPE:
//                            return 4;
//                        case GRID_TYPE:
//                            return 1;
//                        default:
//                            return gridLayoutManager.getSpanCount();
//                    }
//                }
//            });
//        }
//    }
//
//    private void BindGridDiscoveryHolder(GridDiscoveryViewHolder holder, final int position) {
//        if (pageServicesList.size() > 0) {
//            GlideUtil.setLocalImageHead(mContext, pageServicesList.get(position - 1).getPic(), holder.headerViewIv,
//                    R.drawable.icon_home_service_circle_default,R.drawable.icon_home_service_circle_default);
////            GlideUtil.setNormalImage(mContext, pageServicesList.get(position - 1).getPic(), holder.headerViewIv, -1, -1);
//            holder.headerViewName.setText(pageServicesList.get(position - 1).getName());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MGServiceListActivityFactory.startActivity((BaseActivity) mContext, pageServicesList.get(position - 1));
//                }
//            });
//        }
//    }
//
//    private void BindNomalDiscoveryHolder(NomalDiscoveryViewHolder holder, int mposition) {
//        int position = mposition - 1;
//        final SearchMatchDoctorData.RetListEntity data = discoveryNurseList.get(position);
//        if (position == 0) {
//            holder.view_item_line.setVisibility(View.GONE);
//        } else {
//            holder.view_item_line.setVisibility(View.VISIBLE);
//        }
////        if (StringUtil.checkNull(CaiboApp.getInstance().longitude) || StringUtil.checkNull(CaiboApp.getInstance().latitude) || CaiboApp.getInstance().getCityCode().equals("0")){
////            holder.tv_item_distance.setVisibility(View.GONE);
////        }else {
////            holder.tv_item_distance.setText(distanceValue(data.getDistance()));
////        }
//        holder.tv_item_name.setText(data.getNurserName());
//        holder.tv_item_type.setText(TextUtils.isEmpty(data.getExperience())?"":data.getExperience()+"年经验");
////        if(data.getRoleType().equals("004")||data.getRoleType().equals("005")||data.getRoleType().equals("006")){
////            String place = data.getNativeplace();
////            holder.tv_item_desc.setText(place);
////        }else {
//        holder.tv_item_desc.setText("擅长: " + data.getSkilledSymptom());
////        }
//        holder.tv_item_love.setText(data.getAngelValue() + "");
//        GlideUtil.setCircleOverrideImage(holder.itemView.getContext(), data.getUserhead_img(), holder.img_item_head,55,55,
//                R.drawable.icon_intell_doctor_default, R.drawable.icon_intell_doctor_default);
////        GlideUtil.setHeadImage(holder.itemView.getContext(), data.getUserhead_img(), holder.img_item_head, R.drawable.icon_intell_doctor_default, -1);
////        if (data.getRoleId().equals("003")) {
////            holder.tv_item_distance.setVisibility(View.GONE);
////        } else {
////            holder.tv_item_distance.setVisibility(View.VISIBLE);
////        }
//        holder.tv_item_greed.setText((StringUtil.checkNull(data.getHospital()) ? "" : data.getHospital()) + " | " + (StringUtil.checkNull(data.getDepartment()) ? "" : data.getDepartment()));
////        holder.tv_item_keshi.setText(StringUtil.checkNull(data.getDepartment()) ? "" : data.getDepartment());
//        holder.tv_item_love.setText(data.getAngelValue() + "");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int angelValue = 0;
//                try {
//                    angelValue = Integer.parseInt(data.getAngelValue()) ;
//                }catch (Exception e){}
//                Intent intent = BaseHomePageNewActivity.getIntent(mContext, data.getUserId() + "", data.getRoleId() + "", BaseHomePageNewActivity.FROMSEARCH, data.getDistance() + "", "",angelValue);
//                mContext.startActivity(intent);
//            }
//        });
//
////        if(!TextUtils.isEmpty(data.getGzFlag())&&data.getGzFlag().equals("1")){
////            holder.attentionImg.setVisibility(View.VISIBLE);
////        }else{
////            holder.attentionImg.setVisibility(View.GONE);
////        }
//
//        if(!TextUtils.isEmpty(data.getYyFlag())&&data.getYyFlag().equals("1")){
//            holder.appointImg.setVisibility(View.VISIBLE);
//        }else{
//            holder.appointImg.setVisibility(View.GONE);
//        }
//    }
//
//    private void BindTitleDiscoveryHolder(TitleDiscoveryViewHolder holder, int position) {
//
//    }
//
//    class GridDiscoveryViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.discovery_header_view_iv)
//        ImageView headerViewIv;
//        @Bind(R.id.discovery_header_view_tv)
//        TextView headerViewName;
//        public GridDiscoveryViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//
//    class TitleDiscoveryViewHolder extends RecyclerView.ViewHolder{
//        public TitleDiscoveryViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//    class DivideDiscoveryViewHolder extends RecyclerView.ViewHolder{
//        public DivideDiscoveryViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//
//    class NomalDiscoveryViewHolder extends RecyclerView.ViewHolder{
////        @Bind(R.id.doctordetails_img_head)
////        ImageView img_item_head;
//        @Bind(R.id.doctordetails_img_head)
//        CircleImageView img_item_head;
//        @Bind(R.id.doctordetails_tv_headzan)
//        TextView tv_item_love;
//        @Bind(R.id.doctordetails_tv_name)
//        TextView tv_item_name;
//        @Bind(R.id.doctordetails_tv_type)
//        TextView tv_item_type;
////        @Bind(R.id.doctordetails_tv_distance)
////        TextView tv_item_distance;
//        @Bind(R.id.doctordetails_tv_greed)
//TextView tv_item_greed;
////        @Bind(R.id.doctordetails_tv_keshi)
////        TextView tv_item_keshi;
//        @Bind(R.id.doctordetails_tv_desc)
//TextView tv_item_desc;
//        @Bind(R.id.doctordetails_view_line)
//        View view_item_line;
////        @Bind(R.id.doctordetails_img_appoint)
////        ImageView attentionImg;
//        @Bind(R.id.doctordetails_img_appoint)
//ImageView appointImg;
//
//        public NomalDiscoveryViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//
//    private String distanceValue(int distance) {
//        int point = 0;
//        int afterpoint = 0;
//        if (distance >= 1000) {
//            point = distance / 1000;
//            afterpoint = (distance / 100) % 10;
//            String after = afterpoint == 0 ? "km" : "." + afterpoint + "km";
//            return point + after;
//        }
//        return distance + "m";
//    }
//}
