package com.wlm.wlm.http;

import android.content.Context;

import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.http.factory.GsonDConverterFactory;
import com.wlm.wlm.util.LoggerInterceptor;
import com.wlm.wlm.util.NetUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by LG on 2018/11/12.
 */

public class RetrofitHelper {
    private Context mCntext;
    //    接口根地址
//          public static final String BASE_URL = "http://wlm.mmibb.net:99/api/WebService/";
    public static final String BASE_URL = "https://api.vlmei.com/api/WebService/";

    public static final String ImageUrl = "http://api.boos999.com/api/ClearImg/Upload";
    //    设置超时时间
    private static final long DEFAULT_TIMEOUT = 15_000L;
    private static final String TAG = "retrofit";

    OkHttpClient client = null;
    GsonDConverterFactory factory = GsonDConverterFactory.create();
    //    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context.getApplicationContext());
        }
        return instance;
    }

    private RetrofitHelper(Context mContext) {
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {

        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //添加请求头
                //.addInterceptor(LoggingInterceptor)
                //.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                //添加日志打印拦截器
                .addInterceptor(new LoggerInterceptor("===", true))
                .build();


        mRetrofit = new Retrofit.Builder()
                //.baseUrl("https://api.douban.com/v2/")0
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }

    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    };

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isConnected(ProApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isConnected(ProApplication.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}
