package com.zack.kongtv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zack.appupdate.AppUpdate;
import com.zack.kongtv.AppConfig;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.Instance.GetDataInterface;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.About.AboutActivity;
import com.zack.kongtv.activities.MovieList.MovieListActivity;
import com.zack.kongtv.activities.SearchResult.SearchActivity;
import com.zack.kongtv.bean.UpdateInfo;
import com.zack.kongtv.fragments.Category.CategoryFragment;
import com.zack.kongtv.fragments.Category.CategoryFragmentNew;
import com.zack.kongtv.fragments.Home.HomeFragment;
import com.zack.kongtv.fragments.Home.HomeFragmentNew;
import com.zack.kongtv.util.AndroidUtil;
import com.zack.kongtv.util.PackageUtil;
import com.zackdk.Utils.ToastUtil;
import com.zackdk.base.AbsActivity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AbsActivity {
    private ViewPager viewPager;
    private com.antiless.support.widget.TabLayout tabLayout;
    private Toolbar toolbar;
//    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ImageView search;
    private LinkedList<Fragment> fragments = new LinkedList<>();
    private PagerAdapter pagerAdapter;
    private List<String> titles = new LinkedList<>();
    private TextView nav_version,tv_xianlu;
    private long clickTime;
    private Disposable updateInfoDisposable;
    private AppUpdate appupdate;

    @Override
    public int setView() {
        return R.layout.activity_main;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        init();
        initView();
        initLogic();

    }
    public void onclick(View v) {
        int id = v.getId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_collect) {
            startActivity(new Intent(this, MovieListActivity.class).putExtra("mode",Const.Collect));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, MovieListActivity.class).putExtra("mode",Const.History));
        } else if(id == R.id.nav_share){
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);
            share_intent.setType("text/plain");
            //share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
            share_intent.putExtra(Intent.EXTRA_TEXT, "https://apks-1252514056.cos.ap-chengdu.myqcloud.com/now.apk");
            share_intent = Intent.createChooser(share_intent, "风影院，像风一样自由！");
            startActivity(share_intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    private void init() {
        titles.add("首页");
        titles.add("电影");
        titles.add("剧集");
        titles.add("动漫");
        titles.add("综艺");

        fragments.add(new HomeFragmentNew());
        fragments.add(CategoryFragmentNew.instance(Const.Film));
        fragments.add(CategoryFragmentNew.instance(Const.Episode));
        fragments.add(CategoryFragmentNew.instance(Const.Anime));
        fragments.add(CategoryFragmentNew.instance(Const.Variety));
    }

    private void initLogic() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        String name = PackageUtil.packageName(this);
        nav_version.setText("风影院 version"+name);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        updateInfoDisposable = DataResp.getAppUpdateInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<UpdateInfo>() {
                    @Override
                    public void accept(UpdateInfo o) throws Exception {
                        if (o.getApp_version() > PackageUtil.packageCode(MainActivity.this)) {
                            showUpdateDilog(o);
                        }
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updateInfoDisposable!=null &&!updateInfoDisposable.isDisposed())updateInfoDisposable.dispose();
        if(appupdate!=null)appupdate.dismiss();
    }

    private void showUpdateDilog(UpdateInfo updateInfo) {
        String dirFilePath = "";
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //SD卡有用
            dirFilePath = getExternalFilesDir("apk/test.apk").getAbsolutePath();
        }else{
            //SD卡没有用
            dirFilePath = getFilesDir()+ File.separator+"apk/test.apk";
        }
        appupdate = AppUpdate.init(this)
                .setDownloadUrl(updateInfo.getDownload_url())
                .setSavePath(dirFilePath);
        appupdate.showUpdateDialog("检查到有更新！",updateInfo.getApp_updateInfo(),null);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_version = findViewById(R.id.nav_version);
        search = findViewById(R.id.search);
    }

    @Override
    protected void initImmersionBar() {
        immersionBar.statusBarDarkFont(true);

        immersionBar.statusBarColor(R.color.white);

        immersionBar.init();
    }

    public void showPage(int type){
        viewPager.setCurrentItem(type);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(viewPager.getCurrentItem() != 0){
            viewPager.setCurrentItem(0);
        } else if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
