package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawOvalStrategy implements DrawStrategy {
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        gc.strokeOval(p.x, p.y, BLOCK_WIDTH, BLOCK_HEIGHT);
        return p;
    }
}
