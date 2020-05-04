package com.handen.easy_flow_charts.strategies;

import com.handen.easy_flow_charts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easy_flow_charts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawCircleStrategy implements DrawStrategy{
    public static final int circleRadius = (int) (BLOCK_HEIGHT * 0.4);

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        gc.strokeOval(p.x + BLOCK_WIDTH / 2 - circleRadius, p.y, circleRadius * 2, circleRadius * 2);
        p.y -= (BLOCK_HEIGHT - 2 * circleRadius);
        return p;
    }
}