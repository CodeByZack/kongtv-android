package com.zack.kongtv.activities.About;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zack.kongtv.R;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.util.PackageUtil;
import com.zackdk.Utils.ToastUtil;
import com.zackdk.base.AbsActivity;

import java.net.URISyntaxException;

public class AboutActivity extends AbsActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private LinearLayout pay,email,qq;
    private TextView titleVersion;
    @Override
    public int setView() {
        return R.layout.activity_about;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        pay = findViewById(R.id.pay);
        pay.setOnClickListener(this);
        email =  findViewById(R.id.email);
        email.setOnClickListener(this);
        qq = findViewById(R.id.qq);
        qq.setOnClickListener(this);

        titleVersion = findViewById(R.id.title_text);
        String name = PackageUtil.packageName(this);
        titleVersion.setText("风影院，像风一样自由。version"+name);
    }

    @Override
    protected void initImmersionBar() {
        immersionBar.titleBar(toolbar).init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case -1:
                finish();
                break;
            case R.id.pay:
                openALiPay();
                break;
            case R.id.email:
                email();
                break;
            case R.id.qq:
                if(!joinQQGroup("8vr6aLfMnwc4bW-am71lFfqhb7PkTkZl")){
                    ToastUtil.showToast("未安装手Q或安装的版本不支持");
                }
                break;
        }
    }

    public  boolean hasInstalledAlipayClient() {
        String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
        PackageManager pm = this.getApplicationContext().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void openALiPay(){
        CountEventHelper.countAlipay(this);
        String url1="intent://platformapi/startapp?saId=10000007&" +
                "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2Fa6x05048iewv4tsdnwmykbc%3F_s" +
                "%3Dweb-other&_t=1472443966571#Intent;" +
                "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        //String url1=activity.getResources().getString(R.string.alipay);
        Intent intent = null;
        Toast.makeText(this.getApplicationContext(),"感谢您的捐赠！٩(๑❛ᴗ❛๑)۶",Toast.LENGTH_SHORT).show();
        if(hasInstalledAlipayClient()){
            try {
                intent = Intent.parseUri(url1 ,Intent.URI_INTENT_SCHEME );
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(this.getApplicationContext(),"出错啦",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this.getApplicationContext(),"您未安装支付宝哦！(>ω･* )ﾉ",Toast.LENGTH_SHORT).show();
        }
    }
    /****************
     *
     * 发起添加群流程。群号：风影院(793272974) 的 key 为： 2RiYsStZyv56q4x9In9r67xKH-2ft7fY
     * 调用 joinQQGroup(2RiYsStZyv56q4x9In9r67xKH-2ft7fY) 即可发起手Q客户端申请加群 风影院(793272974)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        CountEventHelper.countJoinQQ(this);
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    public void email(){
        CountEventHelper.countOpenEmail(this);
        Uri uri = Uri.parse("mailto:958059970@qq.com");
        String[] email = {"958059970@qq.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "空影院软件的意见反馈"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, ""); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }
}
