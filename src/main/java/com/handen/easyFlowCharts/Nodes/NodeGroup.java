package com.handen.easyFlowCharts.Nodes;

public abstract class NodeGroup extends Node {

    public NodeGroup(String line) {
        super(line);
    }

    abstract public void addNode(Node node);
}
