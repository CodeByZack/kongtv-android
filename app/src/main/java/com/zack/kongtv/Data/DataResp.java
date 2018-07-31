package com.zack.kongtv.Data;

import android.text.TextUtils;
import android.util.Log;

import com.zack.kongtv.AppConfig;
import com.zack.kongtv.Const;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.bean.UpdateInfo;
import com.zackdk.Utils.LogUtil;

import org.json.JSONObject;
import org.jsoup.Connection;
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


    public static Observable getHomeData(){
        Observable<HomeDataBean> observable = Observable.create(new ObservableOnSubscribe<HomeDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<HomeDataBean> emitter) throws Exception {
                HomeDataBean data = null;
                switch (AppConfig.getDefaultXIANLU()){
                    case AppConfig.BABAYU:
                        data = getHomeData(AppConfig.baseUrl);
                        break;
                    case AppConfig.YIMIMAO:
                        data = getHomeData_2(AppConfig.baseUrl);
                        break;
                    case AppConfig._4KWU:
                        data = getHomeData_3(AppConfig.baseUrl);
                        break;
                    case AppConfig.KANKANWU:
                        data = getHomeData_4(AppConfig.baseUrl);
                        break;
                    case AppConfig.PIPIGUI:
                        data = getHomeData_5(AppConfig.baseUrl);
                        break;
                }
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
//                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getTypeData(final String url, final int page){
        Observable<CategoryDataBean> observable = Observable.create(new ObservableOnSubscribe<CategoryDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<CategoryDataBean> emitter) throws Exception {
                CategoryDataBean data = null;
                switch (AppConfig.getDefaultXIANLU()){
                    case AppConfig.BABAYU:
                        data = getCategoryData(url,page);
                        break;
                    case AppConfig.YIMIMAO:
                        data = getCategoryData_2(url,page);
                        break;
                    case AppConfig._4KWU:
                        data = getCategoryData_3(url,page);
                        break;
                    case AppConfig.KANKANWU:
                        data = getCategoryData_4(url,page);
                        break;
                    case AppConfig.PIPIGUI:
                        data = getCategoryData_5(url,page);
                        break;
                }
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getMovieDetail(final String url){
        Observable<MovieDetailBean> observable = Observable.create(new ObservableOnSubscribe<MovieDetailBean>() {
            @Override
            public void subscribe(ObservableEmitter<MovieDetailBean> emitter) throws Exception {
                MovieDetailBean data = null;
                switch (AppConfig.getDefaultXIANLU()){
                    case AppConfig.BABAYU:
                        data = getRealMovieDetail(url);
                        break;
                    case AppConfig.YIMIMAO:
                        data = getRealMovieDetail_2(url);
                        break;
                    case AppConfig._4KWU:
                        data = getRealMovieDetail_3(url);
                        break;
                    case AppConfig.KANKANWU:
                        data = getRealMovieDetail_4(url);
                        break;
                    case AppConfig.PIPIGUI:
                        data = getRealMovieDetail_5(url);
                        break;
                }
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getPlayUrl(final String url){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String data = null;
                switch (AppConfig.getDefaultXIANLU()){
                    case AppConfig.BABAYU:
                        data = getRealPlayUrl(url);
                        break;
                    case AppConfig.YIMIMAO:
                        data = getRealPlayUrl_2(url);
                        break;
                    case AppConfig._4KWU:
                        data = getRealPlayUrl_3(url);
                        break;
                    case AppConfig.KANKANWU:
                        data = getRealPlayUrl_4(url);
                        break;
                    case AppConfig.PIPIGUI:
                        data = getRealPlayUrl_5(url);
                        break;
                }
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable searchText(final  String text,final int page){
        Observable<SearchResultBean> observable = Observable.create(new ObservableOnSubscribe<SearchResultBean>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultBean> emitter) throws Exception {
                SearchResultBean data = null;
                switch (AppConfig.getDefaultXIANLU()){
                    case AppConfig.BABAYU:
                        data = search(text,page);
                        break;
                    case AppConfig.YIMIMAO:
                        data = search_2(text,page);
                        break;
                    case AppConfig._4KWU:
                        data = search_3(text,page);
                        break;
                    case AppConfig.KANKANWU:
                        data = search_4(text,page);
                        break;
                    case AppConfig.PIPIGUI:
                        data = search_5(text,page);
                        break;
                }
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
//                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }

/*    //巴巴鱼解析规则
    private static HomeDataBean getHomeData(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            //Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            Elements banners = document.getElementsByClass("swiper-slide");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
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
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static CategoryDataBean getCategoryData(String url,int page){
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
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static MovieDetailBean getRealMovieDetail(String url) {
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
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            return null;
        }

    }
    private static String getRealPlayUrl(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }*/

    //云播TV解析规则
    private static HomeDataBean getHomeData(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            //Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            Elements banners = document.select(".carousel-inner>.item");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
                itemBean.setImg(url+e.getElementsByTag("img").attr("src"));
                itemBean.setDesc(e.getElementsByTag("h3").text());
                bannerItemBeans.add(itemBean);
            }
            Elements elements = document.select(".panel");
            for (int i = 2; i<elements.size();i++) {

                HomeItemBean homeItemBean = new HomeItemBean();
                homeItemBean.setMovieDetailBeans(new ArrayList<MovieDetailBean>());
                if(i == 2){
                    homeItemBean.setType(Const.Episode);
                }else if(i == 3){
                    homeItemBean.setType(Const.Film);
                }else if( i == 4){
                    homeItemBean.setType(Const.Variety);
                }else if(i == 5){
                    homeItemBean.setType(Const.Anime);
                }else{
                    break;
                }
                for (Element e:elements.get(i).getElementsByClass("card")) {
                    MovieDetailBean movieDetailBean = new MovieDetailBean();
                    movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("data-original"));
                    movieDetailBean.setMovieShortDesc(e.getElementsByClass("label").text());
                    movieDetailBean.setMovieName(e.getElementsByTag("strong").text());
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
//                    movieDetailBean.setMovieScore(e.getElementsByClass("score").text());
                    movieDetailBean.setMovieActors(e.getElementsByClass("card-content").text());
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
    private static CategoryDataBean getCategoryData(String url,int page){
        List<TagItemBean> tag1 = new LinkedList<>();
        List<TagItemBean> tag2 = new LinkedList<>();
        List<TagItemBean> tag3 = new LinkedList<>();
        List<MovieDetailBean> movieDetailBeans = new LinkedList<>();
        //处理页码
        url = url.replaceFirst("PAGE", String.valueOf(page));
        Document document;
        try {
            document = Jsoup.connect(url).get();

            //获取类型分类
            Elements elements = document.select(".query-box>dl");
            for(Element e : elements.get(0).select("dd>span")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("active"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.select("a").attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : elements.get(3).select("dd>span")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.select("a").attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : elements.get(2).select("dd>span")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.select("a").attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements2 = document.select(".card");
            for (Element e : elements2) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("data-original"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("label").text());
                movieDetailBean.setMovieName(e.getElementsByTag("strong").text());
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                //movieDetailBean.setMovieScore(e.getElementsByClass("score").text());
                movieDetailBean.setMovieActors(e.getElementsByClass("card-content").text());
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
    private static MovieDetailBean getRealMovieDetail(String url) {
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
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            return null;
        }

    }
    private static String getRealPlayUrl(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }

    //一米猫解析规则
    private static HomeDataBean getHomeData_2(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
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
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static CategoryDataBean getCategoryData_2(String url,int page){
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
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static MovieDetailBean getRealMovieDetail_2(String url) {
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
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    private static String getRealPlayUrl_2(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            LogUtil.d(trueUrl);
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search_2(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }

    //4K屋解析规则
    private static HomeDataBean getHomeData_3(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
                itemBean.setImg(e.getElementsByTag("img").attr("data-src"));
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
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static CategoryDataBean getCategoryData_3(String url,int page){
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
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static MovieDetailBean getRealMovieDetail_3(String url) {
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
            movieDetailBean.setMovieImg(document.select(".vod-n-img>img").attr("src"));
            //剧集信息
            movieDetailBean.setList(new LinkedList<JujiBean>());
            Elements elements = document.getElementsByClass("plau-ul-list").get(0).getElementsByTag("li");
            for (Element e :elements) {
                JujiBean bean = new JujiBean();
                bean.setText(e.getElementsByTag("a").text());
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    private static String getRealPlayUrl_3(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            LogUtil.d(trueUrl);
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search_3(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }


    //看看屋解析规则
    private static HomeDataBean getHomeData_4(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).timeout(30000).validateTLSCertificates(false).get();
            Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
                itemBean.setImg(AppConfig.baseUrl+e.getElementsByTag("img").attr("src"));
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
                    movieDetailBean.setMovieImg("https:"+e.getElementsByTag("img").attr("src"));
                    movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                    movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static CategoryDataBean getCategoryData_4(String url,int page){
        List<TagItemBean> tag1 = new LinkedList<>();
        List<TagItemBean> tag2 = new LinkedList<>();
        List<TagItemBean> tag3 = new LinkedList<>();
        List<MovieDetailBean> movieDetailBeans = new LinkedList<>();
        //处理页码
        String replace = "index_"+page;
        url = url.replaceFirst("index_.",replace);
        Document document;
        try {
            document = Jsoup.connect(url).timeout(30000).validateTLSCertificates(false).get();

            //获取类型分类
            Element element = document.getElementById("mcid_list");
            for(Element e : element.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static MovieDetailBean getRealMovieDetail_4(String url) {
        MovieDetailBean movieDetailBean = new MovieDetailBean();
        Document document;

        try {
            document = Jsoup.connect(url).timeout(30000).validateTLSCertificates(false).get();
            Element element = document.getElementsByClass("vod-n-l").get(0);
            //电影信息
            movieDetailBean.setMovieName(element.getElementsByTag("h1").text());
            movieDetailBean.setMovieStatus(element.getElementsByTag("p").get(0).text());
            movieDetailBean.setMovieActors(element.getElementsByTag("p").get(1).text());
            movieDetailBean.setMovieType(element.getElementsByTag("p").get(2).text());
            movieDetailBean.setMovieDirector(element.getElementsByTag("p").get(3).text());
            movieDetailBean.setMovieYear(element.getElementsByTag("p").get(4).text());
            movieDetailBean.setMovieLanguage(element.getElementsByTag("p").get(5).text());
            movieDetailBean.setMovieImg(document.select(".vod-n-img>img").attr("src"));
            //剧集信息
            movieDetailBean.setList(new LinkedList<JujiBean>());
            Elements elements = document.getElementsByClass("plau-ul-list").get(0).getElementsByTag("li");
            for (Element e :elements) {
                JujiBean bean = new JujiBean();
                bean.setText(e.getElementsByTag("a").text());
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    private static String getRealPlayUrl_4(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).timeout(30000).validateTLSCertificates(false).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            LogUtil.d(trueUrl);
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search_4(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).timeout(30000).validateTLSCertificates(false).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }


    //皮皮龟解析规则
    private static HomeDataBean getHomeData_5(String url) {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(url).get();
            Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(url+e.getElementsByTag("a").attr("href"));
                itemBean.setImg(AppConfig.baseUrl+e.getElementsByTag("img").attr("src"));
                itemBean.setDesc(e.getElementsByTag("em").text());
                //bannerItemBeans.add(itemBean);
            }
            Elements elements = document.select(".all_tab>.list_tab_img");
            for (int i = 0; i<elements.size();i++) {
                if(i == 2){
                    continue;
                }
                HomeItemBean homeItemBean = new HomeItemBean();
                homeItemBean.setMovieDetailBeans(new ArrayList<MovieDetailBean>());
                if(i == 0){
                    homeItemBean.setType(Const.Film);
                }else if(i == 1){
                    homeItemBean.setType(Const.Episode);
                }else if( i == 3){
                    homeItemBean.setType(Const.Anime);
                }else if(i == 4){
                    homeItemBean.setType(Const.Variety);
                }else{
                    break;
                }
                for (Element e:elements.get(i).getElementsByTag("li")) {
                    MovieDetailBean movieDetailBean = new MovieDetailBean();
                    movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                    movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                    movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                    movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static CategoryDataBean getCategoryData_5(String url,int page){
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
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(AppConfig.baseUrl+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
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
    private static MovieDetailBean getRealMovieDetail_5(String url) {
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
                bean.setUrl(AppConfig.baseUrl+e.getElementsByTag("a").attr("href"));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(".vod_content").text());

            return movieDetailBean;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    private static String getRealPlayUrl_5(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            String trueUrl = document.getElementsByTag("iframe").attr("src");
            LogUtil.d(trueUrl);
            return trueUrl;
        } catch (IOException e1) {
            return null;
        }
    }
    private static SearchResultBean search_5(String text,int page){
        //处理搜索网址
        String search = AppConfig.SearchUrl.replace("TEMP",text);
        search = search.replace("PAGE",String.valueOf(page));
        SearchResultBean movieDetailBeans = new SearchResultBean();
        movieDetailBeans.setList(new LinkedList<MovieDetailBean>());
        Document document;
        try {
            document = Jsoup.connect(search).get();
            Elements ee = document.getElementsByClass("ui-vpages").get(0).getElementsContainingOwnText("下一页");
            if(ee.size()!=0 && ee.get(0).hasAttr("href")){
                movieDetailBeans.setCanLoadMore(true);
            }else{
                movieDetailBeans.setCanLoadMore(false);
            }
            Elements elements = document.getElementsByClass("new_tab_img").get(0).getElementsByTag("li");
            for (Element e :elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(AppConfig.baseUrl+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }


    public static Observable getAppUpdateInfo(){
        Observable<UpdateInfo> observable = Observable.create(new ObservableOnSubscribe<UpdateInfo>() {
            @Override
            public void subscribe(ObservableEmitter<UpdateInfo> emitter) throws Exception {
                Connection.Response body = Jsoup.connect(Const.CHECK_UPDATE_URL)
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                        .ignoreContentType(true).execute();
                String res = body.body();
                JSONObject json = new JSONObject(res);
                Log.d("", "subscribe: "+json);
                UpdateInfo updateInfo = new UpdateInfo();
                String name = json.getString("app_name");
                String updateinfo = json.getString("app_updateInfo");
                String download_url = json.getString("download_url");
                int version = json.getInt("app_version");

                if(!TextUtils.isEmpty(name)){
                    updateInfo.setApp_name(name);
                }
                if(!TextUtils.isEmpty(updateinfo)){
                    updateInfo.setApp_updateInfo(updateinfo);
                }
                if(!TextUtils.isEmpty(download_url)){
                    updateInfo.setDownload_url(download_url);
                }
                updateInfo.setApp_version(version);

                emitter.onNext(updateInfo);
                emitter.onComplete();
            }
        });
        return observable;
    }
}
