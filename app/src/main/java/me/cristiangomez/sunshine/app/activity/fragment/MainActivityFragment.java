package me.cristiangomez.sunshine.app.activity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import me.cristiangomez.sunshine.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ListView mForecastListView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeView(view);
        return view;
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
    }
}
