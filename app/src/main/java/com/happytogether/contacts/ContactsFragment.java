package com.happytogether.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

import com.happytogether.contacts.task.QueryAllContactsTask;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.Contacts;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Monsterkill on 2018/4/16.
 */

public class ContactsFragment extends Fragment{
    public ContactsAdapter adapter;
    public List<Contacts> contactsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_contacts, container, false);
        ListView contactsView = (ListView) rootView.findViewById(R.id.contacts_view);
        adapter = new ContactsAdapter(getActivity(), R.layout.item_contacts,contactsList);
        getContactsList(getActivity());
        contactsView.setAdapter(adapter);
        contactsView.setOnItemClickListener(new MyListener() );
        contactsView.setOnCreateContextMenuListener(new MyListener());
        return rootView;
    }

    public void getContactsList(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
//        Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, null);
//
//        if (cursor != null && cursor.getCount() > 0) {
//                for (cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) {
//                    String contastsName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));  //姓名
//                    String contastsNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  //号码
//                    // 去掉手机号中的空格和“-”
//                    contastsNumber = contastsNumber.replace(" ", "").replace("-", "").replace("+86", "");
//
//                    contactsList.add(new Contacts(contastsName, contastsNumber));
//                }
//                adapter.notifyDataSetChanged();
//            }
//            if (cursor == null) {
//                cursor.close();
//            }

        //界面中框架使用例子（显示所有联系人）
        // 注：为了开发效率和分工，界面本身不处理任何逻辑，一律使用Task来处理业务逻辑

        //1.实例化一个查询所有联系人的Task
        Task getAllContacts = new QueryAllContactsTask();
        //2.将Task交给Processor处理
        Processor.getInstance().process(getAllContacts);
        //3.等待Task执行完毕，期间可以做些其他事情避免界面卡顿，例如进度条
        while(!getAllContacts.finished()){
        }
        //4.使用Task执行完毕获取的数据更新界面
        contactsList.clear();
        contactsList.addAll((List<Contacts>)getAllContacts.getResult());
        adapter.notifyDataSetChanged();
    }

    class MyListener implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener{
        //点击拨打电话
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String phoneNumber = (String) ((TextView)view.findViewById(R.id.contacts_number)).getText();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        //长按弹出菜单
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("选择操作");
            menu.add(0,0,0,"编辑");
            menu.add(0,1,0,"删除");
        }
    }
    //菜单点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getContext(), "编辑联系人的方法", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(getActivity(), "删除联系人的方法", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
