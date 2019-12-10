package com.example.android52;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveRecordedGame extends AppCompatActivity {

    public EditText gameTitleEditText;
    public Button save_button, no_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_recorded_game_layout);

        gameTitleEditText = (EditText) findViewById(R.id.gameTitleEditText);
        save_button = (Button) findViewById(R.id.save_button);
        no_button = (Button) findViewById(R.id.no_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameTitle = gameTitleEditText.getText().toString();

                // We save the game title into the recordedGamesHashMap and arrayOfGameTitles
                RealMainActivity.recordedGamesHashMap.put(gameTitle, RealMainActivity.mVideoURL);
                RealMainActivity.gameTitlesAndDates.put(gameTitle, RealMainActivity.dateOfRecordedGame);
                RealMainActivity.arrayOfGameTitles.add(gameTitle);
                RealMainActivity.arrayOfGameRecordedDates.add(RealMainActivity.dateOfRecordedGame);


                backToHome();
            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });

    }

    public void backToHome(){
        Intent backToHomepage = new Intent(this, RealMainActivity.class);
        startActivity(backToHomepage);
    }
}
