package com.handen.easyFlowCharts.flowchart;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.utils.FileMethodsPair;

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
    private List<FileMethodsPair> filesMethodsPairs;
    private int currentFilePageCounter;
    private Context context;

    private Canvas canvas;
    private int currentWidth;
    private int currentHeight;

    public FlowchartDrawer(List<FileMethodsPair> filesMethodsPairs) {
        this.filesMethodsPairs = filesMethodsPairs;
    }


    public boolean hasNext() {
        boolean hasNext = !filesMethodsPairs.isEmpty();
        return hasNext;
    }

    public Canvas drawPage() {
        prepare();

        FileMethodsPair pair = filesMethodsPairs.get(0);

        if(!pair.methods.isEmpty()) {
            MethodNodeGroup method = pair.methods.get(0);
            pair.methods.remove(0);
            if(methodCanFit(method)) {
                drawMethod(method);
            }
            if(pair.methods.isEmpty()) {
                filesMethodsPairs.remove(0);
            }
        }
        /*
        for(int i = 0; i < pair.methods.size(); i++) {
            MethodNodeGroup method = pair.methods.get(i);
            if(methodCanFit(method)) {
                drawMethod(method);
            }
        }

         */
        currentFilePageCounter++;
        return canvas;
    }

    private void drawMethod(MethodNodeGroup methodNodeGroup) {
         methodNodeGroup.draw(context);
    }

    private void prepare() {
        currentWidth = LIST_LEFT_OFFSET;
        currentHeight = LIST_TOP_OFFSET;
        currentFilePageCounter = 0;
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

    public String getCurrentPageName() {
        String fileName = filesMethodsPairs.get(0).getFileName();

        String pageName = fileName + "_" + currentFilePageCounter;
        return pageName;
    }

    public String getCurrentFileName() {
        String fileName = filesMethodsPairs.get(0).getFileName();
        return fileName;
    }

    public int getRemainFilesCount() {
        int remainCount = filesMethodsPairs.size();
        return remainCount;
    }
}