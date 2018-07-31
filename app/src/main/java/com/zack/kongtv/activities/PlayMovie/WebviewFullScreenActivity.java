package com.zack.kongtv.activities.PlayMovie;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentwebX5.AgentWebX5;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zack.kongtv.R;
import com.zackdk.Utils.LogUtil;

public class WebviewFullScreenActivity extends Activity {

	/**
	 * 用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式
	 */

	LinearLayout webView;
	private AgentWebX5 mAgentWeb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewdemo);
		webView = findViewById(R.id.container);
		Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
		mAgentWeb = AgentWebX5.with(this)
				.setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))
				.useDefaultIndicator()
				.setIndicatorColor(R.color.colorPrimary)
				.setWebViewClient(new WebViewClient(){
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						super.onPageStarted(view, url, favicon);
					}

					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						mAgentWeb.getJsEntraceAccess().callJs("$(\"div.playerbox\").toggle()\n$(\"div\").toggle()\n$(\"header\").toggle()");
						mAgentWeb.getWebCreator().get().setVisibility(View.VISIBLE);
					}
				})
				.createAgentWeb()
				.ready()
				.go(url);

		mAgentWeb.getWebCreator().get().setVisibility(View.INVISIBLE);
	}


}
