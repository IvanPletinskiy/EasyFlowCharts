package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.DrawConstants;

public abstract class SingleNode extends Node {

    public SingleNode(String line) {
        super(line);
    }

    @Override
    public int measureHeight() {
        return DrawConstants.BLOCK_HEIGHT;
    }

    @Override
    public int measureWidth() {
        return DrawConstants.BLOCK_WIDTH;
    }
}
