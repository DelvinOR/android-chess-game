package com.example.android52;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class GameReplay extends AppCompatActivity {

    public VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_replay_layout);

        // Get the game title that was passed from vault whenever an item was clicked
        String gameTitlePassedFromGameVault = getIntent().getStringExtra("Game_Title");
        RealMainActivity.mVideoURL = RealMainActivity.recordedGamesHashMap.get(gameTitlePassedFromGameVault);

        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse(RealMainActivity.mVideoURL));

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        // We will need an anchor view for our media controller
        mediaController.setAnchorView(videoView);
    }
}
