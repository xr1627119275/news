package com.xr.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 16271 on 2018/3/6.
 */

public class SharedUtils {
    public static int getLastPostion(Context context,int threadId) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("lastPostion"+threadId, -1);
    }
    public static void  setLastPostion(Context context,int position,int threadId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt("lastPostion"+threadId,position).commit();

    }
}
