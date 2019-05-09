package com.zack.kongtv.Data.Instance;

import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zack.kongtv.App;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HtmlResovle{

    private static String base_url = "http://m.benbenji.com/";

    public static List<Cms_movie> getHomeData() {
        Document document = null;
        List<Cms_movie> cms_movies = new LinkedList<>();

        try {
            document = Jsoup.connect(base_url).get();
            //找电影
            Elements elements = document.select(".all_tab>.list_tab_img");
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);

                Elements items =  element.select("li");
                for (int j = 0; j < items.size(); j++) {
                    Element item = items.get(j);
                    Cms_movie cms_movie = new Cms_movie();

                    cms_movie.setVodPic(item.getElementsByTag("img").get(0).attr("src"));
                    cms_movie.setVodName(item.getElementsByTag("a").get(0).attr("title"));
                    cms_movie.setTypeName(item.select("label.title").text());
                    cms_movie.setVodPlayUrl(base_url+item.getElementsByTag("a").get(0).attr("href"));

                    cms_movies.add(cms_movie);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return cms_movies;
    }

    public List<Cms_movie> getCategoryData(String url, int page) {
        return null;
    }

    public static Cms_movie getRealMovieDetail(Cms_movie old) {
        Document document = null;
        List<JujiBean> jujiBeans = new LinkedList<>();

        Cms_movie cms_movie = new Cms_movie();
        cms_movie.setVodName(old.getVodName());
        cms_movie.setVodPic(old.getVodPic());
        try {
            document = Jsoup.connect(old.getVodPlayUrl()).get();
            String info = document.select(".vod-n-l").text();
            String desc = document.select(".vod_content").text();

            Elements items = document.select(".plau-ul-list li");
            for (int i = 0; i < items.size(); i++) {
                Element e = items.get(i);
                JujiBean jujiBean = new JujiBean();

                jujiBean.setUrl(base_url + e.getElementsByTag("a").get(0).attr("href"));
                jujiBean.setText(e.getElementsByTag("a").text());

                jujiBeans.add(jujiBean);
            }

            cms_movie.setVodRemarks(info);
            cms_movie.setVodBlurb(desc);
            cms_movie.setJujiBeans(jujiBeans);


        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

        return cms_movie;
    }
    public static String getRealPlayUrl(String url) {
        WebView webView = new WebView(App.getContext());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("Tag", "onLoadResource: "+url);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl(url);
        return null;
    }
    public List<Cms_movie> search(String text, int page) {
        return null;
    }
}
