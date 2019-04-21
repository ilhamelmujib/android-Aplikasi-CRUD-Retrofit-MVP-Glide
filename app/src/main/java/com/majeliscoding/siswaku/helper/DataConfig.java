package com.majeliscoding.siswaku.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.majeliscoding.siswaku.BuildConfig;

public class DataConfig {
    public static final String SP_CONFIG = "SenyumKu";
    public static final String TOKEN = "token";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String BORN_AT = "born_at";
    public static final String BIRTH_DATE = "birth_date";
    public static final String GENDER = "gender";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String DONATE = "donate";
    public static final String REMINDER = "reminder";
    public static final String TOKEN_ID = "token_id";
    public static final String IMAGE = "image";

    public static final String VERSION = "Versi " + BuildConfig.VERSION_NAME;
    public static final String AUTHORITIES_PROVIDER = BuildConfig.APPLICATION_ID + ".provider";

    public static void setLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("login", true);
        editor.apply();
    }

    public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        return sp.getBoolean("login", false);
    }

    public static void storeString(Context context, String key, String data) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (data != null) {
            editor.putString(key, data);
        } else {
            editor.putString(key, "");
        }
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        return sp.getString(key, "");
    }

    public static void storeInt(Context context, String key, int data) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, data);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        return sp.getInt(key, 0);
    }


    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        sp = context.getSharedPreferences(SP_CONFIG, 0);
        editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
