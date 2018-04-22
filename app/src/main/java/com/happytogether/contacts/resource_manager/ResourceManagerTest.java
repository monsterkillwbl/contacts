package com.happytogether.contacts.resource_manager;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.IResourceManager;
import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import java.util.ArrayList;
import java.util.List;

//实现IResourceManager的接口即可，这里只是演示所以写死的假数据
public class ResourceManagerTest implements IResourceManager {

    List<Contacts> contactsList = null;
    List<CallRecord> callRecordList = null;

    public ResourceManagerTest(){
        contactsList = new ArrayList<>();

        Contacts contacts1 = new Contacts();
        contacts1.setName("王博伦");
        contacts1.setNumber("6666601");
        contactsList.add(contacts1);

        Contacts contacts2 = new Contacts();
        contacts2.setName("王维川");
        contacts2.setNumber("6666602");
        contactsList.add(contacts2);

        callRecordList = new ArrayList<CallRecord>();

        CallRecord callRecord = new CallRecord();
        callRecord.setNumber("66666602");
        callRecord.setStatus(CallRecord.NORMAL);
        callRecord.setDuration(60);
        callRecordList.add(callRecord);

        CallRecord callRecord1 = new CallRecord();
        callRecord1.setNumber("6666601");
        callRecord1.setStatus(CallRecord.NORMAL);
        callRecord1.setDuration(45);
        callRecordList.add(callRecord1);

    }

    @Override
    public List<Contacts> getAllContacts() {
        LogBus.Log(LogBus.DEBUGTAGS, "get all contacts ok, num:" + String.valueOf(contactsList.size()));
        return contactsList;
    }

    @Override
    public boolean addContacts(Contacts contacts) {
        contactsList.add(contacts);
        return true;
    }

    @Override
    public boolean removeContacts(Contacts contacts) {
        contactsList.remove(contacts);
        return true;
    }

    @Override
    public boolean modifyContacts(Contacts oldContacts, Contacts newContacts) {
        contactsList.remove(oldContacts);
        contactsList.add(newContacts);
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
        callRecordList.remove(record);
        return true;
    }
}
