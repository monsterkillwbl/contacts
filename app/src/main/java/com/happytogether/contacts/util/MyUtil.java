package com.happytogether.contacts.util;

import com.happytogether.framework.log.LogBus;

//工具方法在这里实现
public class MyUtil {

    public static long nowTs(){
        return 888888;
    }

    public static String converToPinyin(String chinese){
        if(chinese.equals("王维川")){
            return "wangweichuan";
        }
        if(chinese.equals("王博伦")){
            return "wangbolun";
        }
        return "";
    }

    public static boolean callTo(String number){
        LogBus.Log(LogBus.DEBUGTAGS, "calling to " + number);
        return true;
    }
}
