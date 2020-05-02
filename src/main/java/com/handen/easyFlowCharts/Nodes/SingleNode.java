package com.handen.easyFlowCharts.Nodes;

public abstract class SingleNode extends AbstractNode {

    public SingleNode(String line) {
        super(line);
    }

    @Override
    public int measureHeight() {
        return AbstractNode.BLOCK_HEIGHT;
    }
}
