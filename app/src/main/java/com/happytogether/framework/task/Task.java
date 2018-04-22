package com.happytogether.framework.task;

import android.support.annotation.NonNull;

public abstract class Task implements Comparable{

    public static final int SUCCESS = 0;
    public static final int WAIT = 1;
    public static final int ERROR = 2;
    public static final int IDLE = 3;
    public static final int RUNNING = 4;
    public static final int REUSE = 5;

    public static final int SYSTEM = 10;
    public static final int FRAMEWORK = 12;
    public static final int URGENT = 14;
    public static final int NORMAL = 15;
    public static final int BACK = 18;

    private int _status;
    private Object _result;
    private int _priority;
    private boolean _used;

    public Task(){
        _status = IDLE;
        _result = null;
        _priority = NORMAL;
        _used = false;
    }

    public int _exec(){
        if(_used){
            return REUSE;
        }
        _used = true;
        return exec();
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

    public boolean setPriority(int priority){
        _priority = priority;
        return true;
    }

    public int getPriority(){
        return _priority;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o == null){
            throw new NullPointerException();
        }
        if (o == this){
            return 0;
        }
        int o_priority = ((Task)o)._priority;
        if(_priority < o_priority){
            return -1;
        }
        if(_priority > o_priority){
            return 1;
        }
        return 0;
    }
}
