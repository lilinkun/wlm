package com.wlm.wlm.manager;

import android.content.Context;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.ArticleDetailBean;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.BalanceDetailBean;
import com.wlm.wlm.entity.BankBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.entity.ErrorBean;
import com.wlm.wlm.entity.FansBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsCartbean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.GrouponDetailBean;
import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.JdGoodsBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.OpinionBean;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.OrderDetailBean;
import com.wlm.wlm.entity.OrderListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.entity.ProvinceBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.entity.SelfOrderInfoBean;
import com.wlm.wlm.entity.SelfStoreBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbjsonBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.entity.UserBankBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.http.RetrofitHelper;
import com.wlm.wlm.http.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;

/**
 * Created by LG on 2018/11/27.
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object>> tbList(HashMap<String,String> hashMap){
        return mRetrofitService.tbApi(hashMap);
    }

    public Observable<ResultBean<UrlBean, Object>> getUrl(HashMap<String,String> hashMap){
        return mRetrofitService.getUrl(hashMap);
    }

    /**
     * 修改支付密码
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean> modifyPsd(HashMap<String, String> mHashMap){
        return mRetrofitService.modifyPsd(mHashMap);
    }

    /**
     * 发送验证码
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean> register(HashMap<String, String> mHashMap){
        return mRetrofitService.register(mHashMap);
    }

    /**
     * 登陆
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean<LoginBean,Object>> login(HashMap<String, String> mHashMap){
        return mRetrofitService.login(mHashMap);
    }

    /**
     * 是否注册
     * @param mHashMap
     */
    public Observable<ResultBean<Boolean,Object>> isRegister(HashMap<String, String> mHashMap){
        return mRetrofitService.isRegister(mHashMap);
    }

 /**
     * 登陆
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean<String,Object>> loginout(HashMap<String, String> mHashMap){
        return mRetrofitService.loginout(mHashMap);
    }


    public Observable<ResultBean<DownloadBean,Object>> update(HashMap<String,String> mHashMap){
        return mRetrofitService.update(mHashMap);
    }

    /**
     * 意见反馈
     */
    public Observable<ResultBean> opinion(HashMap<String, String> mHashMap){
        return mRetrofitService.opinion(mHashMap);
    }

    /**
     * 意见历史反馈
     */
    public Observable<ResultBean<ArrayList<OpinionBean> ,Object>> opinionHistory(HashMap<String, String> mHashMap){
        return mRetrofitService.opinionHistory(mHashMap);
    }

    /**
     * 反馈类型
     */
    public Observable<ResultBean<ArrayList<ErrorBean>,Object>> getErrorType(HashMap<String, String> mHashMap){
        return mRetrofitService.getErrorType(mHashMap);
    }

    /**
     * 获取个人信息
     */
    public Observable<ResultBean<PersonalInfoBean,Object>> getInfo(HashMap<String, String> mHashMap){
        return mRetrofitService.getInfo(mHashMap);
    }

    /**
     * 获取个人信息
     */
    public Observable<ResultBean<ArrayList<GrouponListBean>,Object>> getMyGrouponData(HashMap<String, String> mHashMap){
        return mRetrofitService.getMyGrouponData(mHashMap);
    }

    /**
     * 上传头像
     */
    public Observable<ResultBean<CollectDeleteBean,Object>> getHeadInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.uploadImage(mHashMap);
    }

    /**
     * 上传头像
     */
    public Observable<ResultBean> getCollect(HashMap<String,String> mHashMap ){
        return mRetrofitService.getCollect(mHashMap);
    }

    public Observable<ResultBean> getNewUrl(HashMap<String,String> mHashMap ){
        return mRetrofitService.getNewUrl(mHashMap);
    }


    /**
     * 扣除积分
     *
     */
    public Observable<ResultBean<CollectDeleteBean,Object>> getData(HashMap<String,String> mHashMap ){
        return mRetrofitService.getData(mHashMap);
    }
    /**
     * 是否扣除积分
     *
     */
    public Observable<ResultBean<String,Object>> isExChange(HashMap<String,String> mHashMap){
        return mRetrofitService.isExChange(mHashMap);
    }

    /**
     * 获取flash
     */
    public Observable<ResultBean<ArrayList<FlashBean>,Object>> getFlash(HashMap<String,String> mHashMap){
        return mRetrofitService.getFlash(mHashMap);
    }

    /**
     * 获取种类
     */
    public Observable<ResultBean<ArrayList<CategoryListBean<ArrayList<Object>>>,Object>> getCategory(HashMap<String,String> mHashMap){
        return mRetrofitService.getCategory(mHashMap);
    }

    /**
     * 获取自营商品详情
     */
    public Observable<ResultBean<GoodsDetailBean<ArrayList>,Object>> getSelfGoodDetail(HashMap<String,String> mHashMap){
        return mRetrofitService.getSelfGoodDetail(mHashMap);
    }

    /**
     * 获取商品详情
     */
    public Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>,Object>> getSelfGoodDetailInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getSelfGoodDetailInfo(mHashMap);
    }

    /**
     * 获取团购商品详情
     */
    public Observable<ResultBean<GrouponDetailBean,Object>> getGrouponDetailInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getGrouponDetailInfo(mHashMap);
    }

    /**
     * 增加自营商品收藏
     */
    public Observable<ResultBean<String,Object>> addGoodCollect(HashMap<String,String> mHashMap){
        return mRetrofitService.addGoodCollect(mHashMap);
    }

    /**
     * 是否是自营商品收藏商品
     */
    public Observable<ResultBean<String,Object>> isGoodCollect(HashMap<String,String> mHashMap){
        return mRetrofitService.isGoodCollect(mHashMap);
    }

    /**
     * 抢购
     */
    public Observable<ResultBean<ArrayList<RushBuyBean>,Object>> rushBuy(HashMap<String,String> mHashMap){
        return mRetrofitService.rushBuy(mHashMap);
    }


    /**
     * 获取自营商品收藏列表
     */
    public Observable<ResultBean<ArrayList<CollectBean>,PageBean>> GoodCollectList(HashMap<String,String> mHashMap){
        return mRetrofitService.GoodCollectList(mHashMap);
    }
    /**
     * 根据用户名获取信息
     */
    public Observable<ResultBean> getMobile(HashMap<String,String> mHashMap){
        return mRetrofitService.getMobile(mHashMap);
    }

    /**
     * 删除自营商品收藏列表
     */
    public Observable<ResultBean<String,Object>> DeleteCollectGood(HashMap<String,String> mHashMap){
        return mRetrofitService.DeleteCollectGood(mHashMap);
    }

    /**
     * 获取自营商品列表
     */
    public Observable<ResultBean<ArrayList<SelfGoodsBean>,PageBean>> getselfGoodList(HashMap<String,String> mHashMap){
        return mRetrofitService.getselfGoodList(mHashMap);
    }

    /**
     * 搜索推荐
     */
    public Observable<ResultBean<ArrayList<String>,Object>> getselfSearch(HashMap<String,String> mHashMap){
        return mRetrofitService.getselfSearch(mHashMap);
    }

    /**
     * 获取店铺列表
     */
    public Observable<ResultBean<ArrayList<SelfStoreBean>,Object>> getSelfStoreGoodsList(HashMap<String,String> mHashMap){
        return mRetrofitService.getSelfStoreGoodsList(mHashMap);
    }

    /**
     * 判断是否有地址
     */
    public Observable<ResultBean<ArrayList<AddressBean>,Object>> getIsAddress(HashMap<String,String> mHashMap){
        return mRetrofitService.getIsAddress(mHashMap);
    }


    /**
     * 获取订单列表
     */
    public Observable<ResultBean<ArrayList<SelfOrderBean>,Object>> getSelfOrderList(HashMap<String,String> mHashMap){
        return mRetrofitService.getSelfOrderList(mHashMap);
    }

    /**
     * 删除订单
     */
    public Observable<ResultBean<String,Object>> exitOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.exitOrder(mHashMap);
    }


    /**
     * 增加购物车
     */
    public Observable<ResultBean<String,Object>> addCartAdd(HashMap<String,String> mHashMap){
        return mRetrofitService.addCartAdd(mHashMap);
    }

    /**
     * 获取收货地址
     */
    public Observable<ResultBean<ArrayList<AddressBean>,Object>> getConsigneeAddress(HashMap<String,String> mHashMap){
        return mRetrofitService.getConsigneeAddress(mHashMap);
    }

    /**
     * 获取银行信息
     */
    public Observable<ResultBean<ArrayList<BankBean>,Object>> getBankInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getBankInfo(mHashMap);
    }

    /**
     * 提交银行信息
     */
    public Observable<ResultBean<String,Object>> upBankInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.upBankInfo(mHashMap);
    }

    /**
     * 获取收货区域
     */
    public Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(HashMap<String,String> mHashMap){
        return mRetrofitService.getLocalData(mHashMap);
    }

    /**
     * 增加收货地址
     */
    public Observable<ResultBean> getSaveAddress(HashMap<String,String> mHashMap){
        return mRetrofitService.getSaveAddress(mHashMap);
    }

    /**
     * 增加收货地址
     */
    public Observable<ResultBean> getDeleteAddress(HashMap<String,String> mHashMap){
        return mRetrofitService.getDeleteAddress(mHashMap);
    }


    /**
     *　设置默认地址
     */
    public Observable<ResultBean<String,Object>> isDefault(HashMap<String,String> mHashMap){
        return mRetrofitService.isDefault(mHashMap);
    }

    /**
     * 获取购物车列表
     */
    public Observable<ResultBean<GoodsCartbean,Object>> getCartList(HashMap<String,String> mHashMap){
        return mRetrofitService.getCartList(mHashMap);
    }

    /**
     * 获取订单详情
     */
    public Observable<ResultBean<OrderDetailAddressBean,Object>> getOrderDetail(HashMap<String,String> mHashMap){
        return mRetrofitService.getOrderDetail(mHashMap);
    }

    /**
     * 获取购物积分
     */
    public Observable<ResultBean<IntegralBean,Object>> getShoppingPrice(HashMap<String,String> mHashMap){
        return mRetrofitService.getShoppingPrice(mHashMap);
    }

    /**
     * 获取京东商品
     */
    public Observable<ResultBean<ArrayList<JdGoodsBean>,Object>> getJdGoods(HashMap<String,String> mHashMap){
        return mRetrofitService.getJdGoods(mHashMap);
    }

    /**
     * 获取购物金额
     */
    public Observable<ResultBean<ArrayList<BalanceDetailBean>,Object>> getAmountPrice(HashMap<String,String> mHashMap){
        return mRetrofitService.getAmountPrice(mHashMap);
    }

    /**
     * 获取银行卡信息
     */
    public Observable<ResultBean<UserBankBean,Object>> getBankBean(HashMap<String,String> mHashMap){
        return mRetrofitService.getBankBean(mHashMap);
    }

    /**
     * 删除购物车
     */
    public Observable<ResultBean<String ,Object>> deleteGoods(HashMap<String,String> mHashMap){
        return mRetrofitService.deleteGoods(mHashMap);
    }

    /**
     * 获取帐户余额
     */
    public Observable<ResultBean<CountBean,Object>> getAccountInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getAccountInfo(mHashMap);
    }

    /**
     * 获取购物车信息
     */
    public Observable<ResultBean<CollectDeleteBean ,Object>> modifyOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.modifyOrder(mHashMap);
    }

    /**
     * 立即购买
     */
    public Observable<ResultBean<RightNowBuyBean<CartBuyBean>,Object>> rightNowBuy(HashMap<String,String> mHashMap){
        return mRetrofitService.rightNowBuy(mHashMap);
    }

    /**
     * 获取单个邮费
     */
    public Observable<ResultBean<FareBean,Object>> getfare(HashMap<String,String> mHashMap){
        return mRetrofitService.getfare(mHashMap);
    }

    /**
     * 获取多个邮费
     */
    public Observable<ResultBean<RightNowBuyBean,Object>> getfares(HashMap<String,String> mHashMap){
        return mRetrofitService.getfares(mHashMap);
    }

    /**
     * 确认订单
     */
    public Observable<ResultBean<String,Object>> sureOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.sureOrder(mHashMap);
    }

    /**
     * 余额支付
     */
    public Observable<ResultBean<String,Object>> selfPay(HashMap<String,String> mHashMap){
        return mRetrofitService.selfPay(mHashMap);
    }
    /**
     * 微信支付
     */
    public Observable<ResultBean<WxInfo,Object>> wxPay(HashMap<String,String> mHashMap){
        return mRetrofitService.wxPay(mHashMap);
    }

    /**
     * 确认收货
     */
    public Observable<ResultBean<String,Object>> sureReceipt(HashMap<String,String> mHashMap){
        return mRetrofitService.sureReceipt(mHashMap);
    }

    /**
     *删除订单
     */
    public Observable<ResultBean<String,Object>> deleteOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.deleteOrder(mHashMap);
    }

    /**
     * 获取团购数据
     */
    public Observable<ResultBean<ArrayList<GoodsListBean>,PageBean>> grouponData(HashMap<String,String> mHashMap){
        return  mRetrofitService.grouponData(mHashMap);
    }

    /**
     * 获取团购订单数据
     */
    public Observable<ResultBean<ArrayList<GoodsListBean>,Object>> grouponOrder(HashMap<String,String> mHashMap){
        return  mRetrofitService.grouponData1(mHashMap);
    }

    /**
     * 获取团购商品详情
     */
    public Observable<ResultBean<GoodsListBean,Object>> getGrouponGoodDetail(HashMap<String,String> mHashMap){
        return mRetrofitService.getGrouponGoodDetail(mHashMap);
    }


    /**
     * 团购立即购买
     */
    public Observable<ResultBean<String,Object>> grouponRightNowBuy(HashMap<String,String> mHashMap){
        return mRetrofitService.grouponRightNowBuy(mHashMap);
    }

    /**
     * 单个商品返回订单信息
     */
    public Observable<ResultBean<RightNowBuyBean<RightNowGoodsBean>,Object>> getGoodsOrderInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getGoodsOrderInfo(mHashMap);
    }


    /**
     * 支付微信订单信息
     */
    public Observable<ResultBean<WxInfo,Object>> sureWxGoodsOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.sureWxGoodsOrder(mHashMap);
    }

    /**
     * 支付订单信息
     */
    public Observable<ResultBean<String,Object>> sureGoodsOrder(HashMap<String,String> mHashMap){
        return mRetrofitService.sureGoodsOrder(mHashMap);
    }


    /**
     * 购物车下单
     */
    public Observable<ResultBean<String,Object>> getOrderInfo(HashMap<String,String> mHashMap){
        return mRetrofitService.getOrderInfo(mHashMap);
    }

    /**
     * 获取商城类别
     */
    public Observable<ResultBean<ArrayList<Category1Bean>,Object>> getCategoryList(HashMap<String,String> mHashMap){
        return mRetrofitService.getCategoryList(mHashMap);
    }

    /**
     * 获取余额
     */
    public Observable<ResultBean<BalanceBean,Object>> getBalance(HashMap<String,String> mHashMap){
        return mRetrofitService.getBalance(mHashMap);
    }

    /**
     * 获取粉丝数据
     */
    public Observable<ResultBean<ArrayList<FansBean>,PageBean>> getFansData(HashMap<String,String> mHashMap){
        return  mRetrofitService.getFansData(mHashMap);
    }

    /**
     * 获取热门推荐商品
     */
    public Observable<ResultBean<ArrayList<GoodsListBean>,Object>> getGoodsList(HashMap<String,String> mHashMap){
        return  mRetrofitService.getGoodsList(mHashMap);
    }


    /**
     * 获取提现支付
     */
    public Observable<ResultBean<String,Object>> getCash(HashMap<String,String> mHashMap){
        return mRetrofitService.getCash(mHashMap);
    }

    /**
     * 获取消息
     */
    public Observable<ResultBean<ArrayList<ArticleBean>,PageBean>> getArticleList(HashMap<String,String> mHashMap){
        return mRetrofitService.getArticleList(mHashMap);
    }

    /**
     * 获取消息详情
     */
    public Observable<ResultBean<ArticleDetailBean,Object>> getArticleDetail(HashMap<String,String> mHashMap){
        return mRetrofitService.getArticleDetail(mHashMap);
    }

    /**
     * 获取更新数据
     */
    public Observable<ResultBean<LoginBean,Object>> getUpdataData(HashMap<String,String> mHashMap){
        return mRetrofitService.getUpdataData(mHashMap);
    }

}
