package com.example.itstest.activities;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.itstest.CustomMediaController;
import com.example.itstest.R;
import com.example.itstest.data.FavouriteVideo;
import com.example.itstest.data.MainViewModel;
import com.example.itstest.data.Video;

import java.io.File;
import java.util.List;

public class VideoViewActivity extends AppCompatActivity {

    private VideoView videoView;
    private CustomMediaController mediaController;

    private Button forwardBtn;
    private Button rewindBtn;

    private List<Video> videos;
    private int position;
    private long lastPressTime = 0;
    private static final int seekTimeToPosition = 15000;
    private static final int toSeekClickTime = 500;

    private MainViewModel viewModel;
    private FavouriteVideo favouriteVideo;

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_view);


        videoView = findViewById(R.id.video_view);
        forwardBtn = findViewById(R.id.forward_btn);
        rewindBtn = findViewById(R.id.rewind_btn);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        /**
         * Get INTENT
         */
        position = getIntent().getIntExtra("position", -1);
        videos = viewModel.getAllVideos();

        //Set Media Controller
        mediaController = new CustomMediaController(this, false);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        setFavouriteVideo();

        if (videos.get(position).isFavourite()){
            mediaController.getFavouriteIv().setVisibility(View.INVISIBLE);
        } else {
            mediaController.getFavouriteIv().setVisibility(View.VISIBLE);
        }

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pressTime = System.currentTimeMillis();

                if (pressTime - lastPressTime <= toSeekClickTime) {
                    videoView.seekTo(videoView.getCurrentPosition() + seekTimeToPosition);
                    Toast.makeText(VideoViewActivity.this, "Forward", Toast.LENGTH_SHORT).show();
                }

                lastPressTime = pressTime;
            }
        });
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pressTime = System.currentTimeMillis();

                if (pressTime - lastPressTime <= toSeekClickTime) {
                    videoView.seekTo(videoView.getCurrentPosition() - seekTimeToPosition);
                    Toast.makeText(VideoViewActivity.this, "Rewind", Toast.LENGTH_SHORT).show();
                }

                lastPressTime = pressTime;
            }
        });

        //Prev-Next VIDEOS
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoViewActivity.this, "Next", Toast.LENGTH_SHORT).show();
                position++;
                setVideo(position);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoViewActivity.this, "Prev", Toast.LENGTH_SHORT).show();
                position--;
                setVideo(position);
            }
        });

        mediaController.setOnFullScreenClickListener(new CustomMediaController.OnFullScreenClickListener() {
            @Override
            public void onFullScreenClick() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    mediaController.getFullScreen().setImageResource(R.drawable.ic_fullscreen_exit);
                } else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    mediaController.getFullScreen().setImageResource(R.drawable.ic_fullscreen);
                }
            }
        });
        mediaController.setOnAddFavouriteClickListener(new CustomMediaController.OnAddFavouriteClickListener() {
            @Override
            public void onAddFavourite() {
                if (favouriteVideo == null){
                    viewModel.insertFavouriteVideo(new FavouriteVideo(videos.get(position)));
                    Toast.makeText(VideoViewActivity.this, "ADDED IN FAVOURITE", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.deleteFavouriteVideo(favouriteVideo);
                }
                setFavouriteVideo();
            }
        });
        mediaController.setOnRemoveFavouriteLister(new CustomMediaController.OnRemoveFavouriteLister() {
            @Override
            public void onRemoveFavourite() {
                videos.get(position).setFavourite(false);
                viewModel.deleteVideo(videos.get(position));
                mediaController.getFavouriteIv().setVisibility(View.INVISIBLE);
                mediaController.getNotFavouriteIv().setVisibility(View.VISIBLE);
            }
        });

        setVideo(position);
    }

    //SET VIDEO FROM URI
    private void setVideo(int position){
        Uri uri = Uri.fromFile(new File(videos.get(position).getPath()));
        videoView.setVideoURI(uri);
        videoView.start();
    }
    private void setFavouriteVideo(){
        favouriteVideo = viewModel.getFavouriteVideoByTitle(videos.get(position).getTitle());
        if (favouriteVideo == null){
            mediaController.getNotFavouriteIv().setVisibility(View.VISIBLE);
            mediaController.getFavouriteIv().setVisibility(View.INVISIBLE);
        } else {
            mediaController.getFavouriteIv().setVisibility(View.VISIBLE);
            mediaController.getNotFavouriteIv().setVisibility(View.INVISIBLE);
        }
    }
}