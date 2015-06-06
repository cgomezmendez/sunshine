package me.cristiangomez.sunshine.app.activity.activity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.activity.activity.listener.OnForecastDownloadListener;
import me.cristiangomez.sunshine.app.activity.net.FetchWeatherTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment implements OnForecastDownloadListener {
    private ListView mForecastListView;
    private static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";

    public ForecastFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_forecast, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeView(view);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new FetchWeatherTask(this).execute(URL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void initializeView(View view) {
        ArrayList<String> forecasts = new ArrayList<>();
        mForecastListView = (ListView) view.findViewById(R.id.listview_forecast);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecasts);
        mForecastListView.setAdapter(arrayAdapter);
        requestData();
    }

    public void requestData() {
        new FetchWeatherTask(this).execute("94043");
    }

    @Override
    public void onForecastDownloaded(String[] forecasts) {
        ((ArrayAdapter)mForecastListView.getAdapter()).addAll(forecasts);
    }
}
