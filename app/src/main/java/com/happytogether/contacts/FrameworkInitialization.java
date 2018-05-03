package com.happytogether.contacts;

import com.happytogether.contacts.processor.NonBlockingSingleThreadingProcessor;
import com.happytogether.contacts.processor.SingleThreadProcessor;
import com.happytogether.contacts.resource_manager.ResourceManagerSystem;
import com.happytogether.contacts.resource_manager.ResourceManagerTest;
import com.happytogether.framework.log.IDELogger;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;
import android.content.ContentResolver;

public class FrameworkInitialization {

    public FrameworkInitialization(MainActivity res){
        Processor.init(new NonBlockingSingleThreadingProcessor());
        ResourceManager.init(new ResourceManagerSystem(res));
        LogBus.getInstance().register(new IDELogger());
    }

}
