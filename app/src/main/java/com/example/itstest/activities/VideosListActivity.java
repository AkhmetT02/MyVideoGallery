package com.example.itstest.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itstest.R;
import com.example.itstest.adapters.VideoAdapter;
import com.example.itstest.data.MainViewModel;
import com.example.itstest.data.Video;

import java.util.ArrayList;
import java.util.List;

public class VideosListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVideos;
    private LiveData<List<Video>> videos;
    private VideoAdapter videoAdapter;

    private ArrayList<Video> videoList;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewVideos = findViewById(R.id.recycler_view_videos);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(this));

        videoAdapter = new VideoAdapter(VideosListActivity.this, VideosListActivity.this);

        if (getIntent().getParcelableArrayListExtra("videoList") != null){
            videoList = getIntent().getParcelableArrayListExtra("videoList");
            videoAdapter.setVideos(videoList);
        } else {
            viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            List<Video> videoList = new ArrayList<>();
            viewModel.getVideos().observe(this, new Observer<List<Video>>() {
                @Override
                public void onChanged(List<Video> videos) {
                    videoList.addAll(videos);
                    videoAdapter.setVideos(videoList);
//                    Toast.makeText(VideosListActivity.this, "In Observe: " + videoList.size(), Toast.LENGTH_SHORT).show();
                }
            });
//            Toast.makeText(this, "" + videoList.size(), Toast.LENGTH_SHORT).show();
            videoAdapter.setVideos(videoList);
        }

        recyclerViewVideos.setAdapter(videoAdapter);
    }
}