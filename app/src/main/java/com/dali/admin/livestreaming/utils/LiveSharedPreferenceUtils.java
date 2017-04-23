package com.dali.admin.livestreaming.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @description:  SharedPreference存储
 *
 * @author: Andruby
 * @time: 2016/12/17 10:23
 */
public class LiveSharedPreferenceUtils {

    private static final String SP_NAME = "souyue_live";

    private static LiveSharedPreferenceUtils instance = new LiveSharedPreferenceUtils();

    LiveSharedPreferenceUtils() {

    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new LiveSharedPreferenceUtils();
        }
    }

    public static LiveSharedPreferenceUtils getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
    }
    public void clear(Context context){
        try {
            SharedPreferences sp = getSp(context);
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(Context context, String key, String def) {
        try {
            SharedPreferences sp = getSp(context);
            if (sp != null)
                def = sp.getString(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putString(Context context, String key, String val) {
        try {
            SharedPreferences sp = getSp(context);
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putString(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getInt(Context context, String key, int def) {
        try {
            SharedPreferences sp = getSp(context);
            if (sp != null)
                def = sp.getInt(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putInt(Context context, String key, int val) {
        try {
            SharedPreferences sp = getSp(context);
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putInt(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
