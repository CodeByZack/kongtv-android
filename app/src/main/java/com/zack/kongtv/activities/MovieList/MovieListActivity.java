package com.zack.kongtv.activities.MovieList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.Const;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivitycopy;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.util.MyImageLoader;
import com.zackdk.base.BaseMvpActivity;

import java.util.LinkedList;
import java.util.List;

public class MovieListActivity extends BaseMvpActivity<MovieListPresenter> implements IMovieListView{
    private TextView title;
    private Button delete;
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAll();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        title = findViewById(R.id.title);
        delete = findViewById(R.id.clear);
    }

    @Override
    protected void initImmersionBar() {
        immersionBar.statusBarColor(R.color.colorAccent).init();
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
        this.title.setText(title);
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

            StringBuilder desc = new StringBuilder();
            desc.append("别名：");
            desc.append(item.getVodName());
            desc.append("\n导演：");
            desc.append(item.getVodDirector());
            desc.append("\n主演：");
            desc.append(item.getVodActor());
            desc.append("\n类型：");
            desc.append(item.getVodClass());
            desc.append("\n语言：");
            desc.append(item.getVodLang());

            helper.setText(R.id.time_progress,desc.toString());
//            helper.setText(R.id.movie_status,item.getVodRemarks());
//            helper.setText(R.id.movie_type,item.getVodClass());
            if(item.getRecord() != null){
                helper.setVisible(R.id.movie_record,true);
                helper.setText(R.id.movie_record,"上次观看到："+item.getRecord());
            }else{
                helper.getView(R.id.movie_record).setVisibility(View.GONE);
            }
        }
    }
}
