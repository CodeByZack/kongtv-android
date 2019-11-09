package com.zack.kongtv.activities.MovieDetail;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.tencent.smtt.sdk.TbsVideo;
import com.zack.kongtv.App;
import com.zack.kongtv.Data.room.CollectMovieDao;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.PlayMovie.PlayMovieActivity;
import com.zack.kongtv.bean.AppConfig;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.util.MyImageLoader;
import com.zackdk.base.BaseMvpActivity;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MovieDetailActivity extends BaseMvpActivity<MovieDetailPresenter> implements IMovieDetailView{

    private CoordinatorLayout mRoot;
    private AppBarLayout mApp_bar;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ImageView mLine_detail_poster;
    private TextView mMv_title;
    private TextView mHead_desc;
    private ExpandableTextView mLine_desc;
    private RecyclerView mPlay_list2;


    private int nowColor;
    private Cms_movie targetMovie;
    private List<JujiBean> data = new LinkedList<>();
    private Adapter adapter;
    private ImageView tvCollect;
    private ImageView adImage;

    private void updateJuji(List<JujiBean> obj) {
        this.data.clear();
        this.data.addAll(obj);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRoot =  findViewById(R.id.root);
        mApp_bar =  findViewById(R.id.app_bar);
        mToolbar =  findViewById(R.id.toolbar);
        mToolbarTitle =  findViewById(R.id.toolbarTitle);
        mLine_detail_poster =  findViewById(R.id.line_detail_poster);
        mMv_title =  findViewById(R.id.mv_title);
        mHead_desc =  findViewById(R.id.head_desc);
        mLine_desc =  findViewById(R.id.line_desc);
        mPlay_list2 =  findViewById(R.id.play_list2);
        tvCollect = findViewById(R.id.collect);
        adImage = findViewById(R.id.ad_image);

        mPlay_list2.setLayoutManager(new GridLayoutManager(this,4));
//        mPlay_list2.addItemDecoration(new GridSpacingItemDecoration(4,30,true));
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

        presenter.checkCollect(targetMovie.getVodId());
    }

    private void initLogic() {
        adapter =  new Adapter(R.layout.m3u8_item,data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startPlay(position);
            }
        });
        mPlay_list2.setAdapter(adapter);
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectMovieDao md = DataBase.getInstance().collectMovieDao();
                md.insert(AndroidUtil.transferCollect(targetMovie));
                collect(true);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final AppConfig config = App.getAppConfig();
        Glide.with(this).load(config.getAdPlayerImg()).into(adImage);
        adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(config.getAdPlayerUrl());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }

    private void startPlay(int position) {
//        HistoryMovieDao md = DataBase.getInstance().historyMovieDao();
//        md.insert(AndroidUtil.transferHistory(targetMovie,data.get(position).getText()));
//        Bundle bundle = new Bundle();
//        bundle.putInt("screenMode", 102);
//        TbsVideo.openVideo(this,data.get(position).getUrl(),bundle);
        Intent intent = new Intent(mActivity, PlayMovieActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("color",nowColor);
        intent.putExtra("juji", (Serializable) data);
        intent.putExtra("movie", targetMovie);
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

        mMv_title.setText(targetMovie.getVodName());
        mLine_desc.setContent(targetMovie.getVodBlurb());
        final String playUrl = targetMovie.getVodPlayUrl();


        Glide.with(this).load(targetMovie.getVodPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mLine_detail_poster.setImageBitmap(resource);
                setColor(resource);
            }
        });
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
        updateJuji(jujiBeans);
        CountEventHelper.countMovieDetail(this,targetMovie.getVodName());
    }

    @Override
    public void collect(boolean c) {
        if(c){
            tvCollect.setClickable(false);
            tvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }else{
            tvCollect.setClickable(true);
            tvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
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
                        nowColor = colorBurn(vibrant.getRgb());
                        ValueAnimator colorAnim2 = ValueAnimator.ofArgb(Color.rgb(110, 110, 100), nowColor);
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
