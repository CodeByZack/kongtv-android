package com.zack.kongtv.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
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

public class MovieListAdapter extends BaseQuickAdapter<Cms_movie,BaseViewHolder> {
    private Context mActivity;
    public MovieListAdapter(Context mActivity,int layoutResId, @Nullable List<Cms_movie> data) {
        super(layoutResId, data);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder holder, Cms_movie obj) {
        MyImageLoader.showImage(mActivity,obj.getVodPic(), (ImageView) holder.getView(R.id.movie_img));
        holder.setText(R.id.tv_name,obj.getVodName());
        if(TextUtils.isEmpty(obj.getVodScore())){
            holder.getView(R.id.tv_score).setVisibility(View.GONE);
        }else{
            holder.setText(R.id.tv_score,obj.getVodScore());
        }
        holder.setText(R.id.tv_shortdesc,obj.getVodRemarks());
        if(TextUtils.isEmpty(obj.getVodActor())){
            holder.setText(R.id.tv_actors,obj.getVodRemarks());
        }else{
            holder.setText(R.id.tv_actors,obj.getVodActor());
        }
    }
}
