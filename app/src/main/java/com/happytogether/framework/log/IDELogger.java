package com.happytogether.framework.log;

import android.util.Log;

import java.util.Set;

public class IDELogger implements ILogger {

    @Override
    public void log(Set<String> tags, String log) {
        for(String tag : tags){
            Log.d(tag, log);
        }
    }
}
