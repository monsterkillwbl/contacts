package com.happytogether.contacts;

import com.happytogether.contacts.processor.NonBlockingSingleThreadingProcessor;
import com.happytogether.contacts.processor.SingleThreadProcessor;
import com.happytogether.contacts.resource_manager.ResourceManagerTest;
import com.happytogether.framework.log.IDELogger;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;

public class FrameworkInitialization {

    public FrameworkInitialization(){
        Processor.init(new NonBlockingSingleThreadingProcessor());
        ResourceManager.init(new ResourceManagerTest());
        LogBus.getInstance().register(new IDELogger());
    }

}
