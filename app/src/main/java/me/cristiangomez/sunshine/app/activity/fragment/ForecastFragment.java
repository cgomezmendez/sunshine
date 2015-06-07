package me.cristiangomez.sunshine.app.activity.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.activity.DetailActivity;
import me.cristiangomez.sunshine.app.activity.listener.OnForecastDownloadListener;
import me.cristiangomez.sunshine.app.net.FetchWeatherTask;
import me.cristiangomez.sunshine.app.util.PreferenceController;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment implements OnForecastDownloadListener {
    private ListView mForecastListView;
    private ArrayAdapter mForecastAdapter;
    private static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
    PreferenceController mPrefrencesManager;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    public void initialize() {
        mPrefrencesManager = PreferenceController.getInstance();
    }

    public void initializeView(View view) {
        ArrayList<String> forecasts = new ArrayList<>();
        mForecastListView = (ListView) view.findViewById(R.id.listview_forecast);
        mForecastAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecasts);
        mForecastListView.setAdapter(mForecastAdapter);
        mForecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.cEXTRA_FORECAST, (String)mForecastAdapter.getItem(i));
                startActivity(intent);
            }
        });
        requestData();
    }

    public void requestData() {
        new FetchWeatherTask(this).execute(mPrefrencesManager.getLocationZipCode() );
    }

    @Override
    public void onForecastDownloaded(String[] forecasts) {
        ((ArrayAdapter) mForecastListView.getAdapter()).addAll(forecasts);
    }
}
