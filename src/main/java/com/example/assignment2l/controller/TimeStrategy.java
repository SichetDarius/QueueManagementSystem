package com.example.assignment2l.controller;

import com.example.assignment2l.model.Server;
import com.example.assignment2l.model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        Server server= servers.get(0);
        for(Server s:servers.subList(1, servers.size())){
            if(s.getWaitingPeriod()< server.getWaitingPeriod()){
            server=s;
            }
        }
        server.addTask(t);
    }
}
