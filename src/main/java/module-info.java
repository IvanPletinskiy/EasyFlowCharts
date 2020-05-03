module easy.flow.charts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    exports com.handen.easy_flow_charts to javafx.graphics;
    exports com.handen.easy_flow_charts.mainScreen to javafx.fxml;
    opens com.handen.easy_flow_charts.mainScreen to javafx.fxml;


    /*
    exports com.handen.lab.controller;
    exports com.handen.lab.data;
    exports com.handen.lab.model;
    exports com.handen.lab.utils;
    exports com.handen.lab.view;
    opens com.handen.lab.controller to javafx.fxml;

     */
}