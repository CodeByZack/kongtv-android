package com.zack.kongtv.Data;


import java.util.List;

public class Config {
    private final String name;

    private final String baseUrl;

    private final String dyUrl;

    private final String dsjUrl;

    private final String dmUrl;

    private final String zyUrl;

    private final String searchUrl;

    private final String bannerSelector;

    private final java.util.List<Selctor> banner;

    private final String itemsSelector;

    private final java.util.List<Selctor> items;

    private final String mcidSelector;

    private final String secondSelector;

    private final String yearSelector;

    private final Selctor category;

    private final String listSelector;

    private final java.util.List<Selctor> list;

    private final String resultSelector;

    private final java.util.List<Selctor> result;
    private final java.util.List<Selctor> movieDetail;

    public Config(String name, String baseUrl, String dyUrl, String dsjUrl, String dmUrl,
                  String zyUrl, String searchUrl, String bannerSelector, List<Selctor> banner,
                  String itemsSelector, List<Selctor> items, String mcidSelector,
                  String secondSelector, String yearSelector, Selctor category, String listSelector,
                  List<Selctor> list, String resultSelector, List<Selctor> result, List<Selctor> movieDetail) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.dyUrl = dyUrl;
        this.dsjUrl = dsjUrl;
        this.dmUrl = dmUrl;
        this.zyUrl = zyUrl;
        this.searchUrl = searchUrl;
        this.bannerSelector = bannerSelector;
        this.banner = banner;
        this.itemsSelector = itemsSelector;
        this.items = items;
        this.mcidSelector = mcidSelector;
        this.secondSelector = secondSelector;
        this.yearSelector = yearSelector;
        this.category = category;
        this.listSelector = listSelector;
        this.list = list;
        this.resultSelector = resultSelector;
        this.result = result;
        this.movieDetail = movieDetail;
    }

    public String getName() {
        return name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDyUrl() {
        return dyUrl;
    }

    public String getDsjUrl() {
        return dsjUrl;
    }

    public String getDmUrl() {
        return dmUrl;
    }

    public String getZyUrl() {
        return zyUrl;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getBannerSelector() {
        return bannerSelector;
    }

    public java.util.List<Selctor> getBanner() {
        return banner;
    }

    public String getItemsSelector() {
        return itemsSelector;
    }

    public java.util.List<Selctor> getItems() {
        return items;
    }

    public String getMcidSelector() {
        return mcidSelector;
    }

    public String getSecondSelector() {
        return secondSelector;
    }

    public String getYearSelector() {
        return yearSelector;
    }

    public Selctor getCategory() {
        return category;
    }

    public String getListSelector() {
        return listSelector;
    }

    public java.util.List<Selctor> getList() {
        return list;
    }

    public String getResultSelector() {
        return resultSelector;
    }

    public List<Selctor> getResult() {
        return result;
    }

    public List<Selctor> getMovieDetail() {
        return movieDetail;
    }

    public static class Selctor {
        private final String selector;

        private final String attr;

        private final String nowClass;
        private final boolean useText;

        public Selctor(String selector, String attr, String nowClass,boolean useText) {
            this.selector = selector;
            this.attr = attr;
            this.nowClass = nowClass;
            this.useText = useText;
        }

        public String getSelector() {
            return selector;
        }

        public String getAttr() {
            return attr;
        }

        public String getNowClass() {
            return nowClass;
        }
    }


}
