package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.Context;

public abstract class AbstractNode {

    protected String line;

    public AbstractNode(String line) {
        this.line = line;
    }

    public abstract String getText();

    public abstract int measureHeight();

    public abstract int measureWidth();

    public abstract Context draw(Context context);
}