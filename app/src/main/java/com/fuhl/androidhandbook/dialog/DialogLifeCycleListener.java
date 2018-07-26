package com.fuhl.androidhandbook.dialog;

import android.app.Dialog;

/**
 * @author tony
 * @date 2018/7/19
 */
public interface DialogLifeCycleListener {

    void onCreate(Dialog alertDialog);

    void onShow(Dialog alertDialog);

    void onDismiss();

}
