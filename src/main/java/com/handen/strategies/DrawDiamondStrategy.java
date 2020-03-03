package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawDiamondStrategy implements DrawStrategy{
    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startX = p.x + BLOCK_WIDTH / 2;
        int startY = p.y;
        int endX = p.x;
        int endY = p.y + BLOCK_HEIGHT / 2;
        //counter clockwise
        gc.strokeLine(startX, startY, endX, endY);
        gc.strokeLine(endX, endY, startX, startY + BLOCK_HEIGHT);
        gc.strokeLine(startX, startY + BLOCK_HEIGHT, endX + BLOCK_WIDTH, endY);
        gc.strokeLine(startX + BLOCK_WIDTH  / 2, endY, startX, startY);

        //Draw yes above right arrow
        int yesX = (int) (p.x + BLOCK_WIDTH * 1.3);
        int yesY = (int) (p.y + BLOCK_HEIGHT * 0.3);
        gc.fillText("да", yesX, yesY, BLOCK_WIDTH);
        //Draw no to the left of bottom arrow
        int noX = (int) (p.x + BLOCK_WIDTH * 0.4);
        int noY = (int) (p.y + BLOCK_HEIGHT * 1.3);
        gc.fillText("нет", noX, noY, BLOCK_WIDTH);
        return p;
    }
}
