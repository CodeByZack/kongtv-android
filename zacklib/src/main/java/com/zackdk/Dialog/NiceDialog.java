package com.zackdk.Dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

/**
 * Created by Zackv on 2018/5/2.
 */

public class NiceDialog extends BaseNiceDialog {
    private ViewConvertListener convertListener;

    public NiceDialog() {
    }

    public static NiceDialog init() {
        return new NiceDialog();
    }

    public int intLayoutId() {
        return this.layoutId;
    }

    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        if(this.convertListener != null) {
            this.convertListener.convertView(holder, dialog);
        }

    }

    public NiceDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public NiceDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            this.convertListener = (ViewConvertListener)savedInstanceState.getParcelable("listener");
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listener", this.convertListener);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.convertListener = null;
    }
}
