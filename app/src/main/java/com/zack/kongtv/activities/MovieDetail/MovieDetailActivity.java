package com.zack.kongtv.activities.MovieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.Instance.Impl_4kwu;
import com.zack.kongtv.Data.Instance.Impl_kankanwu;
import com.zack.kongtv.Data.room.CollectMovie;
import com.zack.kongtv.Data.room.CollectMovieDao;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.activities.PlayMovie.FullScreenActivity;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.PlayMovie.WebviewFullScreenActivity;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.BaseMvpActivity;
import com.zackdk.customview.ExpandableTextView;

import java.util.LinkedList;
import java.util.List;

public class MovieDetailActivity extends BaseMvpActivity<MovieDetailPresenter> implements IMovieDetailView{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ImageView toolbar_img;
    private List<JujiBean> data = new LinkedList<>();

    private AppBarLayout appBar;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageView ivMovie;
    private TextView tvStatus;
    private TextView tvActor;
    private TextView tvType;
    private TextView tvDirector;
    private TextView tvYear;
    private TextView tvLanguage;
    private TextView tvPlay;
    private TextView tvCollect;
    private TextView tvHistory;
    private ExpandableTextView tvMovieDesc;
    private MovieDetailBean movieDetailBean;
    private String targetUrl;


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4,30,true));
        toolbar_img = findViewById(R.id.toolbar_bg);
        appBar = (AppBarLayout)findViewById( R.id.app_bar );
        toolbarLayout = (CollapsingToolbarLayout)findViewById( R.id.toolbar_layout );
        ivMovie = (ImageView)findViewById( R.id.iv_movie );
        tvStatus = (TextView)findViewById( R.id.tv_status );
        tvActor = (TextView)findViewById( R.id.tv_actor );
        tvType = (TextView)findViewById( R.id.tv_type );
        tvDirector = (TextView)findViewById( R.id.tv_director );
        tvYear = (TextView)findViewById( R.id.tv_year );
        tvLanguage = (TextView)findViewById( R.id.tv_language );
        tvPlay = (TextView)findViewById( R.id.tv_play );
        tvCollect = (TextView)findViewById( R.id.tv_collect );
        toolbar = (Toolbar)findViewById( R.id.toolbar );
        tvMovieDesc = (ExpandableTextView) findViewById( R.id.tv_movie_desc );
        tvHistory = findViewById(R.id.tv_history);
    }


    @Override
    public int setView() {
        return R.layout.activity_scrolling;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        targetUrl = getIntent().getStringExtra("url");
        presenter.requestData(targetUrl);
    }

    private void initLogic() {
        MyImageLoader.showFlurImg(mActivity,"",toolbar_img);
        adapter = new Adapter(R.layout.detail_item,data);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay(data.size()-1);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startPlay(position);
            }
        });
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieDetailBean == null){
                    return;
                }
                CollectMovieDao md = DataBase.getInstance().collectMovieDao();
                CollectMovie collec = new CollectMovie();
                collec.setTargetUrl(targetUrl);
                collec.setMovieStatus(movieDetailBean.getMovieStatus());
                collec.setMovieType(movieDetailBean.getMovieType());
                collec.setMovieName(movieDetailBean.getMovieName());
                collec.setMovieImg(movieDetailBean.getMovieImg());
                md.insert(collec);
                collect(true);
            }
        });
        //adapter.addHeaderView(getLayoutInflater().inflate(R.layout.detail_header,null));
    }

    private void startPlay(int position) {
        HistoryMovieDao md = DataBase.getInstance().historyMovieDao();
        HistoryMovie historyMovie = new HistoryMovie();
        historyMovie.setTargetUrl(targetUrl);
        historyMovie.setMovieName(movieDetailBean.getMovieName());
        historyMovie.setMovieImg(movieDetailBean.getMovieImg());
        historyMovie.setMovieStatus(movieDetailBean.getMovieStatus());
        historyMovie.setMovieType(movieDetailBean.getMovieType());
        historyMovie.setMovieRecord(data.get(position).getText());
        md.insert(historyMovie);
        Class target ;
        if(DataResp.INSTANCE.getName() == Impl_4kwu.NAME || DataResp.INSTANCE.getName() == Impl_kankanwu.NAME){
            target = WebviewFullScreenActivity.class;
        }else{
            target = FullScreenActivity.class;
        }
        Intent intent = new Intent(mActivity, target);
        intent.putExtra("url",data.get(position).getUrl());
        intent.putExtra("name",getSupportActionBar().getTitle());
        startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        immersionBar.titleBar(toolbar).init();
    }

    @Override
    protected MovieDetailPresenter setPresenter() {
        return new MovieDetailPresenter();
    }

    @Override
    public void updateView(MovieDetailBean data) {
        movieDetailBean = data;
        MyImageLoader.showFlurImg(mActivity,data.getMovieImg(),toolbar_img);
        MyImageLoader.showImage(mActivity,data.getMovieImg(),ivMovie);

        getSupportActionBar().setTitle(data.getMovieName());
        tvStatus.setText(data.getMovieStatus());
        tvActor.setText(data.getMovieActors());
        tvDirector.setText(data.getMovieDirector());
        tvType.setText(data.getMovieType());
        tvYear.setText(data.getMovieYear());
        tvLanguage.setText(data.getMovieLanguage());
        tvMovieDesc.setText(data.getMovieDesc());
        this.data.clear();
        this.data.addAll(data.getList());
        adapter.notifyDataSetChanged();

        CountEventHelper.countMovieDetail(this,data.getMovieName(),targetUrl);
    }

    @Override
    public void collect(boolean c) {
        if(c){
            tvCollect.setClickable(false);
            tvCollect.setText("已收藏");
        }else{
            tvCollect.setClickable(true);
            tvCollect.setText("收藏");
        }
    }

    @Override
    public void setRecord(String record) {
        tvHistory.setText("上次观看到："+record);
        tvHistory.setVisibility(View.VISIBLE);
    }

    private class Adapter extends BaseQuickAdapter<JujiBean,BaseViewHolder> {
        public Adapter(int layoutResId, @Nullable List<JujiBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JujiBean item) {
            helper.setText(R.id.tv_juji,item.getText());
        }
    }
}
