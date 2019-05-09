package com.zack.kongtv.activities.PlayMovie;


import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zack.kongtv.App;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zackdk.Utils.LogUtil;
import com.zackdk.mvp.p.BasePresenter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayMoviePresenter<V extends IPlayMovieView> extends BasePresenter<V> {

    private String[] msgs = {"开发不易，不妨捐助一波...","多试试X5播放器...","在茫茫人海中，竟遇见了你。"};
    private WebView webView;

    public void requestData(String url) {
        getView().showLoading(getMsg());

        webView = new WebView(App.getContext());
        webView = new WebView(App.getContext());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("Tag", "onLoadResource: " + url);
                if (url.contains(".m3u8") || url.contains(".mp4")) {
                    Log.d("Tag", "onLoadResource: " + url);
                    Toast.makeText(App.getContext(), url, Toast.LENGTH_SHORT).show();
                    getView().play(url);
                    getView().hideLoading();
                    webView.destroy();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl(url);

    }

    private String getMsg(){
        int length = msgs.length;
        int x=(int)(Math.random()*length);
        if(x<length){
            return msgs[x];
        }else{
            return "";
        }
    }
}
