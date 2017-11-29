package com.example.moyutest;

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
import com.example.moyutest.util.BottomNavigationViewHelper;

import org.litepal.tablemanager.Connector;


import java.util.ArrayList;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MainFragmentPagerAdapter myFragmentPagerAdapter;
    private MainFragment mainfragment = new MainFragment();
    private boolean loginFlag = false;
    private MenuItem menuItem;
    //LocalActivityManager用来获取每个activity的view,放于Adapter中
    //MyViewPageAdapter用来放viewpager的内容
    //OnClickListener设置底部图片的点击事件
    //OnPageChangeListener设置图片的滑动事件
    private LocalActivityManager manager;
    private MyViewPagerAdapter viewPageAdapter;
    private View.OnClickListener clickListener;
    private BottomNavigationView bottomNavigationView;
    private ViewPager.OnPageChangeListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //连接SQLiteStudio
        SQLiteStudioService.instance().start(this);
        Connector.getDatabase();
        checklogin();
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
        intent.setClass(this, MyActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("MyActivity", intent));
        viewPageAdapter = new MyViewPagerAdapter(mViews);
        mViewPager.setAdapter(viewPageAdapter);

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();

    }

    private void checklogin() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String token = pref.getString("token", "");
        if (token != null && !token.equals("")) {
            loginFlag = true;
            Log.d("Phone", "登陆token = " + token);
        }
        if (loginFlag == false) {
            Intent logintent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logintent);
            Log.d("Phone", "未登录状态");
            finish();
        }
    }

    //点击监听
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    //判断是否点击edittext
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //隐藏keyboard
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        if (loginFlag == true) {
            SQLiteStudioService.instance().stop();
        }
        super.onDestroy();
    }
}