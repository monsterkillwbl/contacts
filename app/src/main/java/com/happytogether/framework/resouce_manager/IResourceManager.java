package com.happytogether.framework.resouce_manager;

import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import java.util.List;

public interface IResourceManager {

    public abstract List<Contacts> getAllContacts();
    public abstract boolean addContacts(Contacts contacts);
    public abstract boolean removeContacts(Contacts contacts);
    public abstract boolean modifyContacts(Contacts oldContacts, Contacts newContacts);

    public abstract List<CallRecord> getAllCallRecord();
    public abstract boolean addCallRecord(CallRecord record);
    public abstract boolean removeCallRecord(CallRecord record);
}
