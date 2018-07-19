package com.fuhl.androidhandbook.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fuhl.androidhandbook.R;

/**
 * @author tony  自动生成帅哥一枚，谁用谁知道
 * @date 2018/7/19
 */
public class LoadingDialog extends BaseDialog {
    /**
     *  决定提示框的模式（亮色和暗色两种）
     *  请使用 THEME_LIGHT、THEME_DARK 赋值
     */
    public int tip_theme = 0;
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    /**
     *  决定等待框、提示框以及iOS风格的对话框是否启用模糊背景
     */
    public static boolean use_blur = true;
    /**
     *  决定提示框字样大小
     *  当值<=0时使用默认大小
     */
    public static int tip_text_size = 0;

    private AlertDialog alertDialog;
    private static LoadingDialog loadingDialog;
    private boolean isCanCancel = false;

    private Context context;
    private String tip;

    private LoadingDialog() {
    }

    public static LoadingDialog show(Context context, String tip,int mTipTheme) {
        synchronized (LoadingDialog.class) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog();
            }
            loadingDialog.context = context;
            loadingDialog.tip = tip;
            loadingDialog.tip_theme = mTipTheme;
            loadingDialog.showDialog();
            loadingDialog.log("显示等待对话框 -> " + tip);
            return loadingDialog;
        }
    }

    private BlurView blur;
    private RelativeLayout boxInfo;
    private RelativeLayout boxBkg;
    private TextView txtInfo;
    private ProgressView progress;
    private int blur_front_color;

    @Override
    public void showDialog() {
        if (loadingDialog != null) {
            if (loadingDialog.alertDialog != null) {
                loadingDialog.alertDialog.dismiss();
            }
        }
        AlertDialog.Builder builder;
        int bkgResId;
        switch (tip_theme) {
            case THEME_LIGHT:
                builder = new AlertDialog.Builder(context, R.style.lightMode);
                bkgResId = R.drawable.rect_light;
                blur_front_color = Color.argb(150, 255, 255, 255);
                break;
            default:
                builder = new AlertDialog.Builder(context, R.style.darkMode);
                bkgResId = R.drawable.rect_dark;
                blur_front_color = Color.argb(200, 0, 0, 0);
                break;
        }
        builder.setCancelable(isCanCancel);
        alertDialog = builder.create();
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onCreate(alertDialog);
        }
        if (isCanCancel) {
            alertDialog.setCanceledOnTouchOutside(true);
        }
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.loading_dialog);
        boxInfo = window.findViewById(R.id.box_info);
        boxBkg = window.findViewById(R.id.box_bkg);
        txtInfo = window.findViewById(R.id.txt_info);
        progress = window.findViewById(R.id.progress);
        boxBkg.removeAllViews();
        if (tip_theme == THEME_LIGHT) {
            progress.setStrokeColors(new int[]{Color.rgb(0, 0, 0)});
        } else {
            progress.setStrokeColors(new int[]{Color.rgb(255, 255, 255)});
        }
        if (use_blur) {
            blur = new BlurView(context, null);
            boxBkg.post(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.width = boxInfo.getWidth();
                    params.height = boxInfo.getHeight();
                    blur.setOverlayColor(blur_front_color);
                    boxBkg.addView(blur, 0, params);
                }
            });
        } else {
            boxBkg.setBackgroundResource(bkgResId);
        }
        if (!tip.isEmpty()) {
            boxInfo.setVisibility(View.VISIBLE);
            txtInfo.setText(tip);
        } else {
            boxInfo.setVisibility(View.GONE);
        }
        if (tip_text_size > 0) {
            txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, tip_text_size);
        }
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                    alertDialog = null;
                }
            }
        });
        alertDialog.show();
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onShow(alertDialog);
        }
    }

    @Override
    public void doDismiss() {
        if (alertDialog!=null) {
            alertDialog.dismiss();
        }
    }

    public LoadingDialog setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) {
            alertDialog.setCancelable(canCancel);
        }
        return this;
    }

    public static void dismiss() {
        synchronized (LoadingDialog.class) {
            if (loadingDialog != null) {
                if (loadingDialog.alertDialog != null) {
                    try {
                        loadingDialog.alertDialog.dismiss();
                    }catch (Exception ignored){

                    }
                }
            }
        }
    }
}
