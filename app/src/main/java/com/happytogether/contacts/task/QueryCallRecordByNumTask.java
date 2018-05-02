package com.happytogether.contacts.task;

import com.happytogether.contacts.util.MyUtil;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.CallRecord;

import java.util.List;

public class QueryCallRecordByNumTask extends Task {

    private String _num;

    public QueryCallRecordByNumTask (String num)
    {
        _num = num;
    }

    @Override
    public int exec() {
        //获取所有通话记录
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //过滤出指定号码的通话记录
        callRecords = MyUtil.filterCallRecordByNum(callRecords, _num);
        //设置计算结果
        setResult(callRecords);
        //打印日志
        LogBus.Log(LogBus.DEBUGTAGS, "filter all call records by number success");
        //返回运行成功结果(因为这个方法几乎不可能失败，所以直接SUCCESS)
        return Task.SUCCESS;
    }

    public static void test1(){

        //new FrameworkInitialization();

        Task test_task = new QueryCallRecordByNumTask("66666602");
        Processor.getInstance().process(test_task);
        while(!test_task.finished()){ }//加载完成后再进行下一步

        if(test_task.getStatus() == Task.SUCCESS){
            String s = "";
            for(CallRecord r : (List<CallRecord>)test_task.getResult()){
                s += (r.getDuration() + "\t");
            }
            LogBus.Log(LogBus.DEBUGTAGS, "finish task" + s);
        }
    }
}
