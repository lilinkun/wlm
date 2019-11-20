package com.wlm.wlm.util;

import com.wlm.wlm.http.RetrofitService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * *请求控制工具类
 * <p>
 * Created by LG on 2018/12/26.
 */
public class ApiUtil {
    private static final String HOST = "http://www.update.test";//换成你上传用的服务器地址
    private static Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 10;//超时时长，单位：秒

    /**
     * 获取根服务地址
     */
    public static String getHOST() {
        return HOST;
    }

    /**
     * 初始化 Retrofit
     */
    private static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .baseUrl(HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 创建数据请求服务
     */
    private static RetrofitService getApiService() {
        return ApiUtil.getApiRetrofit().create(RetrofitService.class);
    }

    /**
     * 上传头像
     */
    public static Call<Result<String>> uploadMemberIcon(List<MultipartBody.Part> partList, String HeadPic, String SessionId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cls", "UserBase");
        hashMap.put("fun", "UploadHeadPic");
        hashMap.put("HeadPic", HeadPic);
        hashMap.put("SessionId", SessionId);
        return ApiUtil.getApiService().uploadMemberIcon(partList, hashMap);
    }

}
