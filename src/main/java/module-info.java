module EasyFlowCharts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    opens com.handen.easyFlowCharts.mainScreen;
    opens com.handen.easyFlowCharts.Nodes;
    exports com.handen.easyFlowCharts.Nodes;
    exports com.handen.easyFlowCharts;
    exports com.handen.easyFlowCharts.mainScreen;
    exports com.handen.easyFlowCharts.utils;
    exports com.handen.easyFlowCharts.flowchart;
}