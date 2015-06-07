package me.cristiangomez.sunshine.app;

import android.app.Application;

/**
 * Created by cristian on 06/06/15.
 */
public class Sunshine extends Application {
    //========================================================
    //FIELDS
    //========================================================
    private static Sunshine sInstance;
    //========================================================
    //CONSTRUCTORS
    //========================================================

    //========================================================
    //OVERRIDDEN METHODS
    //========================================================

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    //========================================================
    //METHODS
    //========================================================

    //========================================================
    //INNER CLASSES
    //========================================================

    //========================================================
    //GETTERS AND SETTERS
    //========================================================
    public static Sunshine getInstance() {
        return sInstance;
    }
}
