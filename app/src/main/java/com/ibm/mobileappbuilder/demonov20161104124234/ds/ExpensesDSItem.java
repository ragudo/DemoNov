
package com.ibm.mobileappbuilder.demonov20161104124234.ds;
import java.util.Date;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ExpensesDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("dataField0") public Long dataField0;
    @SerializedName("title") public String title;
    @SerializedName("amount") public Double amount;
    @SerializedName("date") public Date date;
    @SerializedName("categoryId") public Long categoryId;
    @SerializedName("id") public String id;

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
        dest.writeValue(amount);
        dest.writeValue(date != null ? date.getTime() : null);
        dest.writeValue(categoryId);
        dest.writeString(id);
    }

    public static final Creator<ExpensesDSItem> CREATOR = new Creator<ExpensesDSItem>() {
        @Override
        public ExpensesDSItem createFromParcel(Parcel in) {
            ExpensesDSItem item = new ExpensesDSItem();

            item.dataField0 = (Long) in.readValue(null);
            item.title = in.readString();
            item.amount = (Double) in.readValue(null);
            Long dateAux = (Long) in.readValue(null);
            item.date = dateAux != null ? new Date(dateAux) : null;
            item.categoryId = (Long) in.readValue(null);
            item.id = in.readString();
            return item;
        }

        @Override
        public ExpensesDSItem[] newArray(int size) {
            return new ExpensesDSItem[size];
        }
    };

}

