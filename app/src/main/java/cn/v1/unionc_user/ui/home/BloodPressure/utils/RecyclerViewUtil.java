package cn.v1.unionc_user.ui.home.BloodPressure.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.BloodPressure.HeaderViewRecyclerAdapter;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.FullyLinearLayoutManager;

/**
 * Created by lawrence on 15/7/20.
 * recyclerview 刷新和加载更多
 */
public class RecyclerViewUtil {


    public interface RecyclerCallBack {
        void doRefresh();

        void doLoadMore();
    }

    private int lastVisibleItemPosition;
    private boolean isLoadingMore = false;

    RecyclerView mRecyclerView;
    RecyclerCallBack mCallBack;

    View mLoadMoreView;
    TextView tv_loadmore_retry;
    ProgressBar loadmore_progress;

    /**
     * @param mCallBack     刷新和加载更多回调
     * @param mRecyclerView recyclerview
     * @param adapter       recyclerview.adapter
     * @param isLoadMore    是否要加载更多
     */
    public RecyclerViewUtil(final RecyclerCallBack mCallBack, final RecyclerView mRecyclerView, RecyclerView.Adapter adapter, boolean isLoadMore) {
        this.mCallBack = mCallBack;
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
//        mRecyclerView.setAdapter(adapter);
        if (isLoadMore) {
            mLoadMoreView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.progress_loadmore, mRecyclerView, false);
            tv_loadmore_retry = ButterKnife.findById(mLoadMoreView, R.id.loadmore_tv_loadmore);
            loadmore_progress = ButterKnife.findById(mLoadMoreView, R.id.loadmore_progress);
            tv_loadmore_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.doLoadMore();
                }
            });
            HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
            if(adapter instanceof HeaderViewRecyclerAdapter){
                headerViewRecyclerAdapter = (HeaderViewRecyclerAdapter) adapter;
            }else{
                headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
                headerViewRecyclerAdapter.addFooterView(mLoadMoreView);
            }
            mRecyclerView.setAdapter(headerViewRecyclerAdapter);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                        isLoadingMore = true;
                        tv_loadmore_retry.setVisibility(View.INVISIBLE);
                        mLoadMoreView.setVisibility(View.VISIBLE);
                        loadmore_progress.setVisibility(View.VISIBLE);
                        mCallBack.doLoadMore();
                    }
//                    else if(lastVisibleItemPosition >= totalItemCount -1 && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        Toast.makeText(mRecyclerView.getContext(),"没有更多数据",Toast.LENGTH_SHORT).show();
//                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                }
            });
        }
    }

    /**
     * @param mCallBack     刷新和加载更多回调
     * @param mRecyclerView recyclerview
     * @param adapter       recyclerview.adapter
     * @param isLoadMore    是否要加载更多
     */
    public RecyclerViewUtil(final RecyclerCallBack mCallBack, final RecyclerView mRecyclerView, RecyclerView.Adapter adapter, boolean isLoadMore, boolean isGrid) {
        this.mCallBack = mCallBack;
        this.mRecyclerView = mRecyclerView;
        if (!isGrid) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        }else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(),4, LinearLayoutManager.VERTICAL,false));
        }
//        mRecyclerView.setAdapter(adapter);
        if (isLoadMore) {
            mLoadMoreView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.progress_loadmore, mRecyclerView, false);
            tv_loadmore_retry = ButterKnife.findById(mLoadMoreView, R.id.loadmore_tv_loadmore);
            loadmore_progress = ButterKnife.findById(mLoadMoreView, R.id.loadmore_progress);
            tv_loadmore_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.doLoadMore();
                }
            });
//            HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
//            if(adapter instanceof HeaderViewRecyclerAdapter){
//                headerViewRecyclerAdapter = (HeaderViewRecyclerAdapter) adapter;
//            }else{
//                headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
//                headerViewRecyclerAdapter.addFooterView(mLoadMoreView);
//            }
//            if (adapter instanceof NewDiscoveryAdapter)
//                ((NewDiscoveryAdapter) adapter).addFooterView(mLoadMoreView);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                        isLoadingMore = true;
                        tv_loadmore_retry.setVisibility(View.INVISIBLE);
                        mLoadMoreView.setVisibility(View.VISIBLE);
                        loadmore_progress.setVisibility(View.VISIBLE);
                        mCallBack.doLoadMore();
                    }
//                    else if(lastVisibleItemPosition >= totalItemCount -1 && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        Toast.makeText(mRecyclerView.getContext(),"没有更多数据",Toast.LENGTH_SHORT).show();
//                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItemPosition = ((GridLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                }
            });
        }
    }

    public RecyclerViewUtil(final RecyclerCallBack mCallBack, RecyclerView mRecyclerView, RecyclerView.Adapter adapter, boolean isLoadMore, FullyLinearLayoutManager fullyLinearLayoutManager) {
        this.mCallBack = mCallBack;
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.setLayoutManager(fullyLinearLayoutManager);
//        mRecyclerView.setAdapter(adapter);
        if (isLoadMore) {
            mLoadMoreView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.progress_loadmore, mRecyclerView, false);
            tv_loadmore_retry = ButterKnife.findById(mLoadMoreView, R.id.loadmore_tv_loadmore);
            loadmore_progress = ButterKnife.findById(mLoadMoreView, R.id.loadmore_progress);
            tv_loadmore_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.doLoadMore();
                }
            });
            HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
            if(adapter instanceof HeaderViewRecyclerAdapter){
                headerViewRecyclerAdapter = (HeaderViewRecyclerAdapter) adapter;
            }else{
                headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
                headerViewRecyclerAdapter.addFooterView(mLoadMoreView);
            }
            mRecyclerView.setAdapter(headerViewRecyclerAdapter);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                        isLoadingMore = true;
                        tv_loadmore_retry.setVisibility(View.INVISIBLE);
                        mLoadMoreView.setVisibility(View.VISIBLE);
                        loadmore_progress.setVisibility(View.VISIBLE);
                        mCallBack.doLoadMore();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                }
            });
        }
    }


    /**
     * 在成功的action里面调用
     * @param isLoadCompleted 是否全部加载完成 可以通过list.size < pageSize来获取此参数
     */
    public void onLoadComplete(boolean isLoadCompleted) {
        isLoadingMore = isLoadCompleted;
        if(isLoadCompleted) {
            mLoadMoreView.setVisibility(View.VISIBLE);
            tv_loadmore_retry.setText(R.string.loadmore_complete);
            tv_loadmore_retry.setVisibility(View.VISIBLE);
            tv_loadmore_retry.setOnClickListener(null);
            loadmore_progress.setVisibility(View.INVISIBLE);

        }else{
            mLoadMoreView.setVisibility(View.GONE);
            tv_loadmore_retry.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 加载失败，在失败的action里面调用
     */
    public void onLoadMoreFailed() {
        isLoadingMore = false;
        loadmore_progress.setVisibility(View.INVISIBLE);
        tv_loadmore_retry.setText(R.string.loadmore_retry);
        tv_loadmore_retry.setVisibility(View.VISIBLE);
        tv_loadmore_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.doLoadMore();
            }
        });
    }

}
