package com.example.itstest.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Folder implements Parcelable, Comparable<Folder> {
    private String name;

    public Folder(String name) {
        this.name = name;
    }

    protected Folder(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int compareTo(Folder o) {
        return name.compareTo(o.name);
    }
}
