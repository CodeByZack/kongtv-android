package com.zack.kongtv.activities.MovieList;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.activities.SearchResult.SearchActivity;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieItem;
import com.zack.kongtv.util.MyImageLoader;
import com.zackdk.Utils.ToastUtil;
import com.zackdk.base.BaseMvpActivity;

import java.util.LinkedList;
import java.util.List;

public class MovieListActivity extends BaseMvpActivity<MovieListPresenter> implements IMovieListView{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private List<Cms_movie> data = new LinkedList<>();

    @Override
    public int setView() {
        return R.layout.activity_movie_list;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        int mode = getIntent().getIntExtra("mode", Const.History);
        initView();
        initLogic();
        presenter.setMode(mode);
        presenter.requestData();
    }

    private void initLogic() {
        adapter = new MovieListAdapter(R.layout.list_item,data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity,MovieDetailActivity.class).putExtra("url",data.get(position)));
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("我的收藏");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                presenter.deleteAll();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initImmersionBar() {
        immersionBar.titleBar(toolbar).statusBarColor(R.color.colorPrimaryDark).init();
    }

    @Override
    protected MovieListPresenter setPresenter() {
        return new MovieListPresenter();
    }

    @Override
    public void updateView(List<Cms_movie> data) {
        adapter.addData(data);
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void clear() {
        this.data.clear();
        adapter.notifyDataSetChanged();
    }

    private class MovieListAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {
        public MovieListAdapter(int layoutResId, @Nullable List<Cms_movie> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Cms_movie item) {
            MyImageLoader.showImage(mActivity,item.getVodPic(), (ImageView) helper.getView(R.id.movie_img));
            helper.setText(R.id.movie_name,item.getVodName());
            helper.setText(R.id.movie_status,item.getVodRemarks());
            helper.setText(R.id.movie_type,item.getVodClass());
            if(item.getRecord() != null){
                helper.setVisible(R.id.movie_record,true);
                helper.setText(R.id.movie_record,"上次观看到："+item.getRecord());
            }else{
                helper.getView(R.id.movie_record).setVisibility(View.GONE);
            }
        }
    }
}
