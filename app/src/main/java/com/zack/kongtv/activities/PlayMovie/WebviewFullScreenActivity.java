package com.zack.kongtv.activities.PlayMovie;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.zack.kongtv.R;
import com.zackdk.Utils.LogUtil;
import com.zackdk.base.AbsActivity;

public class WebviewFullScreenActivity extends AbsActivity {


	LinearLayout webView;
	private AgentWebX5 mAgentWeb;
	private Toolbar toolbar;
	private String name,url;

	@Override
	protected void initImmersionBar() {
		super.initImmersionBar();
		immersionBar.titleBar(toolbar).statusBarColor(R.color.colorPrimaryDark).init();
	}
	@Override
	public int setView() {
		return R.layout.webviewdemo;
	}

	@Override
	public void initBasic(Bundle savedInstanceState) {
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		url = intent.getStringExtra("url");
		initView();
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

	private void initView() {
		webView = findViewById(R.id.container);
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(name);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
