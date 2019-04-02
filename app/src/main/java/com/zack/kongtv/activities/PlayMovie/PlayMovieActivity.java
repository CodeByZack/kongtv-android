package com.zack.kongtv.activities.PlayMovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovieDao;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zackdk.NetWorkChange.NetStateChangeObserver;
import com.zackdk.NetWorkChange.NetStateChangeReceiver;
import com.zackdk.NetWorkChange.NetworkType;
import com.zackdk.base.BaseMvpActivity;

import java.util.LinkedList;
import java.util.List;

import zmovie.com.dlan.DlanPresenter;


public class PlayMovieActivity extends BaseMvpActivity<PlayMoviePresenter> implements IPlayMovieView {

	private Toolbar toolbar;
	private String name,url;
	private IjkVideoView ijkVideoView;
	private LinearLayout root;
	private TextView title;
	private RecyclerView recyclerView;
	private View toupin,copy,outplay;
	private Adapter adpter;
	private List<JujiBean> data;
	private Cms_movie movie;
	private int positionNow;
	private int color;
	private DlanPresenter dlanPresenter;


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
			immersionBar.titleBar(toolbar).statusBarColor(R.color.colorAccent).init();
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
		title = findViewById(R.id.title);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ijkVideoView = findViewById(R.id.player);
		toupin = findViewById(R.id.toupin);
		copy = findViewById(R.id.copy);
		outplay = findViewById(R.id.outplay);

		root = findViewById(R.id.root);
		recyclerView = findViewById(R.id.play_list2);
		recyclerView.setLayoutManager(new GridLayoutManager(this,4));
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

		dlanPresenter = new DlanPresenter(this);
		dlanPresenter.initService();
		toupin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlanPresenter.showDialogTip(PlayMovieActivity.this,"http://vwecam.tc.qq.com/1006_549a434f0a2b42f696e34ceb971fbecc.f20.mp4?ptype=http&vkey=78762F71394A16CE4514E978CC0E12D8BBDD88E294DF8044F58F02198DBEE9F9F4C417A5DD15FCC6B0754EA0F7DB4F92F370C50CD594673C",name);
			}
		});
		copy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AndroidUtil.copy(PlayMovieActivity.this,url);
			}
		});
		outplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
				mediaIntent.setDataAndType(Uri.parse(url), "video/mp4");
				startActivity(mediaIntent);
			}
		});
	}




	private void play2(String url,String name) {
		getSupportActionBar().setTitle(name);
		title.setText(name);
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

	public void onclick(View v) {

//		switch (v.getId()){
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
//			case R.id.touping:
//				//searchDLNA();
//				break;
//			case R.id.third:
//				if(TextUtils.isEmpty(url)){
//					showToast("解析失败咯，不能调用第三方哦！");
//					return;
//				}
//				Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//				mediaIntent.setDataAndType(Uri.parse(url), "video/mp4");
//				startActivity(mediaIntent);
//				break;
//		}
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
