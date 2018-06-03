package com.zack.kongtv.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zack.kongtv.GridAdapter;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.MovieItemBean;
import com.zack.kongtv.view.NoScrollGridView;
import com.zackdk.base.AbsFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zack on 2018/5/30.
 */

public class HomeFragment extends AbsFragment {
    private SwipeRefreshLayout swRefresh;
    private RecyclerView recyclerView;
    private Banner banner;
    private HomeAdapter homeAdapter;
    private List<HomeItemBean> data = new LinkedList<>();
    private List<BannerItemBean> banners = new LinkedList<>();

    @Override
    public int setView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        requestData();
    }

    private void requestData() {
        HomeItemBean itemBean = new HomeItemBean();
        data.add(itemBean);
        data.add(itemBean);
        data.add(itemBean);
        homeAdapter.notifyDataSetChanged();

        List<String> paths = new LinkedList<>();
        paths.add("连载至330集 爱回家之开心速");
        paths.add("连0集 爱回开心速");
        paths.add("连载至330集 爱开心速");
        paths.add("连载至330集 家之开心速");
        paths.add("连载至3之开心速");
        BannerItemBean bean = new BannerItemBean();
        bean.setImg("https://wx1.sinaimg.cn/mw690/005yF2tCgy1fpi9dg6vg2j30b40fkjwd.jpg");
        banners.add(bean);
        banners.add(bean);
        banners.add(bean);
        banners.add(bean);
        banners.add(bean);
        banner.setImages(banners);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerItemBean bean = (BannerItemBean) path;
                Glide.with(mActivity).load(bean.getImg()).placeholder(R.drawable.placeholder).into(imageView);
            }
        });
        banner.setBannerTitles(paths);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.start();
    }

    private void initLogic() {
        homeAdapter = new HomeAdapter(R.layout.home_item,data);
        View view = getLayoutInflater().inflate(R.layout.header,null);
        banner = view.findViewById(R.id.banner);
        homeAdapter.addHeaderView(view);
        recyclerView.setAdapter(homeAdapter);
    }

    private void initView() {
        swRefresh = findViewById(R.id.sw_refresh);
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.requestFocus();
    }

    private class HomeAdapter extends BaseQuickAdapter<HomeItemBean,BaseViewHolder> {
        public HomeAdapter(int layoutResId, @Nullable List<HomeItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeItemBean item) {
            NoScrollGridView gv = helper.getView(R.id.gv_container);

            ArrayList<MovieItemBean> list = new ArrayList<>();
            MovieItemBean itemBean = new MovieItemBean();
            list.add(itemBean);
            list.add(itemBean);
            list.add(itemBean);
            list.add(itemBean);
            list.add(itemBean);
            list.add(itemBean);
            gv.setAdapter(new GridAdapter<MovieItemBean>(list,R.layout.movie_item) {
                @Override
                public void bindView(ViewHolder holder, MovieItemBean obj) {

                }
            });
        }
    }
}
