package com.wlm.wlm.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LG on 2018/11/14.
 */

public class TBbean implements Parcelable {
    public static final Creator<TBbean> CREATOR = new Creator() {
        public TBbean createFromParcel(Parcel paramAnonymousParcel) {
            return new TBbean(paramAnonymousParcel);
        }

        public TBbean[] newArray(int paramAnonymousInt) {
            return new TBbean[paramAnonymousInt];
        }
    };
    public String cate_icon;
    public String cate_name;
    public String id;

    public TBbean() {
    }

    private TBbean(Parcel paramParcel) {
        this.cate_name = paramParcel.readString();
        this.cate_icon = paramParcel.readString();
        this.id = paramParcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getCate_icon() {
        return this.cate_icon;
    }

    public String getCate_name() {
        return this.cate_name;
    }

    public String getId() {
        return this.id;
    }

    public void setCate_icon(String paramString) {
        this.cate_icon = paramString;
    }

    public void setCate_name(String paramString) {
        this.cate_name = paramString;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt) {
        paramParcel.writeString(this.cate_name);
        paramParcel.writeString(this.cate_icon);
        paramParcel.writeString(this.id);
    }
}
