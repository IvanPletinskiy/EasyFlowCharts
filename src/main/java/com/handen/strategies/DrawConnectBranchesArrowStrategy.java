package com.handen.strategies;

import com.handen.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;
import static com.handen.Nodes.AbstractNode.THEN_ARROW_LENGTH;

public class DrawConnectBranchesArrowStrategy implements DrawStrategy {

    public static int ARROW_LENGTH_X = 16;
    public static int ARROW_LENGTH_Y = 8;

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startX = p.x + BLOCK_WIDTH / 2;
        gc.strokeLine(startX, p.y, startX - BLOCK_HEIGHT - THEN_ARROW_LENGTH, p.y);

        p.x = startX - BLOCK_HEIGHT - THEN_ARROW_LENGTH;

        //TODO draw arrow
        int x2 = p.x + ARROW_LENGTH_X;
        int y2 = p.y - ARROW_LENGTH_Y;

        int x3 = p.x + ARROW_LENGTH_X;
        int y3 = p.y + ARROW_LENGTH_Y;

        gc.fillPolygon(new double[]{p.x, x2, x3}, new double[]{p.y, y2, y3}, 3);
        return p;
    }
}