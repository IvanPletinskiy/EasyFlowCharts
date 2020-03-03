package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawTextStrategy implements DrawStrategy {

    private String text;

    public DrawTextStrategy(String text) {
        this.text = text;
    }

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int centerX = p.x + BLOCK_WIDTH / 2;
        int centerY = p.y + BLOCK_HEIGHT / 2;
        gc.fillText(text, centerX, centerY, BLOCK_WIDTH);
        return p;
    }
}
