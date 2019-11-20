package com.wlm.wlm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2019/9/16.
 */
public class TabPageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    private List<String> titles;
    FragmentManager fm;

    public TabPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.fm = fm;
//        fragments.add(allOrderFragment);
//        fragments.add(waitPayFragment);
//        fragments.add(waitReceiveFragment);
//        fragments.add(completedOrderFragment);
//        fragments.add(overOrderFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 将实例化的fragment进行显示即可。
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            Fragment fragment = fragments.get(position);// 获取要销毁的fragment
//            getSupportFragmentManager().beginTransaction().hide(fragment).commit();// 将其隐藏即可，并不需要真正销毁，这样fragment状态就得到了保存
    }
}