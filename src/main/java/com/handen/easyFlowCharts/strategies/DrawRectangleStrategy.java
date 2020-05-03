package com.handen.easyFlowCharts.strategies;

import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawRectangleStrategy implements DrawStrategy {

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        gc.strokeRect(p.x, p.y, BLOCK_WIDTH, BLOCK_HEIGHT);
        return p;
    }
}