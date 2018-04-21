package com.happytogether.contacts.processor;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.IProcess;
import com.happytogether.framework.task.Task;

//处理器部分，实现IProcess接口即可，这里只是初版的单线程处理器，将来会进行更新以提高性能
public class SingleThreadProcessor implements IProcess {

    @Override
    public void process(Task task) {
        LogBus.Log(LogBus.DEBUGTAGS, "start one task");
        task.setStatus(Task.RUNNING);
        task.setStatus(task.exec());
        LogBus.Log(LogBus.DEBUGTAGS, "finish one task");
    }
}
