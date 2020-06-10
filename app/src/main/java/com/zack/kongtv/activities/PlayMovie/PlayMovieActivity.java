package com.zack.kongtv.activities.PlayMovie;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yanbo.lib_screen.callback.ControlCallback;
import com.yanbo.lib_screen.entity.ClingDevice;
import com.yanbo.lib_screen.entity.RemoteItem;
import com.yanbo.lib_screen.event.DeviceEvent;
import com.yanbo.lib_screen.manager.ClingManager;
import com.yanbo.lib_screen.manager.ControlManager;
import com.yanbo.lib_screen.manager.DeviceManager;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zackdk.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class PlayMovieActivity extends BaseMvpActivity<PlayMoviePresenter> implements IPlayMovieView, View.OnClickListener {

	private Toolbar toolbar;
	private String name,url;
	private IjkVideoView ijkVideoView;
	private int color;
	private LinearLayout root;
	private RecyclerView recyclerView;
	private Adapter adpter;
	private List<JujiBean> data;
	private Cms_movie movie;
	private int positionNow;
	private AdView mAdView;
	private LinearLayout adContainerView;
	private List<ClingDevice> clingDevices = new LinkedList<>();

	@Override
	public int setView() {
		return R.layout.palymovie;
	}

	@Override
	protected void initImmersionBar() {
		super.initImmersionBar();

		if(color!=0){
			immersionBar.titleBar(toolbar).statusBarColorInt(color).init();
		}else{
			immersionBar.titleBar(toolbar).statusBarColor(R.color.md_grey_400).init();
		}
	}

	@Override
	protected PlayMoviePresenter setPresenter() {
		return new PlayMoviePresenter();
	}

	@Override
	public void initBasic(Bundle savedInstanceState) {
		Intent intent = getIntent();
		initData(intent);
		initView();
		initLogic();
		initDlna();
	}

	private void initDlna() {
		ClingManager.getInstance().startClingService();
		RemoteItem itemurl = new RemoteItem(name, null, movie.getVodDirector(),
				0, null, null, url);
		ClingManager.getInstance().setRemoteItem(itemurl);
		clingDevices = DeviceManager.getInstance().getClingDeviceList();
	}

	private void initData(Intent intent){

		movie = (Cms_movie) intent.getSerializableExtra("movie");
		color = intent.getIntExtra("color",0);
		data = (List<JujiBean>) intent.getSerializableExtra("juji");
		positionNow = intent.getIntExtra("position",0);

		name = movie.getVodName() + ":" + data.get(positionNow).getText();
		url = data.get(positionNow).getUrl();
		CountEventHelper.countMovieWatch(this,url,name);
	}
	private void initView() {
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ijkVideoView = findViewById(R.id.player);
		root = findViewById(R.id.root);
		recyclerView = findViewById(R.id.play_list2);
		recyclerView.setLayoutManager(new GridLayoutManager(this,4));
		findViewById(R.id.touping).setOnClickListener(this);

		//广告相关
		adContainerView = findViewById(R.id.ad_container);
		// Step 1 - Create an AdView and set the ad unit ID on it.
		mAdView = new AdView(this);
		mAdView.setAdUnitId(Const.BANNER_AD);
//		mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		adContainerView.addView(mAdView);
		loadBanner();
	}
	private void initLogic() {
		setColor(color);
		play2(url,name);
		adpter = new Adapter(R.layout.m3u8_item,data);
		adpter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				String tmpUrl = data.get(position).getUrl();
				if(TextUtils.equals(tmpUrl,url)){
					return;
				}
				url = tmpUrl;
				name = name.substring(0,name.indexOf(":")+1);
				name = name + data.get(position).getText();
				positionNow = position;
				play2(url,name);
			}
		});
		recyclerView.setAdapter(adpter);
	}

	private void loadBanner() {
		// Create an ad request. Check your logcat output for the hashed device ID
		// to get test ads on a physical device, e.g.,
		// "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
		// device."
		AdRequest adRequest = new AdRequest.Builder().build();
		AdSize adSize = getAdSize();
		// Step 4 - Set the adaptive ad size on the ad view.
		mAdView.setAdSize(adSize);
		// Step 5 - Start loading the ad in the background.
		mAdView.loadAd(adRequest);
	}

	private AdSize getAdSize() {
		// Step 2 - Determine the screen width (less decorations) to use for the ad width.
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		float widthPixels = outMetrics.widthPixels;
		float density = outMetrics.density;
		int adWidth = (int) (widthPixels / density);
		// Step 3 - Get adaptive ad size and return for setting on the ad view.
		return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
	}


	private void play2(String url,String name) {
		getSupportActionBar().setTitle(name);
		if(ijkVideoView.isPlaying()){
			ijkVideoView.release();
		}
		ijkVideoView.setUrl(url); //设置视频地址
		ijkVideoView.setTitle(name); //设置视频标题
		StandardVideoController controller = new StandardVideoController(this);
		ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController


		//高级设置（可选，须在start()之前调用方可生效）
		PlayerConfig playerConfig = new PlayerConfig.Builder()
				.autoRotate() //启用重力感应自动进入/退出全屏功能
//				.enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
				.usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
				.savingProgress() //保存播放进度
				.disableAudioFocus() //关闭AudioFocusChange监听
//				.setLooping() //循环播放当前正在播放的视频
				.build();
		ijkVideoView.setPlayerConfig(playerConfig);

		ijkVideoView.start(); //开始播放，不调用则不自动播放

		HistoryMovieDao md = DataBase.getInstance().historyMovieDao();
		md.insert(AndroidUtil.transferHistory(movie,data.get(positionNow).getText()));
	}

	private void showDeviceList() {

		if(clingDevices.size() == 0){
			showToast("未找到可投屏设备，请确认连接至同一wifi！");
			return;
		}

		List<String> arr =new LinkedList<>();
		for (int i = 0; i < clingDevices.size(); i++) {
			ClingDevice now = clingDevices.get(i);
			arr.add(now.getDevice().getDetails().getFriendlyName());
		}
		new MaterialDialog.Builder(this)
				.title("投屏").items(arr).itemsCallback(new MaterialDialog.ListCallback() {
			@Override
			public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
				Log.d("TAG", "onSelection: "+text);
				DeviceManager.getInstance().setCurrClingDevice(clingDevices.get(position));
				startTouping();
			}
		}).show();
	}

	private void startTouping() {
		if (ControlManager.getInstance().getState() == ControlManager.CastState.STOPED) {
			RemoteItem item = ClingManager.getInstance().getRemoteItem();
			ControlManager.getInstance().setState(ControlManager.CastState.TRANSITIONING);
			ControlManager.getInstance().newPlayCast(item, new ControlCallback() {
				@Override
				public void onSuccess() {
					ControlManager.getInstance().setState(ControlManager.CastState.PLAYING);
					ControlManager.getInstance().initScreenCastCallback();
					Log.d("TAG", "onSuccess: 投屏");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showToast("投屏成功！");
						}
					});

				}

				@Override
				public void onError(int code, final String msg) {
					ControlManager.getInstance().setState(ControlManager.CastState.STOPED);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showToast(String.format("投屏失败 %s", msg));
						}
					});
				}
			});
		} else {
			Toast.makeText(getBaseContext(), "正在连接设备，稍后操作", Toast.LENGTH_SHORT).show();
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventBus(DeviceEvent event) {
		clingDevices = DeviceManager.getInstance().getClingDeviceList();
		showDeviceList();
	}

	public void setColor(int color){
		if(color!=0){
			toolbar.setBackgroundColor(color);
			root.setBackgroundColor(color);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()){
//			case R.id.openAlipay:
//
//				break;
//			case R.id.copy:
//				AndroidUtil.copy(this,url);
//				showToast(url);
//				break;
//			case R.id.change:
//
//				break;
			case R.id.touping:
				showDeviceList();
				break;
//			case R.id.third:
//				if(TextUtils.isEmpty(url)){
//					showToast("解析失败咯，不能调用第三方哦！");
//					return;
//				}
//				Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//				mediaIntent.setDataAndType(Uri.parse(url), "video/mp4");
//				startActivity(mediaIntent);
//				break;
		}
	}

	private class Adapter extends BaseQuickAdapter<JujiBean,BaseViewHolder> {
		public Adapter(int layoutResId, @Nullable List<JujiBean> data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, JujiBean item) {
			helper.setText(R.id.btPlayText,item.getText());
		}
	}
}
