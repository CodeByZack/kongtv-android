package com.zack.kongtv.fragments.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zack.kongtv.Const;
import com.zack.kongtv.Adapter.GridAdapter;
import com.zack.kongtv.activities.MainActivity;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivitycopy;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.NoScrollGridView;
import com.zackdk.base.BaseMvpFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zack on 2018/5/30.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView{
    private SwipeRefreshLayout swRefresh;
    private RecyclerView recyclerView;
    private Banner banner;
    private HomeAdapter homeAdapter;
    private List<HomeItemBean> data = new LinkedList<>();
    private List<Cms_movie> banners = new LinkedList<>();

    @Override
    public int setView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        presenter.requestData();
    }

    private void initLogic() {
        homeAdapter = new HomeAdapter(R.layout.home_item,data);
        View view = getLayoutInflater().inflate(R.layout.header,null);
        banner = view.findViewById(R.id.banner);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                startActivity(new Intent(mActivity, MovieDetailActivitycopy.class).putExtra("url",banners.get(position)));
            }
        });
        homeAdapter.addHeaderView(view);
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
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Cms_movie bean = (Cms_movie) path;
                MyImageLoader.showImage(mActivity,bean.getVodPic(),imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
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
        List<String> path = new LinkedList<>();
        for(Cms_movie cms_movie:dataBean.getBannerItemBeans()){
            path.add(cms_movie.getVodName());
        }
        banner.setBannerTitles(path);
        banner.setImages(banners);
        banner.start();
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

//            helper.setText(R.id.tv_title,title);
            helper.setText(R.id.tv_more,title);
            helper.addOnClickListener(R.id.tv_more);

            NoScrollGridView gv = helper.getView(R.id.gv_container);

            gv.setAdapter(new GridAdapter<Cms_movie>(item.getMovieDetailBeans(),R.layout.movie_item) {
                @Override
                public void bindView(ViewHolder holder, final Cms_movie obj) {
                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity, MovieDetailActivitycopy.class).putExtra("url",obj));
                        }
                    });
                    MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.post_img));
                    holder.setText(R.id.post_title,obj.getVodName());
                }
            });
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
