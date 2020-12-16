package com.example.itstest.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("SELECT * FROM videos")
    LiveData<List<Video>> getAllVideos();

    @Query("SELECT * FROM videos")
    List<Video> getAllVideosAsList();

    @Query("SELECT * FROM videos WHERE title = :title")
    Video getVideoByTitle(String title);

    @Insert
    void insertVideo(Video video);

    @Delete
    void deleteVideo(Video video);

    @Query("DELETE FROM videos")
    void deleteAllVideo();

    @Query("SELECT * FROM favourite_video")
    LiveData<List<FavouriteVideo>> getAllFavouriteVideos();

    @Query("SELECT * FROM favourite_video")
    List<FavouriteVideo> getAllFavouriteVideosAsList();

    @Query("SELECT * FROM favourite_video WHERE title = :title")
    FavouriteVideo getFavouriteVideoByTitle(String title);

    @Insert
    void insertFavouriteVideo(FavouriteVideo favouriteVideo);

    @Delete
    void deleteFavouriteVideo(FavouriteVideo favouriteVideo);
}
