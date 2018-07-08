package com.zack.kongtv.activities.MovieList;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.zack.kongtv.R;
import com.zackdk.base.BaseMvpActivity;

public class MovieListActivity extends BaseMvpActivity<MovieListPresenter> {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    public int setView() {
        return R.layout.activity_movie_list;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview);
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
    protected void initImmersionBar() {
        immersionBar.titleBar(toolbar).init();
    }

    @Override
    protected MovieListPresenter setPresenter() {
        return new MovieListPresenter();
    }
}
