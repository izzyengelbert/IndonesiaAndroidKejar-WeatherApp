package com.example.izzy.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.izzy.weatherapp.R;
import com.example.izzy.weatherapp.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izzyengelbert on 2/10/2018.
 */

public class WeatherHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iconMiniList)
    ImageView iconMiniList;
    @BindView(R.id.statusList)
    TextView statusList;
    @BindView(R.id.timeList)
    TextView timeList;
    @BindView(R.id.maxTempList)
    TextView maxTempList;
    @BindView(R.id.minTempList)
    TextView minTempList;

    public WeatherHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateUI(Weather weatherList){
        maxTempList.setText(String.valueOf(weatherList.getTempMax()));
        minTempList.setText(String.valueOf(weatherList.getTempMin()));
        statusList.setText(weatherList.getStatus());
        /*String time = weatherList.getTime();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm");
        try {
            Date timesFormat = defaultFormat.parse(time);
            time = timeFormat.format(timesFormat);
            timeList.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        timeList.setText(weatherList.getTime());




        if (weatherList.getStatus().equals("Rain")){
            iconMiniList.setImageResource(R.drawable.rain_mini);
        }else if (weatherList.getStatus().equals("Clouds")){
            iconMiniList.setImageResource(R.drawable.cold_mini);
        }else{
            iconMiniList.setImageResource(R.drawable.sun_mini);
        }

    }
}
