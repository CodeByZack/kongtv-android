package com.zack.kongtv.activities.PlayMovie;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.zack.kongtv.AppConfig;
import com.zack.kongtv.R;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.view.CustomPlayerControl;
import com.zackdk.NetWorkChange.NetStateChangeObserver;
import com.zackdk.NetWorkChange.NetStateChangeReceiver;
import com.zackdk.NetWorkChange.NetworkType;
import com.zackdk.Utils.LogUtil;
import com.zackdk.base.BaseMvpActivity;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.AudioItem;
import org.fourthline.cling.support.model.item.ImageItem;
import org.fourthline.cling.support.model.item.VideoItem;
import org.seamless.util.MimeType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class FullScreenActivity extends BaseMvpActivity<PlayMoviePresenter> implements IPlayMovieView,NetStateChangeObserver {
    public static String TEST_URL = "http://disp.titan.mgtv.com/vod.do?fmt=4&pno=1051000&fid=CC3A3E5D49EE9B10DCCC3B3E48734547&file=/c1/2018/06/22_0/CC3A3E5D49EE9B10DCCC3B3E48734547_20180622_1_1_1254.mp4";
    public static final ServiceType AV_TRANSPORT_SERVICE = new UDAServiceType("AVTransport");
    private static final String DIDL_LITE_FOOTER = "</DIDL-Lite>";
    private static final String DIDL_LITE_HEADER = "<?xml version=\"1.0\"?>" +
            "<DIDL-Lite " + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" " +
            "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" " +
            "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">";

    public static final int IMAGE_TYPE = 0;
    public static final int VIDEO_TYPE = 1;
    public static final int AUDIO_TYPE = 2;

	private Toolbar toolbar;
	private NiceVideoPlayer mNiceVideoPlayer;
	private String name,url;

	private MaterialDialog loading;
	//通过该service 拿到 控制点进行操作
	private AndroidUpnpService upnpService;
	//拿到upnpService
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			upnpService = (AndroidUpnpService) service;
			// 添加设备搜索监听
			upnpService.getRegistry().addListener(registryListener);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			upnpService = null;
		}
	};
	//搜索设备监听器
	private RegistryListener registryListener = new DefaultRegistryListener(){
		@Override
		public void deviceAdded(Registry registry, Device device) {
			//问题：一个一个回掉回来  如何知道什么时候是最后一个设备回掉？
			super.deviceAdded(registry, device);
			if(loading!=null)loading.dismiss();
			listDevice.add(device);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showList();
				}
			});
		}

		@Override
		public void deviceRemoved(Registry registry, Device device) {
			super.deviceRemoved(registry, device);
		}
	};
    //设备列表
    private List<Device> listDevice = new LinkedList<>();
	private String video_url;
	private int playerType = NiceVideoPlayer.TYPE_IJK;
	private CustomPlayerControl controller;
	private WebView webView;

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
		presenter.requestData(url);
		OrientationEventListenerImpl ore = new OrientationEventListenerImpl(this);
		ore.enable();
		bindService(new Intent(this, AndroidUpnpServiceImpl.class), serviceConnection, Context.BIND_AUTO_CREATE);
	}

    public void onclick(View v) {

		switch (v.getId()){
			case R.id.openAlipay:
				AndroidUtil.copy(this,AndroidUtil.getAlipayText());
				AndroidUtil.openAlipay(this);
				break;
			case R.id.copy:
				AndroidUtil.copy(this,video_url);
				break;
			case R.id.change:
//				if(playerType == NiceVideoPlayer.TYPE_IJK){
//					playerType = NiceVideoPlayer.TYPE_NATIVE;
//					showToast("已切换为NATIVEPLAYER");
//				}else{
//					playerType = NiceVideoPlayer.TYPE_IJK;
//					showToast("已切换为IJKPLAYER");
//				}
//				play2(video_url,playerType);
				Intent intent = new Intent(mActivity, WebviewFullScreenActivity.class);
				intent.putExtra("url",url);
				intent.putExtra("name",getSupportActionBar().getTitle());
				startActivity(intent);
				break;
			case R.id.touping:
				searchDLNA();
				break;
			case R.id.third:
				Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
				mediaIntent.setDataAndType(Uri.parse(video_url), "video/mp4");
				startActivity(mediaIntent);
				break;
		}
    }
	@Override
	protected void onResume() {
		super.onResume();
		NetStateChangeReceiver.registerObserver(this);
	}

	@Override
	public void play(String url) {
		String id = "";
//		if(AppConfig.getDefaultXIANLU() == AppConfig.PIPIGUI){
//			id+=url.substring(url.indexOf("id=")+3,url.indexOf("&"));
//		}else if(AppConfig.getDefaultXIANLU() == AppConfig._4KWU){
//
//		}
//		else{
//			id+=url.substring(url.indexOf("id=")+3);
//		}
		id+=url.substring(url.indexOf("id=")+3);
		final String js_kkkkwu = "window.local_obj.showSource($('video').attr('src'))";
		final String js_kkkkwu2 = "window.local_obj.showSource($('body').html())";
		final String js = "if(typeof(vid)!='undefined'){\n" +
				"    window.local_obj.showSource(vid)\n" +
				"}else{\n" +
				"    $.post(\"url.php\", {\"id\": \""+id+"\",\"type\": \""+id+"\",\"siteuser\": '',\"md5\": sign($('#hdMd5').val()),\"hd\":\"\",\"lg\":\"\",\"iqiyicip\":iqiyicip},\n" +
				"    function(data){\n" +
				"		console.log('data:'+JSON.stringify(data));\n" +
				"		window.local_obj.showSource(data.url)\n" +
				"    },\"json\");\n" +
				"}";
		final String jsGetContent = "window.local_obj.showSource('<head>'+"
				+ "document.getElementsByTagName('body')[0].innerHTML+'</head>');";
		webView = new WebView(this);
		//WebView webView = findViewById(R.id.webview);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		WebSettings webSettings = webView.getSettings();
		// 设置与Js交互的权限
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:"+js_kkkkwu2);
			}

		});
		Map extraHeaders = new HashMap();
		extraHeaders.put("Referer", this.url);
		webView.loadUrl(url, extraHeaders);
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

	public final class InJavaScriptLocalObj {
		@JavascriptInterface
		public void showSource(final String videoUrl) {
			LogUtil.d(videoUrl);
			video_url = videoUrl;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					play2(videoUrl,NiceVideoPlayer.TYPE_IJK);
					webView.destroy();
				}
			});

		}
	}

	private void play2(String url,int playerType) {
		hideLoading();
		boolean flag = false;
		if(mNiceVideoPlayer.isPlaying()){
			flag = true;
			mNiceVideoPlayer.release();
		}
		mNiceVideoPlayer.setPlayerType(playerType); // or NiceVideoPlayer.TYPE_NATIVE
		mNiceVideoPlayer.setUp(url, null);
		if(controller == null){
			controller = new CustomPlayerControl(this);
			controller.setImage(R.drawable.bg_black);
		}
		controller.setTitle(name);
		mNiceVideoPlayer.setController(controller);
		if(flag) mNiceVideoPlayer.start();

	}

	private void initView() {
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
		getSupportActionBar().setTitle(name);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.playmovie_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
//			case R.id.refresh:
//				presenter.requestData(url);
//				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void searchDLNA() {
		loading = new MaterialDialog.Builder(this)
                .title("查找设备中...")
                .content("投屏播放属于测试阶段，需要电视段支持DLNA。\n本人只在自家电视上测试通过了。不保证都能用哈。")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
		// 搜索所有的设备
		upnpService.getControlPoint().search();
	}

	@Override
	protected void initImmersionBar() {
		super.initImmersionBar();
		immersionBar.titleBar(toolbar).statusBarColor(R.color.colorPrimaryDark).init();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 在onStop时释放掉播放器
		NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
		NetStateChangeReceiver.unregisterObserver(this);
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

    //展示搜索到的设备
    private void showList() {
        List<String> list = new LinkedList<>();
        for (Device d :listDevice) {
            list.add(d.getDetails().getFriendlyName());
        }
        new MaterialDialog.Builder(this)
                .title("请选择播放设备")
                .items(list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        chooseDevice(position);
                    }
                })
                .show();
    }

    private void chooseDevice(int which) {
        //需要的参数之一
        final Service avtService = listDevice.get(which).findService(AV_TRANSPORT_SERVICE);
        //需要的参数之二
        String metadata = pushMediaToRender(video_url, "id", "name", "0",VIDEO_TYPE);

        upnpService.getControlPoint().execute(new SetAVTransportURI(avtService, video_url,metadata) {
            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                showToast("投屏成功！");
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                showToast("投屏失败！");
            }
        });
    }

    private String pushMediaToRender(String url, String id, String name, String duration,int ItemType) {
        long size = 0;
        long bitrate = 0;
        Res res = new Res(new MimeType(ProtocolInfo.WILDCARD, ProtocolInfo.WILDCARD), size, url);

        String creator = "unknow";
        String resolution = "unknow";
        String metadata = null;

        switch (ItemType){
            case IMAGE_TYPE:
                ImageItem imageItem = new ImageItem(id, "0", name, creator, res);
                metadata = createItemMetadata(imageItem);
                break;
            case VIDEO_TYPE:
                VideoItem videoItem = new VideoItem(id, "0", name, creator, res);
                metadata = createItemMetadata(videoItem);
                break;
            case AUDIO_TYPE:
                AudioItem audioItem = new AudioItem(id,"0",name,creator,res);
                metadata = createItemMetadata(audioItem);
                break;
        }

        Log.e("tag", "metadata: " + metadata);
        return metadata;
    }

    private String createItemMetadata(DIDLObject item) {
        StringBuilder metadata = new StringBuilder();
        metadata.append(DIDL_LITE_HEADER);

        metadata.append(String.format("<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item.getId(), item.getParentID(), item.isRestricted() ? "1" : "0"));

        metadata.append(String.format("<dc:title>%s</dc:title>", item.getTitle()));
        String creator = item.getCreator();
        if (creator != null) {
            creator = creator.replaceAll("<", "_");
            creator = creator.replaceAll(">", "_");
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator));
        metadata.append(String.format("<upnp:class>%s</upnp:class>", item.getClazz().getValue()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        metadata.append(String.format("<dc:date>%s</dc:date>", time));

        Res res = item.getFirstResource();
        if (res != null) {
            // protocol info
            String protocolinfo = "";
            ProtocolInfo pi = res.getProtocolInfo();
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"", pi.getProtocol(), pi.getNetwork(), pi.getContentFormatMimeType(), pi
                        .getAdditionalInfo());
            }
            Log.e("tag", "protocolinfo: " + protocolinfo);

            // resolution, extra info, not adding yet
            String resolution = "";
            if (res.getResolution() != null && res.getResolution().length() > 0) {
                resolution = String.format("resolution=\"%s\"", res.getResolution());
            }

            // duration
            String duration = "";
            if (res.getDuration() != null && res.getDuration().length() > 0) {
                duration = String.format("duration=\"%s\"", res.getDuration());
            }

            // res begin
            //            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration));

            // url
            String url = res.getValue();
            metadata.append(url);

            // res end
            metadata.append("</res>");
        }
        metadata.append("</item>");

        metadata.append(DIDL_LITE_FOOTER);

        return metadata.toString();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);

    }

	public class OrientationEventListenerImpl extends OrientationEventListener {
		public OrientationEventListenerImpl(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int rotation) {
			// 设置为竖屏
			// 设置为横屏
			if(!mNiceVideoPlayer.isFullScreen()){
				return;
			}
			if(((rotation >= 225) && (rotation <= 315))) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			//设置为横屏（逆向）
			if(((rotation >= 45) && (rotation <= 135))) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
			}
		}
	}
}
