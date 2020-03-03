package com.handen.Nodes;

public abstract class SingleNode extends AbstractNode {

    public SingleNode(String line) {
        super(line);
    }

    @Override
    public int measureHeight() {
        return BLOCK_HEIGHT;
    }
}
