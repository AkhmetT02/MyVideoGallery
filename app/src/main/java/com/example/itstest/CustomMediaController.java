package com.example.itstest;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;

public class CustomMediaController extends MediaController {
    private ImageView fullScreen;
    private ImageView favouriteIv;
    private ImageView notFavouriteIv;

    private OnFullScreenClickListener onFullScreenClickListener;
    private  OnAddFavouriteClickListener onAddFavouriteClickListener;
    private OnRemoveFavouriteLister onRemoveFavouriteLister;

    public ImageView getFullScreen() {
        return fullScreen;
    }

    public ImageView getFavouriteIv() {
        return favouriteIv;
    }

    public ImageView getNotFavouriteIv() {
        return notFavouriteIv;
    }

    public CustomMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        fullScreen = new ImageView(super.getContext());
        favouriteIv = new ImageView(super.getContext());
        notFavouriteIv = new ImageView(super.getContext());

        fullScreen.setImageResource(R.drawable.ic_fullscreen);
        favouriteIv.setImageResource(R.drawable.ic_is_favourite);
        notFavouriteIv.setImageResource(R.drawable.ic_is_not_favourite);
        favouriteIv.setVisibility(INVISIBLE);

        FrameLayout.LayoutParams favouriteParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        favouriteParams.gravity = Gravity.LEFT | Gravity.TOP;
        addView(favouriteIv, favouriteParams);
        addView(notFavouriteIv, favouriteParams);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        addView(fullScreen, params);
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFullScreenClickListener != null){
                    onFullScreenClickListener.onFullScreenClick();
                }
            }
        });

        favouriteIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRemoveFavouriteLister != null){
                    onRemoveFavouriteLister.onRemoveFavourite();
                }
            }
        });
        notFavouriteIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAddFavouriteClickListener != null){
                    onAddFavouriteClickListener.onAddFavourite();
                }
            }
        });
    }

    public interface OnFullScreenClickListener{
        void onFullScreenClick();
    }
    public interface OnAddFavouriteClickListener{
        void onAddFavourite();
    }
    public interface  OnRemoveFavouriteLister{
        void onRemoveFavourite();
    }


    public void setOnFullScreenClickListener(OnFullScreenClickListener onFullScreenClickListener) {
        this.onFullScreenClickListener = onFullScreenClickListener;
    }

    public void setOnAddFavouriteClickListener(OnAddFavouriteClickListener onAddFavouriteClickListener) {
        this.onAddFavouriteClickListener = onAddFavouriteClickListener;
    }

    public void setOnRemoveFavouriteLister(OnRemoveFavouriteLister onRemoveFavouriteLister) {
        this.onRemoveFavouriteLister = onRemoveFavouriteLister;
    }
}
