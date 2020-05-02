package com.handen.easyFlowCharts;

import com.handen.easyFlowCharts.strategies.DrawStrategy;
import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

public class Context {

    private GraphicsContext gc;
    private DrawStrategy strategy;
    private Point currentPoint;

    public static final int PAGE_HEIGHT = 2400;

    public Context(GraphicsContext gc) {
        this.gc = gc;
        currentPoint = new Point(50, 50);
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

    public void goToNextPage(int page) {
        currentPoint = new Point(page * (PAGE_HEIGHT / 2), 50);
    }
}