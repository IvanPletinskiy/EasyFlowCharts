package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.ARROW_LENGTH;
import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawArrowStrategy implements DrawStrategy {
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int centerX = p.x + BLOCK_WIDTH / 2;
        int startY = p.y + BLOCK_HEIGHT;
        gc.strokeLine(centerX, startY, centerX, startY + ARROW_LENGTH);
        p.y = startY + ARROW_LENGTH;
        return p;
    }
}
