package com.example.itstest.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.itstest.R;
import com.example.itstest.data.Folder;
import com.example.itstest.data.MainViewModel;
import com.example.itstest.data.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StartActivity extends AppCompatActivity {

    private ImageView folderIv;
    private ImageView videosIv;
    private ImageView favouriteVideosIv;

    private Set<Folder> foldersSet = new TreeSet<>();

    private MainViewModel viewModel;
    private LiveData<List<Video>> videosFromLiveData;
    private List<Video> videosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        folderIv = findViewById(R.id.folder_start_iv);
        videosIv = findViewById(R.id.videos_start_iv);
        favouriteVideosIv = findViewById(R.id.favourite_videos_start_iv);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        videosFromLiveData = viewModel.getVideos();

        permission();
        videosIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, VideosListActivity.class);
                startActivity(intent);
            }
        });
        folderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Folder> folders = new ArrayList<>();
                folders.addAll(foldersSet);
                Intent intent = new Intent(StartActivity.this, FoldersActivity.class);
                intent.putParcelableArrayListExtra("folderList", folders);
                startActivity(intent);
            }
        });
        favouriteVideosIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, FavouriteVideosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void permission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        } else {
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            getVideos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                getVideos();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        }
    }
    private void getVideos(){
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE
        };
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        int dataInd = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int heightInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
        int widthInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
        int durationInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
        int titleInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
        int sizeInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
        int folderNameInd = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()){
            Video video = new Video(cursor.getString(dataInd), cursor.getString(titleInd), cursor.getDouble(heightInd),
                    cursor.getDouble(widthInd), cursor.getLong(durationInd), cursor.getInt(sizeInd), cursor.getString(folderNameInd));
            Folder folder = new Folder(cursor.getString(folderNameInd));
            videosList.add(video);
            foldersSet.add(folder);
        }
        List<Video> tempVideos = viewModel.getAllVideos();
        for (Video video : videosList){
            if (!tempVideos.contains(video)){
                viewModel.insertVideo(video);
            }
        }
    }
}