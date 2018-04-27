package com.happytogether.contacts.util;

import android.content.Context;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static List <CallRecord> filterCallRecordByTime(List <CallRecord> callRecords, long start_ts, long end_ts) {
        Iterator it = callRecords.iterator();
        List <CallRecord> reRecord = new ArrayList<CallRecord>();
        while(it.hasNext())
        {
            CallRecord theRecord = (CallRecord)it.next();
            long startTime = theRecord.getStartTime();
            long endTime = startTime + theRecord.getDuration();
            if(startTime >= start_ts && endTime <= end_ts)
                reRecord.add(theRecord);
        }
        return reRecord;
    }

    public static Integer sumCallRecordsDuration(List callRecords) {
        Iterator it = callRecords.iterator();
        Integer count = 0;
        while(it.hasNext())
        {
            CallRecord theRecord = (CallRecord)it.next();
            count += theRecord.getDuration();
        }
        return count;
    }

    public static List filterCallRecordByNum(List callRecords, String num) {
        Iterator it = callRecords.iterator();
        List <CallRecord> reRecord = new ArrayList<CallRecord>();
        while(it.hasNext())
        {
            CallRecord theRecord = (CallRecord)it.next();
            if(num.equals(theRecord.getNumber()))
                reRecord.add(theRecord);
        }
        return reRecord;
    }

    public static String findNumberByname(List contacts, String name) {
        Iterator it = contacts.iterator();
        while(it.hasNext())
        {
            Contacts thecontact = (Contacts)it.next();
            if(thecontact.getName().equals(name))
                return thecontact.getNumber();
        }
        return null;
    }
}
