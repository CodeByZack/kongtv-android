package com.zack.kongtv.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.util.MyImageLoader;

import java.util.List;

import androidx.annotation.Nullable;

public class MovieListAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {
    private Context mActivity;
    public MovieListAdapter(Context mActivity,int layoutResId, @Nullable List<Cms_movie> data) {
        super(layoutResId, data);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder holder, Cms_movie obj) {
        MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.post_img));
        holder.setText(R.id.post_title,obj.getVodName());
    }
}
