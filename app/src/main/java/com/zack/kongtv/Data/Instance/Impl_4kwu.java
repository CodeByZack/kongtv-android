package com.zack.kongtv.Data.Instance;

import com.zack.kongtv.AppConfig;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zackdk.Utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Impl_4kwu implements GetDataInterface {
    private static final String baseUrl_4KWU = "http://m.kkkkmao.com";
    private static final String MovieUrl_4KWU = baseUrl_4KWU+"/movie/index_1_______1.html";
    private static final String EpisodeUrl_4KWU = baseUrl_4KWU+"/tv/index_1_______1.html";
    private static final String AnimeUrl_4KWU = baseUrl_4KWU+"/Animation/index_1_______1.html";
    private static final String VarietyUrl_4KWU = baseUrl_4KWU+"/Arts/index_1_______1.html";
    private static final String SearchUrl_4KWU = baseUrl_4KWU+"/vod-search-wd-TEMP-p-PAGE.html";
    public static final String NAME = "线路一";


    @Override
    public HomeDataBean getHomeData() {
        Document document;
        List<BannerItemBean> bannerItemBeans = new LinkedList<>();
        List<HomeItemBean> homeItemBeans = new LinkedList<>();
        try {
            document = Jsoup.connect(baseUrl_4KWU).get();
            Elements banners = document.getElementsByClass("focusList").get(0).getElementsByClass("con");
            for (Element e: banners) {
                BannerItemBean itemBean = new BannerItemBean();
                itemBean.setTargetUrl(baseUrl_4KWU+e.getElementsByTag("a").attr("href"));
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
                    movieDetailBean.setTargetUrl(baseUrl_4KWU+e.getElementsByTag("a").get(0).attr("href"));
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

    @Override
    public CategoryDataBean getCategoryData(String url, int page) {
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
                tagItemBean.setUrl(baseUrl_4KWU+e.attr("href"));
                tag1.add(tagItemBean);
            }
            //获取地区分类
            Element element2 = document.getElementById("second_list");
            for(Element e : element2.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(baseUrl_4KWU+e.attr("href"));
                tag2.add(tagItemBean);
            }
            //获取年份分类
            Element element3 = document.getElementById("year_list");
            for(Element e : element3.getElementsByTag("a")){
                TagItemBean tagItemBean = new TagItemBean();
                tagItemBean.setSelect(e.hasClass("cur"));
                tagItemBean.setTag(e.text());
                tagItemBean.setUrl(baseUrl_4KWU+e.attr("href"));
                tag3.add(tagItemBean);
            }

            Elements elements = document.getElementsByClass("list_tab_img").get(0).getElementsByTag("li");
            for (Element e : elements) {
                MovieDetailBean movieDetailBean = new MovieDetailBean();
                movieDetailBean.setMovieImg(e.getElementsByTag("img").attr("src"));
                movieDetailBean.setMovieShortDesc(e.getElementsByClass("title").get(0).text());
                movieDetailBean.setMovieName(e.getElementsByTag("a").attr("title"));
                movieDetailBean.setTargetUrl(baseUrl_4KWU+e.getElementsByTag("a").get(0).attr("href"));
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

    @Override
    public MovieDetailBean getRealMovieDetail(String url) {
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
                bean.setUrl(baseUrl_4KWU+e.getElementsByTag("a").attr("href"));
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



    @Override
    public SearchResultBean search(String text, int page) {
        //处理搜索网址
        String search = HtmlResolve.searchUrl.replace("TEMP",text);
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
                movieDetailBean.setTargetUrl(baseUrl_4KWU+e.getElementsByTag("a").get(0).attr("href"));
                movieDetailBean.setMovieActors(e.getElementsByTag("p").get(2).getElementsByTag("span").text());
                movieDetailBeans.getList().add(movieDetailBean);
            }
        } catch (IOException e1) {
            return null;
        }
        return movieDetailBeans;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl_4KWU;
    }

    @Override
    public String getMovieUrl() {
        return MovieUrl_4KWU;
    }

    @Override
    public String getEpisodeUrl() {
        return EpisodeUrl_4KWU;
    }

    @Override
    public String getAnimeUrl() {
        return AnimeUrl_4KWU;
    }

    @Override
    public String getVarietyUrl() {
        return VarietyUrl_4KWU;
    }

    @Override
    public String getSearchUrl() {
        return SearchUrl_4KWU;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
