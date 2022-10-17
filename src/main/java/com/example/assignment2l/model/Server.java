package com.example.assignment2l.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private final int id;
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod=new AtomicInteger(0);
    private boolean flag=false;
    public Server(int id) {
        this.id = id;
        tasks=new LinkedBlockingQueue<>();
    }

    public int getId() {
        return id;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }
 public int queueSize(){
        return tasks.size();
 }

    public void addTask(Task newTask){
if(newTask!=null){
    tasks.add(newTask);
    waitingPeriod.addAndGet(newTask.getServiceTime());
}
    }
    @Override
    public void run(){
        Task currentTask=null;
    try{
        synchronized (this){
            wait();
        }
        while(!flag){
            if(currentTask==null){
                currentTask=tasks.peek(); //returns the head of the queue
            }else if(currentTask.getServiceTime()==1){
                tasks.remove(currentTask);
                currentTask=tasks.peek(); //returns the head of the queue
                waitingPeriod.decrementAndGet();
            }else{
                waitingPeriod.decrementAndGet();
                currentTask.decrementServiceTime();
            }
            synchronized (this){
                wait();
            }
        }
    } catch (InterruptedException e){
        e.printStackTrace();
    }

    }
   // public Task[] getTasks(){
   //    return getTasks();
   // }
public void changeFlag(boolean stop){
        this.flag=stop;
}

    @Override
    public String toString() {
        return "Queue " +
                 + id +
                ":" + tasks +
                " "
                ;
    }
}
