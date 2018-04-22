package com.happytogether.framework.log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LogBus {

    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARING = "WARING";
    public static final String ERROR = "ERROR";

    public static Set<String> DEBUGTAGS = new HashSet<String>(){{add(DEBUG);}};
    public static Set<String> INFOTAGS = new HashSet<String>(){{add(INFO);}};
    public static Set<String> WARINGTAGS = new HashSet<String>(){{add(WARING);}};
    public static Set<String> ERRORTAGS = new HashSet<String>(){{add(ERROR);}};

    private static LogBus _logbus = new LogBus();
    private List<ILogger> _loggers = new LinkedList<ILogger>();

    public static LogBus getInstance(){
        return _logbus;
    }

    public static void Log(Set<String> tags, String log){
        LogBus.getInstance().log(tags, log);
    }

    private LogBus(){}

    public boolean register(ILogger logger){
        _loggers.add(logger);
        return true;
    }

    public boolean unregister(ILogger logger){
        _loggers.remove(logger);
        return true;
    }

    public void log(Set<String> tags, String log){
        for (ILogger logger : _loggers) {
            logger.log(tags, log);
        }
    }
}
