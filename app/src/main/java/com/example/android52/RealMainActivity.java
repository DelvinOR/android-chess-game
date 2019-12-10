package com.example.android52;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RealMainActivity extends AppCompatActivity {

    // This will hold all the recorded games that the user decides to record
    // Since it is static, then we can use it in all java files in the same packages
    public static HashMap<String,String> recordedGamesHashMap = new HashMap<>();
    public static HashMap<String,String> gameTitlesAndDates = new HashMap<>();
;   public static ArrayList<String> arrayOfGameTitles = new ArrayList<>();
    public static ArrayList<String> arrayOfGameRecordedDates = new ArrayList<>();
    // Following fields will be used for screen recording
    public static final int REQUEST_CODE = 1000;
    public static final int REQUEST_PERMISSION = 1001;
    public static final SparseIntArray ORIENTATION = new SparseIntArray();


    public static MediaProjectionManager mMediaProjectManager;
    public static MediaProjection mMediaProjection;
    public static VirtualDisplay mVirtualDisplay;
    public static MediaProjectionCallback mMediaProjectionCallBack;
    public static MediaRecorder mMediaRecorder;

    public static int mScreenDensity;
    public static final int DISPLAY_WIDTH = 720;
    public static final int DISPLAY_HEIGHT = 1280;

    static{
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);

    }

    public static String mVideoURL; // holds link to the recorded game
    public static String dateOfRecordedGame;

    private TextView welcomeTextView;
    private Button playChessButton, gameVaultButton;
    private LinearLayout chessLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.densityDpi;

        mMediaRecorder = new MediaRecorder();
        mMediaProjectManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);


        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        playChessButton = (Button) findViewById(R.id.playChessButton);
        gameVaultButton = (Button) findViewById(R.id.gameVaultButton);
        chessLayout = (LinearLayout) findViewById(R.id.chessLayout);

        playChessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RealMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(RealMainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(RealMainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(RealMainActivity.this,Manifest.permission.RECORD_AUDIO)){
                        Snackbar.make(chessLayout, "Permission", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Enable", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(RealMainActivity.this,
                                                new String[] {
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                        Manifest.permission.RECORD_AUDIO
                                                },
                                                REQUEST_PERMISSION);
                                    }
                                });
                    }else{
                        ActivityCompat.requestPermissions(RealMainActivity.this,
                                new String[] {
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.RECORD_AUDIO
                                },
                                REQUEST_PERMISSION);
                    }

                }else{
                    toggleScreenShare();
                }

                playChess();
            }
        });

        gameVaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameVault();
            }
        });

    }

    public void toggleScreenShare(){
        initRecorder();
        recordScreen();
    }

    public void initRecorder(){
        try{
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
            dateOfRecordedGame = simpleDateFormat.format(new Date());

            mVideoURL = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
                    new StringBuilder("/ChessRecord-").append(dateOfRecordedGame).append(".mp4").toString();

            mMediaRecorder.setOutputFile(mVideoURL);
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512*1000);
            mMediaRecorder.setVideoFrameRate(30);

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATION.get(rotation+90);
            mMediaRecorder.setOrientationHint(orientation);
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordScreen(){
        if (mMediaProjection == null){
            startActivityForResult(mMediaProjectManager.createScreenCaptureIntent(),REQUEST_CODE);
            return;
        }
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    public VirtualDisplay createVirtualDisplay(){
        return mMediaProjection.createVirtualDisplay("MainActivity", DISPLAY_WIDTH,DISPLAY_HEIGHT,
                mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(),
                null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE){
            Toast.makeText(this, "Unk error", Toast.LENGTH_SHORT).show();
            return;
        }

        if(resultCode != RESULT_OK){
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            return;
        }

        mMediaProjectionCallBack = new MediaProjectionCallback();
        mMediaProjection = mMediaProjectManager.getMediaProjection(resultCode, data);
        mMediaProjection.registerCallback(mMediaProjectionCallBack, null);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    public void playChess(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openGameVault(){
        Intent openGameVault = new Intent(this, GameVault.class);
        startActivity(openGameVault);
    }

    public void stopRecordScreen(){
        if (mVirtualDisplay == null) {
            return;
        }

        // mVirtualDisplay contains the screen recording video
        mVirtualDisplay.release();
        destroyMediaProjection();

        // call for save recorded game pop out window
        Intent saveGameIntent = new Intent(this, SaveRecordedGame.class);
        startActivity(saveGameIntent);

    }

    public void destroyMediaProjection(){
        if (mMediaProjection != null){
            mMediaProjection.unregisterCallback(mMediaProjectionCallBack);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case REQUEST_PERMISSION:{
                if (grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                    toggleScreenShare();
                }else{
                    Snackbar.make(chessLayout, "Permission", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Enable", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(RealMainActivity.this,
                                            new String[] {
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.RECORD_AUDIO
                                            },
                                            REQUEST_PERMISSION);
                                }
                            });
                }
                return;
            }
        }
    }
}
