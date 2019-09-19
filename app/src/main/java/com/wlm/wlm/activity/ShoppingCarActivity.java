package com.wlm.wlm.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyShoppingCarAdapter;
import com.wlm.wlm.adapter.ShoppingCarAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.OrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.GoodsCartbean;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderChildBean;
import com.wlm.wlm.entity.OrderGroupBean;
import com.wlm.wlm.entity.OrderListBean;
import com.wlm.wlm.presenter.OrderPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.UtilTool;
import com.wlm.wlm.util.UtilsLog;
import com.wlm.wlm.util.WlmUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Created by LG on 2018/12/4.
 */

public class ShoppingCarActivity extends BaseActivity implements View.OnClickListener, OrderContract, ShoppingCarAdapter.ModifyCountInterface, ShoppingCarAdapter.CheckInterface {

    @BindView(R.id.ry_goods_cart)
    RecyclerView ry_goods_cart;
    @BindView(R.id.all_checkBox)
    CheckBox all_checkBox;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.go_pay)
    TextView goPay;
    @BindView(R.id.order_info)
    LinearLayout orderInfo;
    @BindView(R.id.share_info)
    LinearLayout shareInfo;
    @BindView(R.id.mPtrframe)
    PtrFrameLayout mPtrFrame;
    @BindView(R.id.rl_cart_bottom)
    RelativeLayout rl_cart_bottom;
    @BindView(R.id.ll_no_goods)
    LinearLayout linearLayout;
    @BindView(R.id.go_shopping)
    TextView tv_no_shopping;
    @BindView(R.id.tv_cart_num)
    TextView tv_cart_num;
    @BindView(R.id.tv_cart_edit)
    TextView tv_cart_edit;
    @BindView(R.id.ll_cart)
    LinearLayout ll_cart;
    @BindView(R.id.ll_title_back)
    LinearLayout ll_title_back;


    private OrderPresenter orderPresenter = new OrderPresenter();
    private ShoppingCarAdapter myShoppingCarAdapter;
    private ArrayList<OrderBean> orderListBeans;
    private Map<String,OrderChildBean> map = new HashMap<>();
    private double mtotalPrice = 0.00;
    private int mtotalCount = 0;
    //false就是编辑，ture就是完成
    private boolean flag = false;
    private int order_result = 0x9123;
    private int goods_result = 0x02231;
    private ArrayList<OrderChildBean> orderChildBeans = null;

    private OrderBean orderBean;


    @Override
    public int getLayoutId()  {
        return R.layout.activity_shoppingcar;
    }

    @Override
    public void initEventAndData() {
//        Eyes.setStatusBarWhiteColor(getActivity(),getResources().getColor(R.color.white));
        Eyes.translucentStatusBar(this);
        orderPresenter.onCreate(this,this);
        orderPresenter.getList(ProApplication.SESSIONID(this));

        initPtrFrame();
    }

    private void initPtrFrame() {
//        final StoreHouseHeader header=new StoreHouseHeader(this);
//        header.setPadding(dp2px(20), dp2px(20), 0, 0);
//        header.initWithString("xiaoma is good");
        final PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(this);
        header.setPadding(dp2px(20), dp2px(20), 0, 0);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                orderPresenter.getList(ProApplication.SESSIONID(ShoppingCarActivity.this));
                updataData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        ry_goods_cart.setLayoutManager(linearLayoutManager);
        ry_goods_cart.addItemDecoration(new SpaceItemDecoration(0,20,0));
    }

    public void setData(){
        orderPresenter.getList(ProApplication.SESSIONID(this));
    }

    @OnClick({R.id.all_checkBox,R.id.del_goods,R.id.tv_cart_edit,R.id.go_pay,R.id.go_shopping,R.id.ll_title_back})
    public void onClick(View view){
        AlertDialog dialog;
        if (!ButtonUtils.isFastDoubleClick(view.getId()) && view.getId() != R.id.all_checkBox) {
            switch (view.getId()) {
                case R.id.all_checkBox:
                    doCheckAll();
                    break;

                case R.id.tv_cart_edit:
                    flag = !flag;
                    setVisiable();
                    break;

                case R.id.del_goods:
                    if (mtotalCount == 0) {
                        UtilTool.toast(this, "请选择要删除的商品");
                        return;
                    }
                    dialog = new AlertDialog.Builder(this).create();
                    dialog.setMessage("确认要删除该商品吗?");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doDelete();
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    dialog.show();
                    break;

                case R.id.go_pay:
                    if (mtotalCount == 0) {
                        UtilTool.toast(this, "请选择要支付的商品");
                        return;
                    }

                    orderPresenter.getOrderInfo(getOrderList(),ProApplication.SESSIONID(this));

//                    orderPresenter.isUserAddress(ProApplication.SESSIONID(this));
                    break;
                case R.id.go_shopping:

                    Bundle bundle = new Bundle();
                    bundle.putString("goodsname", "");
                    UiHelper.launcherForResultBundle(this, ManufactureStoreActivity.class, goods_result, bundle);

                    break;

                case R.id.ll_title_back:

                    finish();

                    break;
            }
        }else {
            if (view.getId() == R.id.all_checkBox){
                doCheckAll();
            }
        }
    }


    private void setVisiable() {
        if (flag) {
            orderInfo.setVisibility(View.GONE);
            shareInfo.setVisibility(View.VISIBLE);
            tv_cart_edit.setText("完成");
            goPay.setVisibility(View.GONE);
        } else {
            orderInfo.setVisibility(View.VISIBLE);
            shareInfo.setVisibility(View.GONE);
            goPay.setVisibility(View.VISIBLE);
            tv_cart_edit.setText("编辑");
        }
    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderChildBean child = map.get(orderListBeans.get(i).getCartId());
            child.setChoosed(all_checkBox.isChecked());//这里出现过错误
        }
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    private void updataData(){
        if (orderListBeans != null && orderListBeans.size() > 0) {
            /*for (int i = 0; i < orderListBeans.size(); i++) {
                OrderGroupBean group = orderListBeans.get(i);
                group.setChoosed(false);
                List<OrderChildBean> child = map.get(group.getOrderListBean().getStore_id());
                for (int j = 0; j < child.size(); j++) {
                    child.get(j).setChoosed(false);//这里出现过错误
                }
            }*/
            myShoppingCarAdapter.notifyDataSetChanged();
            calulate();
            all_checkBox.setChecked(false);
        }
        mPtrFrame.refreshComplete();
    }

    @Override
    public void OrderListSuccess(GoodsCartbean goodsCartbean) {

        orderListBeans = (ArrayList<OrderBean>)goodsCartbean.getList();

        if (orderListBeans.size() > 0) {
            linearLayout.setVisibility(View.GONE);
            rl_cart_bottom.setVisibility(View.VISIBLE);
            ll_cart.setVisibility(View.VISIBLE);

            orderChildBeans = new ArrayList<>();
            for (OrderBean orderBean : orderListBeans){

                OrderChildBean orderChildBean = new OrderChildBean();
                orderChildBean.setOrderBean(orderBean);
                orderChildBean.setChoosed(false);
                orderChildBean.setParentId(orderBean.getCartId());
                orderChildBeans.add(orderChildBean);
                map.put(orderBean.getCartId(),orderChildBean);
            }


            myShoppingCarAdapter = new ShoppingCarAdapter(this,orderListBeans,map);
            ry_goods_cart.setAdapter(myShoppingCarAdapter);
            myShoppingCarAdapter.setCheckInterface(this);
            myShoppingCarAdapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
            myShoppingCarAdapter.setItemClickListener(new ShoppingCarAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String type = WlmUtil.MANUFACURE;
                    if (orderListBeans.get(position).getGoodsType() == 1){
                        type = WlmUtil.INTEGRAL;
                    }else if (orderListBeans.get(position).getGoodsType() == 2){
                        type = WlmUtil.MANUFACURE;
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString(WlmUtil.GOODSID,orderListBeans.get(position).getGoodsId());
                    bundle.putString(WlmUtil.TYPE,type);
                    UiHelper.launcherBundle(ShoppingCarActivity.this, SelfGoodsDetailActivity.class,bundle);
                }
            });
//            ry_goods_cart.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    int firstVisiablePostion=view.getFirstVisiblePosition();
//                    int top=-1;
//                    View firstView=view.getChildAt(firstVisibleItem);
//                    if(firstView!=null){
//                        top=firstView.getTop();
//                    }
//                    UtilsLog.i("firstVisiableItem="+firstVisibleItem+",fistVisiablePosition="+firstVisiablePostion+",firstView="+firstView+",top="+top);
//                    if(firstVisibleItem==0&&top==0){
//                        mPtrFrame.setEnabled(true);
//                    }else{
//                        mPtrFrame.setEnabled(false);
//                    }
//                }
//            });
        }else {
            linearLayout.setVisibility(View.VISIBLE);
            rl_cart_bottom.setVisibility(View.GONE);
            ll_cart.setVisibility(View.GONE);
        }
    }

    @Override
    public void OrderListFail(String msg) {

    }

    @Override
    public void modifyOrderSuccess(CollectDeleteBean collectDeleteBean, String num, View showCountView) {
        if (collectDeleteBean.getStatus() != 0){
            UToast.show(this,collectDeleteBean.getMessage());
        }else {
            orderBean.setNum(Integer.valueOf(num));
            ((TextView) showCountView).setText(String.valueOf(num));
            myShoppingCarAdapter.notifyDataSetChanged();
            calulate();
        }
    }

    @Override
    public void modifyOrderFail(String msg) {

    }

    @Override
    public void deleteGoodsSuccess(String collectDeleteBean) {
        UToast.show(this,"删除成功");
        calulate();
        all_checkBox.setChecked(false);

    }

    @Override
    public void deleteGoodsFail(String msg) {
        UToast.show(this,"删除失败");
    }

    @Override
    public void cartOrderBuySuccess(String str) {

        Bundle bundle = new Bundle();
        bundle.putInt("type",1);
        bundle.putString("CartId",getOrderList());
        UiHelper.launcherForResultBundle(this, CartOrderActivity.class,order_result,bundle);
    }

    @Override
    public void cartOrderBuyFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void isAddressSuccess(ArrayList<AddressBean> msg) {

        Bundle bundle = new Bundle();
        bundle.putInt("type",1);
        bundle.putString("CartId",getOrderList());
        UiHelper.launcherForResultBundle(this, CartOrderActivity.class,order_result,bundle);
    }

    @Override
    public void isAddressFail(String msg) {

        new android.app.AlertDialog.Builder(this).setMessage("您还没有收货地址，请填写").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UiHelper.launcher(ShoppingCarActivity.this, ChooseAddressActivity.class);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        orderBean = orderListBeans.get(position);
        int count = Integer.valueOf(orderBean.getNum());
        count++;
        orderPresenter.modifyOrder(count+"",orderBean.getCartId(),showCountView,ProApplication.SESSIONID(this));
        orderBean.setNum(count);
        ((TextView) showCountView).setText(String.valueOf(count));
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        orderBean = orderListBeans.get(position);
        int count = Integer.valueOf(orderBean.getNum());
        if (count == 1) {
            return;
        }
        count--;
        orderPresenter.modifyOrder(count+"",orderBean.getCartId(),showCountView,ProApplication.SESSIONID(this));
        orderBean.setNum(count);
        ((TextView) showCountView).setText("" + count);
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void doUpdate(int position, View showCountView, boolean isChecked) {
//        orderBean = (OrderChildBean) myShoppingCarAdapter.getChild(groupPosition, childPosition);
        int count = Integer.valueOf(orderBean.getNum());
        orderPresenter.modifyOrder(count + "", orderBean.getCartId(),showCountView, ProApplication.SESSIONID(this));
//        UtilsLog.i("进行更新数据，数量" + count + "");
//        ((TextView) showCountView).setText(String.valueOf(count));
//        myShoppingCarAdapter.notifyDataSetChanged();
//        calulate();
    }

    /**
     * @param childPosition 思路:当子元素=0，那么组元素也要删除
     */
    @Override
    public void childDelete(int childPosition) {
        /*OrderGroupBean group = orderListBeans.get(groupPosition);
        List<OrderChildBean> child = map.get(group.getOrderListBean().getStore_id());
        child.remove(childPosition);
        if (child.size() == 0) {
            orderListBeans.remove(groupPosition);
        }*/
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void sumGoodsNum(int num) {
        tv_cart_num.setText("总共"+num+"件宝贝");
    }

    public String getOrderList(){
        String OrderStr = "";
        for (int i = 0; i < orderListBeans.size(); i++) {

            OrderChildBean child = map.get(orderListBeans.get(i).getCartId());
            if (child.isChoosed()){
                if (OrderStr.equals("")){
                    OrderStr = orderListBeans.get(i).getCartId();
                }else {
                    OrderStr = OrderStr + "," + orderListBeans.get(i).getCartId();
                }
            }
        }
        return OrderStr;
    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        String deleteStr = "";
        List<OrderBean> toBeDeleteChilds = new ArrayList<OrderBean>();//待删除的子元素
        for (int i = 0; i < orderChildBeans.size(); i++) {
            OrderBean orderBean = orderListBeans.get(i);

            if (map.get(orderBean.getCartId()).isChoosed()){
                toBeDeleteChilds.add(map.get(orderBean.getCartId()).getOrderBean());
                if (deleteStr.equals("")){
                    deleteStr = orderBean.getCartId();
                }else {
                    deleteStr = deleteStr + "," + orderBean.getCartId();
                }
            }

        }
        orderListBeans.removeAll(toBeDeleteChilds);
        //重新设置购物车
        setCartNum();
        myShoppingCarAdapter.notifyDataSetChanged();

        orderPresenter.deleteOrder(deleteStr,ProApplication.SESSIONID(this));
    }

    /**
     * 设置购物车的数量
     */
    private void setCartNum() {
        int count = 0;
        /*for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            group.setChoosed(all_checkBox.isChecked());
            List<OrderChildBean> child = map.get(group.getOrderListBean().getStore_id());
            for (OrderChildBean childs : child) {
                count++;
            }
        }*/

        //购物车已经清空
        if (count == 0) {
            clearCart();
        } else {
            tv_cart_num.setText("总共" + count + "件宝贝");
        }

    }

    private void clearCart() {
        tv_cart_num.setText("总共0件宝贝");
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {
        mtotalPrice = 0.00;
        mtotalCount = 0;

        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderChildBean good = map.get(orderListBeans.get(i).getCartId());
            if (good.isChoosed()) {
                mtotalCount += Integer.valueOf(good.getOrderBean().getNum());
                mtotalPrice += good.getOrderBean().getPrice() * Integer.valueOf(good.getOrderBean().getNum());
            }
        }

        if (mtotalCount == 0){
            goPay.setText("结算(" + mtotalCount + ")");
        }else {
            goPay.setText("去支付(" + mtotalCount + ")");
        }

        BigDecimal b = new BigDecimal(mtotalPrice);
        mtotalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        total_price.setText("¥" + mtotalPrice + "");

        /*BigDecimal b = new BigDecimal(mtotalPrice);
        mtotalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        total_price.setText("¥" + mtotalPrice + "");
        goPay.setText("去支付(" + mtotalCount + ")");
        if (mtotalCount == 0) {
            setCartNum();
        } else {
            tv_cart_num.setText("总共" + mtotalCount + "件宝贝");
        }

        if (orderListBeans.size() == 0){
            rl_cart_bottom.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == order_result){
                orderPresenter.getList(ProApplication.SESSIONID(this));
            }
            if (requestCode == goods_result){
                orderPresenter.getList(ProApplication.SESSIONID(this));
            }
        }
    }

    @Override
    public void checkChild(int position, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态

        OrderChildBean child = map.get(orderListBeans.get(position).getCartId());
        child.setChoosed(isChecked);

        if (isCheckAll()) {
            all_checkBox.setChecked(true);//全选
        } else {
            all_checkBox.setChecked(false);//反选
        }

        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (OrderBean orderBean : orderListBeans) {

            if (!map.get(orderBean.getCartId()).isChoosed()){
                return false;
            }
        }
        return true;
    }
}