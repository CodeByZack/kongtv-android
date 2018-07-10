package com.zack.kongtv.activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.About.AboutActivity;
import com.zack.kongtv.activities.MovieList.MovieListActivity;
import com.zack.kongtv.activities.SearchResult.SearchActivity;
import com.zack.kongtv.fragments.Category.CategoryFragment;
import com.zack.kongtv.fragments.Home.HomeFragment;
import com.zackdk.base.AbsActivity;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AbsActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private LinkedList<Fragment> fragments = new LinkedList<>();
    private PagerAdapter pagerAdapter;
    private List<String> titles = new LinkedList<>();
    private long clickTime;

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

    private void init() {
        titles.add("首页");
        titles.add("电影");
        titles.add("剧集");
        titles.add("动漫");
        titles.add("综艺");

        fragments.add(new HomeFragment());
        fragments.add(CategoryFragment.instance(DataResp.MovieUrl));
        fragments.add(CategoryFragment.instance(DataResp.EpisodeUrl));
        fragments.add(CategoryFragment.instance(DataResp.AnimeUrl));
        fragments.add(CategoryFragment.instance(DataResp.VarietyUrl));
    }

    private void initLogic() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.collect:
                startActivity(new Intent(this, MovieListActivity.class).putExtra("mode", Const.Collect));
                break;
            case R.id.history:
                startActivity(new Intent(this, MovieListActivity.class).putExtra("mode", Const.History));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_collect) {
            startActivity(new Intent(this, MovieListActivity.class).putExtra("mode",Const.Collect));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, MovieListActivity.class).putExtra("mode",Const.History));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
