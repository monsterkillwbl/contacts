package com.happytogether.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.happytogether.contacts.task.QueryAllContactsTask;
import com.happytogether.contacts.util.History;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.CallRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Monsterkill on 2018/4/16.
 */

public class HistoryFragment extends Fragment{
    public HistoryAdapter adapter;
    public List<CallRecord> HistoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_history, container, false);
        ListView historyView = (ListView) rootView.findViewById(R.id.history_view);
        adapter = new HistoryAdapter(getActivity(), R.layout.item_history, HistoryList);
        getCallHistoryList(getActivity());
        historyView.setAdapter(adapter);
        historyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //点击拨号
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phoneNumber = (String) ((TextView)view.findViewById(R.id.history_number)).getText();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void getCallHistoryList(Activity activity) {
//        Cursor cs;
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},1);
//        }
//            cs = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
//                new String[]{
//                        CallLog.Calls.CACHED_NAME,  //姓名
//                        CallLog.Calls.NUMBER,    //号码
//                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
//                        CallLog.Calls.DATE,  //拨打时间
//                        CallLog.Calls.DURATION,   //通话时长
//                        CallLog.Calls.GEOCODED_LOCATION     //归属地
//                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
//
//            if (cs != null && cs.getCount() > 0) {
//                Date date = new Date(System.currentTimeMillis());
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String date_today = simpleDateFormat.format(date);
//                for (cs.moveToFirst(); (!cs.isAfterLast()); cs.moveToNext()) {
//                    String callName = cs.getString(0);  //名称
//                    String callNumber = cs.getString(1);  //号码
//
//                    //通话类型
//                    int callType = Integer.parseInt(cs.getString(2));
//                    String callTypeStr = "";
//                    switch (callType) {
//                        case CallLog.Calls.INCOMING_TYPE:
//                            callTypeStr = "呼入";
//                            break;
//                        case CallLog.Calls.OUTGOING_TYPE:
//                            callTypeStr = "呼出";
//                            break;
//                        case CallLog.Calls.MISSED_TYPE:
//                            callTypeStr = "未接";
//                            break;
//                        default:
//                            continue;
//                    }
//
//                    //拨打时间
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Date callDate = new Date(Long.parseLong(cs.getString(3)));
//                    String callDateStr = sdf.format(callDate);
//                    if (callDateStr.equals(date_today)) { //判断是否为今天
//                        sdf = new SimpleDateFormat("HH:mm");
//                        callDateStr = sdf.format(callDate);
//                    } else if (date_today.contains(callDateStr.substring(0, 7))) { //判断是否为当月
//                        sdf = new SimpleDateFormat("dd");
//                        int callDay = Integer.valueOf(sdf.format(callDate));
//
//                        int day = Integer.valueOf(sdf.format(date));
//                        if (day - callDay == 1) {
//                            callDateStr = "昨天";
//                        } else {
//                            sdf = new SimpleDateFormat("MM-dd");
//                            callDateStr = sdf.format(callDate);
//                        }
//                    } else if (date_today.contains(callDateStr.substring(0, 4))) { //判断是否为当年
//                        sdf = new SimpleDateFormat("MM-dd");
//                        callDateStr = sdf.format(callDate);
//                    }
//
//                    //通话时长
//                    int callDuration = Integer.parseInt(cs.getString(4));
//                    int min = callDuration / 60;
//                    int sec = callDuration % 60;
//                    String callDurationStr = "";
//                    if (sec > 0) {
//                        if (min > 0) {
//                            callDurationStr = min + "分" + sec + "秒";
//                        } else {
//                            callDurationStr = sec + "秒";
//                        }
//                    } else {
//                        callDurationStr = "未接通";
//                    }
//
//                    //归属地
//                    String callLocation = cs.getString(5);
//                    /**
//                     * callName 名字
//                     * callNumber 号码
//                     * callTypeStr 通话类型
//                     * callDateStr 通话日期
//                     * callDurationStr 通话时长
//                     */
//                    HistoryList.add(new History(callName,callNumber,callTypeStr,callDateStr,callDurationStr,callLocation));
//                }
//                adapter.notifyDataSetChanged();
//            }

        //显示所有通讯记录例子，介绍见显示联系人部分

        Task getAllCallRecords = new QueryAllContactsTask();
        Processor.getInstance().process(getAllCallRecords);
        while(!getAllCallRecords.finished()){
        }
        HistoryList.clear();
        HistoryList.addAll((List<CallRecord>) ResourceManager.getInstance().getAllCallRecord());
        adapter.notifyDataSetChanged();
    }

}
