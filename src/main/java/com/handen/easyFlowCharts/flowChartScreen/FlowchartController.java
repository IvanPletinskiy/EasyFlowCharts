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

public class FlowchartController implements Initializable {

    public Button previous_button;
    public Label status_label;
    public Button next_button;
    public ScrollPane canvas_container;

    private int total = 0;
    private int current = 0;
    private List<Canvas> canvases = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addCanvas(Canvas canvas) {
        canvases.add(canvas);
        if(total == 0) {
            fillCanvasContainer();
        }
        total++;
        updateStatusLabel();
        updateButtons();
    }

    public void addCanvases(List<Canvas> canvases) {
        this.canvases.addAll(canvases);
        total += canvases.size();
    }

    private void updateButtons() {
        boolean isPreviousVisible = current != 0;
        previous_button.setVisible(isPreviousVisible);
        boolean isNextVisible = current != total;
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
        fillCanvasContainer();
        updateButtons();
    }

    public void onNextClicked(ActionEvent actionEvent) {
        current++;
        fillCanvasContainer();
        updateButtons();
    }

    private void fillCanvasContainer() {
        Canvas canvas = canvases.get(current);
        canvas_container.setContent(canvas);
    }
}
