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
import com.zackdk.base.AbsActivity;

import java.net.URISyntaxException;

public class AboutActivity extends AbsActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView linearLayout,email;
    @Override
    public int setView() {
        return R.layout.activity_about;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        linearLayout = (TextView) findViewById(R.id.pay);
        linearLayout.setOnClickListener(this);

        email = (TextView) findViewById(R.id.email);
        email.setOnClickListener(this);
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

    public void email(){
        Uri uri = Uri.parse("mailto:958059970@qq.com");
        String[] email = {"958059970@qq.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "Aladd软件的意见反馈"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, ""); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }
}
