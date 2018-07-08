package com.zack.kongtv.activities.PlayMovie;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.R;
import com.zackdk.Utils.LogUtil;
import com.zackdk.Utils.ToastUtil;
import com.zackdk.base.AbsActivity;
import com.zackdk.base.BaseActivity;
import com.zackdk.base.BaseMvpActivity;

import java.util.HashMap;
import java.util.Map;


public class FullScreenActivity extends BaseMvpActivity<PlayMoviePresenter> implements IPlayMovieView{

	private Toolbar toolbar;
	private NiceVideoPlayer mNiceVideoPlayer;
	private String name,url;

	@Override
	public int setView() {
		return R.layout.filechooser_layout;
	}

	@Override
	public void initBasic(Bundle savedInstanceState) {
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		url = intent.getStringExtra("url");
		initView();
		initLogic();
		presenter.requestData(url);
	}

	private void initLogic() {

	}

	@Override
	public void play(String url) {
		String id = url.substring(url.indexOf("id=")+3);
		final String js = "if(typeof(vid)!='undefined'){\n" +
				"    window.local_obj.showSource(vid)\n" +
				"}else{\n" +
				"    $.post(\"url.php\", {\"id\": \""+id+"\",\"type\": \""+id+"\",\"siteuser\": '',\"md5\": sign($('#hdMd5').val()),\"hd\":\"\",\"lg\":\"\",\"iqiyicip\":iqiyicip},\n" +
				"    function(data){\n" +
				"        window.local_obj.showSource(data.url)\n" +
				"    },\"json\");\n" +
				"}";
		WebView webView = new WebView(this);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		WebSettings webSettings = webView.getSettings();
		// 设置与Js交互的权限
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:"+js);
			}

		});
		Map extraHeaders = new HashMap();
		extraHeaders.put("Referer", this.url);
		webView.loadUrl(url, extraHeaders);

	}

	public final class InJavaScriptLocalObj {
		@JavascriptInterface
		public void showSource(final String videoUrl) {
			LogUtil.d(videoUrl);
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					play2(videoUrl);
				}
			});
		}
	}

	private void play2(String url) {
		hideLoading();
		mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
		mNiceVideoPlayer.setUp(url, null);
		TxVideoPlayerController controller = new TxVideoPlayerController(this);
		controller.setTitle(name);
		controller.setImage(R.drawable.bg_black);
		mNiceVideoPlayer.setController(controller);

	}

	private void initView() {
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
		getSupportActionBar().setTitle(name);
		toolbar.setNavigationIcon(R.drawable.ic_player_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void initImmersionBar() {
		super.initImmersionBar();
		immersionBar.titleBar(toolbar).statusBarColor(R.color.colorPrimary).init();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 在onStop时释放掉播放器
		NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
	}
	@Override
	public void onBackPressed() {
		// 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
		// 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
		if (NiceVideoPlayerManager.instance().onBackPressd()) return;
		super.onBackPressed();
	}

	@Override
	protected PlayMoviePresenter setPresenter() {
		return new PlayMoviePresenter();
	}
}
