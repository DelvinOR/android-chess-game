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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GameVault extends AppCompatActivity {

    public ListView recordedGamesListView;
    public ArrayAdapter theAdapter;
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
                    sortByTitles();
                }else{
                    sortByDates();
                }
            }
        });

        // Whenever an item in the list view is clicked
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
        // Make a date string array
        // convert date string array to date Date array
        // Arrays.sort();
        int arrayLength = RealMainActivity.arrayOfGameTitles.size();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
        Date[] dates = new Date[arrayLength];
        String[] sortedGameDates = new String[arrayLength];

        for(int i = 0; i< arrayLength; i++){
            sortedGameDates[i] = RealMainActivity.gameTitlesAndDates.get(sortedGameTitlesArray[i]);
        }

        for(int i = 0; i < arrayLength; i++){
            try {
                dates[i] = sdf.parse(sortedGameDates[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Arrays.sort(dates);

        for(int i = 0; i < arrayLength; i++){
            sortedGameDates[i] = sdf.format(dates[i]);
        }

        // sortedGameDates contain correct dates in order

        for(int i = 0; i < arrayLength; i++){
            int index = RealMainActivity.arrayOfGameRecordedDates.indexOf(sortedGameDates[i]);
            sortedGameTitlesArray[i] = RealMainActivity.arrayOfGameTitles.get(index);
        }

        // sortedGameTitlesArray contains the correct game titles sorted by dates
        theAdapter.notifyDataSetChanged();

    }

    public void sortByTitles(){
        Arrays.sort(sortedGameTitlesArray);
        theAdapter.notifyDataSetChanged();
    }

    public void playVideo(String gameTitleClicked){
        Intent intent = new Intent(this, GameReplay.class);
        intent.putExtra("Game_Title",gameTitleClicked);
        startActivity(intent);
    }
}
