package com.zackdk.Dialog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zackv on 2018/5/2.
 */

public abstract class ViewConvertListener implements Parcelable {
    public static final Creator<ViewConvertListener> CREATOR = new Creator<ViewConvertListener>() {
        public ViewConvertListener createFromParcel(Parcel source) {
            return new ViewConvertListener(source) {
                protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                }
            };
        }

        public ViewConvertListener[] newArray(int size) {
            return new ViewConvertListener[size];
        }
    };

    protected abstract void convertView(ViewHolder var1, BaseNiceDialog var2);

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public ViewConvertListener() {
    }

    protected ViewConvertListener(Parcel in) {
    }
}
