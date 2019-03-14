package com.zack.kongtv.activities.MovieDetail;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.zack.kongtv.Data.room.CollectMovieDao;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.PlayMovie.FullScreenActivity;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.BaseMvpActivity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MovieDetailActivity extends BaseMvpActivity<MovieDetailPresenter> implements IMovieDetailView{

    private CoordinatorLayout mRoot;
    private AppBarLayout mApp_bar;
    private CollapsingToolbarLayout mToolbar_layout;
    private Toolbar mToolbar;
    private LinearLayout mTitleview;
    private ImageView mBack_icon;
    private TextView mToolbarTitle;
    private ImageView mToolbarIcon;
    private android.support.v4.widget.NestedScrollView mScroll_content;
    private android.support.v7.widget.CardView mPoster_border;
    private ImageView mLine_detail_poster;
    private TextView mMv_title;
    private TextView mHead_desc;
    private LinearLayout mDesc_content;
    private TextView mDesc_title;
    private ExpandableTextView mLine_desc;
    private TextView mM3u8_title;
    private RecyclerView mPlay_list2;
    private TextView mWeburl_title;
    private RecyclerView mPlay_list;
    private TextView mRec_title;
    private RecyclerView mRec_list;


    private Cms_movie targetMovie;
    private List<JujiBean> data = new LinkedList<>();
    private Adapter adapter;


    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRoot =  findViewById(R.id.root);
        mApp_bar =  findViewById(R.id.app_bar);
        mToolbar_layout =  findViewById(R.id.toolbar_layout);
        mToolbar =  findViewById(R.id.toolbar);
        mTitleview =  findViewById(R.id.titleview);
        mBack_icon =  findViewById(R.id.back_icon);
        mToolbarTitle =  findViewById(R.id.toolbarTitle);
        mScroll_content =  findViewById(R.id.scroll_content);
        mPoster_border =  findViewById(R.id.poster_border);
        mLine_detail_poster =  findViewById(R.id.line_detail_poster);
        mMv_title =  findViewById(R.id.mv_title);
        mHead_desc =  findViewById(R.id.head_desc);
        mDesc_content =  findViewById(R.id.desc_content);
        mDesc_title =  findViewById(R.id.desc_title);
        mLine_desc =  findViewById(R.id.line_desc);
        mM3u8_title =  findViewById(R.id.m3u8_title);
        mPlay_list2 =  findViewById(R.id.play_list2);
        mPlay_list =  findViewById(R.id.play_list);
        mRec_title =  findViewById(R.id.rec_title);
        mRec_list =  findViewById(R.id.rec_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mPlay_list2.setLayoutManager(new GridLayoutManager(this,4));
        mPlay_list2.addItemDecoration(new GridSpacingItemDecoration(4,30,true));
    }




    @Override
    public int setView() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        targetMovie = (Cms_movie) getIntent().getSerializableExtra("url");
        if (targetMovie != null){
            updateView();
        }else{
            showToast("没有获取到影片信息！");
            finish();
        }
    }

    private void initLogic() {
        adapter =  new Adapter(R.layout.m3u8_item,data);
        mPlay_list2.setAdapter(adapter);
    }

    private void startPlay(int position) {
        HistoryMovieDao md = DataBase.getInstance().historyMovieDao();
        md.insert(AndroidUtil.transferHistory(targetMovie,data.get(position).getText()));

        Intent intent = new Intent(mActivity, FullScreenActivity.class);
        intent.putExtra("url",data.get(position).getUrl());
        intent.putExtra("name",getSupportActionBar().getTitle());
        startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        immersionBar.titleBar(mToolbar).init();
    }

    @Override
    protected MovieDetailPresenter setPresenter() {
        return new MovieDetailPresenter();
    }

    public void updateView() {
        mToolbarTitle.setText(targetMovie.getVodName());
        StringBuilder desc = new StringBuilder();
        desc.append("别名：");
        desc.append(targetMovie.getVodName());
        desc.append("\n导演：");
        desc.append(targetMovie.getVodDirector());
        desc.append("\n主演：");
        desc.append(targetMovie.getVodActor());
        desc.append("\n类型：");
        desc.append(targetMovie.getVodClass());
        desc.append("\n地区：");
        desc.append(targetMovie.getVodArea());
        desc.append("\n语言：");
        desc.append(targetMovie.getVodLang());

        mHead_desc.setText(desc.toString());
        Glide.with(this).load(targetMovie.getVodPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setColor(resource);
            }
        });

        MyImageLoader.showImage(this,targetMovie.getVodPic(),mLine_detail_poster);
        mMv_title.setText(targetMovie.getVodName());
        mLine_desc.setContent(targetMovie.getVodBlurb());
        String playUrl = targetMovie.getVodPlayUrl();
        String[] tmp;
        if(playUrl.contains("$$$")){
            tmp = playUrl.split("\\$\\$\\$");
            if(tmp[0].contains("m3u8")){
                tmp = tmp[0].split("#");
            }else{
                tmp = tmp[1].split("#");
            }
        }else{
            tmp = playUrl.split("#");
        }
        List<JujiBean> jujiBeans = new LinkedList<>();
        for (int i = 0; i < tmp.length ; i++) {
            String t = tmp[i];
            if(!t.contains("m3u8")){
                continue;
            }
            JujiBean jujiBean = new JujiBean();
            String[] tt = t.split("\\$");
            jujiBean.setUrl(tt[1]);
            jujiBean.setText(tt[0]);
            jujiBeans.add(jujiBean);
        }
        Collections.reverse(jujiBeans);
        this.data.clear();
        this.data.addAll(jujiBeans);
        adapter.notifyDataSetChanged();
        CountEventHelper.countMovieDetail(this,targetMovie.getVodName());
    }

    @Override
    public void collect(boolean c) {
        if(c){
//            tvCollect.setClickable(false);
//            tvCollect.setText("已收藏");
        }else{
//            tvCollect.setClickable(true);
//            tvCollect.setText("收藏");
        }
    }

    @Override
    public void setRecord(String record) {
//        tvHistory.setText("上次观看到："+record);
//        tvHistory.setVisibility(View.VISIBLE);
    }

    private class Adapter extends BaseQuickAdapter<JujiBean,BaseViewHolder> {
        public Adapter(int layoutResId, @Nullable List<JujiBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JujiBean item) {
            helper.setText(R.id.btPlayText,item.getText());
        }
    }

    public void setColor(Bitmap bitmap) {
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getMutedSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                if (mRoot != null) {
                    if (vibrant != null) {

                        ValueAnimator colorAnim2 = ValueAnimator.ofArgb(Color.rgb(110, 110, 100), colorBurn(vibrant.getRgb()));
                        colorAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mRoot.setBackgroundColor((Integer) animation.getAnimatedValue());
                                // toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                                mApp_bar.setBackgroundColor((Integer) animation.getAnimatedValue());
                            }
                        });
                        colorAnim2.setDuration(300);
                        colorAnim2.setRepeatMode(ValueAnimator.RESTART);
                        colorAnim2.start();

                        if (Build.VERSION.SDK_INT >= 21) {
                            Window window = getWindow();
                            window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                            window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                        }
                    }
                }

            }
        });
    }

    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        Log.e("testcolor", red + "" + green + "" + blue);
        return Color.rgb(red, green, blue);
    }
}
