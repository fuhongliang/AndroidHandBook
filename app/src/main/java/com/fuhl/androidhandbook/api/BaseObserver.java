package com.fuhl.androidhandbook.api;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author tony
 * @date 2018/7/17
 */
public abstract class BaseObserver <T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    protected Context mContext;

    public BaseObserver(Context cxt) {
        this.mContext = cxt;
    }

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.w(TAG, "onError: " + e.toString());
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException
                    || e instanceof SocketTimeoutException) {
                onFailure(e, true);
            } else {
                if (e instanceof HttpException) {
                    HttpException error = (HttpException) e;
                    if (400 <= error.code() && error.code() < 500) {
                        Gson gson = new Gson();
                        BaseEntity<T> linkBaseEntity = gson.fromJson(error.response().errorBody().string(), BaseEntity.class);
                        /**
                         * 未登录
                         */
                        if (linkBaseEntity != null && linkBaseEntity.getCode() != null && linkBaseEntity.getCode().equals("authentication_failed")) {
                        }
                        onCodeError(linkBaseEntity);
                    } else if (error.code() == 500) {
                        onFailure(e, false);
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {

    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {

    }

    public void closeProgressDialog() {

    }
}