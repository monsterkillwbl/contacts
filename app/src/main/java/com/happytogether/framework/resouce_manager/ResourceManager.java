package com.happytogether.framework.resouce_manager;

import com.happytogether.framework.type.CallRecord;
import com.happytogether.framework.type.Contacts;

import java.util.List;

public class ResourceManager {

    private static ResourceManager _manager = new ResourceManager();
    private static IResourceManager _iResourceManager = null;

    public static boolean init(IResourceManager iResourceManager){
        _iResourceManager = iResourceManager;
        return true;
    }

    public static ResourceManager getInstance(){
        return _manager;
    }

    private ResourceManager(){}

    public List<Contacts> getAllContacts(){
        return _iResourceManager.getAllContacts();
    }
    public boolean addContacts(Contacts contacts){
        return _iResourceManager.addContacts(contacts);
    }
    public boolean removeContacts(Contacts contacts){
        return _iResourceManager.removeContacts(contacts);
    }
    public boolean modifyContacts(Contacts oldContacts, Contacts newContacts){
        return _iResourceManager.modifyContacts(oldContacts, newContacts);
    }

    public List<CallRecord> getAllCallRecord(){
        return _iResourceManager.getAllCallRecord();
    }
    public boolean addCallRecord(CallRecord record){
        return _iResourceManager.addCallRecord(record);
    }
    public boolean removeCallRecord(CallRecord record){
        return _iResourceManager.removeCallRecord(record);
    }
}
