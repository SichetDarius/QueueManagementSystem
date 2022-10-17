package com.example.assignment2l.model;

public class Task {
    private final int id;
    private final int arrivalTime;
    private int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }
    public void decrementServiceTime(){
        if(this.serviceTime>0){
            this.serviceTime--;
        }
    }

    @Override
    public String toString() {
        return "(" +
                 + id +
                ", " + arrivalTime +
                ", " + serviceTime +
                ')';
    }
}
