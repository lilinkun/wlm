package com.wlm.wlm.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.entity.TBbean;
import com.wlm.wlm.fragment.SelfGoodFragment;

import java.util.List;

/**
 * Created by LG on 2018/12/12.
 */

public class GetPagerAdapter extends FragmentStatePagerAdapter {

    List<TBbean> list;
    private SelfGoodFragment selfGoodFragment;
    public OnPageChange onPageChange;

    public GetPagerAdapter(FragmentManager fm, List<TBbean> paramList,OnPageChange onPageChange) {
        super(fm);
        List localList;
        this.list = paramList;
        this.onPageChange = onPageChange;
    }

    public GetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        selfGoodFragment = new SelfGoodFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString("TBId", ((TBbean) this.list.get(position)).getCate_name());
        selfGoodFragment.setArguments(localBundle);
        Log.v("TAG","getrrecords");
        onPageChange.getChange(selfGoodFragment,(list.get(position)).getCate_name());
        return selfGoodFragment;
    }

    public void setPage(int page){
        if (selfGoodFragment != null){
            selfGoodFragment.onPageChange(page);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public interface OnPageChange{
        void getChange(BaseFragment baseFragment, String str);
    }
}

