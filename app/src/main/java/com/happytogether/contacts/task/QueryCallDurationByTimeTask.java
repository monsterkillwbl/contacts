package com.happytogether.contacts.task;

import com.happytogether.contacts.util.MyUtil;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.CallRecord;

import java.util.List;

public class QueryCallDurationByTimeTask extends Task {

    private long _start_ts;
    private long _end_ts;

    public QueryCallDurationByTimeTask(long start_ts, long end_ts){
        _start_ts = start_ts;
        _end_ts = end_ts;
    }

    @Override
    public int exec() {
        //获取所有通话记录
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //过滤出时间段内的通话记录
        callRecords = MyUtil.filterCallRecordByTime(callRecords, _start_ts, _end_ts);
        //计算这些通话记录的总时长
        Integer callDuration = MyUtil.sumCallRecordsDuration(callRecords);
        //设置计算结果
        setResult(callDuration);
        //打印日志
        LogBus.Log(LogBus.DEBUGTAGS, "count all call records duration success");
        //返回运行成功结果(因为这个方法几乎不可能失败，所以直接SUCCESS)
        return Task.SUCCESS;
    }

    public static void test1(){

        //new FrameworkInitialization();

        Task test_task = new QueryCallDurationByTimeTask(0, 60);
        Processor.getInstance().process(test_task);
        while(!test_task.finished()){ }//加载完成后再进行下一步

        if(test_task.getStatus() == Task.SUCCESS){
            String s = "";
            /*for(CallRecord r : (List<CallRecord>)test_task.getResult()){
                s += (r.getDuration() + "\t");
            }*/
            s += test_task.getResult();
            LogBus.Log(LogBus.DEBUGTAGS, "finish task" + s);
        }
    }
}
