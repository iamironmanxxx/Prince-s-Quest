package com.smartgenlabs.princequest;

/**
 *
 * Reused by Pankaj Vaghela on 30-09-2018.
 * Reused by Pankaj Vaghela on 04-05-2018.
 * Reused by Pankaj Vaghela on 02-04-2018.
 * written by Pankaj Vaghela on 29-01-2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPreference {

    public static final String LOG_TAG = "ScreenShot_SharedPref";

    public static final String IS_LOGGED_IN = "isLoggenIn";
    public static final String IS_FIRST_TIME_OPENED= "isFirstTimeOpened";
    public static final String NO_AGAIN_START_DIALOG= "noAgainStartDialog";

    Context context;
    SharedPreferences sp;

    public static final String PREFS_NAME = "ScreenshotCapturePrefs";

    public SharedPreference(Context context) {
        super();
        this.context=context;

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if(!sp.getBoolean("firstTimeSetting", false))
        {
            DefaultSetting();

            Editor editor = sp.edit();
            editor.putBoolean("firstTimeSetting", true);
            editor.commit();
        }
    }

    private void DefaultSetting()
    {
        set(IS_FIRST_TIME_OPENED,false);
        set(NO_AGAIN_START_DIALOG,false);

        set("firstTimeHome",false);
        set("firstTimeDBData",false);
        set("firstAdShown",false);
        set(IS_LOGGED_IN,false);

    }

    public void set(String key, String value) {
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(key, value); //3

        editor.apply(); //4
    }
    public void set(String key, boolean value) {
        Log.i(LOG_TAG,"set "+key+" = "+value);

        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(key, value); //3

        editor.apply(); //4
    }

    public void set(String key, int value){
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putInt(key, value); //3
        editor.apply(); //4
    }
    public void set(String key, float value){
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putFloat(key, value); //3
        editor.apply(); //4
    }

    public String getString(String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, null);

        Log.i(LOG_TAG,"get string "+key+" = "+text);
        return text;
    }
    public boolean getBoolean(String key) {
        SharedPreferences settings;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //Log.i(LOG_TAG,"get "+key+" = "+settings.getBoolean(key, false));
        return settings.getBoolean(key, false);
    }

    public int getInt(String key) {
        return getInt(key,0);
    }

    public int getInt(String key, int defValue) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defValue);
    }
    public float getFloat(String key) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, 0);
    }

    public void clearSharedPreference() {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    public void removeValue(String key) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.apply();
    }


    //*********************************
    // STATIC METHODS
    //
    // **************
    public static void DefaultSetting(Context context)
    {
        set(context,IS_FIRST_TIME_OPENED,false);
        set(context,NO_AGAIN_START_DIALOG,false);
    }

    public static void set(Context context, String key, String value) {
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(key, value); //3

        editor.apply(); //4
    }
    public static void set(Context context, String key, boolean value) {
        Log.i(LOG_TAG,"set "+key+" = "+value);

        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(key, value); //3

        editor.apply(); //4
    }

    public static void set(Context context,String key, int value){
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putInt(key, value); //3
        editor.apply(); //4
    }
    public static void set(Context context,String key, float value){
        Log.i(LOG_TAG,"set "+key+" = "+value);
        SharedPreferences settings;
        Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putFloat(key, value); //3
        editor.apply(); //4
    }

    public static String getString(Context context,String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, null);

        Log.i(LOG_TAG,"get string "+key+" = "+text);
        return text;
    }
    public static boolean getBoolean(Context context,String key) {
        SharedPreferences settings;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //Log.i(LOG_TAG,"get "+key+" = "+settings.getBoolean(key, false));
        return settings.getBoolean(key, false);
    }
    public static int getInt(Context context,String key) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }
    public static int getInt(Context context,String key, int defValue ) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defValue);
    }
    public static float getFloat(Context context,String key) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, 0);
    }


    public static void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    public static void removeValue(Context context,String key) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.apply();
    }


}
