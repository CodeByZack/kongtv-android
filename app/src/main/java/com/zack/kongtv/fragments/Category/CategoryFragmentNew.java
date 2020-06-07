package com.zack.kongtv.fragments.Category;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.wang.avi.AVLoadingIndicatorView;
import com.zack.kongtv.Const;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieItemBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.util.MyImageLoader;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.base.BaseMvpFragment;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by zack on 2018/5/31.
 */

public class CategoryFragmentNew extends BaseMvpFragment<CategoryPresenter> implements ICategoryView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AVLoadingIndicatorView loadingView;
    private CategoryAdapter adapter;
    private List<Cms_movie> data = new LinkedList<>();
    private AdLoader adLoader;

    @Override
    public int setView() {
        return R.layout.fragment_category;
    }

    public static CategoryFragmentNew instance(int type){
        CategoryFragmentNew categoryFragment = new CategoryFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }
    @Override
    public void initBasic(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int type;
        if(bundle!=null){
            type = bundle.getInt("type");
        }else {
            type = Const.Film;
        }
        initView();
        initLogic();
        initAd();
        presenter.setTargetType(type);
        presenter.requestData();
    }

    private void initAd() {
        adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        Log.d("TAG", "onUnifiedNativeAdLoaded: ");
                        Cms_movie ad = new Cms_movie();
                        ad.setAd(unifiedNativeAd);
                        ad.setType(Cms_movie.AD);
                        adapter.addData(ad);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .setMediaAspectRatio(NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_PORTRAIT)
//                        .setMediaAspectRatio(NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_LANDSCAPE)
                        .build())
                .build();
//        adLoader.loadAds(new AdRequest.Builder().build(),5);
    }


    private void initLogic() {

        adapter = new CategoryAdapter(data);
        adapter.openLoadAnimation();

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(0,3,getResources().getDimensionPixelSize(R.dimen.little_margin),true));
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
                Cms_movie now = data.get(position);
                if(now.getType() == Cms_movie.MOVIE){
                    startActivity(new Intent(mActivity,MovieDetailActivity.class).putExtra("url",data.get(position)));
                }else{
                    Log.d("ad", "处理广告点击");
                }
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleview);
        swipeRefreshLayout = findViewById(R.id.sw_refresh);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadingView = findViewById(R.id.loading);
    }


    @Override
    protected CategoryPresenter setPresenter() {
        return new CategoryPresenter();
    }

    @Override
    public void updateView(CategoryDataBean data) {
        adLoader.loadAd(new AdRequest.Builder().build());
        adapter.addData(data.getMovieItemBeans());
        loadingView.smoothToHide();
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        adapter.loadMoreEnd();
    }

    private class CategoryAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {
        public CategoryAdapter(List<Cms_movie> data) {
            super(data);
            //Step.1
            setMultiTypeDelegate(new MultiTypeDelegate<Cms_movie>() {
                @Override
                protected int getItemType(Cms_movie entity) {
                    //根据你的实体类来判断布局类型
                    return entity.getType();
                }
            });
            //Step.2
            getMultiTypeDelegate()
                    .registerItemType(Cms_movie.AD, R.layout.ad_layout)
                    .registerItemType(Cms_movie.MOVIE, R.layout.movie_item);
        }

        @Override
        protected void convert(BaseViewHolder holder, Cms_movie obj) {
            Log.d(TAG, "convert: "+obj.getType());
            switch (holder.getItemViewType()) {
                case Cms_movie.AD:
                    // do something
                    // do something
//                    TemplateView templateView = (TemplateView) holder.itemView;
//                    templateView.setNativeAd(obj.getAd());
                    populateUnifiedNativeAdView(obj.getAd(), (UnifiedNativeAdView) holder.itemView);
                    break;
                case Cms_movie.MOVIE:
                    MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.post_img));
                    holder.setText(R.id.post_title,obj.getVodName());
                    break;
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

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//        adView.setBodyView(adView.findViewById(R.id.ad_body));
//        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
//        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        adView.getMediaView().setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
//        if (nativeAd.getBody() == null) {
//            adView.getBodyView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getBodyView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        }

//        if (nativeAd.getCallToAction() == null) {
//            adView.getCallToActionView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getCallToActionView().setVisibility(View.VISIBLE);
//            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//        }

//        if (nativeAd.getIcon() == null) {
//            adView.getIconView().setVisibility(View.GONE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(
//                    nativeAd.getIcon().getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }

//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }

//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }

//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView())
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }

//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
//        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
//        if (vc.hasVideoContent()) {
//            videoStatus.setText(String.format(Locale.getDefault(),
//                    "Video status: Ad contains a %.2f:1 video asset.",
//                    vc.getAspectRatio()));
//
//            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
//            // VideoController will call methods on this object when events occur in the video
//            // lifecycle.
//            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
//                @Override
//                public void onVideoEnd() {
//                    // Publishers should allow native ads to complete video playback before
//                    // refreshing or replacing them with another ad in the same UI location.
//                    refresh.setEnabled(true);
//                    videoStatus.setText("Video status: Video playback has ended.");
//                    super.onVideoEnd();
//                }
//            });
//        } else {
//            videoStatus.setText("Video status: Ad does not contain a video asset.");
//            refresh.setEnabled(true);
//        }
    }
}
