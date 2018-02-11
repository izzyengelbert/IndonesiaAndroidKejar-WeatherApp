package com.example.izzy.weatherapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.izzy.weatherapp.activity.MainActivity;
import com.example.izzy.weatherapp.fragment.DetailFragment;
import com.example.izzy.weatherapp.model.Weather;

import java.util.ArrayList;

/**
 * Created by izzyengelbert on 2/10/2018.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

    private ArrayList<Weather> weathers;
    private Context context;

    public WeatherAdapter(ArrayList<Weather> weathers, Context context){
        this.weathers = weathers;
        this.context = context;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather,parent,false);
        return new WeatherHolder(card);
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {
        final Weather weather = weathers.get(position);
        holder.updateUI(weather);

        holder.itemView.findViewById(R.id.cvWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time =  weather.getTime();
                Log.d("Test",time);

                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                DetailFragment detailFragment = new DetailFragment();
                fragmentManager.beginTransaction().replace(R.id.frameLayout,detailFragment).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putString("ID",weather.getId());
                bundle.putParcelable("WeatherObject",weather);
                detailFragment.setArguments(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }
}
