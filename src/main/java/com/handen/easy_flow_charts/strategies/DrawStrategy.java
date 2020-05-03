package com.handen.easy_flow_charts.strategies;

import com.handen.easy_flow_charts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

public interface DrawStrategy {

    /**
     * Draw Element(ex arrow, rectangle)
     * @param p start p(left top corner)
     * @return endpoint for next Node
     */
    Point draw(GraphicsContext gc, Point p);
}
