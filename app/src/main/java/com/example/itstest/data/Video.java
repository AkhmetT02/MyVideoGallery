package com.example.itstest.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "videos")
public class Video implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String path;
    private double height;
    private double width;
    private long duration;
    private String title;
    private int size;
    private String folderName;
    private boolean isFavourite;

    public Video(int id, String path, String title, double height, double width, long duration, int size, String folderName) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.height = height;
        this.width = width;
        this.duration = duration;
        this.size = size;
        this.folderName = folderName;
    }
    @Ignore
    public Video(String path, String title, double height, double width, long duration, int size, String folderName) {
        this.path = path;
        this.title = title;
        this.height = height;
        this.width = width;
        this.duration = duration;
        this.size = size;
        this.folderName = folderName;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Video(Parcel in) {
        path = in.readString();
        height = in.readDouble();
        width = in.readDouble();
        duration = in.readLong();
        title = in.readString();
        size = in.readInt();
        folderName = in.readString();
        isFavourite = in.readBoolean();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeDouble(height);
        dest.writeDouble(width);
        dest.writeLong(duration);
        dest.writeString(title);
        dest.writeInt(size);
        dest.writeString(folderName);
        dest.writeBoolean(isFavourite);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(title, video.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
