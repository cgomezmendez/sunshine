package me.cristiangomez.sunshine.app.activity.net;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.cristiangomez.sunshine.BuildConfig;
import me.cristiangomez.sunshine.app.activity.activity.listener.OnForecastDownloadListener;
import me.cristiangomez.sunshine.app.activity.util.ForecastParser;

/**
 * Created by cristian on 06/06/15.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

    //========================================================
    //FIELDS
    //========================================================
    private String cLOG_TAG = FetchWeatherTask.class.getSimpleName();
    private String cBASE_URL = "api.openweathermap.org";
    private Uri.Builder builder;
    private static final String cPARAM_QUERY = "q";
    private static final String cPARAM_MODE = "mode";
    private static final String cPARAM_METRIC = "metric";
    private static final String cPARAM_COUNT = "ctn";
    private static final String cMODE = "json";
    private static final String cUNITS = "metric";
    private static final int cCOUNT = 7;
    private static ForecastParser mParser = new ForecastParser();
    private OnForecastDownloadListener mOnForecastDownloadListener;
    //========================================================
    //CONSTRUCTORS
    //========================================================

    public FetchWeatherTask(OnForecastDownloadListener listener) {
        this.builder = new Uri.Builder();
        mParser = new ForecastParser();
        mOnForecastDownloadListener = listener;
    }

    //========================================================
    //OVERRIDDEN METHODS
    //========================================================

    @Override
    protected String[] doInBackground(String... strings) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(buildUrl(strings[0]).toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(cLOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(cLOG_TAG, "Error closing stream", e);
                }
            }
        }
        Log.v(cLOG_TAG, forecastJsonStr);
        try {
            return mParser.getWeatherDataFromJson(forecastJsonStr, cCOUNT);
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        mOnForecastDownloadListener.onForecastDownloaded(strings);
    }

    //========================================================
    //METHODS
    //========================================================
    private Uri buildUrl(String zipCode) {
        builder.scheme("http");
        builder.authority("api.openweathermap.org");
        builder.path("/data/2.5/forecast/daily");
        builder.appendQueryParameter(cPARAM_QUERY,zipCode);
        builder.appendQueryParameter(cPARAM_MODE, cMODE);
        builder.appendQueryParameter(cPARAM_COUNT, cCOUNT+"");
        builder.appendQueryParameter(cPARAM_METRIC, cUNITS);
        return builder.build();
    }
    //========================================================
    //INNER CLASSES
    //========================================================

    //========================================================
    //GETTERS AND SETTERS
    //========================================================
}
