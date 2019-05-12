package com.zack.kongtv.Data.Instance;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.Config;
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

    private static String baseUrl = null;
    private static String dyUrl = null;
    private static String dsjUrl = null;
    private static String dmUrl = null;
    private static String zyUrl = null;
    private static Config configNow = null;
    private static List<Config> configs;

    public static void initResolveConfig(){
        String json = AndroidUtil.getJson("config.json");
        configs = AndroidUtil.parseJSON2Map(json);
        configNow = configs.get(0);
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


                itemBean.setImg(e.select(imgSelctor.getSelector()).attr(imgSelctor.getAttr()));
                itemBean.setDesc(e.select(descSelctor.getSelector()).text());
                itemBean.setTargetUrl(configNow.getBaseUrl()+e.select(urlSelctor.getSelector()).attr(urlSelctor.getAttr()));

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

                    String imgUrl = e.select(imgSelctor.getSelector()).attr(imgSelctor.getAttr());
                    movieDetailBean.setMovieImg(imgUrl);

                    String desc = e.select(descSelctor.getSelector()).text();
                    movieDetailBean.setMovieShortDesc(desc);

                    String name = e.select(nameSelctor.getSelector()).attr(nameSelctor.getAttr());
                    movieDetailBean.setMovieName(name);

                    String url = configNow.getBaseUrl()+e.select(urlSelctor.getSelector()).attr(urlSelctor.getAttr());
                    movieDetailBean.setTargetUrl(url);

                    String score = e.select(scoreSelctor.getSelector()).text();
                    movieDetailBean.setMovieScore(score);


                    String actors = e.select(actorsSelctor.getSelector()).text();
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
        String replace = "index_"+page;
        url = url.replaceFirst("index_.",replace);
        Document document;
        try {
            document = Jsoup.connect(url).get();

            //获取类型分类
            Element element = document.select(configNow.getMcidSelector()).get(0);
            for(Element e : element.select(configNow.getCategory().getSelector())){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(configNow.getBaseUrl()+e.attr(configNow.getCategory().getAttr()));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.select(configNow.getSecondSelector()).get(0);
            for(Element e : element2.select(configNow.getCategory().getSelector())){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(configNow.getBaseUrl()+e.attr(configNow.getCategory().getAttr()));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.select(configNow.getYearSelector()).get(0);
            for(Element e : element3.select(configNow.getCategory().getSelector())){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass(configNow.getCategory().getNowClass()));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(configNow.getBaseUrl()+e.attr(configNow.getCategory().getAttr()));
                tag3.add(tagItemBean);
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

                String imgUrl = e.select(imgSelctor.getSelector()).attr(imgSelctor.getAttr());
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
            movieDetailBean.setMovieName(document.select(title.getSelector()).text());
            movieDetailBean.setMovieStatus(document.select(pInfo.getSelector()).get(0).text());
            movieDetailBean.setMovieActors(document.select(pInfo.getSelector()).get(1).text());
            movieDetailBean.setMovieType(document.select(pInfo.getSelector()).get(2).text());
            movieDetailBean.setMovieDirector(document.select(pInfo.getSelector()).get(3).text());
            movieDetailBean.setMovieYear(document.select(pInfo.getSelector()).get(4).text());
            movieDetailBean.setMovieLanguage(document.select(pInfo.getSelector()).get(5).text());
            movieDetailBean.setMovieImg(document.select(img.getSelector()).attr(img.getAttr()));
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

    public String getBaseUrl() {
        return null;
    }

    public String getMovieUrl() {
        return null;
    }

    public String getEpisodeUrl() {
        return null;
    }

    public String getAnimeUrl() {
        return null;
    }

    public String getVarietyUrl() {
        return null;
    }

    public String getSearchUrl() {
        return null;
    }

    public String getName() {
        return null;
    }
}
