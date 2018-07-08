package com.zack.kongtv.Data;

import com.zack.kongtv.Const;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zackdk.Utils.LogUtil;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataResp {
    public static final String baseUrl = "https://m.babayu.com";
    public static final String MovieUrl = baseUrl+"/dy/index_1_______1.html";
    public static final String EpisodeUrl = baseUrl+"/dsj/index_1_______1.html";
    public static final String AnimeUrl = baseUrl+"/Animation/index_1_______1.html";
    public static final String VarietyUrl = baseUrl+"/Arts/index_1_______1.html";
    public static Observable getHomeData(){
        Observable<HomeDataBean> observable = Observable.create(new ObservableOnSubscribe<HomeDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<HomeDataBean> emitter) throws Exception {
                HomeDataBean data = getHomeData("https://m.babayu.com/");
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getTypeData(final String url, final int page){
        Observable<CategoryDataBean> observable = Observable.create(new ObservableOnSubscribe<CategoryDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<CategoryDataBean> emitter) throws Exception {
                CategoryDataBean data = getCategoryData(url,page);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }

    public static Observable getMovieDetail(final String url){
        Observable<MovieDetailBean> observable = Observable.create(new ObservableOnSubscribe<MovieDetailBean>() {
            @Override
            public void subscribe(ObservableEmitter<MovieDetailBean> emitter) throws Exception {
                MovieDetailBean data = getRealMovieDetail(url);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getPlayUrl(final String url){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String data = getRealPlayUrl(url);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }

    public static HomeDataBean getHomeData(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            for (Element e: document.getElementsByClass("swiper-slide")) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(baseUrl+e.getElementsByTag("a").attr("href"));
                itemBean.setImg(e.getElementsByTag("img").attr("src"));
                itemBean.setDesc(e.getElementsByTag("em").text());
                bannerItemBeans.add(itemBean);
            }
            Elements elements = document.select(".all_tab>.list_tab_img");
            for (int i = 0; i<elements.size();i++) {
                HomeItemBean homeItemBean = new HomeItemBean();
                homeItemBean.setMovieDetailBeans(new ArrayList<MovieDetailBean>());
                if(i == 0){
                    homeItemBean.setType(Const.Film);
                }else if(i == 1){
                    homeItemBean.setType(Const.Episode);
                }else if( i == 2){
                    homeItemBean.setType(Const.Anime);
                }else if(i == 3){
                    homeItemBean.setType(Const.Variety);
                }else{
                    break;
                }
                for (Element e:elements.get(i).getElementsByTag("li")) {
                    MovieDetailBean movieDetailBean = new MovieDetailBean();
                    movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                    movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                    movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                    movieDetailBean.setTargetUrl(baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                    movieDetailBean.setMovieScore(e.getElementsByClass("score").text());
                    movieDetailBean.setMovieActors(e.getElementsByTag("p").text());
                    homeItemBean.getMovieDetailBeans().add(movieDetailBean);
                }
                homeItemBeans.add(homeItemBean);
            }
            HomeDataBean dataBean = new HomeDataBean();
            dataBean.setHomeItemBeans(homeItemBeans);
            dataBean.setBannerItemBeans(bannerItemBeans);
            return dataBean;
        } catch (IOException e1) {
            return null;
        }

    }

    public static CategoryDataBean getCategoryData(String url,int page){
        List<TagItemBean> tag1 = new LinkedList<>();
        List<TagItemBean> tag2 = new LinkedList<>();
        List<TagItemBean> tag3 = new LinkedList<>();
        List<MovieDetailBean> movieDetailBeans = new LinkedList<>();
        //处理页码
        String replace = "index_"+page;
        url = url.replaceFirst("index_.",replace);
        Document document;
        try {
            document = Jsoup.connect(url).get();

            //获取类型分类
            Element element = document.getElementById("mcid_list");
            for(Element e : element.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieScore(e.getElementsByClass("score").text());
                movieDetailBean.setMovieActors(e.getElementsByTag("p").text());
                movieDetailBeans.add(movieDetailBean);
            }
            CategoryDataBean categoryDataBean = new CategoryDataBean();
            categoryDataBean.setMovieDetailBeans(movieDetailBeans);
            categoryDataBean.setTag1(tag1);
            categoryDataBean.setTag2(tag2);
            categoryDataBean.setTag3(tag3);
            return categoryDataBean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MovieDetailBean getRealMovieDetail(String url) {
        MovieDetailBean movieDetailBean = new MovieDetailBean();
        Document document;

        try {
            document = Jsoup.connect(url).get();
            Element element = document.getElementsByClass("vod-n-l").get(0);
            //电影信息
            movieDetailBean.setMovieName(element.getElementsByTag("h1").text());
            movieDetailBean.setMovieStatus(element.getElementsByTag("p").get(0).text());
            movieDetailBean.setMovieActors(element.getElementsByTag("p").get(1).text());
            movieDetailBean.setMovieType(element.getElementsByTag("p").get(2).text());
            movieDetailBean.setMovieDirector(element.getElementsByTag("p").get(3).text());
            movieDetailBean.setMovieYear(element.getElementsByTag("p").get(4).text());
            movieDetailBean.setMovieLanguage(element.getElementsByTag("p").get(5).text());
            movieDetailBean.setMovieImg(document.select(".vod-n-img>img").attr("data-original"));
            //剧集信息
            movieDetailBean.setList(new LinkedList<JujiBean>());
            Elements elements = document.getElementsByClass("plau-ul-list").get(0).getElementsByTag("li");
            for (Element e :elements) {
                JujiBean bean = new JujiBean();
                bean.setText(e.getElementsByTag("a").text());
                bean.setUrl(baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            return null;
        }

    }

    public static String getRealPlayUrl(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }


}
