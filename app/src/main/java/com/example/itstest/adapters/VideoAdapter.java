package com.example.itstest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.itstest.R;
import com.example.itstest.activities.VideoViewActivity;
import com.example.itstest.data.FavouriteVideo;
import com.example.itstest.data.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> videos;
    private Context context;
    private Activity activity;

    public VideoAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        videos = new ArrayList<>();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }
    public void setFavouriteVideos(List<FavouriteVideo> favouriteVideos){
//        this.videos.clear();
        this.videos.addAll(favouriteVideos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);

        Glide.with(context)
             .load(video.getPath())
             .apply(new RequestOptions()
             .placeholder(R.mipmap.ic_launcher)
             .fitCenter())
             .into(holder.videoThumb);
        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(video.getDuration()),
                TimeUnit.MILLISECONDS.toMinutes(video.getDuration()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(video.getDuration())), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(video.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(video.getDuration())));
        holder.videoDuration.setText(time);
        holder.videoTitle.setText(video.getTitle());

        double sizeAsMB = video.getSize() / 1048576;
        if (sizeAsMB > 1024){
            sizeAsMB /= 1024;
            int dotInd = Double.toString(sizeAsMB).indexOf('.');
            holder.videoSize.setText(Double.toString(sizeAsMB).substring(0,dotInd + 2) + "GB");
        } else {
            int dotInd = Double.toString(sizeAsMB).indexOf('.');
            holder.videoSize.setText(Double.toString(sizeAsMB).substring(0,dotInd + 2) + "MB");
        }
        holder.videoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_anim);
                holder.itemView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(context, VideoViewActivity.class);
                        intent.putExtra("position", position);
                        activity.startActivity(intent);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        private ImageView videoThumb;
        private TextView videoDuration;
        private TextView videoTitle;
        private TextView videoSize;
        private ConstraintLayout videoItem;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumb = itemView.findViewById(R.id.video_thumb_item);
            videoDuration = itemView.findViewById(R.id.video_duration_tv);
            videoTitle = itemView.findViewById(R.id.video_title_tv);
            videoSize = itemView.findViewById(R.id.video_size_tv);
            videoItem = itemView.findViewById(R.id.video_item_layout);
        }
    }
}
