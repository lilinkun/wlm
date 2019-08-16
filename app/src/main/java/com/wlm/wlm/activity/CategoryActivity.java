package com.wlm.wlm.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MenuLeftAdapter;
import com.wlm.wlm.adapter.MenuRightAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.CategoryContract;
import com.wlm.wlm.entity.CategoryBean;
import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.presenter.CategoryPresenter;
import com.wlm.wlm.util.Eyes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/12/7.
 */

public class CategoryActivity extends BaseActivity implements CategoryContract{

    private List<String> menuList = new ArrayList<>();
    private List<CategoryBean.DataBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private ListView lv_menu;
    private ListView lv_home;

    private MenuLeftAdapter menuAdapter;
    private MenuRightAdapter homeAdapter;
    private int currentItem;

    private TextView tv_title;

    private CategoryPresenter categoryPresenter = new CategoryPresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        categoryPresenter.attachView(this);
        categoryPresenter.onCreate(this);

//        categoryPresenter.getCategoryList(ProApplication.SESSIONID(this));

        initView();
        loadData();
    }

    private void loadData() {

        String json = getJson(this, "category.json");
        Gson gson = new Gson();
        CategoryBean categoryBean  = gson.fromJson(json, CategoryBean.class);
//        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
        showTitle = new ArrayList<>();
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            CategoryBean.DataBean dataBean = categoryBean.getData().get(i);
            menuList.add(dataBean.getModuleTitle());
            showTitle.add(i);
            homeList.add(dataBean);
        }
        tv_title.setText(categoryBean.getData().get(0).getModuleTitle());

        menuAdapter.notifyDataSetChanged();
        homeAdapter.notifyDataSetChanged();
    }

    private void initView() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        tv_title = (TextView) findViewById(R.id.tv_titile);
        lv_home = (ListView) findViewById(R.id.lv_home);
//        menuAdapter = new MenuLeftAdapter(this, menuList);
        lv_menu.setAdapter(menuAdapter);

        homeAdapter = new MenuRightAdapter(this, homeList);
        lv_home.setAdapter(homeAdapter);

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                tv_title.setText(menuList.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });


        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });

        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("asdads");
            }
        });
    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }


    @Override
    public void getDataSuccess(ArrayList<CategoryListBean<ArrayList<Object>>> categoryListBeans) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
