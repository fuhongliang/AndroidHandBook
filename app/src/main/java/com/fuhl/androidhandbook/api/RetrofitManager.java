package com.fuhl.androidhandbook.api;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fuhl.androidhandbook.MyApplication;
import com.fuhl.androidhandbook.R;
import com.fuhl.androidhandbook.device.DeviceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author tony  自动生成帅哥一枚，谁用谁知道
 * @date 2018/7/17
 */
public class RetrofitManager {
    private static final long TIMEOUT = 10;
    private static Retrofit retrofit;
    private static Retrofit uploadRetrofit;

    private RetrofitManager() {

    }

    /**
     * 获取请求json接口Retrofit实例，"Content-Type"为"application/json"
     *
     * @return Retrofit实例
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpConfig.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }
        return retrofit;
    }

    /**
     * 上传文件使用的Retrofit实例
     *
     * @return Retrofit实例
     */
    public static Retrofit getUploadClient() {
        if (uploadRetrofit == null) {
            uploadRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpConfig.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericUploadClient())
                    .build();
        }
        return uploadRetrofit;
    }

    /**
     * 生成请求Json接口OkHttpClient
     * @return OkHttpClient
     */
    private static OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("88-device-id", DeviceUtil.getDeviceId())
                        .addHeader("User-Agent", DeviceUtil.getUserAgent())
                        .addHeader("88-token", token)
                        .build();
                return chain.proceed(request);
            }

        }).addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("RetrofitAPIManager", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));

        builder.sslSocketFactory(getSSLSocketFactory());
        return builder.build();
    }

    /**
     * 生成上传文件接口OkHttpClient
     * @return OkHttpClient
     */
    private static OkHttpClient genericUploadClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = "";
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("88-device-id", DeviceUtil.getDeviceId())
                                .addHeader("User-Agent", DeviceUtil.getUserAgent())
                                .addHeader("88-token", token)
                                .build();
                        return chain.proceed(request);
                    }

                })
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("RetrofitAPIManager", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return httpClient;
    }

    /**
     * 添加证书
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            InputStream caInput = MyApplication.getApplication().getResources().openRawResource(R.raw.meeting);
            Certificate ca = null;
            try {
                ca = certificateFactory.generateCertificate(caInput);
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            if (ca == null) {
                return null;
            }
            keyStore.setCertificateEntry("ca", ca);

            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            sslSocketFactory = sslContext.getSocketFactory();
        } catch (CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

}
