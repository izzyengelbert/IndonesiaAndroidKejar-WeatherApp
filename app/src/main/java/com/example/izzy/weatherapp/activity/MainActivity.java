package com.example.izzy.weatherapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.izzy.weatherapp.R;
import com.example.izzy.weatherapp.fragment.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new WeatherFragment()).commit();
    }
}
