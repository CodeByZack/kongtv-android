package com.zack.kongtv.fragments.Home;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.ErrConsumer;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zackdk.mvp.p.BasePresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter<T extends IHomeView> extends BasePresenter<T> {
    public void requestData() {
        getView().showLoading();
        Disposable d = DataResp.getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cms_movie>>() {
                    @Override
                    public void accept(List<Cms_movie> cms_movies) throws Exception {
                        HomeDataBean data = new HomeDataBean();

                        List<Cms_movie> banners = new LinkedList<>();
                        data.setBannerItemBeans(banners);

                        List<HomeItemBean> items = new LinkedList<>();
                        data.setHomeItemBeans(items);

                        HomeItemBean film = new HomeItemBean();
                        film.setType(Const.Film);
                        film.setMovieDetailBeans(new ArrayList<Cms_movie>());

                        HomeItemBean episode = new HomeItemBean();
                        episode.setType(Const.Episode);
                        episode.setMovieDetailBeans(new ArrayList<Cms_movie>());

                        HomeItemBean anime = new HomeItemBean();
                        anime.setType(Const.Anime);
                        anime.setMovieDetailBeans(new ArrayList<Cms_movie>());

                        HomeItemBean variety = new HomeItemBean();
                        variety.setType(Const.Variety);
                        variety.setMovieDetailBeans(new ArrayList<Cms_movie>());

                        items.add(film);
                        items.add(episode);
                        items.add(anime);
                        items.add(variety);

                        for (Cms_movie movie : cms_movies) {
                            if (movie.getTypeId1() == Const.Film) {
                                film.getMovieDetailBeans().add(movie);
                                if (film.getMovieDetailBeans().size() == 1) {
                                    banners.add(movie);
                                }
                            }
                            if (movie.getTypeId1() == Const.Episode) {
                                episode.getMovieDetailBeans().add(movie);
                                if (episode.getMovieDetailBeans().size() == 1) {
                                    banners.add(movie);
                                }
                            }
                            if (movie.getTypeId() == Const.Variety) {
                                anime.getMovieDetailBeans().add(movie);
                                if (anime.getMovieDetailBeans().size() == 1) {
                                    banners.add(movie);
                                }
                            }
                            if (movie.getTypeId() == Const.Anime) {
                                variety.getMovieDetailBeans().add(movie);
                                if (variety.getMovieDetailBeans().size() == 1) {
                                    banners.add(movie);
                                }
                            }
                        }


                        getView().updateView(data);
                        getView().hideLoading();
                    }
                },new ErrConsumer());

        addDispoasble(d);
    }

    public void refresh() {
        requestData();
        getView().setRefresh(false);
    }
}
