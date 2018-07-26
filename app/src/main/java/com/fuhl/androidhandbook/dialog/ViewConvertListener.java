package com.fuhl.androidhandbook.dialog;

import java.io.Serializable;

/**
 * @author tony
 */
public interface ViewConvertListener extends Serializable {
    long serialVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder holder, BaseNiceDialog dialog);
}
