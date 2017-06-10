package xjh.livewallpaper.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import xjh.livewallpaper.Application.BaseApplication;

/**
 * Created by lovexujh on 2017/5/21
 */

public class SpUtils {
    private SharedPreferences sharedPreferences;

    public SpUtils() {
        sharedPreferences = BaseApplication.getInstance().getSharedPreferences(Contants.SpUtils.SP_WALLPAPER_NAME, Context.MODE_PRIVATE);
    }

    public SpUtils(String fileName) {
        sharedPreferences = BaseApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defaultValue) {
        String value = sharedPreferences.getString(key, defaultValue);
        return value;
    }

    public int getInt(String key, int defaultValue) {
        int value = sharedPreferences.getInt(key, defaultValue);
        return value;
    }

    public Set<String> getAllKeys() {
        Map<String, String> all = (Map<String, String>) sharedPreferences.getAll();
        return all.keySet();
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean getBoolean(String s) {
        return sharedPreferences.getBoolean(s, false);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
