package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawCircleStrategy implements DrawStrategy{
    public static final int circleRadius = (int) (BLOCK_HEIGHT * 0.4);

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startY = p.y + BLOCK_HEIGHT / 2 - circleRadius;
        gc.strokeOval(p.x + BLOCK_WIDTH / 2 - circleRadius, startY, circleRadius * 2, circleRadius * 2);
        return p;
    }
}
