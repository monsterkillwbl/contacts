package com.happytogether.contacts.task;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.happytogether.framework.task.Task;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by wwc on 2018/4/22.
 */

public class QueryAllPeopleOperate extends Task {
    @Override
    public int exec() {
        //1.处理业务逻辑（注意，这里只是因为逻辑简单所以直接从ResourceManager获取即可，不要把业务逻辑写到ResourceManager中）
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //2.将处理结果写入到result
        setResult(callRecords);
        //3.输出日志，便于调试和测试
        LogBus.Log(LogBus.DEBUGTAGS, "get all call records success");
        //4.返回运行结果（很重要，这里使用int就是为了能区分出足够多的运行状态信息，当前已有的通用状态在Task定义）
        return Task.SUCCESS;
    }

    //search users' name by number查找联系人
    public void ContactByNumber (String phone,Context context)
    {
        Uri phoneUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phone));
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(phoneUri, new String[]{android.provider.ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        if(cursor.moveToFirst()){
            String name = cursor.getString(0);
            //获得名字，可以通过方法显示在View组件里
            Log.i(TAG, name);
        }
        cursor.close();

    }

    //添加联系人
    public void AddContacts(Context context,String name1,String phone1) throws Exception{
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        long contactid = ContentUris.parseId(resolver.insert(uri, values));

        uri = Uri.parse("content://com.android.contacts/data");

        String Su="success";
        //添加姓名
        values.put("raw_contact_id", contactid);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data1", name1);
        resolver.insert(uri, values);
        values.clear();
        Log.i(TAG,Su);

        //添加电话
        values.put("raw_contact_id", contactid);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data1", phone1);
        resolver.insert(uri, values);
        values.clear();
        Log.i(TAG,Su);

        /*添加相关类型加入type即可
         values.put("raw_contact_id", contactid);
        values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data1", type);
        resolver.insert(uri, values);
        values.clear();
         */
    }

    //删除联系人
    public void testDelete(String name,Context context)throws Exception{
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID},"display_name=?", new String[]{name}, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
            String Del="Delete Successfully";
            Log.i(TAG,Del);
        }
    }

    //更改联系人信息
    public void ChangeContacts(Context context)
    {


    }
}
