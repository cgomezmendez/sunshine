package me.cristiangomez.sunshine.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.activity.fragment.ForecastDetailFragment;
import me.cristiangomez.sunshine.app.util.PreferenceController;

public class DetailActivity extends AppCompatActivity {
    public static final String cEXTRA_FORECAST = "EXTRA_FORECAST";
    private PreferenceController mPreferencesController;
    private ShareActionProvider mShareActionProvider;
    private String mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPreferencesController = PreferenceController.getInstance();
        Bundle bundle = getIntent().getExtras();
        mForecast = bundle.getString(DetailActivity.cEXTRA_FORECAST);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share_forecast);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_show_location) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("geo:0,0?q=" + mPreferencesController.getLocationZipCode());
            intent.setData(uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        if (mShareActionProvider != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String message = mForecast+" "+getString(R.string.share_message);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            mShareActionProvider.setShareIntent(intent);
        }
    }

}
