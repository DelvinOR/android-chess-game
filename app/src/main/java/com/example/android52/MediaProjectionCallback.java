package com.example.android52;

import android.media.projection.MediaProjection;
import com.example.android52.RealMainActivity;


public class MediaProjectionCallback extends MediaProjection.Callback {

    @Override
    public void onStop() {
        super.onStop();

        RealMainActivity.mMediaRecorder.stop();
        RealMainActivity.mMediaRecorder.reset();
        //RealMainActivity.mMediaProjection = null;
        RealMainActivity.destroyMediaProjection();
    }

}
