package com.fuhl.androidhandbook.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fuhl.androidhandbook.MyApplication;
import com.fuhl.androidhandbook.R;

import java.lang.ref.WeakReference;

/**
 * @author tony  自动生成帅哥一枚，谁用谁知道
 * @date 2018/7/17
 */
public class ToastHelper extends AbstractToast {
    private static WeakReference<ToastHelper> sToast;
    private Context mContext;
    private TextView mTextView;
    /**
     * 正常Toast类型
     */
    public static final int NORMALTOAST = 0;
    /**
     * 成功并且带有图标
     */
    public static final int SUCCESSWITHICONTOAST = 2;
    /**
     * 失败加上图标
     */
    public static final int FAILWITHICONTOAST = 3;
    /**
     * 警告
     */
    public static final int WARNWITHICONTOAST = 1;

    /**
     * 警告
     */
    public static final int ONLYWORDTOAST = 4;

    /**
     * 当前样式
     */
    public static int CURRENTTOAST = 0;

    /**
     * 构造行数
     * @param context 上下文
     * @param style toast类型
     */
    public ToastHelper(Context context,int style){
        super(context);
        mContext = context;
        init(style);
    }

    private void init(int style){
        View view;
        switch (style){
            case NORMALTOAST:
                view = LayoutInflater.from(mContext).inflate(R.layout.toast_normal_layout, null, false);
                break;
            case SUCCESSWITHICONTOAST:
                view = LayoutInflater.from(mContext).inflate(R.layout.toast_ok_layout, null, false);
                break;
            case FAILWITHICONTOAST:
                view = LayoutInflater.from(mContext).inflate(R.layout.toast_fail_layout, null, false);
                break;
            case WARNWITHICONTOAST:
                view = LayoutInflater.from(mContext).inflate(R.layout.toast_warn_layout, null, false);
                break;
            case ONLYWORDTOAST:
                view = LayoutInflater.from(mContext).inflate(R.layout.toast_word_layout, null, false);
                break;
           default:
               view = LayoutInflater.from(mContext).inflate(R.layout.toast_ok_layout, null, false);
               break;
        }
        CURRENTTOAST = style;
        mTextView = view.findViewById(R.id.tv_message);
        setView(view);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(Toast.LENGTH_LONG);
    }

    public static ToastHelper makeText(CharSequence text, int duration,int style) {
        text = TextUtils.isEmpty(text)?"":text;
        if (sToast != null && sToast.get() != null && CURRENTTOAST == style) {
            sToast.get().setText(text);
            sToast.get().setDuration(duration);
        } else {
            ToastHelper mToast = new ToastHelper(MyApplication.getApplication(),style);
            mToast.setText(text);
            mToast.setDuration(duration);
            sToast = new WeakReference<>(mToast);
        }
        return sToast.get();
    }

    @Override
    public void setText(CharSequence s) {
        mTextView.setText(s);
    }

    @Override
    public void show() {
        String message = mTextView.getText().toString();
        if (!TextUtils.isEmpty(message) && sToast != null && sToast.get() != null) {
            super.show();
        }
    }
}
