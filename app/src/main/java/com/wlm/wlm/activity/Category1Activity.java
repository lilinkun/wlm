package com.wlm.wlm.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MenuItemAdapter;
import com.wlm.wlm.adapter.MenuLeftAdapter;
import com.wlm.wlm.adapter.MenuRightAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.CategoryContract;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.entity.HomeCategoryBean;
import com.wlm.wlm.presenter.CategoryPresenter;
import com.wlm.wlm.ui.GridViewForScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/16.
 */

public class Category1Activity extends BaseActivity implements CategoryContract, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_menu)
    ListView lv_menu;
    @BindView(R.id.gridView)
    GridViewForScrollView gridView;

    private CategoryPresenter categoryPresenter = new CategoryPresenter();
    private ArrayList<CategoryListBean<ArrayList<Object>>> homeCategoryBeans;
    private MenuItemAdapter adapter;
    private ArrayList<HomeCategoryBean> homeCategorys = new ArrayList<>();
    private MenuLeftAdapter menuLeftAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_category1;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        categoryPresenter.onCreate(this,this);

        categoryPresenter.getCategoryList(ProApplication.SESSIONID(this));

    }

    @Override
    public void getDataSuccess(ArrayList<CategoryListBean<ArrayList<Object>>> homeCategoryBeans) {
        this.homeCategoryBeans = homeCategoryBeans;
        menuLeftAdapter = new MenuLeftAdapter(this, homeCategoryBeans);
        lv_menu.setAdapter(menuLeftAdapter);
        lv_menu.setOnItemClickListener(this);

        setGridview(0);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @OnClick({R.id.text_search, R.id.lin_list})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.text_search:
                    UiHelper.launcher(this, SearchActivity.class);
                    break;

                case R.id.lin_list:

                    finish();

                    break;
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        if (!ButtonUtils.isFastDoubleClick()) {
            menuLeftAdapter.setSelectItem(position);
            menuLeftAdapter.notifyDataSetChanged();
            homeCategorys.clear();
            setGridview(position);
//        }
    }

    private void setGridview(int position) {
        if (homeCategoryBeans.get(position).getSubclass().size() > 0) {
            ArrayList objects = (ArrayList) homeCategoryBeans.get(position).getSubclass();
            Gson gson = new Gson();
            for (int j = 0; j < objects.size(); j++) {
                String str = gson.toJson(objects.get(j));
                HomeCategoryBean homeCategoryBean = gson.fromJson(str, HomeCategoryBean.class);
                homeCategorys.add(homeCategoryBean);

            }
        }
        if (adapter == null) {
            adapter = new MenuItemAdapter(this, homeCategorys);
            gridView.setAdapter(adapter);
        } else {
            adapter.setData(homeCategorys);
        }
    }
}
