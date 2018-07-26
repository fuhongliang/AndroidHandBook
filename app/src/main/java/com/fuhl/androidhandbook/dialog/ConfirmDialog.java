package com.fuhl.androidhandbook.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fuhl.androidhandbook.R;

/**
 * @author tony
 * @date 2018/7/21
 */
public class ConfirmDialog  extends BaseNiceDialog {
    public static ConfirmDialog newInstance() {
        Bundle bundle = new Bundle();
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setText(R.id.title, "震惊！！！！");
        holder.setText(R.id.message, "WE小姐赛战胜SKT，卢兮夜单杀两次瓜皮，心疼一波！");
        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}