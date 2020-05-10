package com.handen.easyFlowCharts.flowchart;

import com.handen.easyFlowCharts.strategies.DrawStrategy;
import com.handen.easyFlowCharts.utils.Point;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.*;

public class Context {

    private GraphicsContext gc;
    private Point currentPoint;
    private int loopNumber;
    private int referenceIndex;
    private int column;

    public Context(GraphicsContext gc, int currentX, int currentY, int loopNumber, int referenceIndex) {
        this.gc = gc;
        this.currentPoint = new Point(currentX, currentY);
        this.loopNumber = loopNumber;
        this.referenceIndex = referenceIndex;
        this.column = 0;
    }

    public void drawStrategy(DrawStrategy strategy) {
        currentPoint =  strategy.draw(gc, currentPoint);
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = new Point(currentPoint);
    }

    //TODO перенести логику в FlowchartDrawer
    public void goToNextColumn(int previousColumnWidth) {
        int x = LIST_LEFT_OFFSET + currentPoint.x + previousColumnWidth;
        x += DrawConstants.COLUMN_SPACE;
        currentPoint = new Point(x, DrawConstants.LIST_TOP_OFFSET);
    }

    public String getLoopLabelText() {
        String label = "A" + loopNumber;
        loopNumber++;
        return label;
    }

    public String getReferenceLabel() {
        String label = String.valueOf((char)('A' + referenceIndex));
        referenceIndex++;
        return label;
    }

    public int getLoopNumber() {
        return loopNumber;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }
}