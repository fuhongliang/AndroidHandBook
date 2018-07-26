package com.fuhl.androidhandbook.dialog;

import com.orhanobut.logger.Logger;

/**
 * @author tony
 * @date 2018/7/19
 */
public abstract class BaseDialog {

    public boolean isDialogShown = false;

    private DialogLifeCycleListener dialogLifeCycleListener;

    public void log(Object o) {
        Logger.d("DialogSDK >>>", o.toString());
    }

    public void setDialogLifeCycleListener(DialogLifeCycleListener listener) {
        dialogLifeCycleListener = listener;
    }

    public DialogLifeCycleListener getDialogLifeCycleListener() {
        return dialogLifeCycleListener;
    }

    public void cleanDialogLifeCycleListener() {
        dialogLifeCycleListener = null;
    }

    public abstract void showDialog();

    public abstract void doDismiss();
}
