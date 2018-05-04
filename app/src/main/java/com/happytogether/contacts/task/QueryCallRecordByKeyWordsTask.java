package com.happytogether.contacts.task;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.happytogether.contacts.util.MyUtil;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import com.happytogether.framework.task.Task;
import com.happytogether.framework.type.CallRecord;

import java.util.List;

public class QueryCallRecordByKeyWordsTask extends Task {

    private String _KW;

    public QueryCallRecordByKeyWordsTask(String KW)
    {
        _KW = KW;
    }

    @Override
    public int exec(){
        //获取所有通话记录
        List callRecords = ResourceManager.getInstance().getAllCallRecord();
        //根据关键词过滤出指定的通话记录
        try {
            callRecords = MyUtil.filterCallRecordByKeyWords(callRecords, _KW);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        //设置计算结果
        setResult(callRecords);
        //打印日志
        LogBus.Log(LogBus.DEBUGTAGS, "filter all call records by KeyWords success");
        //返回运行成功结果(因为这个方法几乎不可能失败，所以直接SUCCESS)
        return Task.SUCCESS;
    }

    public static void test1(){
        //new FrameworkInitialization();
        Task test_task = new QueryCallRecordByKeyWordsTask("hjz");
        Processor.getInstance().process(test_task);
        while(!test_task.finished()){ }//加载完成后再进行下一步
        if(test_task.getStatus() == Task.SUCCESS){
            String s = "";
            for(CallRecord r : (List<CallRecord>)test_task.getResult()){
                s += (r.getStartTime() + "@" + r.getName() + "\n");
            }
            LogBus.Log(LogBus.DEBUGTAGS, "finish task" + s);
        }
    }
}
