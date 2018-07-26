package com.demo.account.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.account.App;


/**
 * manage SharedPreferences的get/set
 */

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    private SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        this.sharedEditor = sharedPreferences.edit();
    }

    /**
     * get SharedPreferencesHelper
     *
     * @return
     */
    public static SharedPreferencesHelper getInstance(){
        return SharedPreferencesHelperHolder.INSTANCE;
    }

    private static class SharedPreferencesHelperHolder{
        private static final SharedPreferencesHelper INSTANCE =
                new SharedPreferencesHelper(App.getContext());
    }

    /**
     * get int by key
     *
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
        return this.sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * store int-key pair
     *
     * @param key   key
     * @param value value
     */
    public void putInt(String key, int value) {
        this.sharedEditor.putInt(key, value);
        this.sharedEditor.commit();
    }

    /**
     * get string by key
     *
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public String getString(String key, String defaultValue){
        return this.sharedPreferences.getString(key, defaultValue);
    }

    /**
     * store string-key pair
     *
     * @param key   key
     * @param value value
     */
    public void putString(String key, String value){
        this.sharedEditor.putString(key, value);
        this.sharedEditor.commit();
    }

    /**
     * get boolean value by key
     *
     * @param key key
     * @param defaultValue defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue){
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * store boolean-key pair
     *
     * @param key key
     * @param value value
     */
    public void putBoolean(String key, boolean value){
        this.sharedEditor.putBoolean(key, value);
        this.sharedEditor.commit();
    }
    /**
     * get lone value by key
     *
     * @param key key
     * @param defaultValue defaultValue
     * @return
     */
    public long getLong( String key, long defaultValue) {
        return this.sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * store value by key
     *
     * @param key   key
     * @param value value
     */
    public void putLong( String key, long value) {
        this.sharedEditor.putLong(key, value);
        this.sharedEditor.commit();
    }

}
