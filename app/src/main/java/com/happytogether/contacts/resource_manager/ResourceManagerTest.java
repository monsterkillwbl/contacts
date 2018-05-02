package com.happytogether.contacts.resource_manager;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.IResourceManager;
import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ResourceManagerTest implements IResourceManager {

    List<Contacts> contactsList = null;
    List<CallRecord> callRecordList = null;
    static SQLiteDatabase db;
    static String address;
    static public void dbInit(String  Address) //sqlite数据库所在地址初始化
    {
        address = Address;
    }
    private boolean tabbleIsExist(SQLiteDatabase db)  //判断表之前是否存在
    {
        String query = "select * from contacts";
        try
        {
            Cursor cursor = db.rawQuery(query, null);
        }
        catch (Exception e)
        {
            return false;
        }
        return  true;
    }
    public ResourceManagerTest(){
        contactsList = new ArrayList<Contacts>();
        callRecordList = new ArrayList<CallRecord>();
        db = SQLiteDatabase.openOrCreateDatabase(address+"/my.db",null);
        if(!tabbleIsExist(db))
        {
            String builder = "create table contacts( Number varchar(20) NOT NULL,Name varchar(20) NOT NULL)";
            db.execSQL(builder);
            builder = "create table callrecord( Number varchar(20) NOT NULL,Status int NOT NULL," +
                    "Duration int NOT NULL,StartTime int NOT NULL)";
            db.execSQL(builder);
        }
        Cursor cursor = db.rawQuery("select * from callrecord",null);
        if(cursor.getCount() == 0)
        {
            Contacts contacts = new Contacts();
            contacts.setName("叶良辰");
            contacts.setNumber("测试样例");
            addContacts(contacts);
            CallRecord callRecord = new CallRecord();
            callRecord.setNumber("测试样例");
            callRecord.setStatus(CallRecord.NORMAL);
            callRecord.setDuration(60);
            addCallRecord(callRecord);
        }
    }

    @Override
    public List<Contacts> getAllContacts() {
        if(contactsList.isEmpty())
        {
            String items = "select * from contacts";
            Cursor cursor = db.rawQuery(items,null);
            while(cursor.moveToNext())
            {
                Contacts contact = new Contacts();
                int index = cursor.getColumnIndex("Name");
                contact.setName(cursor.getString(index));
                index =  cursor.getColumnIndex("Number");
                contact.setNumber(cursor.getString(index));
                contactsList.add(contact);
            }
        }
        LogBus.Log(LogBus.DEBUGTAGS, "get all contacts ok, num:" + String.valueOf(contactsList.size()));
        return contactsList;
    }

    @Override
    public boolean addContacts(Contacts contacts) {
        contactsList.add(contacts);
        db.execSQL("insert into contacts values(?,?)",new String[]{contacts.getNumber(),contacts.getName()});
        return true;
    }

    @Override
    public boolean removeContacts(Contacts contacts) {
        contactsList.remove(contacts);
        db.execSQL("delete from contacts where Number = " + contacts.getNumber());
        return true;
    }

    @Override
    public boolean modifyContacts(Contacts oldContacts, Contacts newContacts) {
        removeContacts(oldContacts);
        addContacts(newContacts);
        return true;
    }

    @Override
    public List<CallRecord> getAllCallRecord() {
        if(callRecordList.isEmpty())
        {
            String items = "select * from callrecord";
            Cursor cursor = db.rawQuery(items,null);
            while(cursor.moveToNext())
            {
                CallRecord callrecord = new CallRecord();
                int index = cursor.getColumnIndex("Number");
                callrecord.setNumber(cursor.getString(index));
                index =  cursor.getColumnIndex("Status");
                callrecord.setStatus(cursor.getInt(index));
                index =  cursor.getColumnIndex("StartTime");
                callrecord.setStartTime(cursor.getInt(index));
                index =  cursor.getColumnIndex("Duration");
                callrecord.setDuration(cursor.getInt(index));
                callRecordList.add(callrecord);
            }
        }
        return callRecordList;
    }

    @Override
    public boolean addCallRecord(CallRecord record) {
        db.execSQL("insert into callrecord values(?,?,?,?)",new String[]{record.getNumber(),
                record.getStatus()+"",record.getDuration()+"",record.getStartTime()+""});
        callRecordList.add(record);
        return true;
    }

    @Override
    public boolean removeCallRecord(CallRecord record) {
        db.execSQL("delete from callrecord where StartTime = " + record.getStartTime());
        callRecordList.remove(record);
        return true;
    }

    public static  void onDestroy()
    {
        if(db != null && db.isOpen())
        {
            db.close();
        }
    }
}
