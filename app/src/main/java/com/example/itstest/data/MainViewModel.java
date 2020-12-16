package com.example.itstest.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static VideoDatabase database;
    private LiveData<List<Video>> videos;
    private LiveData<List<FavouriteVideo>> favouriteVideos;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = VideoDatabase.getInstance(application);
        videos = database.getVideoDao().getAllVideos();
        favouriteVideos = database.getVideoDao().getAllFavouriteVideos();
    }

    public LiveData<List<Video>> getVideos() {
        return videos;
    }
    public LiveData<List<FavouriteVideo>> getFavouriteVideos() {
        return favouriteVideos;
    }

    public void insertVideo(Video video){
        new InsertVideoTask().execute(video);
    }
    public void deleteAllVideo(){
        new DeleteAllVideoTask().execute();
    }
    public void deleteVideo(Video video){
        new DeleteVideoTask().execute(video);
    }
    public Video getVideoByTitle(String title){
        try {
            return new GetVideoByTitleTask().execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Video> getAllVideos(){
        try {
            return new GetAllVideosTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertFavouriteVideo(FavouriteVideo favouriteVideo){
        new InsertFavouriteVideoTask().execute(favouriteVideo);
    }
    public void deleteFavouriteVideo(FavouriteVideo favouriteVideo){
        new DeleteFavouriteVideoTask().execute(favouriteVideo);
    }
    public FavouriteVideo getFavouriteVideoByTitle(String title){
        try {
            return new GetFavouriteVideoByTitleTask().execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<FavouriteVideo> getAllFavouriteVideosAsList(){
        try {
            return new GetAllFavouriteVideosAsListTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertVideoTask extends AsyncTask<Video, Void, Void>{
        @Override
        protected Void doInBackground(Video... videos) {
            if (videos != null && videos.length > 0){
                database.getVideoDao().insertVideo(videos[0]);
            }
            return null;
        }
    }
    private static class DeleteAllVideoTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.getVideoDao().deleteAllVideo();
            return null;
        }
    }
    private static class DeleteVideoTask extends AsyncTask<Video, Void, Void>{
        @Override
        protected Void doInBackground(Video... videos) {
            if (videos != null && videos.length > 0){
                database.getVideoDao().deleteVideo(videos[0]);
            }
            return null;
        }
    }
    private static class GetVideoByTitleTask extends AsyncTask<String, Void, Video>{
        @Override
        protected Video doInBackground(String... strings) {
            if (strings != null && strings.length > 0){
                return database.getVideoDao().getVideoByTitle(strings[0]);
            }
            return null;
        }
    }
    private static class GetAllVideosTask extends AsyncTask<Void, Void, List<Video>>{
        @Override
        protected List<Video> doInBackground(Void... voids) {
            return database.getVideoDao().getAllVideosAsList();
        }
    }

    private static class InsertFavouriteVideoTask extends AsyncTask<FavouriteVideo, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteVideo... favouriteVideos) {
            if (favouriteVideos.length > 0 && favouriteVideos != null){
                database.getVideoDao().insertFavouriteVideo(favouriteVideos[0]);
            }
            return null;
        }
    }
    private static class DeleteFavouriteVideoTask extends AsyncTask<FavouriteVideo, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteVideo... favouriteVideos) {
            if (favouriteVideos.length > 0 && favouriteVideos != null){
                database.getVideoDao().deleteFavouriteVideo(favouriteVideos[0]);
            }
            return null;
        }
    }
    private static class GetFavouriteVideoByTitleTask extends AsyncTask<String, Void, FavouriteVideo>{
        @Override
        protected FavouriteVideo doInBackground(String... strings) {
            if (strings.length > 0 && strings != null){
                return database.getVideoDao().getFavouriteVideoByTitle(strings[0]);
            }
            return null;
        }
    }
    private static class GetAllFavouriteVideosAsListTask extends AsyncTask<Void, Void, List<FavouriteVideo>>{
        @Override
        protected List<FavouriteVideo> doInBackground(Void... voids) {
            return database.getVideoDao().getAllFavouriteVideosAsList();
        }
    }
}
