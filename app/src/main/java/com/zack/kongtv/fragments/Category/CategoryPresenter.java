package com.zack.kongtv.fragments.Category;

import android.text.TextUtils;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.ErrConsumer;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.MovieItemBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zackdk.Utils.LogUtil;
import com.zackdk.mvp.p.BasePresenter;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryPresenter<V extends ICategoryView> extends BasePresenter<V> {
    private int page = 1;
    private int type;
    private String year;
    private String area;
    private String classname;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void requestData(){
        getView().showLoading();
        Disposable d = requestDataByType();
        addDispoasble(d);
    }

    private Disposable requestDataByType() {
        Observable o = null;
        switch (type){
            case Const.Film:
                o = DataResp.getFilmData(year,area,classname,page);
                break;
            case Const.Episode:
                o = DataResp.getEpisodeData(year,area,classname,page);
                break;
            case Const.Anime:
                o = DataResp.getAnimeData(year,area,classname,page);
                break;
            case Const.Variety:
                o = DataResp.getVarietyData(year,area,classname,page);
                break;
            default:
                o = DataResp.getFilmData(year,area,classname,page);
                break;
        }
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cms_movie>>() {
                    @Override
                    public void accept(List<Cms_movie> dataBean) throws Exception {
                        if (dataBean.size() != 0) {
                            getView().updateView(transferData(dataBean));
                            page++;
                            getView().loadMoreComplete();
                        } else {
                            getView().loadMoreEnd();
                        }
                        getView().hideLoading();
                    }
                },new ErrConsumer());
    }

    private CategoryDataBean transferData(List<Cms_movie> cms_movies){
        CategoryDataBean categoryDataBean = new CategoryDataBean();
        categoryDataBean.setMovieItemBeans(cms_movies);
        categoryDataBean.setTag1(new LinkedList<TagItemBean>());
        categoryDataBean.setTag2(new LinkedList<TagItemBean>());
        categoryDataBean.setTag3(new LinkedList<TagItemBean>());

        String [] tmpAreaArray;
        String [] tmpYearArray;
        String [] tmpClassArray;
        switch (type){
            case Const.Film:
                tmpAreaArray = Const.tag_area_dy;
                tmpYearArray = Const.tag_year_dy;
                tmpClassArray = Const.tag_class_dy;
                break;
            case Const.Episode:
                tmpAreaArray = Const.tag_area_dsj;
                tmpYearArray = Const.tag_year_dsj;
                tmpClassArray = Const.tag_class_dsj;
                break;
            case Const.Anime:
                tmpAreaArray = Const.tag_area_dm;
                tmpYearArray = Const.tag_year_dm;
                tmpClassArray = Const.tag_class_dm;
                break;
            case Const.Variety:
                tmpAreaArray = Const.tag_area_zy;
                tmpYearArray = Const.tag_year_zy;
                tmpClassArray = Const.tag_class_zy;
                break;
            default:
                tmpAreaArray = Const.tag_area_dy;
                tmpYearArray = Const.tag_year_dy;
                tmpClassArray = Const.tag_class_dy;
                break;
        }

        TagItemBean itemBean1 = new TagItemBean();
        TagItemBean itemBean2 = new TagItemBean();
        TagItemBean itemBean3 = new TagItemBean();
        itemBean1.setTag("全部");
        itemBean2.setTag("全部");
        itemBean3.setTag("全部");
        categoryDataBean.getTag1().add(itemBean1);
        categoryDataBean.getTag2().add(itemBean2);
        categoryDataBean.getTag3().add(itemBean3);

        if(classname == null){
            itemBean1.setSelect(true);
        }
        for (int i = 0; i < tmpClassArray.length; i++) {
            TagItemBean tagItemBean = new TagItemBean();
            tagItemBean.setTag(tmpClassArray[i]);
            if(tmpClassArray[i].equals(classname)){
                tagItemBean.setSelect(true);
            }
            categoryDataBean.getTag1().add(tagItemBean);
        }

        if(area == null){
            itemBean2.setSelect(true);
        }
        for (int i = 0; i < tmpAreaArray.length; i++) {
            TagItemBean tagItemBean = new TagItemBean();
            tagItemBean.setTag(tmpAreaArray[i]);
            if(tmpAreaArray[i].equals(area)){
                tagItemBean.setSelect(true);
            }
            categoryDataBean.getTag2().add(tagItemBean);

        }

        if(year == null){
            itemBean3.setSelect(true);
        }
        for (int i = 0; i < tmpYearArray.length; i++) {
            TagItemBean tagItemBean = new TagItemBean();
            tagItemBean.setTag(tmpYearArray[i]);
            if(tmpYearArray[i].equals(year)){
                tagItemBean.setSelect(true);
            }
            categoryDataBean.getTag3().add(tagItemBean);
        }



        return categoryDataBean;
    }


    public void loadMore() {
        requestData();
    }

    public void setTargetType(int type) {
        //改变了分类，重置页数
        this.type = type;
    }

    public void refresh() {
        page = 1;
        requestData();
    }
}
