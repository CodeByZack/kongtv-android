package com.zack.kongtv.activities.PlayMovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
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
	private IjkVideoView ijkVideoView;


	private void play2(String url,String name) {
		ijkVideoView.setUrl(url); //设置视频地址
		ijkVideoView.setTitle(name); //设置视频标题
		StandardVideoController controller = new StandardVideoController(this);
		ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController


		//高级设置（可选，须在start()之前调用方可生效）
		PlayerConfig playerConfig = new PlayerConfig.Builder()
				.autoRotate() //启用重力感应自动进入/退出全屏功能
				.enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
				.usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
				.savingProgress() //保存播放进度
				.disableAudioFocus() //关闭AudioFocusChange监听
//				.setLooping() //循环播放当前正在播放的视频
				.build();
		ijkVideoView.setPlayerConfig(playerConfig);

		ijkVideoView.start(); //开始播放，不调用则不自动播放
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
		ijkVideoView = findViewById(R.id.player);
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
		return R.layout.palymovie;
	}

	@Override
	public void initBasic(Bundle savedInstanceState) {
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		url = intent.getStringExtra("url");
		CountEventHelper.countMovieWatch(this,url,name);
		initView();
		play2(url,name);
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
	protected void onPause() {
		super.onPause();
		ijkVideoView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ijkVideoView.resume();
		NetStateChangeReceiver.registerObserver(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ijkVideoView.release();
	}


	@Override
	public void onBackPressed() {
		if (!ijkVideoView.onBackPressed()) {
			super.onBackPressed();
		}
	}

}
