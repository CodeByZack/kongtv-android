package com.zack.kongtv.activities.MovieDetail;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antiless.support.widget.TabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.EmptySignature;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;
import com.tencent.smtt.sdk.TbsVideo;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.CollectMovieDao;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.PlayMovie.PlayMovieActivity;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.util.MyImageLoader;
import com.zackdk.base.BaseMvpActivity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TabLayout sourceTab;

    private int nowColor;
    private Cms_movie targetMovie;
    private List<JujiBean> data = new LinkedList<>();
    List<List<JujiBean>> allSource = new LinkedList<>();
    private Adapter adapter;
    private ImageView tvCollect;
    private AdView mAdView;
    private CardView adContainerView;
//    private Adview mAdView;

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
        mPlay_list2.setLayoutManager(new GridLayoutManager(this,4));

        sourceTab = findViewById(R.id.source_tab);

        adContainerView = findViewById(R.id.ad_container);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(Const.BANNER_MOVIE_DETAIL);
//		mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adContainerView.addView(mAdView);
        loadBanner();
//        mPlay_list2.addItemDecoration(new GridSpacingItemDecoration(4,30,true));
    }


    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        mAdView.setAdSize(adSize);
        // Step 5 - Start loading the ad in the background.
        mAdView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
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
        sourceTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tab.getPosition()
                if(allSource.size()==0){return;}
                updateJuji(allSource.get((Integer) tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

        File file = getCacheFile(targetMovie.getVodPic());
        if(file == null){
//            setAllColor(nowColor);
        }else{
            Glide.with(this).load(file).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mLine_detail_poster.setImageBitmap(resource);
                    setColor(resource);
                }
            });
        }


        String[] tmp;
        tmp = playUrl.split("\\$\\$\\$");
        for (int i = 0; i < tmp.length; i++) {
            String now = tmp[i];
            if(now.contains("m3u8")){
                sourceTab.addTab(sourceTab.newTab().setText("源"+i));
                allSource.add(handleJuji(now));

            }
        }
        updateJuji(allSource.get(0));
        CountEventHelper.countMovieDetail(this,targetMovie.getVodName());
    }

    private List<JujiBean> handleJuji(String now) {
        String[] tmp = now.split("#");
        List<JujiBean> nowJuji = new LinkedList<>();
        for (int i = 0; i < tmp.length ; i++) {
            String t = tmp[i];
            if(!t.contains("m3u8")){
                continue;
            }
            JujiBean jujiBean = new JujiBean();
            String[] tt = t.split("\\$");
            jujiBean.setUrl(tt[1]);
            jujiBean.setText(tt[0]);
            nowJuji.add(jujiBean);
        }
        return nowJuji;
    }

    public File getCacheFile(String id) {
        OriginalKey originalKey = new OriginalKey(id, EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(originalKey);
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(new File(getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                if(vibrant!=null){
                    nowColor = colorBurn(vibrant.getRgb());
                    setAllColor(nowColor);
                }
            }
        });
    }

    private void setAllColor(int nowColor) {
        if (mRoot != null) {
//            ValueAnimator colorAnim2 = ValueAnimator.ofArgb(Color.rgb(110, 110, 100), nowColor);
//            colorAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    mRoot.setBackgroundColor((Integer) animation.getAnimatedValue());
//                    mApp_bar.setBackgroundColor((Integer) animation.getAnimatedValue());
//                }
//            });
//            colorAnim2.setDuration(300);
//            colorAnim2.setRepeatMode(ValueAnimator.RESTART);
//            colorAnim2.start();
            mRoot.setBackgroundColor(nowColor);
            mApp_bar.setBackgroundColor(nowColor);
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.setStatusBarColor(nowColor);
                window.setNavigationBarColor(nowColor);
            }
        }
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
