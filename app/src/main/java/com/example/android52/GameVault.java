package com.example.android52;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class GameVault extends AppCompatActivity {

    public ListView recordedGamesListView;
    public ListAdapter theAdapter;
    public Spinner sortBySpinner;
    public ArrayList<String> sortedGameTitles;
    public String[] sortedGameTitlesArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vault_layout);

        recordedGamesListView = (ListView)findViewById(R.id.recordedGamesListView);
        sortBySpinner = (Spinner) findViewById(R.id.sortBySpinner);
        sortedGameTitles = new ArrayList<>(RealMainActivity.arrayOfGameTitles);
        sortedGameTitlesArray = sortedGameTitles.toArray(new String[sortedGameTitles.size()]);
        Arrays.sort(sortedGameTitlesArray);
        // Default in spinner is set to Sort by Title
        sortBySpinner.setSelection(0);
        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, sortedGameTitlesArray);// HOW TO CHANGE ADAPTER DATA
        recordedGamesListView.setAdapter(theAdapter);


        sortBySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> spinner, View view, int pos, long l) {
                String desiredSort = String.valueOf(spinner.getItemAtPosition(pos));
                if(desiredSort.equals("Sort by Title")){
                    // Do nothing
                    sortByTitles();
                }else{
                    sortByDates();
                }
            }
        });

        // NEED TO SORT THESE RECORDED GAMES BY DATE AND TITLE (USER CAN SELECT WHICH WAY TO SORT
        // Maybe add a Date field into RealMain Activity

        recordedGamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                // Whenever an game title is clicked on, we pass that game title string into extras and
                // send start the GameReplay actitivity
                String gameTitleClicked = String.valueOf(adapterView.getItemAtPosition(pos));

                playVideo(gameTitleClicked);
            }
        });

    }

    public void sortByDates(){

    }

    public void sortByTitles(){

    }

    public void playVideo(String gameTitleClicked){
        Intent intent = new Intent(this, GameReplay.class);
        intent.putExtra("Game_Title",gameTitleClicked);
        startActivity(intent);
    }
}
