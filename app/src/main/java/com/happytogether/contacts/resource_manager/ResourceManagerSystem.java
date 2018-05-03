package com.happytogether.contacts.resource_manager;

import com.happytogether.contacts.*;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.IResourceManager;
import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.CallLog;
import java.util.ArrayList;
import java.util.List;

public class ResourceManagerSystem implements IResourceManager {
    List<Contacts> contactsList = null;
    List<CallRecord> callRecordList = null;
    MainActivity _res = null;
    public ResourceManagerSystem(MainActivity res)
    {
        contactsList = new ArrayList<Contacts>();
        callRecordList = new ArrayList<CallRecord>();
        _res = res;
        initContacts();
        initCallRecord();
    }
    public void initCallRecord()
    {
        ContentResolver cr = _res.getContentResolver();
        Cursor cs = cr.query(CallLog.Calls.CONTENT_URI, new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,    //号码
                CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                CallLog.Calls.DATE,  //拨打时间
                CallLog.Calls.DURATION   //通话时长
        }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        if (cs.getCount() > 0)
        {
            while (cs.moveToNext())
            {
                String id = cs.getString(cs.getColumnIndex(CallLog.Calls._ID));
                String name =  cs.getString(cs.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cs.getString(cs.getColumnIndex(CallLog.Calls.NUMBER));
                long dateLong = cs.getLong(cs.getColumnIndex(CallLog.Calls.DATE));
                int duration = cs.getInt(cs.getColumnIndex(CallLog.Calls.DURATION));
                int type = cs.getInt(cs.getColumnIndex(CallLog.Calls.TYPE));
                CallRecord call = new CallRecord();
                call.setId(id);
                call.setName(name);
                call.setNumber(number);
                call.setStartTime(dateLong);
                call.setStatus(type);
                call.setDuration(duration);
                callRecordList.add(call);
            }
            cs.close();
        }
    }
    public  void initContacts()
    {
        ContentResolver cr = _res.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                Contacts ct = new Contacts();
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String rawContactsId = "";
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                ct.setId(id);
                Cursor rawContactsIdCur = cr.query(RawContacts.CONTENT_URI, null, RawContacts.CONTACT_ID +" = ?", new String[]{id}, null);
                if (rawContactsIdCur.moveToFirst())
                {
                    rawContactsId = rawContactsIdCur.getString(rawContactsIdCur.getColumnIndex(RawContacts._ID));
                }
                rawContactsIdCur.close();
                ct.setName(name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor PhoneCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID +" = ?",
                            new String[]{rawContactsId}, null);
                    if (PhoneCur.moveToNext())
                    {
                        String number = PhoneCur.getString(PhoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String numberType = PhoneCur.getString(PhoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        ct.setNumber(number);
                    }
                    PhoneCur.close();
                }
                if(ct.getNumber() == "") continue;
                contactsList.add(ct);
            }
        }
        cur.close();
    }
    @Override
    public List<Contacts> getAllContacts() {

        LogBus.Log(LogBus.DEBUGTAGS, "get all contacts ok, num:" + String.valueOf(contactsList.size()));
        return contactsList;
    }

    @Override
    public boolean addContacts(Contacts contacts) {
        try
        {
            ContentValues values = new ContentValues();

            // 下面的操作会根据RawContacts表中已有的rawContactId使用情况自动生成新联系人的rawContactId
            Uri rawContactUri = _res.getContentResolver().insert(RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);

            // 向data表插入姓名数据
            if (contacts.getName() != "")
            {
                values.clear();
                values.put(Data.RAW_CONTACT_ID, rawContactId);
                values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                values.put(StructuredName.GIVEN_NAME, contacts.getName());
                _res.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            }
            // 向data表插入电话数据
            if (contacts.getNumber() != "")
            {
                values.clear();
                values.put(Data.RAW_CONTACT_ID, rawContactId);
                values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                values.put(Phone.NUMBER, contacts.getNumber());
                values.put(Phone.TYPE, Phone.TYPE_MOBILE);
                _res.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
            }
        }

        catch (Exception e)
        {
            return false;
        }
        contactsList.add(contacts);
        return true;
    }

    @Override
    public boolean removeContacts(Contacts contacts) {
        _res.getContentResolver().delete(ContentUris.withAppendedId(RawContacts.CONTENT_URI,Long.parseLong(contacts.getId())), null, null);
        contactsList.remove(contacts);
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
        return callRecordList;
    }

    @Override
    public boolean addCallRecord(CallRecord record) {
        callRecordList.add(record);
        return true;
    }

    @Override
    public boolean removeCallRecord(CallRecord record) {
        _res.getContentResolver().delete(CallLog.Calls.CONTENT_URI, CallLog.Calls._ID+"=?", new String[]{record.getId()+""});
        callRecordList.remove(record);
        return true;
    }

}
