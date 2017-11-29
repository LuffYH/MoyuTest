package com.example.moyutest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moyutest.HotFragment;
import com.example.moyutest.MainFragment;

/**
 * Created by Administrator on 2017/11/26.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"首页", "热门"};

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new HotFragment();
        }
        return new MainFragment();

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
