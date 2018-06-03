package com.zack.kongtv.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.MovieItemBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.AbsFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zack on 2018/5/31.
 */

public class CategoryFragment extends AbsFragment {
    private RecyclerView recyclerView;
    private View headerView;
    private RecyclerView rv1,rv2,rv3;
    private TextView tvMore;
    private CategoryAdapter adapter;
    private TagAdapter tagAdapter1,tagAdapter2,tagAdapter3;
    private List<MovieItemBean> data = new LinkedList<>();
    private List<TagItemBean> data1,data2,data3;
    @Override
    public int setView() {
        return R.layout.fragment_category;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        requestData();
    }

    private void requestData() {
        MovieItemBean bean = new MovieItemBean();
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        adapter.notifyDataSetChanged();
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
        data1 = new LinkedList<>();
        TagItemBean tagItemBean = new TagItemBean();
        tagItemBean.setTag("全部");
        TagItemBean tagItemBean2 = new TagItemBean();
        tagItemBean2.setTag("测试选中");
        tagItemBean2.setSelect(true);
        TagItemBean tagItemBean3 = new TagItemBean();
        tagItemBean3.setTag("全部");
        TagItemBean tagItemBean4 = new TagItemBean();
        tagItemBean4.setTag("全部");
        TagItemBean tagItemBean5 = new TagItemBean();
        tagItemBean5.setTag("全部");
        TagItemBean tagItemBean6 = new TagItemBean();
        tagItemBean6.setTag("全部");
        TagItemBean tagItemBean7 = new TagItemBean();
        tagItemBean7.setTag("全部");
        TagItemBean tagItemBean8 = new TagItemBean();
        tagItemBean8.setTag("全部");
        data1.add(tagItemBean2);
        data1.add(tagItemBean);
        data1.add(tagItemBean3);
        data1.add(tagItemBean4);
        data1.add(tagItemBean5);
        data1.add(tagItemBean6);
        data1.add(tagItemBean7);
        data1.add(tagItemBean8);
        tagAdapter1 = new TagAdapter(R.layout.tag_tv,data1);
        tagAdapter2 = new TagAdapter(R.layout.tag_tv,data1);
        tagAdapter3 = new TagAdapter(R.layout.tag_tv,data1);

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
            }
        });

        tagAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(TagItemBean bean:data1){
                    bean.setSelect(false);
                }
                data1.get(position).setSelect(true);
                tagAdapter3.notifyDataSetChanged();
            }
        });
        tagAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(TagItemBean bean:data1){
                    bean.setSelect(false);
                }
                data1.get(position).setSelect(true);
                tagAdapter2.notifyDataSetChanged();
            }
        });
        adapter = new CategoryAdapter(R.layout.movie_item,data);
        adapter.addHeaderView(headerView);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        getHeaderView();
        recyclerView = findViewById(R.id.recycleview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,getResources().getDimensionPixelSize(R.dimen.little_margin),true));
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

    private class CategoryAdapter extends BaseQuickAdapter<MovieItemBean,BaseViewHolder> {
        public CategoryAdapter(int layoutResId, @Nullable List<MovieItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MovieItemBean item) {

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
}
