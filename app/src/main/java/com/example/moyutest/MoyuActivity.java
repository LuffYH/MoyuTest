package com.example.moyutest;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.moyutest.adapter.MainFragmentPagerAdapter;

public class MoyuActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainFragmentPagerAdapter myFragmentPagerAdapter;
    private MainFragment mainfragment = new MainFragment();
    private TabLayout.Tab one;
    private TabLayout.Tab two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化视图
        initViews();
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);

    }
}
