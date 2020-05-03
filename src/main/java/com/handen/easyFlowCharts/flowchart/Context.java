package com.handen.easyFlowCharts.flowchart;

import com.handen.easyFlowCharts.strategies.DrawStrategy;
import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

public class Context {

    private GraphicsContext gc;
    private DrawStrategy strategy;
    private Point currentPoint;

    public Context(GraphicsContext gc, int currentX, int currentY) {
        this.gc = gc;
        currentPoint = new Point(currentX, currentY);
    }

    public void setStrategy(DrawStrategy strategy) {
        this.strategy = strategy;
    }

    public void drawCurrentStrategy() {
        currentPoint =  strategy.draw(gc, currentPoint);
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = new Point(currentPoint);
    }

    //TODO перенести логику в FlowchartDrawer
    public void goToNextPage(int page) {
        currentPoint = new Point(page * (DrawConstants.LIST_HEIGHT / 2), 50);
    }
}