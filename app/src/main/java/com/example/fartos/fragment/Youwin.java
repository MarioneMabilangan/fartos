package com.example.fartos.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.example.fartos.R;

import java.lang.reflect.Field;

import pl.droidsonroids.gif.GifImageView;

public class Youwin extends DialogFragment {

    private GifImageView mGifImageView;
    private ImageView mCloseImageView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youwin, container, false);
        mGifImageView = view.findViewById(R.id.gif_image_view);
        mCloseImageView = view.findViewById(R.id.close_image_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String gifUrl = "https://media.tenor.com/KA9EHBk-SfUAAAAd/warwick-lol.gif";
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(gifUrl)
                .apply(options)
                .into(mGifImageView);

        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGifImageView != null) {
            try {
                GifDrawable gifDrawable = (GifDrawable) mGifImageView.getDrawable();
                Field stateField = GifDrawable.class.getDeclaredField("mState");
                stateField.setAccessible(true);
                Object state = stateField.get(gifDrawable);
                Field isRecycledField = state.getClass().getDeclaredField("isRecycled");
                isRecycledField.setAccessible(true);
                boolean isRecycled = isRecycledField.getBoolean(state);
                if (!isRecycled) {
                    gifDrawable.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}