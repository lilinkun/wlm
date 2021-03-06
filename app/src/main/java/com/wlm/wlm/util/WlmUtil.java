package com.wlm.wlm.util;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.ui.CountdownView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2018/11/10.
 */

public class WlmUtil {

    public static final int PAGE_HOMEPAGE = 0;
    public static final int PAGE_FIND = 1;
    public static final int PAGE_MALL = 2;
    public static final int PAGE_ME = 3;

    public static final int WXTYPE_LOGIN = 1;
    public static final int WXTYPE_SHARED = 2;

    public static final String GOODSTYPE = "goodstype";

    public static final int GOODSTYPE_INTEGRAL = 1;//积分商城
    public static final int GOODSTYPE_GROUPON = 2;//团购
    public static final int GOODSTYPE_VIP = 4;//vip礼包
    public static final int GOODSTYPE_WLM = 8;//唯乐美商品
    public static final int GOODSTYPE_CROWDFUNDING = 16;//众筹
    public static final int GOODSTYPE_POINT = 32;//九九尖货
    public static final int GOODSTYPE_WLMBUY = 64;//唯乐购
    public static final int GOODSTYPE_SECKILL = 128;//秒杀
    public static final int GOODSTYPE_BEAUTY_HEALTH = 256;//医美健康

    public static final String PAGE_COUNT = "20";

    //    public static final String APP_ID = "wx7b154709878a1cbe";
    public static final String APP_ID = "wx11a116ef840f67f9";
    public static final String SECRET = "579a6a72da743151f2a4eb6c78fef144";

    public static final int GOODS_ALL_WEAR = 3756;
    public static final int GOODS_WOMAN_WEAR = 3767;
    public static final int GOODS_HOUSE = 3758;
    public static final int GOODS_DIGITAL = 3759;
    public static final int GOODS_SHOES = 3762;
    public static final int GOODS_MAKEUP = 3763;
    public static final int GOODS_MAN_WEAR = 3764;
    public static final int GOODS_UNDERWEAR = 3765;
    public static final int GOODS_MOTHER = 3760;
    public static final int GOODS_FOOD = 3761;
    public static final int GOODS_MOTION = 3766;

    public static final int BANNER_GROUPON = 0x0011;
    public static final int BANNER_HOME = 0x0012;
    public static final int BANNER_CROWDFUNDING = 0x0013;

    public static final String TYPEID = "TYPEID";
    public static final String USERNAME = "username";
    public static final String GOODSNAME = "goodsname";
    public static final String USERID = "userid";
    public static final String LOGIN = "login";
    public static final String OPENID = "openid";
    public static final String UNIONID = "unionid";
    public static final String GROUPONGOODS = "groupongoods";
    public static final String INTEGRAL = "integral";
    public static final String MANUFACURE = "Manufacure";
    public static final String VIP = "vip";
    public static final String GOODSID = "goodsid";
    public static final String TYPE = "type";
    public static final String GOODSCHOOSEBEAN = "goodsChooseBean";
    public static final String GOODSDETAILINFOBEAN = "GoodsDetailInfoBean";
    public static final String RIGHTNOWBUYBEAN = "RightNowBuyBean";
    public static final String GOODSNUM = "GoodsNum";
    public static final String ATTRID = "attr_id";
    public static final String ORDERID = "orderid";
    public static final String ORDERAMOUNT = "orderamount";
    public static final String CATID = "catid";
    public static final String ACCOUNT = "account";
    public static final String POINT = "point";
    public static final String HEADIMGURL = "headimgurl";
    public static final String TEAMID = "teamid";
    public static final String TELEPHONE = "telephone";
    public static final String PRICE = "price";
    public static final String BALANCEBEAN = "balanceBean";
    public static final String IMG = "img";
    public static final String BANNERIMG = "bannerimg";
    public static final String WHERE = "where";
    public static final String GOODS = "goods";
    public static final String CUSTOMER = "customer";
    public static final String SHAREDIMG = "shared";
    public static final String VIPVALIDITY = "VipValidity";
    public static final String WLMCOIN = "wlmcoin";
    public static final String USERBANKBEAN = "UserBankBean";
    public static final String USERLEVEL = "UserLevel";
    public static final String USERLEVELNAME = "UserLevelName";
    public static final String SHAREDMEIMG = "Sharedmeimg";


    public static String RESULT_SUCCESS = "success";
    public static String RESULT_FAIL = "fail";

    public static String[] strs = {"tk_total_sales_des", "total_sales_des", "price_des", "price_asc", "tk_total_commi_des", "tk_total_commi_asc"};


