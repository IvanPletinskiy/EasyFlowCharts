package com.handen.easyFlowCharts.flowChartScreen;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class FlowchartController implements Initializable {

    public Button previous_button;
    public Label status_label;
    public Button next_button;
    public ScrollPane canvas_container;
    public Label content_loading_label;

    private int total = 0;
    private int current = 0;
    public List<Canvas> canvases = new LinkedList<>();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            //Get how much scroll was done in Y axis.
            double delta = event.getDeltaY();
            //Add it to the Z-axis location.
            canvas_container.translateZProperty().set(canvas_container.getTranslateZ() + delta);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addCanvases(List<Canvas> canvases) {
        this.canvases.addAll(canvases);
        total += canvases.size();
        update();
    }

    private void update() {
        fillCanvasContainer();
        updateButtons();
        updateStatusLabel();

        content_loading_label.setVisible(canvases.isEmpty());
    }

    private void updateButtons() {
        boolean isPreviousVisible = current != 0;
        previous_button.setVisible(isPreviousVisible);
        boolean isNextVisible = current != total - 1;
        next_button.setVisible(isNextVisible);
    }

    private void updateStatusLabel() {
        String status;
        if(total == 0) {
            status = (current) + "/" + total;
        }
        else {
            status = (current + 1) + "/" + total;
        }

        status_label.setText(status);
    }

    public void onPreviousClicked(ActionEvent actionEvent) {
        current--;
        update();
    }

    public void onNextClicked(ActionEvent actionEvent) {
        current++;
        update();
    }

    private void fillCanvasContainer() {
        Canvas canvas = canvases.get(current);
        //canvas.setScaleX(2);
      //  canvas.setScaleX(5);
       // canvas.setScaleY(5);
        canvas_container.setContent(canvas);
    }
}
