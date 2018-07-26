package com.demo.account.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.account.App;


/**
 * 用于管理SharedPreferences的get/set
 */

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    private SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        this.sharedEditor = sharedPreferences.edit();
    }

    /**
     * 获取SharedPreferencesHelper
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
     * 根据关键字获取 Int 值
     *
     * @param key           关键字
     * @param defaultValue  默认值
     * @return
     */
    public int getInt(String key, int defaultValue) {
        return this.sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 存储Int 键值对
     *
     * @param key   关键字
     * @param value 值
     */
    public void putInt(String key, int value) {
        this.sharedEditor.putInt(key, value);
        this.sharedEditor.commit();
    }

    /**
     * 根据关键字获取 String 值
     *
     * @param key           关键字
     * @param defaultValue  默认值
     * @return
     */
    public String getString(String key, String defaultValue){
        return this.sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 存储String 键值对
     *
     * @param key   关键字
     * @param value 值
     */
    public void putString(String key, String value){
        this.sharedEditor.putString(key, value);
        this.sharedEditor.commit();
    }

    /**
     * 根据关键字获取 Boolean 值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue){
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 存储Boolean 键值对
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value){
        this.sharedEditor.putBoolean(key, value);
        this.sharedEditor.commit();
    }
    /**
     * 根据关键字获取 Long 值
     *
     * @param key 关键字
     * @param defaultValue
     * @return
     */
    public long getLong( String key, long defaultValue) {
        return this.sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 根据关键字存储
     *
     * @param key   关键字
     * @param value 值
     */
    public void putLong( String key, long value) {
        this.sharedEditor.putLong(key, value);
        this.sharedEditor.commit();
    }

}
