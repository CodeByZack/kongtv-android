package com.zack.kongtv.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.util.MyImageLoader;

import java.util.List;

public class MovieListAdapter extends BaseQuickAdapter<MovieDetailBean,BaseViewHolder> {
    private Context mActivity;
    public MovieListAdapter(Context mActivity,int layoutResId, @Nullable List<MovieDetailBean> data) {
        super(layoutResId, data);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieDetailBean obj) {
        MyImageLoader.showImage(mActivity,obj.getMovieImg(), (ImageView) holder.getView(R.id.movie_img));
        holder.setText(R.id.tv_name,obj.getMovieName());
        if(TextUtils.isEmpty(obj.getMovieScore())){
            holder.getView(R.id.tv_score).setVisibility(View.GONE);
        }else{
            holder.setText(R.id.tv_score,obj.getMovieScore());
        }
        holder.setText(R.id.tv_shortdesc,obj.getMovieShortDesc());
        if(TextUtils.isEmpty(obj.getMovieActors())){
            holder.setText(R.id.tv_actors,obj.getMovieShortDesc());
        }else{
            holder.setText(R.id.tv_actors,obj.getMovieActors());
        }
    }
}
