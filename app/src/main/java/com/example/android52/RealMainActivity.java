package com.example.android52;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RealMainActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button playChessButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        playChessButton = (Button) findViewById(R.id.playChessButton);

        playChessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playChess();
            }
        });

    }

    public void playChess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
