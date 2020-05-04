package com.handen.easy_flow_charts.strategies;

import com.handen.easy_flow_charts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_WIDTH;
import static com.handen.easy_flow_charts.flowchart.DrawConstants.LOOP_BLOCK_OFFSET;

public class DrawCloseLoopPolygonStrategy implements DrawStrategy {
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int offset = LOOP_BLOCK_OFFSET;
        //counter clockwise

        gc.strokeLine(p.x, p.y, p.x, p.y + BLOCK_HEIGHT - offset);
        gc.strokeLine(p.x, p.y + BLOCK_HEIGHT - offset, p.x + offset, p.y + BLOCK_HEIGHT);
        gc.strokeLine(p.x + offset, p.y + BLOCK_HEIGHT, p.x + BLOCK_WIDTH - offset, p.y + BLOCK_HEIGHT);
        gc.strokeLine(p.x + BLOCK_WIDTH - offset, p.y + BLOCK_HEIGHT, p.x + BLOCK_WIDTH, p.y + BLOCK_HEIGHT - offset);
        gc.strokeLine(p.x + BLOCK_WIDTH, p.y + BLOCK_HEIGHT - offset, p.x + BLOCK_WIDTH, p.y);
        gc.strokeLine(p.x + BLOCK_WIDTH, p.y, p.x, p.y);
        return p;
    }
}