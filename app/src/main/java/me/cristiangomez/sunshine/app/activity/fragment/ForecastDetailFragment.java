package me.cristiangomez.sunshine.app.activity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.activity.DetailActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastDetailFragment extends Fragment {
    private String mForecast;
    private TextView mForecastTextView;

    public ForecastDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        mForecast = bundle.getString(DetailActivity.cEXTRA_FORECAST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeView(view);
        return view;
    }

    public void initializeView(View view) {
        mForecastTextView = (TextView) view.findViewById(R.id.fragment_detail_tv_forecast);
        mForecastTextView.setText(mForecast);
    }
}
