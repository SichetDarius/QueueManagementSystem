package com.example.assignment2l.controller;

import com.example.assignment2l.model.Server;
import com.example.assignment2l.model.Task;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int minimumProcessingTime;
    private int maximumProcessingTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private String ctrl="";
    private int numberOfTasks;
    private int numberOfQueues;
    private float averageProcessingTime=0;
    private float averageWaitingTime=0;
    private boolean stop=false;
    private final List<Task> taskList = new ArrayList<>();
    private Scheduler scheduler;
    private TheController controller;
private StringBuilder string=new StringBuilder();
    public SimulationManager(int timeLimit, int minimumProcessingTime, int maximumProcessingTime, int minimumArrivalTime, int maximumArrivalTime, int numberOfTasks, int numberOfQueues, Strategy.SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.minimumProcessingTime = minimumProcessingTime;
        this.maximumProcessingTime = maximumProcessingTime;
        this.minimumArrivalTime = minimumArrivalTime;
        this.maximumArrivalTime = maximumArrivalTime;
        this.numberOfTasks = numberOfTasks;
        this.numberOfQueues = numberOfQueues;
        scheduler = new Scheduler(selectionPolicy, numberOfQueues);
        generateRandomTasks();
    }

    public SimulationManager() {

    }

    public void setController(TheController controller) {
        this.controller = controller;
    }

    public void generateRandomTasks() {
        Random random = new Random();
        for (int i = 0; i < numberOfTasks; i++) {
            int arrivalTime = random.nextInt(maximumArrivalTime - minimumArrivalTime) + minimumArrivalTime;
            int processingTime = random.nextInt(maximumProcessingTime - minimumProcessingTime) + minimumProcessingTime;
            Task t = new Task(i, arrivalTime, processingTime);
            taskList.add(t);
            averageProcessingTime += taskList.get(i).getServiceTime();
        }
        averageProcessingTime /= (double) taskList.size();
    }
public void stop(){
        stop=true;
        scheduler.stopQueues();
}

    @Override
    public void run() {//taskList

        try {
            FileWriter fw = new FileWriter("C:/Users/40745/assignment2l/test");
            int currentTime = 0;
            while (currentTime < timeLimit && stop==false) {
                if (taskList.size() != 0) {
                    Task t = taskList.get(0);

                }
                System.out.println("Time: " + currentTime);
                ctrl += "Time: " + currentTime + "\n";
                fw.write("Time: " + currentTime + "\n");
                //string.append("Time: "+ currentTime+"\n");
                Iterator<Task> taskIterator = taskList.listIterator();
                while (taskIterator.hasNext()) {
                    Task task = taskIterator.next();
                    if (task.getArrivalTime() == currentTime) {
                        taskIterator.remove();
                        scheduler.dispatchTask(task);
                        System.out.println("Dispatched:" + task);
                        //string.append("Dispatched:"+task+"\n");
                        ctrl += "Dispatched:" + task + "\n";
                        fw.write("Dispatched:" + task + "\n");
                    } else {
                        System.out.println("Waiting:" + task);
                        //string.append("Waiting:"+task+"\n");
                        ctrl += "Waiting:" + task + "\n";
                        fw.write( "Waiting:" + task + "\n");
                    }
                }
                //string.append(ctrl);
                scheduler.notifyServers();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Server server : scheduler.getServers()) {
                   if(server.queueSize()>=1){
                       averageWaitingTime+= server.queueSize()-1;
                   }
                    System.out.println(server.toString());
                    // string.append(server.toString()+"\n");
                    ctrl += server.toString() + "\n";
                    fw.write( server.toString() + "\n");
                }

                string.append(ctrl);
                currentTime++;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.setTextArea(string.toString());
                        string = new StringBuilder();
                    }
                });

            }
            ctrl+="Average service time:"+averageProcessingTime+"\n";
            ctrl+="Average waiting time:"+averageWaitingTime/numberOfTasks;
            string.append(ctrl);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    controller.setTextArea(string.toString());
                    string = new StringBuilder();
                }
            });

            scheduler.stopQueues();
            System.out.println("Average service time:"+averageProcessingTime);
            fw.write("Average service time:"+averageProcessingTime+"\n");
            System.out.println("Average waiting time:"+averageWaitingTime/numberOfTasks);
            fw.write("Average waiting time:"+averageWaitingTime/numberOfTasks);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    }


