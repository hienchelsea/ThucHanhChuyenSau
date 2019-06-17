package com.example.cachua.photographerapp.View.config;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtils {

    private static String PREFERENCES_NAME = "PREFERENCES_NAME";

    public static void setInt(Context pContext, String key, int value) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context pContext, String key) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        return sp.getInt(key, 0);
    }

    public static void setFloat(Context pContext, String key, float value) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context pContext, String key) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        return sp.getFloat(key, 0F);
    }

    public static void setString(Context pContext, String key, String value) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context pContext, String key) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        return sp.getString(key, "");
    }


    public static void setBoolean(Context pContext, String key, boolean value) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context pContext, String key) {
        SharedPreferences sp = pContext.getSharedPreferences(PREFERENCES_NAME, 0);
        return sp.getBoolean(key, false);
    }



}
