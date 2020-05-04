package com.handen.easy_flow_charts.strategies;

import com.handen.easy_flow_charts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawRectangleStrategy implements DrawStrategy {

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        gc.strokeRect(p.x, p.y, BLOCK_WIDTH, BLOCK_HEIGHT);
        return p;
    }
}