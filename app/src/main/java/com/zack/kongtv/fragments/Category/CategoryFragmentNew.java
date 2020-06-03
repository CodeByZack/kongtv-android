package com.zack.kongtv.fragments.Category;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.wang.avi.AVLoadingIndicatorView;
import com.zack.kongtv.Const;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.BaseMvpFragment;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by zack on 2018/5/31.
 */

public class CategoryFragmentNew extends BaseMvpFragment<CategoryPresenter> implements ICategoryView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AVLoadingIndicatorView loadingView;
    private CategoryAdapter adapter;
    private List<Cms_movie> data = new LinkedList<>();
    private AdLoader adLoader;

    @Override
    public int setView() {
        return R.layout.fragment_category;
    }

    public static CategoryFragmentNew instance(int type){
        CategoryFragmentNew categoryFragment = new CategoryFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }
    @Override
    public void initBasic(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int type;
        if(bundle!=null){
            type = bundle.getInt("type");
        }else {
            type = Const.Film;
        }
        initView();
        initLogic();
        initAd();
        presenter.setTargetType(type);
        presenter.requestData();
    }

    private void initAd() {
        adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        Log.d("TAG", "onUnifiedNativeAdLoaded: ");
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
    }


    private void initLogic() {

        adapter = new CategoryAdapter(R.layout.movie_item,data);
        adapter.openLoadAnimation();

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(0,3,getResources().getDimensionPixelSize(R.dimen.little_margin),true));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadMore();
            }
        },recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                presenter.refresh();
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity,MovieDetailActivity.class).putExtra("url",data.get(position)));
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleview);
        swipeRefreshLayout = findViewById(R.id.sw_refresh);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadingView = findViewById(R.id.loading);
    }


    @Override
    protected CategoryPresenter setPresenter() {
        return new CategoryPresenter();
    }

    @Override
    public void updateView(CategoryDataBean data) {

        adapter.addData(data.getMovieItemBeans());
        loadingView.smoothToHide();
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        adapter.loadMoreEnd();
    }

    private class CategoryAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {
        public CategoryAdapter(int layoutResId, @Nullable List<Cms_movie> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Cms_movie obj) {
            MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.post_img));
            holder.setText(R.id.post_title,obj.getVodName());
        }
    }

    private class TagAdapter extends BaseQuickAdapter<TagItemBean,BaseViewHolder> {
        public TagAdapter(int layoutResId, @Nullable List<TagItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TagItemBean item) {
            TextView tv = helper.getView(R.id.tv_tag);
            helper.setText(R.id.tv_tag,item.getTag());
            if(!item.isSelect()){
                tv.setBackground(null);
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            }else{
                tv.setBackground(getResources().getDrawable(R.drawable.tag_bg));
                tv.setTextColor(getResources().getColor(R.color.colorIcon));
            }
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
