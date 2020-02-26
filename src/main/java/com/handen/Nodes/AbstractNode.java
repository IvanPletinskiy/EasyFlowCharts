package com.handen.Nodes;

abstract class AbstractNode {
    protected String line;

    public AbstractNode(String line) {
        this.line = line;
    }

    public abstract String getText();

    abstract int measureHeight();
}
