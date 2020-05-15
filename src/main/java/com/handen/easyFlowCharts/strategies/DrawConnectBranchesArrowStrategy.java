package com.handen.easyFlowCharts.strategies;

import com.handen.easyFlowCharts.flowchart.DrawConstants;
import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawConnectBranchesArrowStrategy implements DrawStrategy {

    private Point leftBranchEndpoint;

    public DrawConnectBranchesArrowStrategy(Point leftBranchEndpoint) {
        this.leftBranchEndpoint = leftBranchEndpoint;
    }

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        int startX = p.x + BLOCK_WIDTH / 2;
        int endX = leftBranchEndpoint.x + (BLOCK_WIDTH / 2);

        gc.strokeLine(startX, p.y, endX, p.y);

        p.x = endX;

        int x2 = p.x + DrawConstants.ARROW_LENGTH_X;
        int y2 = p.y - DrawConstants.ARROW_LENGTH_Y;

        int x3 = p.x + DrawConstants.ARROW_LENGTH_X;
        int y3 = p.y + DrawConstants.ARROW_LENGTH_Y;

        gc.fillPolygon(new double[]{p.x, x2, x3}, new double[]{p.y, y2, y3}, 3);
        return p;
    }
}