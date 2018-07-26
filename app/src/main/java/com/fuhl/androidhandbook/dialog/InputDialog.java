package com.fuhl.androidhandbook.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.fuhl.androidhandbook.R;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.fuhl.androidhandbook.dialog.DialogSettings.THEME_DARK;
import static com.fuhl.androidhandbook.dialog.DialogSettings.TYPE_MATERIAL;
import static com.fuhl.androidhandbook.dialog.DialogSettings.currenType;
import static com.fuhl.androidhandbook.dialog.DialogSettings.dialogList;
import static com.fuhl.androidhandbook.dialog.DialogSettings.dialog_background_color;
import static com.fuhl.androidhandbook.dialog.DialogSettings.dialog_theme;
import static com.fuhl.androidhandbook.dialog.DialogSettings.showNextDialog;

/**
 * @author tony
 * @date 2018/7/19
 */
public class InputDialog  extends BaseDialog {

    private AlertDialog alertDialog;
    private static InputDialog inputDialog;
    private boolean isCanCancel = false;

    private Context context;
    private String title;
    private String message;
    private String defaultInputText = "";
    private String defaultInputHint = "请输入您的姓名";
    private String okButtonCaption = "确定";
    private String cancelButtonCaption = "取消";
    private InputDialogOkListener onOkButtonClickListener;
    private DialogInterface.OnClickListener onCancelButtonClickListener;

    public InputDialog() {
    }
    public static InputDialog show(Context context, String title, String message, InputDialogOkListener onOkButtonClickListener) {
        return show(context, title, message, "确定", onOkButtonClickListener, "取消", null);
    }

    public static InputDialog show(Context context, String title, String message, String okButtonCaption, InputDialogOkListener onOkButtonClickListener,
                                   String cancelButtonCaption, DialogInterface.OnClickListener onCancelButtonClickListener) {
        synchronized (InputDialog.class) {
            inputDialog = new InputDialog();
            inputDialog.alertDialog = null;
            inputDialog.context = context;
            inputDialog.title = title;
            inputDialog.message = message;
            inputDialog.okButtonCaption = okButtonCaption;
            inputDialog.cancelButtonCaption = cancelButtonCaption;
            inputDialog.onOkButtonClickListener = onOkButtonClickListener;
            inputDialog.onCancelButtonClickListener = onCancelButtonClickListener;
            inputDialog.log("装载输入对话框 -> " + message);
            dialogList.add(inputDialog);
            showNextDialog();
            return inputDialog;
        }
    }

    private EditText txtInput;
    private RelativeLayout customView;

    @Override
    public void showDialog() {
        AlertDialog.Builder builder;
        if (dialog_theme == THEME_DARK) {
            builder = new AlertDialog.Builder(context, R.style.materialDialogDark);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setCancelable(isCanCancel);

        alertDialog = builder.create();
        alertDialog.setView(new EditText(context));
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onCreate(alertDialog);
        }
        if (isCanCancel) {
            alertDialog.setCanceledOnTouchOutside(true);
        }

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (customView != null) {
                    customView.removeAllViews();
                }
                if (onCancelButtonClickListener != null) {
                    onCancelButtonClickListener.onClick(alertDialog, BUTTON_NEGATIVE);
                }
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                dialogList.remove(InputDialog.this);
                showNextDialog();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (customView != null) {
                    customView.removeAllViews();
                }
                if (onCancelButtonClickListener != null) {
                    onCancelButtonClickListener.onClick(alertDialog, BUTTON_NEGATIVE);
                }
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                dialogList.remove(InputDialog.this);
                showNextDialog();
            }
        });

        txtInput = new EditText(context);
        txtInput.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) txtInput.getLayoutParams();
                p.setMargins(dip2px(context, 20), 0, dip2px(context, 20), 0);
                txtInput.requestLayout();
            }
        });
        txtInput.setText(defaultInputText);
        txtInput.setHint(defaultInputHint);

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setView(txtInput);
        alertDialog.setButton(BUTTON_POSITIVE, okButtonCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setButton(BUTTON_NEGATIVE, cancelButtonCaption, onCancelButtonClickListener);

        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkButtonClickListener != null) {
                    onOkButtonClickListener.onClick(alertDialog, txtInput.getText().toString());
                }
            }
        });

        if (dialog_theme == THEME_DARK) {
            txtInput.setTextColor(Color.rgb(255, 255, 255));
        } else {
            txtInput.setTextColor(Color.rgb(0, 0, 0));
        }
        if (dialog_background_color != -1) {
            alertDialog.getWindow().getDecorView().setBackgroundResource(dialog_background_color);
        }
        isDialogShown = true;
        if (getDialogLifeCycleListener() != null) {
            getDialogLifeCycleListener().onShow(alertDialog);
        }
    }

    @Override
    public void doDismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public InputDialog setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) {
            alertDialog.setCancelable(canCancel);
        }
        return this;
    }

    public InputDialog setDefaultInputText(String defaultInputText) {
        this.defaultInputText = defaultInputText;
        if (alertDialog != null) {
            txtInput.setText(defaultInputText);
            txtInput.setHint(defaultInputHint);
        }
        return this;
    }

    public InputDialog setDefaultInputHint(String defaultInputHint) {
        this.defaultInputHint = defaultInputHint;
        if (alertDialog != null) {
            txtInput.setText(defaultInputText);
            txtInput.setHint(defaultInputHint);
        }
        return this;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public InputDialog setCustomView(View view) {
        if (currenType == TYPE_MATERIAL) {
            customView = new RelativeLayout(context);
            customView.addView(view);
            alertDialog.setContentView(customView);
        } else {
            if (alertDialog != null && view != null) {
                customView.setVisibility(View.VISIBLE);
                customView.addView(view);
            }
        }
        return this;
    }

    private void setIMMStatus(boolean show, EditText editText) {
        if (show) {
            editText.requestFocus();
            editText.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } else {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public interface InputDialogOkListener {
        /** 点击按钮后获取输入框内的内容
         * @param dialog
         * @param inputText
         */
        void onClick(Dialog dialog, String inputText);
    }

}
