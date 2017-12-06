package com.example.moyutest;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.moyutest.adapter.MainFragmentPagerAdapter;
import com.example.moyutest.adapter.MyViewPagerAdapter;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.BottomNavigationViewHelper;

import org.litepal.tablemanager.Connector;


import java.util.ArrayList;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private MainFragmentPagerAdapter myFragmentPagerAdapter;
    private MainFragment mainfragment = new MainFragment();
    private MenuItem menuItem;
    private LocalActivityManager manager;
    private MyViewPagerAdapter viewPageAdapter;
    private View.OnClickListener clickListener;
    private BottomNavigationView bottomNavigationView;
    private ViewPager.OnPageChangeListener pageChangeListener;
    public static Activity mMainActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //连接SQLiteStudio
        mMainActivity = this;
        SQLiteStudioService.instance().start(this);
        Connector.getDatabase();
//        checklogin();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //取消动画
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        mViewPager = (ViewPager) findViewById(R.id.mainviewPager);
        initViews();
    }

    private void initViews() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.main:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.message:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.fl_post:
                                mViewPager.setCurrentItem(2);
                                break;
                            case R.id.discovery:
                                mViewPager.setCurrentItem(3);
                                break;
                            case R.id.my:
                                mViewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });
        InitPager();
    }

    private void InitPager() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//禁止ViewPager滑动
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        AddActivitiesToViewPager();
        mViewPager.setCurrentItem(0);
    }

    private void AddActivitiesToViewPager() {
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();

        intent.setClass(this, MoyuActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("MoyuActivity", intent));

        intent.setClass(this, MessageActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("Message", intent));

        intent.setClass(this, PostActivity.class);
        intent.putExtra("id", 3);
        mViews.add(getView("Edit", intent));

        intent.setClass(this, SearchActivity.class);
        intent.putExtra("id", 4);
        mViews.add(getView("Search", intent));

        intent.setClass(this, MyActivity.class);
        intent.putExtra("id", 5);
        mViews.add(getView("MyActivity", intent));

        viewPageAdapter = new MyViewPagerAdapter(mViews);
        mViewPager.setAdapter(viewPageAdapter);

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();

    }
}