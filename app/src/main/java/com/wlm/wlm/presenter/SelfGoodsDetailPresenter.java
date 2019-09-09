package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.SelfGoodsDetailContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/8.
 */

public class SelfGoodsDetailPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SelfGoodsDetailContract selfGoodsDetailContract;


    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        selfGoodsDetailContract = (SelfGoodsDetailContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void getGoodsDetail(String goodsId,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsGet");
        params.put("GoodsId",goodsId);
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getSelfGoodDetailInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, Object>() {

                    @Override
                    public void onResponse(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> objectObjectGoodsDetailBean, String status,Object page) {
                        selfGoodsDetailContract.getDataSuccess(objectObjectGoodsDetailBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.getDataFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }



    public void onCollect(String goodsId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Collect");
        params.put("fun","CollectCreate");
        params.put("goodsId",goodsId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.addGoodCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectBean, Object>() {

                    @Override
                    public void onResponse(CollectBean collectBean, String status,Object page) {
                        selfGoodsDetailContract.addCollectSuccess(collectBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.addCollectFail(msg);
                    }
                }));
    }

    /**
     * 是否有收藏
     * @param goodsId
     * @param SessionId
     */
    public void isCollect(String goodsId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Collect");
        params.put("fun","Is_Collect");
        params.put("goodsId",goodsId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.isGoodCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String collectBean, String status,Object page) {
//                        selfGoodsDetailContract.addCollectSuccess(collectBean);
                        selfGoodsDetailContract.isGoodsCollectSuccess(collectBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
//                        selfGoodsDetailContract.addCollectFail(msg);
                    }

                }));
    }

    /**
     * 删除收藏
     * @param collectId
     * @param SessionId
     */
    public void deleteCollect(String collectId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Collect");
        params.put("fun","ProjectCheckDelete");
        params.put("collectId",collectId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.DeleteCollectGood(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean collectBeans, String status,Object page) {
                        if (collectBeans.getStatus() == 0) {
                            selfGoodsDetailContract.deleteCollectSuccess(collectBeans.getMessage());
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }
                }));
    }


    /**
     * 加入购物车
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param SessionId
     */
    public void addCartAdd(String GoodsId,String AttrId,String Num,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","加入购物车...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Cart");
        params.put("fun","CartCreate");
        params.put("GoodsId",GoodsId);
        params.put("attr_id",AttrId);
        params.put("Num",Num);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.addCartAdd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean collectBeans, String status,Object page) {
                        if (collectBeans.getStatus() == 0) {
                            selfGoodsDetailContract.addCartSuccess(collectBeans.getMessage());
                        }else {
                            selfGoodsDetailContract.addCartFail("加入购物车失败"+collectBeans.getMessage());
                        }
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.addCartFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 随机商品
     * @param GoodsId
     * @param SessionId
     */
    public void randomGoods(String GoodsId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","RandomGoodsList");
        params.put("GoodsId",GoodsId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getselfGoodList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<SelfGoodsBean>,PageBean>() {
                    @Override
                    public void onResponse(ArrayList<SelfGoodsBean> selfGoodsBeans, String status,PageBean page) {
                        selfGoodsDetailContract.getCommendGoodsSuccess(selfGoodsBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.getCommendGoodsFail(msg);
                    }
                }));
    }

    /**
     * 立即购买
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param SessionId
     */
    public void rightNowBuy(String GoodsId,String AttrId,String Num,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","Buy");
        params.put("GoodsId",GoodsId);
        params.put("AttrId",AttrId);
        params.put("Num",Num);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.rightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BuyBean,Object>() {
                    @Override
                    public void onResponse(BuyBean rightNows, String status,Object page) {
                        selfGoodsDetailContract.getRightNowBuySuccess(rightNows);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.getRightNowBuyFail(msg);
                    }
                }));
    }

    /**
     * 判断是否有地址
     * @param SessionId
     */
    public void isUserAddress(String PageIndex,String PageCount,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","ReceiptAddress");
        params.put("fun","ReceiptAddressList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getConsigneeAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<AddressBean> addressBeans, String status,Object page) {

                        selfGoodsDetailContract.isAddressSuccess(addressBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsDetailContract.isAddressFail(msg);
                    }
                }));
    }

}
