module com.example.assignment2l {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment2l to javafx.fxml;
    exports com.example.assignment2l;
    exports com.example.assignment2l.controller;
    opens com.example.assignment2l.controller to javafx.fxml;
    opens com.example.assignment2l.model to javafx.fxml;
}