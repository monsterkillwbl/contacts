package com.happytogether.framework.processor;

import com.happytogether.framework.task.Task;

public class Processor {

    private static Processor _processor = new Processor();
    private static IProcess _iProcess = null;

    public static boolean init(IProcess processFunc){
        _iProcess = processFunc;
        return true;
    }

    public static Processor getInstance(){
        return _processor;
    }

    private Processor(){}

    public void process(Task task){
        _iProcess.process(task);
    }

    protected static Processor _getInstance() {
        return _processor;
    }

}
