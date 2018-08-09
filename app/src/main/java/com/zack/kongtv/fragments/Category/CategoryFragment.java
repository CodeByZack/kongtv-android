package com.zack.kongtv.fragments.Category;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.zack.kongtv.AppConfig;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.BaseMvpFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zack on 2018/5/31.
 */

public class CategoryFragment extends BaseMvpFragment<CategoryPresenter> implements ICategoryView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View headerView;
    private RecyclerView rv1,rv2,rv3;
    private TextView tvMore;
    private CategoryAdapter adapter;
    private TagAdapter tagAdapter1,tagAdapter2,tagAdapter3;
    private List<MovieDetailBean> data = new LinkedList<>();
    private List<TagItemBean> data1 = new LinkedList<>(),data2 = new LinkedList<>(),data3 = new LinkedList<>();

    @Override
    public int setView() {
        return R.layout.fragment_category;
    }

    public static CategoryFragment instance(String url){
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart("CategoryFragment");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd("CategoryFragment");
//    }
    @Override
    public void initBasic(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String targetUrl;
        if(bundle!=null){
            targetUrl = bundle.getString("url");
        }else {
            targetUrl = DataResp.MovieUrl;
        }
        presenter.setTargetUrl(targetUrl);
        initView();
        initLogic();
        presenter.requestData();
    }


    private void initLogic() {
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rv2.getVisibility() == View.VISIBLE){
                    rv2.setVisibility(View.GONE);
                    rv3.setVisibility(View.GONE);
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_more_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvMore.setCompoundDrawables(null,null,drawable,null);
                }else{
                    rv2.setVisibility(View.VISIBLE);
                    rv3.setVisibility(View.VISIBLE);
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_more_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvMore.setCompoundDrawables(null,null,drawable,null);
                }
            }
        });


        tagAdapter1 = new TagAdapter(R.layout.tag_tv,data1);
        tagAdapter2 = new TagAdapter(R.layout.tag_tv,data2);
        tagAdapter3 = new TagAdapter(R.layout.tag_tv,data3);
        rv1.setAdapter(tagAdapter1);
        rv2.setAdapter(tagAdapter2);
        rv3.setAdapter(tagAdapter3);

        tagAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(TagItemBean bean:data1){
                    bean.setSelect(false);
                }
                data1.get(position).setSelect(true);
                tagAdapter1.notifyDataSetChanged();
                presenter.setTargetUrl(data1.get(position).getUrl());
                data.clear();
                presenter.requestData();
            }
        });

        tagAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(TagItemBean bean:data3){
                    bean.setSelect(false);
                }
                data3.get(position).setSelect(true);
                tagAdapter3.notifyDataSetChanged();
                presenter.setTargetUrl(data3.get(position).getUrl());
                data.clear();
                presenter.requestData();
            }
        });
        tagAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(TagItemBean bean:data2){
                    bean.setSelect(false);
                }
                data2.get(position).setSelect(true);
                tagAdapter2.notifyDataSetChanged();
                presenter.setTargetUrl(data2.get(position).getUrl());
                data.clear();
                presenter.requestData();
            }
        });
        adapter = new CategoryAdapter(R.layout.movie_item,data);
        adapter.addHeaderView(headerView);
        adapter.openLoadAnimation();

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,3,getResources().getDimensionPixelSize(R.dimen.little_margin),true));
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
                startActivity(new Intent(mActivity,MovieDetailActivity.class).putExtra("url",data.get(position).getTargetUrl()));
            }
        });
    }

    private void initView() {
        getHeaderView();
        recyclerView = findViewById(R.id.recycleview);
        swipeRefreshLayout = findViewById(R.id.sw_refresh);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public View getHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.header2,null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mActivity);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv1 = headerView.findViewById(R.id.rv1);
        rv2 = headerView.findViewById(R.id.rv2);
        rv3 = headerView.findViewById(R.id.rv3);
        rv1.setLayoutManager(linearLayoutManager);
        rv2.setLayoutManager(linearLayoutManager2);
        rv3.setLayoutManager(linearLayoutManager3);
        tvMore = headerView.findViewById(R.id.tv_more);
        return headerView;
    }

    @Override
    protected CategoryPresenter setPresenter() {
        return new CategoryPresenter();
    }

    @Override
    public void updateView(CategoryDataBean data) {
        data1.clear();
        data2.clear();
        data3.clear();

        data1.addAll(data.getTag1());
        data2.addAll(data.getTag2());
        data3.addAll(data.getTag3());

        adapter.addData(data.getMovieDetailBeans());
        tagAdapter1.notifyDataSetChanged();
        tagAdapter2.notifyDataSetChanged();
        tagAdapter3.notifyDataSetChanged();
    }

    @Override
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        adapter.loadMoreEnd();
    }

    private class CategoryAdapter extends BaseQuickAdapter<MovieDetailBean,BaseViewHolder> {
        public CategoryAdapter(int layoutResId, @Nullable List<MovieDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, MovieDetailBean obj) {
            MyImageLoader.showImage(mActivity,obj.getMovieImg(), (ImageView) holder.getView(R.id.movie_img));
            holder.setText(R.id.tv_name,obj.getMovieName());
            if(TextUtils.isEmpty(obj.getMovieScore())){
                holder.getView(R.id.tv_score).setVisibility(View.GONE);
            }else{
                holder.setText(R.id.tv_score,obj.getMovieScore());
            }
            holder.setText(R.id.tv_shortdesc,obj.getMovieShortDesc());
            if(TextUtils.isEmpty(obj.getMovieActors())){
                holder.setText(R.id.tv_actors,obj.getMovieShortDesc());
            }else{
                holder.setText(R.id.tv_actors,obj.getMovieActors());
            }
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
