package com.henrychua.mydailyassessment.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by henrychua on 21/01/2015.
 */
public class Customer implements Parcelable {
    private String name;
    private int phone;
    private String email;
    private Bitmap Signature;

    public Customer(String name, int phone, String email, Bitmap signature) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        Signature = signature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.phone);
        dest.writeString(this.email);
        dest.writeParcelable(this.Signature, 0);
    }

    private Customer(Parcel in) {
        this.name = in.readString();
        this.phone = in.readInt();
        this.email = in.readString();
        this.Signature = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
