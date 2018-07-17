package com.fuhl.androidhandbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuhl.androidhandbook.api.BaseEntity;
import com.fuhl.androidhandbook.api.BaseObserver;
import com.fuhl.androidhandbook.api.RetrofitManager;
import com.fuhl.androidhandbook.api.service.FileUploadService;
import com.fuhl.androidhandbook.api.service.LoginService;

import java.io.File;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public void upLoadFile(String mPath){
        File file = new File(mPath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitManager.getUploadClient().create(FileUploadService.class)
                .uploads(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FileUploadService.FileModel>() {
                    @Override
                    protected void onSuccees(BaseEntity<FileUploadService.FileModel> t) throws Exception {

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }

                    @Override
                    protected void onCodeError(BaseEntity<FileUploadService.FileModel> t) throws Exception {
                        super.onCodeError(t);
                    }
                });
    }
}
