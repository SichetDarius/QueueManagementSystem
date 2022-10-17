package com.example.assignment2l.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TheController implements Initializable {

    ObservableList<String> choiceBoxList= FXCollections.observableArrayList("Queue Strategy","Time Strategy");

@FXML
    private TextField nrOfClientsField;
    @FXML
    private TextField nrOfQueuesField;
    @FXML
    private TextField maxTimeSimulationField;
    @FXML
    private TextField minTimeArrivalField;
    @FXML
    private TextField maxTimeArrivalField;
    @FXML
    private TextField minimumTimeServiceField;
    @FXML
    private TextField maximumTimeServiceField;
    @FXML
    private TextArea textAreaField;

    private Strategy.SelectionPolicy selectionPolicy;
    private SimulationManager simulation=new SimulationManager();
    @FXML
    private SplitMenuButton strategyField;
  //  private MenuItem
    @FXML
    private ChoiceBox choiceBoxField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
choiceBoxField.setValue("Queue Strategy");
choiceBoxField.setItems(choiceBoxList);
    }
    public void setTextArea(String s){
        textAreaField.setText(s);
    }
    public void onStartButtonClick(ActionEvent actionEvent){
        String s2=(String) choiceBoxField.getValue();
if(s2.equals("Queue Strategy")){
    selectionPolicy= Strategy.SelectionPolicy.SHORTEST_QUEUE;
}else {
    selectionPolicy= Strategy.SelectionPolicy.SHORTEST_TIME;
}
        String s=maxTimeSimulationField.getText();
        System.out.println(s);
simulation=new SimulationManager(Integer.parseInt(maxTimeSimulationField.getText()),
        Integer.parseInt(minimumTimeServiceField.getText()),
        Integer.parseInt(maximumTimeServiceField.getText()),
        Integer.parseInt(minTimeArrivalField.getText()),
        Integer.parseInt(maxTimeArrivalField.getText()),
        Integer.parseInt(nrOfClientsField.getText()),
        Integer.parseInt(nrOfQueuesField.getText()),
        selectionPolicy
        );
simulation.setController(this);
Thread thread=new Thread(simulation);
thread.start();

//String s2=(String) choiceBoxField.getValue();
//        System.out.println(s2);
    }
}
