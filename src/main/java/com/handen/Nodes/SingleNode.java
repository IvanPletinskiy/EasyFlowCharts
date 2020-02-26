package com.handen.Nodes;

abstract class SingleNode extends AbstractNode {

    public SingleNode(String line) {
        super(line);
    }

    @Override
    int measureHeight() {
        return 1;
    }
}
