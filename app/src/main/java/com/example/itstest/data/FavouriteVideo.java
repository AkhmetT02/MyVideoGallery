package com.example.itstest.data;

import androidx.room.Entity;

@Entity(tableName = "favourite_video")
public class FavouriteVideo extends Video {

    public FavouriteVideo(int id, String path, String title, double height, double width, long duration, int size, String folderName) {
        super(id, path, title, height, width, duration, size, folderName);
    }

    public FavouriteVideo(Video v){
        super(v.getId(), v.getPath(), v.getTitle(), v.getHeight(), v.getWidth(), v.getDuration(), v.getSize(), v.getFolderName());
    }
}
