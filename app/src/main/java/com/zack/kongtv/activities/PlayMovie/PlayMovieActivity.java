package com.zack.kongtv.activities.PlayMovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.zack.kongtv.R;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zackdk.NetWorkChange.NetStateChangeObserver;
import com.zackdk.NetWorkChange.NetStateChangeReceiver;
import com.zackdk.NetWorkChange.NetworkType;
import com.zackdk.base.BaseMvpActivity;


public class PlayMovieActivity extends BaseMvpActivity<PlayMoviePresenter> implements IPlayMovieView,NetStateChangeObserver {

	private Toolbar toolbar;
	private String name,url;


	private void play2(String url,int playerType) {

	}

	private void initView() {
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

	public void onclick(View v) {

		switch (v.getId()){
			case R.id.openAlipay:

				break;
			case R.id.copy:
				AndroidUtil.copy(this,url);
				showToast(url);
				break;
			case R.id.change:

				break;
			case R.id.touping:
				//searchDLNA();
				break;
			case R.id.third:
				if(TextUtils.isEmpty(url)){
					showToast("解析失败咯，不能调用第三方哦！");
					return;
				}
				Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
				mediaIntent.setDataAndType(Uri.parse(url), "video/mp4");
				startActivity(mediaIntent);
				break;
		}
	}


	@Override
	protected void initImmersionBar() {
		super.initImmersionBar();
		immersionBar.titleBar(toolbar).statusBarColor(R.color.colorAccent).init();
	}

	@Override
	public int setView() {
		return R.layout.filechooser_layout;
	}

	@Override
	public void initBasic(Bundle savedInstanceState) {
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		url = intent.getStringExtra("url");
		CountEventHelper.countMovieWatch(this,url,name);
		initView();
		play2(url,1);
	}

	@Override
	protected PlayMoviePresenter setPresenter() {
		return new PlayMoviePresenter();
	}



	@Override
	public void onNetDisconnected() {
		showToast("网络断开了！");
	}

	@Override
	public void onNetConnected(NetworkType networkType) {
		if(networkType == NetworkType.NETWORK_2G || networkType == NetworkType.NETWORK_4G || networkType == NetworkType.NETWORK_3G){
			showToast("温馨提示，你正在使用流量观看视频!");
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		NetStateChangeReceiver.registerObserver(this);
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
