package com.zack.kongtv.Data.Instance;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.zack.kongtv.App;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.Config;
import com.zack.kongtv.activities.MainActivity;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.util.AndroidUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zackv on 2019/5/10.
 */

public class HtmlResolve{

    public static String baseUrl = null;
    public static String dyUrl = null;
    public static String dsjUrl = null;
    public static String dmUrl = null;
    public static String zyUrl = null;
    public static String searchUrl = null;
    public static String name = null;
    public static List<String> videoReg = null;
    public static Config configNow = null;
    public static List<Config> configs = null;

    public static void initResolveConfig(){
        String json = AndroidUtil.getJson("config.json");
        configs = AndroidUtil.parseJSON2Map(json);
        configNow = configs.get(0);
        baseUrl = configNow.getBaseUrl();
        name = configNow.getName();
        dyUrl = configNow.getBaseUrl() + configNow.getDyUrl();
        dsjUrl = configNow.getBaseUrl() + configNow.getDsjUrl();
        dmUrl = configNow.getBaseUrl() + configNow.getDmUrl();
        zyUrl = configNow.getBaseUrl() + configNow.getZyUrl();
        searchUrl = configNow.getBaseUrl() + configNow.getSearchUrl();
        videoReg = configNow.getVideoReg();
    }

    public static void changeConfig(int index){
        configNow = configs.get(index);
        baseUrl = configNow.getBaseUrl();
        name = configNow.getName();
        dyUrl = configNow.getBaseUrl() + configNow.getDyUrl();
        dsjUrl = configNow.getBaseUrl() + configNow.getDsjUrl();
        dmUrl = configNow.getBaseUrl() + configNow.getDmUrl();
        zyUrl = configNow.getBaseUrl() + configNow.getZyUrl();
        searchUrl = configNow.getBaseUrl() + configNow.getSearchUrl();
        videoReg = configNow.getVideoReg();
        Toast.makeText(App.getContext(),"切换线路，重启中。。。",Toast.LENGTH_SHORT);
        App.finshAllActivity();
        Intent intent = new Intent(App.getContext(), MainActivity.class);
        App.getContext().startActivity(intent);
    }

