package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;

public abstract class AbstractNode {

    protected String line;

    public AbstractNode(String line) {
        this.line = line;
    }

    public abstract String getOpeningBlockText();

    public abstract int measureHeight();

    public abstract int measureWidth();

    public abstract Context draw(Context context);
}