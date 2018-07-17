package com.fuhl.androidhandbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuhl.androidhandbook.api.BaseEntity;
import com.fuhl.androidhandbook.api.BaseObserver;
import com.fuhl.androidhandbook.api.RetrofitManager;
import com.fuhl.androidhandbook.api.service.LoginService;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(){
        RetrofitManager.getClient().create(LoginService.class).auth(new LoginService.LoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginService.LoginReponseDataModel>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    protected void onSuccees(BaseEntity<LoginService.LoginReponseDataModel> t) throws Exception {

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
