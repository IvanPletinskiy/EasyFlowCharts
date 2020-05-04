package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.DrawConstants;

public abstract class SingleNode extends AbstractNode {

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