    public static HomeDataBean getHomeData() {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(configNow.getBaseUrl()).get();
            Elements banners = document.select(configNow.getBannerSelector());
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();

                Config.Selctor imgSelctor = configNow.getBanner().get(0);
                Config.Selctor descSelctor = configNow.getBanner().get(1);
                Config.Selctor urlSelctor = configNow.getBanner().get(2);

                String imgUrl = select(e,imgSelctor);
                imgUrl = hadnleImgurl(imgUrl);
                itemBean.setImg(imgUrl);

                String desc = select(e,descSelctor);
                itemBean.setDesc(desc);

                String url = select(e,urlSelctor);
                if(url.contains("location.href")){
                    url = url.replace("location.href='",baseUrl);
                    url = url.replace("';","");
                }
                itemBean.setTargetUrl(url);

                bannerItemBeans.add(itemBean);
            }
            Elements elements = document.select(configNow.getItemsSelector());
            for (int i = 0; i<elements.size();i++) {
                Element element = elements.get(i);
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
                Elements liNodes = element.select("li");
                for (int j = 0; j < liNodes.size(); j++) {
                    Element e = liNodes.get(j);
                    MovieDetailBean movieDetailBean = new MovieDetailBean();
                    Config.Selctor imgSelctor = configNow.getItems().get(0);
                    Config.Selctor descSelctor = configNow.getItems().get(1);
                    Config.Selctor nameSelctor = configNow.getItems().get(2);
                    Config.Selctor urlSelctor = configNow.getItems().get(3);
                    Config.Selctor scoreSelctor = configNow.getItems().get(4);
                    Config.Selctor actorsSelctor = configNow.getItems().get(5);

                    String imgUrl = select(e,imgSelctor);
                    imgUrl = hadnleImgurl(imgUrl);
                    movieDetailBean.setMovieImg(imgUrl);

                    String desc = select(e,descSelctor);
                    movieDetailBean.setMovieShortDesc(desc);

                    String name = select(e,nameSelctor);
                    movieDetailBean.setMovieName(name);

                    String url = configNow.getBaseUrl()+select(e,urlSelctor);
                    movieDetailBean.setTargetUrl(url);

                    String score = select(e,scoreSelctor);
                    movieDetailBean.setMovieScore(score);

                    String actors = select(e,actorsSelctor);
                    movieDetailBean.setMovieActors(actors);

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

    public static CategoryDataBean getCategoryData(String url, int page) {
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
            if(!TextUtils.isEmpty(configNow.getMcidSelector())){
                Element element = document.select(configNow.getMcidSelector()).get(0);
                for(Element e : element.select(configNow.getCategory().getSelector())){
                    TagItemBean tagItemBean = new TagItemBean();
                    tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                    tagItemBean.setTag(e.text());
                    tagItemBean.setUrl(baseUrl+e.attr(configNow.getCategory().getAttr()));
                    tag1.add(tagItemBean);
                }
            }
            if(!TextUtils.isEmpty(configNow.getSecondSelector())){
                Element element2 = document.select(configNow.getSecondSelector()).get(0);
                for(Element e : element2.select(configNow.getCategory().getSelector())){
                    TagItemBean tagItemBean = new TagItemBean();
                    tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                    tagItemBean.setTag(e.text());
                    tagItemBean.setUrl(baseUrl+e.attr(configNow.getCategory().getAttr()));
                    tag2.add(tagItemBean);
                }
            }
            if(!TextUtils.isEmpty(configNow.getYearSelector())){
                Element element3 = document.select(configNow.getYearSelector()).get(0);
                for(Element e : element3.select(configNow.getCategory().getSelector())){
                    TagItemBean tagItemBean = new TagItemBean();
                    tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                    tagItemBean.setTag(e.text());
                    tagItemBean.setUrl(baseUrl+e.attr(configNow.getCategory().getAttr()));
                    tag3.add(tagItemBean);
                }
            }

            Elements elements = document.select(configNow.getListSelector());
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                Config.Selctor imgSelctor = configNow.getList().get(0);
                Config.Selctor descSelctor = configNow.getList().get(1);
                Config.Selctor nameSelctor = configNow.getList().get(2);
                Config.Selctor urlSelctor = configNow.getList().get(3);
                Config.Selctor scoreSelctor = configNow.getList().get(4);
                Config.Selctor actorsSelctor = configNow.getList().get(5);

                String imgUrl = select(e,imgSelctor);
                imgUrl = hadnleImgurl(imgUrl);
                movieDetailBean.setMovieImg(imgUrl);

                String desc = select(e,descSelctor);
                movieDetailBean.setMovieShortDesc(desc);

                String name = select(e,nameSelctor);
                movieDetailBean.setMovieName(name);

                String turl = configNow.getBaseUrl()+select(e,urlSelctor);
                movieDetailBean.setTargetUrl(turl);

                String score = select(e,scoreSelctor);
                movieDetailBean.setMovieScore(score);

                String actors = select(e,actorsSelctor);
                movieDetailBean.setMovieActors(actors);

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

            Config.Selctor title = configNow.getMovieDetail().get(0);
            Config.Selctor pInfo = configNow.getMovieDetail().get(1);
            Config.Selctor img = configNow.getMovieDetail().get(2);
            Config.Selctor juji = configNow.getMovieDetail().get(3);
            Config.Selctor desc = configNow.getMovieDetail().get(4);

            //电影信息
            String _title = select(document,title);
            movieDetailBean.setMovieName(_title);

            String _pInfo = select(document,pInfo);
            movieDetailBean.setMovieStatus(_pInfo);

            String _img = select(document,img);
            _img = hadnleImgurl(_img);
            movieDetailBean.setMovieImg(_img);

            String _desc = select(document,desc);
            movieDetailBean.setMovieDesc(_desc);

//
//            movieDetailBean.setMovieStatus(document.select(pInfo.getSelector()).get(0).text());
//            movieDetailBean.setMovieActors(document.select(pInfo.getSelector()).get(1).text());
//            movieDetailBean.setMovieType(document.select(pInfo.getSelector()).get(2).text());
//            movieDetailBean.setMovieDirector(document.select(pInfo.getSelector()).get(3).text());
//            movieDetailBean.setMovieYear(document.select(pInfo.getSelector()).get(4).text());
//            movieDetailBean.setMovieLanguage(document.select(pInfo.getSelector()).get(5).text());
//            String imgUrl = document.select(img.getSelector()).attr(img.getAttr());
//            imgUrl = hadnleImgurl(imgUrl);
//            movieDetailBean.setMovieImg(imgUrl);
            //剧集信息
            movieDetailBean.setList(new LinkedList<JujiBean>());
            Elements elements = document.select(juji.getSelector());
            for (Element e :elements) {
                JujiBean bean = new JujiBean();
                bean.setText(e.text());
                bean.setUrl(configNow.getBaseUrl()+e.attr(juji.getAttr()));
                movieDetailBean.getList().add(bean);
            }
            //简介信息
            movieDetailBean.setMovieDesc(document.select(desc.getSelector()).text());

            return movieDetailBean;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static SearchResultBean search(String text, int page) {
//处理搜索网址
        String search = configNow.getBaseUrl()+configNow.getSearchUrl().replace("TEMP",text);
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
            Elements elements = document.select(configNow.getResultSelector());
            for (Element e :elements) {                MovieDetailBean movieDetailBean = new MovieDetailBean();
                Config.Selctor imgSelctor = configNow.getResult().get(0);
                Config.Selctor descSelctor = configNow.getResult().get(1);
                Config.Selctor nameSelctor = configNow.getResult().get(2);
                Config.Selctor urlSelctor = configNow.getResult().get(3);
                Config.Selctor scoreSelctor = configNow.getResult().get(4);
                Config.Selctor actorsSelctor = configNow.getResult().get(5);

                String imgUrl = e.select(imgSelctor.getSelector()).attr(imgSelctor.getAttr());
                if(!imgUrl.contains("http")){
                    imgUrl = baseUrl + imgUrl;
                }
                movieDetailBean.setMovieImg(imgUrl);

                String desc = e.select(descSelctor.getSelector()).text();
                movieDetailBean.setMovieShortDesc(desc);

                String name = e.select(nameSelctor.getSelector()).attr(nameSelctor.getAttr());
                movieDetailBean.setMovieName(name);

                String turl = configNow.getBaseUrl()+e.select(urlSelctor.getSelector()).attr(urlSelctor.getAttr());
                movieDetailBean.setTargetUrl(turl);

                String score = e.select(scoreSelctor.getSelector()).text();
                movieDetailBean.setMovieScore(score);


                String actors = e.select(actorsSelctor.getSelector()).text();
                movieDetailBean.setMovieActors(actors);
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }

    private static String select(Element e, Config.Selctor selctor){
        String res = null;
        if(TextUtils.isEmpty(selctor.getSelector())){
            res =  "";
        }else {
            Elements el = e.select(selctor.getSelector());
            if(selctor.isUseText()){
                res = el.text();
            }else{
                res = el.attr(selctor.getAttr());
            }
        }
        return res;
    }

    private static String hadnleImgurl(String url){
        if(url.startsWith("//")){
            url = url.replace("//","http://");
        }else if(!url.contains("http")) {
            url = baseUrl + url;
        }
        return url;
    }
}
