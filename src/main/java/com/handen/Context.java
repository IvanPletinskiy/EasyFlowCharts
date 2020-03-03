package com.handen;

import com.handen.strategies.DrawStrategy;
import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

public class Context {
    private GraphicsContext gc;
    private DrawStrategy strategy;
    private Point currentPoint;

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
}