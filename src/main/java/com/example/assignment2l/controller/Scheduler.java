package com.example.assignment2l.controller;

import com.example.assignment2l.model.Server;
import com.example.assignment2l.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers=new ArrayList<>();
    private Strategy strategy;

    public Scheduler(Strategy.SelectionPolicy selectionPolicy,int maxNoServers) {
    changeStrategy(selectionPolicy);
    for(int i=0;i<maxNoServers;i++){
        servers.add(new Server(i));
        new Thread(servers.get(i)).start();
    }
    }

    public List<Server> getServers() {
        return servers;
    }

    public void changeStrategy(Strategy.SelectionPolicy selectionPolicy){
if(selectionPolicy==Strategy.SelectionPolicy.SHORTEST_QUEUE){
    this.strategy=new ShortestQueueStrategy();
}else{
    this.strategy=new TimeStrategy();
}
}
public void dispatchTask(Task task){
strategy.addTask(servers,task);
}

    public void notifyServers(){ //wakes up the threads
        for(Server server : servers){
            synchronized (server){
                server.notify();
            }
        }
    }
public void stopQueues(){
        boolean stop=true;
        for(Server server:servers){
            server.changeFlag(stop);
        }
        notifyServers();
}


    @Override
   public String toString() {
      String result="";
     for(int i=0;i<this.servers.size();i++){
       result+="Queue "+i+this.servers.get(i).toString()+"\n";
        }
       return result;
   }
}
