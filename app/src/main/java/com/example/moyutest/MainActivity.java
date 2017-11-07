package com.example.moyutest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.litepal.tablemanager.Connector;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private BottomNavigationView mBottomNavigationView;
    private ViewPager viewPager;
    private MyFragment myfragment = new MyFragment();
    private MainFragment mainfragment = new MainFragment();
    private boolean loginFlag = false;

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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //viewpager监听
        viewPager.addOnPageChangeListener(this);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        //点击切换
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        //点击BottomNavigationView的Item项，切换ViewPager页面
                        //menu/navigation.xml里加的android:orderInCategory属性
                        //就是下面item.getOrder()取的值
                        viewPager.setCurrentItem(item.getOrder());
                        return true;
                    }
                });
        //获取fragment
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mainfragment;
                    case 1:
                        return myfragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        if (!loginFlag) {

        }
    }

    private void checklogin() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String token = pref.getString("token", "");
        if (token != null && !token.equals("")) {
            loginFlag = true;
            Log.d("Phone", token);
        }
        if (loginFlag == false) {
            Intent logintent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logintent);
            Log.d("Phone", "false");
            finish();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //页面滑动的时候，改变BottomNavigationView的Item高亮
    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
