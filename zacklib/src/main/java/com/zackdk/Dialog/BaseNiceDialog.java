package com.zackdk.Dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zackdk.Utils.Screenutils;
import com.zackdk.customview.R;

/**
 * Created by Zackv on 2018/5/2.
 */

public abstract class BaseNiceDialog extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";
    private int margin;
    private int width;
    private int height;
    private float dimAmount = 0.5F;
    private boolean showBottom;
    private boolean outCancel = true;
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;


    private int gravity = -1;

    public BaseNiceDialog() {
    }

    public abstract int intLayoutId();

    public abstract void convertView(ViewHolder var1, BaseNiceDialog var2);

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(1, R.style.NiceDialog);
        this.layoutId = this.intLayoutId();
        if(savedInstanceState != null) {
            this.margin = savedInstanceState.getInt("margin");
            this.width = savedInstanceState.getInt("width");
            this.height = savedInstanceState.getInt("height");
            this.dimAmount = savedInstanceState.getFloat("dim_amount");
            this.showBottom = savedInstanceState.getBoolean("show_bottom");
            this.outCancel = savedInstanceState.getBoolean("out_cancel");
            this.animStyle = savedInstanceState.getInt("anim_style");
            this.layoutId = savedInstanceState.getInt("layout_id");
        }

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(this.layoutId, container, false);
        this.convertView(ViewHolder.create(view), this);
        return view;
    }

    public void onStart() {
        super.onStart();
        this.initParams();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("margin", this.margin);
        outState.putInt("width", this.width);
        outState.putInt("height", this.height);
        outState.putFloat("dim_amount", this.dimAmount);
        outState.putBoolean("show_bottom", this.showBottom);
        outState.putBoolean("out_cancel", this.outCancel);
        outState.putInt("anim_style", this.animStyle);
        outState.putInt("layout_id", this.layoutId);
    }

    private void initParams() {
        Window window = this.getDialog().getWindow();
        if(window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = this.dimAmount;
            if(this.showBottom) {
                lp.gravity = 80;
                if(this.animStyle == 0) {
                    this.animStyle = R.style.DefaultAnimation;
                }
            }

            if(this.width == 0) {
                lp.width = Screenutils.getScreenWidth(this.getContext()) - 2 * Screenutils.dp2px(this.getContext(), (float)this.margin);
            } else if(this.width == -1) {
                lp.width = -2;
            } else {
                lp.width = Screenutils.dp2px(this.getContext(), (float)this.width);
            }

            if(this.height == 0) {
                lp.height = -2;
            } else if(this.height == -1) {
                lp.height = -1;
            } else {
                lp.height = Screenutils.dp2px(this.getContext(), (float)this.height);
            }
            if(this.gravity != -1){
                lp.gravity = this.gravity;
            }
            window.setWindowAnimations(this.animStyle);
            window.setAttributes(lp);
        }

        this.setCancelable(this.outCancel);
    }

    public BaseNiceDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseNiceDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseNiceDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseNiceDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseNiceDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public BaseNiceDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseNiceDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseNiceDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseNiceDialog show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if(this.isAdded()) {
            ft.remove(this).commit();
        }

        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }
}

