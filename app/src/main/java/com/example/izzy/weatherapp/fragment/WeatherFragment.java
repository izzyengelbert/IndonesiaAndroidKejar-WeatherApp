package com.example.izzy.weatherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.izzy.weatherapp.R;
import com.example.izzy.weatherapp.WeatherAdapter;
import com.example.izzy.weatherapp.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String API_URL = "http://api.openweathermap.org/data/2.5/forecast/?lat=-6.1877386&lon=106.7400824&units=metric&APPID=1e3de4728980ad93f48b9ceb902be94d";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.tvCity) TextView tvCity;
    @BindView(R.id.tvDayDate) TextView tvDayDate;
    @BindView(R.id.tvMaxTemp) TextView tvMaxTemp;
    @BindView(R.id.tvMinTemp) TextView tvMinTemp;
    @BindView(R.id.tvStatus) TextView tvStatus;
    @BindView(R.id.imgCuaca) ImageView imgCuaca;

    RecyclerView recyclerView;

    ArrayList<Weather> weatherList = new ArrayList<>();
    WeatherAdapter weatherAdapter;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        recyclerView = view.findViewById(R.id.recycleList);
        weatherAdapter = new WeatherAdapter(weatherList,getContext());
        recyclerView.setAdapter(weatherAdapter);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("OnResponse Value", String.valueOf(response));
                try {
                    JSONArray weatherArray = response.getJSONArray("list");
                    JSONObject city = response.getJSONObject("city");
                    tvCity.setText(city.getString("name")+", "+city.getString("country"));

                    JSONObject firstObject = weatherArray.getJSONObject(0);
                    JSONObject dayAndDate = firstObject;
                    String dt_txt = dayAndDate.getString("dt_txt");

                    SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");

                    String time = dt_txt;
                    String date = dt_txt;

                    try {
                        Date timesFormat = defaultFormat.parse(time);
                        time = timeFormat.format(timesFormat);

                        Date datesFormat = defaultFormat.parse(date);
                        date = dateFormat.format(datesFormat);

                        tvDayDate.setText(date);
                        tvTime.setText(time);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    JSONObject main = firstObject.getJSONObject("main");
                    JSONArray weather = firstObject.getJSONArray("weather");
                    JSONObject weatherFirst = weather.getJSONObject(0);
                    String status = weatherFirst.getString("main");

                    tvMaxTemp.setText(main.getDouble("temp_max")+"°");
                    tvMinTemp.setText(main.getDouble("temp_min")+"°");
                    tvStatus.setText(status);

                    if (status.equals("Rain")){
                        imgCuaca.setImageResource(R.drawable.rain_large);
                    }else if (status.equals("Clouds")){
                        imgCuaca.setImageResource(R.drawable.coldly_large);
                    }else{
                        imgCuaca.setImageResource(R.drawable.sun_large);
                    }


                    int count = 10;

                    for (int x=1; x<count; x++){
                        JSONObject objectList = weatherArray.getJSONObject(x);
                        JSONObject mainObjectList = objectList.getJSONObject("main");
                        Double tempMaxList = mainObjectList.getDouble("temp_max");
                        Double tempMinList= mainObjectList.getDouble("temp_min");

                        JSONArray weatherArrayList = objectList.getJSONArray("weather");
                        JSONObject weatherObjectList = weatherArrayList.getJSONObject(0);
                        String statusList = weatherObjectList.getString("main");
                        String dateList = objectList.getString("dt_txt");

                        Weather weatherModel = new Weather(Integer.toString(x),statusList,tempMaxList,tempMinList,dateList);
                        weatherList.add(weatherModel);
                        weatherAdapter.notifyDataSetChanged();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("OnErrorResponse Value", String.valueOf(error));
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

        return view;
    }

}
