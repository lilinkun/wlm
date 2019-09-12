package com.wlm.wlm.http;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.GoodsCartbean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.JdGoodsBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderDetailBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.entity.ProvinceBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.entity.SelfStoreBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbjsonBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxRechangeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by LG on 2018/11/7.
 */

public interface RetrofitService {

//    @FormUrlEncoded
//    @POST("LoginDateServlet")
//    Observable<HashMap<String,String>> setTest(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object>> tbApi(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<UrlBean, Object>> getUrl(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> modifyPsd(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> register(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<LoginBean,Object>> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<Boolean,Object>> isRegister(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> loginout(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<DownloadBean,Object>> update(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> opinion(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<PersonalInfoBean,Object>> getInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getCollect(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getNewUrl(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> getData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> isExChange(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<HomeHeadBean,Object>> getFlash(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CategoryListBean<ArrayList<Object>>>,Object>> getCategory(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsDetailBean<ArrayList>,Object>> getSelfGoodDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>,Object>> getSelfGoodDetailInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectBean,Object>> addGoodCollect(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> isGoodCollect(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<RushBuyBean>,Object>> rushBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CollectBean>,PageBean>> GoodCollectList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getMobile(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<SelfGoodsBean>,PageBean>> getselfGoodList(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<String>,Object>> getselfSearch(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<SelfStoreBean>,Object>> getSelfStoreGoodsList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<AddressBean>,Object>> getIsAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<SelfOrderBean>,Object>> getSelfOrderList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> exitOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> addCartAdd(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<RightNowBuyBean<CartBuyBean>,Object>> rightNowBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<FareBean,Object>> getfare(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<FaresBean>,Object>> getfares(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> DeleteCollectGood(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> sureOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> selfPay(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<WxRechangeBean,Object>> wxPay(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> sureReceipt(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> deleteOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> isDefault(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> modifyOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> deleteGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CountBean,Object>> getAccountInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<AddressBean>,Object>> getConsigneeAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getSaveAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getDeleteAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsCartbean,Object>> getCartList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<OrderDetailBean>,Object>> getOrderDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<IntegralBean,Object>> getShoppingPrice(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<JdGoodsBean>,Object>> getJdGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<AmountPriceBean,Object>> getAmountPrice(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<GoodsListBean>,Object>> grouponData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsListBean,Object>> getGrouponGoodDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> grouponRightNowBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<RightNowBuyBean<RightNowGoodsBean>,Object>> getGoodsOrderInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<WxInfo,Object>> sureGoodsOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> getOrderInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<Category1Bean>,Object>> getCategoryList(@FieldMap Map<String, String> params);

    /**
     * 上传图片
     * @return
     */
    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectDeleteBean,Object>> uploadImage(@FieldMap Map<String, String> params);

    /**
     * 上传头像
     */
    @Multipart
    @POST("/member/uploadMemberIcon.do")
    Call<Result<String>> uploadMemberIcon(@Part List<MultipartBody.Part> partList, @FieldMap Map<String, String> params);

}
