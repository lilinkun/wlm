package com.wlm.wlm.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.entity.TBbean;
import com.wlm.wlm.fragment.TBAllFragment;

import java.util.List;

/**
 * Created by LG on 2018/11/14.
 */

public class GetRecordsPagerAdapter extends FragmentStatePagerAdapter {

    List<TBbean> list;
    private TBAllFragment tbAllFragment;
    public OnPageChange onPageChange;
    private boolean isSearch = false;
    private String searchStr = "";

    public GetRecordsPagerAdapter(FragmentManager fm, List<TBbean> paramList, OnPageChange onPageChange) {
        super(fm);
        List localList;
        this.list = paramList;
        this.onPageChange = onPageChange;
    }

    public GetRecordsPagerAdapter(FragmentManager fm, List<TBbean> paramList, boolean isSearch, String searchStr, OnPageChange onPageChange) {
        super(fm);
        List localList;
        this.list = paramList;
        this.isSearch = isSearch;
        this.searchStr = searchStr;
        this.onPageChange = onPageChange;
    }


    public GetRecordsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        tbAllFragment = new TBAllFragment();
        Bundle localBundle = new Bundle();
        if (!isSearch) {
            localBundle.putString("TBId", ((TBbean) this.list.get(position)).getCate_name());
            tbAllFragment.setArguments(localBundle);
            Log.v("TAG", "getrrecords");
            onPageChange.getChange(tbAllFragment, (list.get(position)).getCate_name());
        } else {
            localBundle.putString("TBId", searchStr);
            onPageChange.getChange(tbAllFragment, searchStr);
            tbAllFragment.setArguments(localBundle);
        }
        return tbAllFragment;
    }

    public void setPage(int page) {
        if (tbAllFragment != null) {
            tbAllFragment.onPageChange(page);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public interface OnPageChange {
        void getChange(BaseFragment baseFragment, String str);
    }
}
