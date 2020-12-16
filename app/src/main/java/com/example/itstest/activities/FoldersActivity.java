package com.example.itstest.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itstest.R;
import com.example.itstest.adapters.FoldersAdapter;
import com.example.itstest.data.Folder;
import com.example.itstest.data.MainViewModel;
import com.example.itstest.data.Video;

import java.util.ArrayList;
import java.util.List;

public class FoldersActivity extends AppCompatActivity {

    private RecyclerView recyclerFolders;
    private FoldersAdapter foldersAdapter;

    private ArrayList<Folder> folders = new ArrayList<>();
    private LiveData<List<Video>> videos;
    private ArrayList<Video> videoList = new ArrayList<>();

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);

        folders = getIntent().getParcelableArrayListExtra("folderList");

        recyclerFolders = findViewById(R.id.folders_recycler_view);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        videos = viewModel.getVideos();
        videos.observe(this, new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                videoList.addAll(videos);
            }
        });

        foldersAdapter = new FoldersAdapter();
        foldersAdapter.setFolders(folders);

        recyclerFolders.setLayoutManager(new LinearLayoutManager(this));
        recyclerFolders.setAdapter(foldersAdapter);

        foldersAdapter.setOnItemClickListener(new FoldersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Folder folder = folders.get(position);
                ArrayList<Video> folderVideos = new ArrayList<>();
                for (Video video : videoList){
                    if (video.getFolderName().equals(folder.getName())){
                        folderVideos.add(video);
                    }
                }
                Intent intent = new Intent(FoldersActivity.this, VideosListActivity.class);
                intent.putParcelableArrayListExtra("videoList", folderVideos);
                startActivity(intent);
            }
        });
    }
}