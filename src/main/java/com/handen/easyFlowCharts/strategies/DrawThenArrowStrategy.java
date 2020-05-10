package com.handen.easyFlowCharts.strategies;

import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.ARROW_LENGTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.THEN_ARROW_LENGTH;

public class DrawThenArrowStrategy implements DrawStrategy {

    private int leftBranchWidth;

    public DrawThenArrowStrategy(int leftBranchWidth) {
        this.leftBranchWidth = leftBranchWidth;
    }

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        //Draw horizontal line
        int offsetLineLength = leftBranchWidth - BLOCK_WIDTH;
        int arrowLength = offsetLineLength + THEN_ARROW_LENGTH;
        int startX = p.x + BLOCK_WIDTH;
        int startY = p.y + BLOCK_HEIGHT / 2;
        gc.strokeLine(startX, startY, startX + arrowLength, startY);

        //Draw vertical line with half-block width
        startX = startX + THEN_ARROW_LENGTH;
        gc.strokeLine(startX, startY, startX, startY + (BLOCK_HEIGHT / 2) + ARROW_LENGTH);

        p.x = startX - (BLOCK_WIDTH / 2);
        p.y = startY + (BLOCK_HEIGHT / 2) + ARROW_LENGTH;
        return p;
    }
}