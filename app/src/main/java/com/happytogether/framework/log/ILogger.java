package com.happytogether.framework.log;

import java.util.Set;

public interface ILogger {

    public abstract void log(Set<String> tags, String log);

}
