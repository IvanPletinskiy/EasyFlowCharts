package com.handen.easyFlowCharts.flowchart;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.utils.FileMethodsPair;

import java.util.LinkedList;
import java.util.List;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.FILL_COLOR;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.FONT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LINE_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_BOTTOM_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_LEFT_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_TOP_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.STROKE_COLOR;

public class FlowchartDrawer {
   // private List<FileMethodsPair> filesMethodsPairs;
    private int currentFilePageCounter;
    private Context context;

    private Canvas canvas;
    private int currentWidth;
    private int currentHeight;

    public FlowchartDrawer() {
        //this.filesMethodsPairs = filesMethodsPairs;
    }

    private void drawMethod(MethodNodeGroup methodNodeGroup) {
         methodNodeGroup.draw(context);
    }

    private void prepareCanvas() {
        currentWidth = LIST_LEFT_OFFSET;
        currentHeight = LIST_TOP_OFFSET;
        canvas = new Canvas(LIST_WIDTH, LIST_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        initializeGraphicsContext(gc);

        context = new Context(gc, currentWidth, currentHeight);
    }

    private boolean methodCanFit(MethodNodeGroup method) {
        int methodHeight = method.measureHeight();
        int methodWidth = method.measureWidth();
        int remainColumnSpace = LIST_HEIGHT - currentHeight - LIST_BOTTOM_OFFSET;
        //TODO
        return true;
    }

    private void initializeGraphicsContext(GraphicsContext gc) {
        gc.setStroke(STROKE_COLOR);
        gc.setFill(FILL_COLOR);
        gc.setFont(FONT);
        gc.setLineWidth(LINE_WIDTH);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
    }

    public List<Canvas> drawFile(FileMethodsPair pair) {
        List<Canvas> canvases = new LinkedList<>();
        currentFilePageCounter = 0;
        for(int i = 0; i < pair.methods.size(); i++) {
            prepareCanvas();
            MethodNodeGroup method = pair.methods.get(i);
            if(methodCanFit(method)) {
                drawMethod(method);
            }
            int bracetIndex = method.getText().indexOf("(");
            String methodName = method.getText().substring(0, bracetIndex);
            String canvasName = pair.getFileName() + "_" + methodName;
            canvas.setId(canvasName);
            canvases.add(canvas);
        }

        return canvases;
    }
}