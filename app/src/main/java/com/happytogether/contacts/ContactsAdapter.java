package com.happytogether.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.happytogether.framework.type.Contacts;

import java.util.List;

/**
 * Created by Monsterkill on 2018/4/20.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts> {
    private int resourceID;
    public ContactsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Contacts> objects) {
        super(context, textViewResourceId, objects);
        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacts contacts = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView name = (TextView) view.findViewById(R.id.contacts_name);
        TextView number = (TextView) view.findViewById(R.id.contacts_number);


        name.setText(contacts.getName());
        number.setText(contacts.getNumber());
        return view;
    }
}
