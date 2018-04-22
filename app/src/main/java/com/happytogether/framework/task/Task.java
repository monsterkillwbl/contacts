package com.happytogether.framework.task;

public abstract class Task {

    public static final int SUCCESS = 0;
    public static final int WAIT = 1;
    public static final int ERROR = 2;
    public static final int IDLE = 3;
    public static final int RUNNING = 4;

    private int _status;
    private Object _result;

    public Task(){
        _status = IDLE;
        _result = null;
    }

    public abstract int exec();

    public boolean setStatus(int status){
        _status = status;
        return true;
    }

    public int getStatus(){
        return _status;
    }

    public boolean finished(){
        return (_status != WAIT) && (_status != RUNNING);
    }

    public Object getResult(){
        return _result;
    }

    protected boolean setResult(Object result){
        _result = result;
        return true;
    }
}
