package com.example.itstest.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itstest.R;
import com.example.itstest.adapters.VideoAdapter;
import com.example.itstest.data.FavouriteVideo;
import com.example.itstest.data.MainViewModel;

import java.util.List;

public class FavouriteVideosActivity extends AppCompatActivity {

    private RecyclerView recyclerFavouriteVideos;

    private MainViewModel viewModel;
    private VideoAdapter adapter;
    private List<FavouriteVideo> favouriteVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_videos);

        recyclerFavouriteVideos = findViewById(R.id.favourite_videos_recycler);

        adapter = new VideoAdapter(FavouriteVideosActivity.this, FavouriteVideosActivity.this);

        recyclerFavouriteVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerFavouriteVideos.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        favouriteVideos = viewModel.getAllFavouriteVideosAsList();
        adapter.setFavouriteVideos(favouriteVideos);
    }
}