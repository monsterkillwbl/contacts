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
        //Processor.init(new SingleThreadProcessor()); 如果上一行的多线程不成功就用这一行的单线程
        //不过实际上一般是因为自己写了bug 所以往往多线程GG的时候单线程也会GG的
        ResourceManager.init(new ResourceManagerSystem(res));
        LogBus.getInstance().register(new IDELogger());
    }
}
