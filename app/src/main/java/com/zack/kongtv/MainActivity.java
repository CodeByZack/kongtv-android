package com.zack.kongtv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zack.kongtv.fragments.CategoryFragment;
import com.zack.kongtv.fragments.HomeFragment;
import com.zackdk.base.AbsActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AbsActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private LinkedList<Fragment> fragments = new LinkedList<>();
    private PagerAdapter pagerAdapter;
    private List<String> titles = new LinkedList<>();

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
        fragments.add(new CategoryFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