    public static final void setInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 序列化对象
     *
     * @param loginBean
     * @return
     * @throws IOException
     */
    public static String serialize(LoginBean loginBean) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(loginBean);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 序列化对象
     *
     * @return
     * @throws IOException
     */
    public static String serialize(Serializable t) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(t);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LoginBean deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        LoginBean loginBean = (LoginBean) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return loginBean;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Serializable unSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Serializable loginBean = (Serializable) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return loginBean;
    }

    //WlmUtil.wxPay(wxInfo.getAppId(),wxInfo.getPartnerid(),wxInfo.getPrepayid(),wxInfo.getNonceStr(),wxInfo.getTimeStamp(),wxInfo.getPaySign(),this);
    public static void wxPay(String appid, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign, Context context) {
        Toast.makeText(context, "获取订单中...", Toast.LENGTH_SHORT).show();
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(appid);
        try {
//            org.json.JSONObject json = new org.json.JSONObject(result);
//            if (null != json && !json.has("retcode")) {
            PayReq req = new PayReq();
            req.appId = appid;
            req.partnerId = partnerId;
            req.prepayId = prepayId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.packageValue = "Sign=WXPay";
            req.sign = sign;
            req.extData = "app data";
            api.sendReq(req);
//            } else {
//                Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
//                Toast.makeText(this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(context, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void wxPay1(String appid, String prepayId, String partnerId, String nonceStr, String timeStamp, String sign, Context context) {
        Toast.makeText(context, "获取订单中...", Toast.LENGTH_SHORT).show();
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(appid);
        try {
//            org.json.JSONObject json = new org.json.JSONObject(result);
//            if (null != json && !json.has("retcode")) {
            PayReq req = new PayReq();
            req.appId = appid;
            req.prepayId = prepayId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.packageValue = "Sign=WXPay";
            req.sign = sign;
            req.extData = "app data";
            api.sendReq(req);
//            } else {
//                Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
//                Toast.makeText(this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(context, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCurDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curDate = simpleDateFormat.format(date);
        return curDate;
    }

    public static String redecuStr(String total, String reduce) {
        String dd = "";
        if (total.contains(reduce)) {
            dd = total.substring(0, total.indexOf(reduce));
            dd = dd + total.substring(total.indexOf(reduce) + reduce.length(), total.length());
        }
        return dd;
    }


    public static int isCountdown(String startStr, String endStr, CountdownView countdownView) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = 0;
        long endTime = 0;
        try {
            startTime = simpleDateFormat.parse(startStr).getTime();
            endTime = simpleDateFormat.parse(endStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long nowTime = (new Date()).getTime();
        if (startTime > nowTime) {
            countdownView.start(startTime - nowTime);
            return 0;
        } else if (endTime > (new Date()).getTime()) {
            countdownView.start(endTime - nowTime);
            return 1;
        } else {
            return 2;
        }
    }

    public static String getPriceNum(double price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        String balance = numberFormat.format(price);
        return balance;
    }

    public static String getType(String type) {
        if (type.equals("1")) {
            type = WlmUtil.INTEGRAL;
        } else if (type.equals("4")) {
            type = WlmUtil.VIP;
        } else if (type.equals("8")) {
            type = WlmUtil.MANUFACURE;
        } else if (type.equals("2")) {
            type = WlmUtil.GROUPONGOODS;
        }
        return type;
    }

    /**
     *
     * @param iwxapi
     * @param path
     * @param title
     * @param description
     * @param byteArray
     * @param which  0:分享微信好友 1:分享微信朋友圈 2:分享微信收藏
     */
    public static void setShared(IWXAPI iwxapi, String path, String title, String description, byte[] byteArray,int which) {

        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = ProApplication.SHAREDIMG; // 兼容低版本的网页链接
//        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
//        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
        miniProgramObj.path = path;
        //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = description;               // 小程序消息desc

        msg.thumbData = byteArray;// 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (which == 0) {
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 发送到好友界面
        }else if (which == 1){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//发送到朋友圈
        } else if (which == 2) {
            req.scene = SendMessageToWX.Req.WXSceneFavorite;//添加到微信收藏
        }
        iwxapi.sendReq(req);
    }

    public static void setShared1(IWXAPI iwxapi, String path, String title, String description, byte[] byteArray,int which) {

        /*WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = ProApplication.SHAREDIMG; // 兼容低版本的网页链接
//        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
        miniProgramObj.path = path;*/

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = path;

        //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;                    // 小程序消息title
        msg.description = description;               // 小程序消息desc

        msg.thumbData = byteArray;// 小程序消息封面图片，小于128k


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (which == 0) {
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 发送到好友界面
        }else if (which == 1){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//发送到朋友圈
        } else if (which == 2) {
            req.scene = SendMessageToWX.Req.WXSceneFavorite;//添加到微信收藏
        }
        iwxapi.sendReq(req);
    }

}
