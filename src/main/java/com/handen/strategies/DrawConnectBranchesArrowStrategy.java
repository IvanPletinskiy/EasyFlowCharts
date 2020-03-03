package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;
import static com.handen.Nodes.AbstractNode.THEN_ARROW_LENGTH;

public class DrawConnectBranchesArrowStrategy implements DrawStrategy {
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startX = p.x + BLOCK_WIDTH / 2;
        gc.strokeLine(startX, p.y, startX - BLOCK_HEIGHT - THEN_ARROW_LENGTH, p.y);

        //TODO draw arrow

        p.x = startX - BLOCK_HEIGHT - THEN_ARROW_LENGTH;
        return p;
    }
}