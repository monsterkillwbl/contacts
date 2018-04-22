package com.happytogether.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.happytogether.framework.type.CallRecord;

import java.util.List;

/**
 * Created by Monsterkill on 2018/4/20.
 */

public class HistoryAdapter extends ArrayAdapter<CallRecord> {

    private int resourceID;
    public HistoryAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<CallRecord> objects) {
        super(context,textViewResourceId, objects);
        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CallRecord history = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView name = (TextView) view.findViewById(R.id.history_name);
        TextView number= (TextView) view.findViewById(R.id.history_number);
        TextView type = (TextView) view.findViewById(R.id.history_type);
        TextView date = (TextView) view.findViewById(R.id.history_date);
        TextView duration = (TextView) view.findViewById(R.id.history_duration);
        TextView location= (TextView) view.findViewById(R.id.history_location);

        name.setText("test");
        number.setText(history.getNumber());
        type.setText("test");
        date.setText(String.valueOf(history.getStartTime()));
        duration.setText(String.valueOf(history.getDuration()));
        location.setText("test");
        return view;
    }
}