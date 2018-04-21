package com.happytogether.contacts.task;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;

import java.util.HashSet;
import java.util.Set;

//介绍见获取所有通话记录的Task
public class QueryAllContactsTask extends Task {
    @Override
    public int exec() {
        setResult(ResourceManager.getInstance().getAllContacts());
        LogBus.Log(LogBus.DEBUGTAGS, "get all contact success");
        return Task.SUCCESS;
    }
}
