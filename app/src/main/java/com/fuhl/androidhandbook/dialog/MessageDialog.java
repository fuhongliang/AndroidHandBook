package com.fuhl.androidhandbook.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import com.fuhl.androidhandbook.R;
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
public class MessageDialog extends BaseDialog {

    private AlertDialog alertDialog;
    private boolean isCanCancel = true;
    private Context context;
    private String title;
    private String message;
    private String buttonCaption = "确定";
    private DialogInterface.OnClickListener onOkButtonClickListener;

    private MessageDialog() {

    }

    public static MessageDialog show(Context context, String title, String message, String buttonCaption, DialogInterface.OnClickListener onOkButtonClickListener) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = new MessageDialog();
            messageDialog.alertDialog = null;
            messageDialog.context = context;
            messageDialog.title = title;
            messageDialog.buttonCaption = buttonCaption;
            messageDialog.message = message;
            messageDialog.onOkButtonClickListener = onOkButtonClickListener;
            dialogList.add(messageDialog);
            showNextDialog();
            return messageDialog;
        }
    }

    private RelativeLayout customView;

    @Override
    public void showDialog() {
        AlertDialog.Builder builder;
        switch (currenType) {
            case TYPE_MATERIAL:
                if (dialog_theme == THEME_DARK) {
                    builder = new AlertDialog.Builder(context, R.style.materialDialogDark);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                break;
            default:
                builder = new AlertDialog.Builder(context);
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

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (customView != null) {
                    customView.removeAllViews();
                }
                customView = null;
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                dialogList.remove(MessageDialog.this);
                showNextDialog();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (customView != null) {
                    customView.removeAllViews();
                }
                customView = null;
                if (getDialogLifeCycleListener() != null) {
                    getDialogLifeCycleListener().onDismiss();
                }
                isDialogShown = false;
                dialogList.remove(MessageDialog.this);
                showNextDialog();
            }
        });

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(BUTTON_POSITIVE, buttonCaption, onOkButtonClickListener);
        if (dialog_background_color != -1) {
            alertDialog.getWindow().getDecorView().setBackgroundResource(dialog_background_color);
        }
        alertDialog.show();
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
}
