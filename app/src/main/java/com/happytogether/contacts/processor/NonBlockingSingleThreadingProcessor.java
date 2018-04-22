package com.happytogether.contacts.processor;

import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.IProcess;
import com.happytogether.framework.task.Task;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class NonBlockingSingleThreadingProcessor implements IProcess {

    private PriorityBlockingQueue<Task> _taskQueue;
    private _Processor _processor;

    public NonBlockingSingleThreadingProcessor(){
        _taskQueue = new PriorityBlockingQueue<Task>();
        _processor = new _Processor(_taskQueue);
        _processor.start();
    }

    @Override
    public void process(Task task) {
        task.setStatus(Task.WAIT);
        _taskQueue.put(task);
    }

    class _Processor extends Thread{

        private SingleThreadProcessor _processor = new SingleThreadProcessor();
        private PriorityBlockingQueue<Task> _queue;
        private boolean _quit = false;

        public _Processor(PriorityBlockingQueue<Task> task_queue){
            _queue = task_queue;
        }

        @Override
        public void run() {
            while(!_quit){
                try {
                    LogBus.Log(LogBus.DEBUGTAGS, "NonBlockingSingleThreadingProcessor - start process one");
                    _processor.process(_queue.take());
                    LogBus.Log(LogBus.DEBUGTAGS, "NonBlockingSingleThreadingProcessor - finish process one");
                }catch (Exception e){
                    LogBus.Log(LogBus.ERRORTAGS, "NonBlockingSingleThreadingProcessor - get task error");
                }
            }
        }
    }

}
