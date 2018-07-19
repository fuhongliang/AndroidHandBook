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
import com.orhanobut.logger.Logger;

/**
 * @author tony  自动生成帅哥一枚，谁用谁知道
 * @date 2018/7/17
 */
public class ToastHelper extends AbstractToast {
    private static ToastHelper toastHelper;
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

    /**
     * 根据样式进行加载不同的布局文件进行初始化
     * @param style toast样式
     */
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

    /**
     * 创建ToastHelper
     * @param text toast文本
     * @param duration 时间长短
     * @param style 样式
     * @return
     */
    public static ToastHelper makeText(CharSequence text, int duration,int style) {
        text = TextUtils.isEmpty(text)?"":text;
        if (toastHelper != null && CURRENTTOAST == style) {
            toastHelper.setText(text);
            toastHelper.setDuration(duration);
        } else {
            reset();
            toastHelper = new ToastHelper(MyApplication.getApplication(),style);
            toastHelper.setText(text);
            toastHelper.setDuration(duration);
        }
        return toastHelper;
    }

    /**
     * 设置toast内容
     * @param s 文本内容
     */
    @Override
    public void setText(CharSequence s) {
        mTextView.setText(s);
    }

    /**
     * toast show
     */
    @Override
    public void show() {
        String message = mTextView.getText().toString();
        if (!TextUtils.isEmpty(message) && toastHelper != null ) {
            Logger.d("ToastHelper show1>>>"+ message);
            super.show();
        }
    }

    public static void reset() {
        toastHelper = null;
    }
}
