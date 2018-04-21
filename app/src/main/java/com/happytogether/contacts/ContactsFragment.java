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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

import java.lang.reflect.GenericArrayType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Monsterkill on 2018/4/16.
 */

public class ContactsFragment extends Fragment implements AdapterView.OnItemClickListener{
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
        contactsView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        return rootView;
    }

    public void getContactsList(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, null);

        if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) {
                    String contastsName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));  //姓名
                    String contastsNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  //号码
                    // 去掉手机号中的空格和“-”
                    contastsNumber = contastsNumber.replace(" ", "").replace("-", "").replace("+86", "");

                    contactsList.add(new Contacts(contastsName, contastsNumber));
                }
                adapter.notifyDataSetChanged();
            }
            if (cursor == null) {
                cursor.close();
            }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String phoneNumber = (String) ((TextView)view.findViewById(R.id.contacts_number)).getText();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
