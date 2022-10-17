package com.example.assignment2l.controller;

import com.example.assignment2l.model.Server;
import com.example.assignment2l.model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task t) {
        Server server=servers.get(0);
        for(Server s:servers.subList(1, servers.size())){
            if(s.queueSize()<server.queueSize()){
                server=s;
            }
        }
        server.addTask(t);
    }
}
