package com.handen.easyFlowCharts.flowchart;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.utils.FileMethodsPair;
import com.handen.easyFlowCharts.utils.Point;

import java.util.LinkedList;
import java.util.List;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.FILL_COLOR;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.FONT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LINE_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_LEFT_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_TOP_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.STROKE_COLOR;

public class FlowchartDrawer {
    private Context context;

    private Canvas canvas;

    private int loopNumber;
    private int referenceId;

    public FlowchartDrawer() {
        loopNumber = 1;
        referenceId = 0;
    }

    private boolean methodCanFit(MethodNodeGroup method) {
        int methodHeight = method.measureHeight();
        int methodWidth = method.measureWidth();
        //  int remainColumnSpace = LIST_HEIGHT - currentHeight - LIST_BOTTOM_OFFSET;
        //TODO
        return true;
    }

    public List<Canvas> drawFile(FileMethodsPair pair) {
        List<Canvas> canvases = new LinkedList<>();

        for(MethodNodeGroup method : pair.methods) {
            prepareCanvas();

            method.draw(context);

            setCanvasId(pair, method);
            canvases.add(canvas);
        }

        return canvases;
    }

    private void setCanvasId(FileMethodsPair pair, MethodNodeGroup method) {
        int bracketIndex = method.getText().indexOf("(");
        String methodName = method.getText().substring(0, bracketIndex);
        String canvasName = pair.getFileName() + "_" + methodName;
        canvas.setId(canvasName);
    }

    private void prepareCanvas() {
        canvas = new Canvas(LIST_WIDTH, LIST_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        initializeGraphicsContext(gc);

        if(context != null) {
            //Скопировать loopNumber и referenceIndex из предыдущего context
            loopNumber = context.getLoopNumber();
            referenceId = context.getReferenceIndex();
        }

        Point startPoint = new Point(LIST_LEFT_OFFSET, LIST_TOP_OFFSET);
        context = new Context(gc, startPoint, loopNumber, referenceId);
    }

    private void initializeGraphicsContext(GraphicsContext gc) {
        gc.setStroke(STROKE_COLOR);
        gc.setFill(FILL_COLOR);
        gc.setFont(FONT);
        gc.setLineWidth(LINE_WIDTH);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
    }
}