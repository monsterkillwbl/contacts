package com.happytogether.contacts.task;

import com.happytogether.contacts.FrameworkInitialization;
import com.happytogether.contacts.util.MyUtil;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.CallRecord;

import java.util.List;

public class QueryCallRecordByNameTask extends Task {

    private String _name;

    public QueryCallRecordByNameTask(String name){
        _name = name;
    }

    @Override
    public int exec() {
        //获取所有联系人
        List contacts = ResourceManager.getInstance().getAllContacts();
        //获取该联系人的电话号码
        String _num = MyUtil.findNumberByname(contacts, _name);
        //获取所有通话记录
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //过滤出指定号码的通话记录
        callRecords = MyUtil.filterCallRecordByNum(callRecords, _num);
        //设置计算结果
        setResult(callRecords);
        //打印日志
        LogBus.Log(LogBus.DEBUGTAGS, "filter all call records by name success");
        //返回运行成功结果(因为这个方法几乎不可能失败，所以直接SUCCESS)
        return Task.SUCCESS;
    }

    public static void test1(){

        //new FrameworkInitialization();

        Task test_task = new QueryCallRecordByNameTask("王博伦");
        Processor.getInstance().process(test_task);
        while(!test_task.finished()){ }//加载完成后再进行下一步

        if(test_task.getStatus() == Task.SUCCESS){
            String s = "";
            for(CallRecord r : (List<CallRecord>)test_task.getResult()){
                s += (r.getNumber() + "\t");
            }
            LogBus.Log(LogBus.DEBUGTAGS, "finish task" + s);
        }
    }
}
