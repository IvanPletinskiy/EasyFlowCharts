package com.handen.easyFlowCharts.strategies;

import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawParallelogramStrategy implements DrawStrategy{
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startX = p.x + BLOCK_WIDTH / 5;
        //counter clockwise
        gc.strokeLine(startX, p.y, p.x, p.y + BLOCK_HEIGHT);
        gc.strokeLine(p.x, p.y + BLOCK_HEIGHT , p.x + BLOCK_WIDTH * 0.8, p.y + BLOCK_HEIGHT);
        gc.strokeLine(p.x + BLOCK_WIDTH, p.y, p.x + BLOCK_WIDTH * 0.8, p.y + BLOCK_HEIGHT);
        gc.strokeLine(p.x + BLOCK_WIDTH, p.y, startX, p.y);
        return p;
    }
}