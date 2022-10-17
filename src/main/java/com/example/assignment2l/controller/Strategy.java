package com.example.assignment2l.controller;

import com.example.assignment2l.model.Server;
import com.example.assignment2l.model.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
    enum SelectionPolicy{
        SHORTEST_QUEUE, SHORTEST_TIME
    }
}