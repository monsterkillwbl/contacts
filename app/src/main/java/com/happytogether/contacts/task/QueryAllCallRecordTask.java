package com.happytogether.contacts.task;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//业务逻辑编写例子：一律使用Task来执行业务逻辑，以来便于合作开发，而来便于下一阶段优化程序性能和使用体验
public class QueryAllCallRecordTask extends Task {

    @Override
    public int exec() {
        //1.处理业务逻辑（注意，这里只是因为逻辑简单所以直接从ResourceManager获取即可，不要把业务逻辑写到ResourceManager中）
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //2.将处理结果写入到result
        setResult(callRecords);
        //3.输出日志，便于调试和测试
        LogBus.Log(LogBus.DEBUGTAGS, "get all call records success");
        //4.返回运行结果（很重要，这里使用int就是为了能区分出足够多的运行状态信息，当前已有的通用状态在Task定义）
        return Task.SUCCESS;
    }
}
