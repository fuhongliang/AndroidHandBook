package com.fuhl.androidhandbook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fuhl.androidhandbook.api.BaseEntity;
import com.fuhl.androidhandbook.api.BaseObserver;
import com.fuhl.androidhandbook.api.RetrofitManager;
import com.fuhl.androidhandbook.api.service.FileUploadService;
import com.fuhl.androidhandbook.api.service.LoginService;
import com.fuhl.androidhandbook.dialog.BaseNiceDialog;
import com.fuhl.androidhandbook.dialog.ConfirmDialog;
import com.fuhl.androidhandbook.dialog.InputDialog;
import com.fuhl.androidhandbook.dialog.LoadingDialog;
import com.fuhl.androidhandbook.dialog.MessageDialog;
import com.fuhl.androidhandbook.dialog.NiceDialog;
import com.fuhl.androidhandbook.dialog.ViewConvertListener;
import com.fuhl.androidhandbook.dialog.ViewHolder;
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
    public static final String[] ITEMS = {"横条toast", "成功toast", "错误toast", "警告toast", "文字toast", "Loading对话框", "消息对话框", "输入对话框","底部评论对话框","分享对话框","底部弹出设置对话框","弹出广告对话框","确认对话框"};

    ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListview = findViewById(R.id.listview);
        setListview();
    }

    public void setListview() {
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
                    case 2:
                        showFailToast();
                        break;
                    case 3:
                        showWarnToast();
                        break;
                    case 4:
                        showToast();
                        break;
                    case 5:
                        showLoadingDialog();
                        break;
                    case 6:
                        showMessageDialog();
                        break;
                    case 7:
                        showInputDialog();
                        break;
                    case 8:
                        showCommitDialog();
                        break;
                    case 9:
                        showShareDialog();
                        break;
                    case 10:
                        showSettingDialog();
                        break;
                    case 11:
                        showAdDialog();
                        break;
                    case 12:
                        showConfirmDialog();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void showConfirmDialog() {
        ConfirmDialog.newInstance()
                .setMargin(60)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    public void showAdDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.ad_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.close, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(210)
                .setOutCancel(false)
                .setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }

    public void showSettingDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.friend_set_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

                    }
                })
                .setShowBottom(true)
                .setHeight(310)
                .show(getSupportFragmentManager());
    }

    public void showShareDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.share_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setDimAmount(0.3f)
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showCommitDialog() {
        NiceDialog.init().setLayoutId(R.layout.commit_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        final EditText editText = holder.getView(R.id.edit_input);
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editText, 0);
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showInputDialog() {
        InputDialog.show(this, "验证", "请出入正确的用户名：", new InputDialog.InputDialogOkListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                dialog.dismiss();
                ToastHelper.makeText(inputText, Toast.LENGTH_SHORT, ToastHelper.ONLYWORDTOAST).show();
            }
        });
    }

    public void showMessageDialog() {
        MessageDialog.show(this, "消息提示框", "你已报名成功了", "知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void showLoadingDialog() {
        LoadingDialog.show(this, "载入中...", 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.dismiss();
            }
        }, 3000);
    }

    public void showNormalToast() {
        ToastHelper.makeText("关注成功", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
    }

    public void showSuccessToast() {
        ToastHelper.makeText("关注成功", Toast.LENGTH_SHORT, ToastHelper.SUCCESSWITHICONTOAST).show();
    }

    public void showFailToast() {
        ToastHelper.makeText("关注失败", Toast.LENGTH_SHORT, ToastHelper.FAILWITHICONTOAST).show();
    }

    public void showWarnToast() {
        ToastHelper.makeText("提示", Toast.LENGTH_SHORT, ToastHelper.WARNWITHICONTOAST).show();
    }

    public void showToast() {
        ToastHelper.makeText("登录成功！", Toast.LENGTH_SHORT, ToastHelper.ONLYWORDTOAST).show();
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
