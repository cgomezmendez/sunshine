package me.cristiangomez.sunshine.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.cristiangomez.sunshine.R;
import me.cristiangomez.sunshine.app.Sunshine;

/**
 * Created by cristian on 06/06/15.
 */
public class PreferenceController {
    //========================================================
    //FIELDS
    //========================================================
    private static PreferenceController sInstance;
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    //========================================================
    //CONSTRUCTORS
    //========================================================
    private PreferenceController() {
        mContext = Sunshine.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }
    //========================================================
    //OVERRIDDEN METHODS
    //========================================================

    //========================================================
    //METHODS
    //========================================================

    //========================================================
    //INNER CLASSES
    //========================================================

    //========================================================
    //GETTERS AND SETTERS
    //========================================================
    public static PreferenceController getInstance() {
        if (sInstance == null) {
            sInstance = new PreferenceController();
        }
        return sInstance;
    }

    public String getLocationZipCode() {
        return mSharedPreferences.getString(mContext.getString(R.string.pref_location_key),
                mContext.getString(R.string.pref_location_default));
    }
}
