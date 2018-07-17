package com.fuhl.androidhandbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fuhl.androidhandbook.api.BaseEntity;
import com.fuhl.androidhandbook.api.BaseObserver;
import com.fuhl.androidhandbook.api.RetrofitManager;
import com.fuhl.androidhandbook.api.service.FileUploadService;
import com.fuhl.androidhandbook.api.service.LoginService;
import com.fuhl.androidhandbook.toast.ToastHelper;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author tony
 */
public class MainActivity extends AppCompatActivity {
    public static final String[] ITEMS = {"通用toast", "强调toast", "可点击toast"
            , "通用 + 成功toast", "通用 + 警告toast", "通用 + 错误toast"
            , "强调 + 成功toast", "强调 + 警告toast", "强调 + 错误toast"
            , "可点击 + 成功toast", "可点击 + 警告toast", "可点击 + 错误toast"};

    ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListview = findViewById(R.id.listview);
        setListview();
    }

    public void setListview(){
        mListview.setAdapter(new ArrayAdapter(MainActivity.this, R.layout.item, ITEMS));
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showNormalToast();
                        break;
                    case 1:
                        showSuccessToast();
                        break;
                    default:
                }
            }
        });
    }
    public void showNormalToast() {
        ToastHelper.makeText("关注成功", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
    }

    public void showSuccessToast() {
        ToastHelper.makeText("上传成功", Toast.LENGTH_SHORT,ToastHelper.SUCCESSWITHICONTOAST).show();
    }

    public void login() {
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

    public void upLoadFile(String mPath) {
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
