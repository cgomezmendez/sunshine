package me.cristiangomez.sunshine.app.activity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.activity.net.FetchWeatherTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private ListView mForecastListView;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeView(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initializeView(View view) {
        ArrayList<String> forecasts = new ArrayList<>();
        forecasts.add("Today - Sunny - 33 / 27");
        forecasts.add("Tomorrow - Foggy - 33 / 27");
        forecasts.add("Weds - Cloudy - 33 / 27");
        forecasts.add("Thurds - Rainy - 33 / 27");
        forecasts.add("Frid - Foggy - 33 / 27");
        forecasts.add("Sat - Sunny - 33 / 27");
        mForecastListView = (ListView) view.findViewById(R.id.listview_forecast);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecasts);
        mForecastListView.setAdapter(arrayAdapter);
        requestData();
    }

    public void requestData() {
        new FetchWeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
    }
}
