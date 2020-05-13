package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;

public abstract class Node {
    protected String line;

    public Node(String line) {
        this.line = line;
    }

    public abstract String getText();
    public abstract int measureHeight();
    public abstract int measureWidth();
    public abstract void draw(Context context);
}