
package com.ibm.mobileappbuilder.demonov20161104124234.ds;
import android.graphics.Bitmap;
import android.net.Uri;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class CategoriesDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("dataField0") public Long dataField0;
    @SerializedName("title") public String title;
    @SerializedName("dataField1") public String dataField1;
    @SerializedName("icon") public String icon;
    @SerializedName("id") public String id;
    @SerializedName("iconUri") public transient Uri iconUri;

    @Override
    public String getIdentifiableId() {
      return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dataField0);
        dest.writeString(title);
        dest.writeString(dataField1);
        dest.writeString(icon);
        dest.writeString(id);
    }

    public static final Creator<CategoriesDSItem> CREATOR = new Creator<CategoriesDSItem>() {
        @Override
        public CategoriesDSItem createFromParcel(Parcel in) {
            CategoriesDSItem item = new CategoriesDSItem();

            item.dataField0 = (Long) in.readValue(null);
            item.title = in.readString();
            item.dataField1 = in.readString();
            item.icon = in.readString();
            item.id = in.readString();
            return item;
        }

        @Override
        public CategoriesDSItem[] newArray(int size) {
            return new CategoriesDSItem[size];
        }
    };

}

