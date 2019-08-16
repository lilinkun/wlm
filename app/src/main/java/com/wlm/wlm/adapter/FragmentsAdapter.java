package com.wlm.wlm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.wlm.wlm.base.BaseFragment;

/**
 * Created by LG on 2018/11/10.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseFragment> fragmentSparseArr;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(SparseArray<BaseFragment> fragmentSparseArray) {
        this.fragmentSparseArr = fragmentSparseArray;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (null != fragmentSparseArr) return fragmentSparseArr.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (null != fragmentSparseArr) return fragmentSparseArr.size();
        return 0;
    }

}
