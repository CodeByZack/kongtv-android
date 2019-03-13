package com.zack.kongtv.fragments.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.CarouselLayoutManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zack.kongtv.Adapter.GridAdapter;
import com.zack.kongtv.Const;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MainActivity;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.NoScrollGridView;
import com.zackdk.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zack on 2018/5/30.
 */

public class HomeFragmentNew extends BaseMvpFragment<HomePresenter> implements IHomeView{
    private SwipeRefreshLayout swRefresh;
    private RecyclerView recyclerView;
    private AutoPlayRecyclerView banner;
    private HomeAdapter homeAdapter;
    private List<HomeItemBean> data = new LinkedList<>();
    private List<Cms_movie> banners = new LinkedList<>();
    private BannerAdapter bannerAdapter;
    private CarouselLayoutManager carouselLayoutManager;
    @Override
    public int setView() {
        return R.layout.fragment_home_new;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        presenter.requestData();
    }

    private void initLogic() {
        homeAdapter = new HomeAdapter(R.layout.home_item,data);
        bannerAdapter = new BannerAdapter(R.layout.banner_item,banners);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(mActivity, FullScreenActivity.class));
            }
        });
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.tv_more){
                    ((MainActivity)mActivity).showPage(data.get(position).getType());
                }
            }
        });
        recyclerView.setAdapter(homeAdapter);

        banner.setAdapter(bannerAdapter);

        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    private void initView() {
        swRefresh = findViewById(R.id.sw_refresh);
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.requestFocus();

        banner.setLayoutManager(carouselLayoutManager);

    }

    @Override
    protected HomePresenter setPresenter() {
        return new HomePresenter();
    }

    @Override
    public void updateView(HomeDataBean dataBean) {
        data.clear();
        data.addAll(dataBean.getHomeItemBeans());
        homeAdapter.notifyDataSetChanged();
        banners.clear();
        banners.addAll(dataBean.getBannerItemBeans());
        bannerAdapter.notifyDataSetChanged();

    }

    @Override
    public void setRefresh(boolean refresh) {
        swRefresh.setRefreshing(refresh);
    }

    private class HomeAdapter extends BaseQuickAdapter<HomeItemBean,BaseViewHolder> {

        public HomeAdapter(int layoutResId, @Nullable List<HomeItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, HomeItemBean item) {
            String title = "";
            String more = "";
            int id = 0;
            switch (item.getType()){
                case Const.Film:
                    title = "电影热映";
                    more = "更多电影";
                    id = R.drawable.ic_dianying;
                    break;
                case Const.Episode:
                    title = "热播电视";
                    more = "更多电视剧";
                    id = R.drawable.ic_dianshiju;
                    break;
                case Const.Anime:
                    title = "动漫";
                    more = "更多动漫";
                    id = R.drawable.ic_dongman;
                    break;
                case Const.Variety:
                    title = "综艺";
                    more = "更多综艺";
                    id = R.drawable.ic_zongyi;
                    break;
            }
            Drawable drawable = getResources().getDrawable(R.drawable.ic_dianshiju);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            TextView textView = helper.getView(R.id.tv_title);
            textView.setCompoundDrawables(drawable,null,null,null);
            helper.setText(R.id.tv_title,title);
            helper.setText(R.id.tv_more,more);
            helper.addOnClickListener(R.id.tv_more);

            NoScrollGridView gv = helper.getView(R.id.gv_container);

            gv.setAdapter(new GridAdapter<Cms_movie>(item.getMovieDetailBeans(),R.layout.movie_item) {
                @Override
                public void bindView(ViewHolder holder, final Cms_movie obj) {
                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity, MovieDetailActivity.class).putExtra("url",obj));
                        }
                    });

                    MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.movie_img));
                    holder.setText(R.id.tv_name,obj.getVodName());
                    if(TextUtils.isEmpty(obj.getVodScore())){
                        holder.getView(R.id.tv_score).setVisibility(View.GONE);
                    }else{
                        holder.setText(R.id.tv_score,obj.getVodScore()+"分");
                    }
                    holder.setText(R.id.tv_shortdesc,obj.getVodRemarks());
                    if(TextUtils.isEmpty(obj.getVodActor())){
                        holder.setText(R.id.tv_actors,obj.getVodRemarks());
                    }else{
                        holder.setText(R.id.tv_actors,obj.getVodActor());
                    }
                }
            });
        }
    }

    private class BannerAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {

        public BannerAdapter(int layoutResId, @Nullable List<Cms_movie> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, Cms_movie item) {
            MyImageLoader.showImage(mActivity,item.getVodPic(), (ImageView) helper.getView(R.id.movimg));
            helper.setText(R.id.movtitle,item.getVodName());
        }




    }

    @Override
    public void showLoading() {
        swRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swRefresh.setRefreshing(false);
    }
}
