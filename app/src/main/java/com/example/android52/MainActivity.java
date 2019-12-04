package com.example.android52;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android52.controllers.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.main(null);
        setContentView(R.layout.activity_main);
    }
}
