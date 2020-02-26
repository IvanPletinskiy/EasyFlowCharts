package com.handen.Nodes;

import java.util.ArrayList;
import java.util.List;

abstract class OneBranchNodeGroup extends AbstractNode {

    private ArrayList<AbstractNode> children = new ArrayList<>();

    public OneBranchNodeGroup(String line) {
        super(line);
    }

    List<AbstractNode> getChildren() {
        return children;
    }

    void addNode(AbstractNode node) {
        children.add(node);
    }

    abstract String getClosingBlockText();

    @Override
    int measureHeight() {
        int sum = 0;
        for(AbstractNode child : getChildren()) {
            sum += child.measureHeight();
        }
        return sum;
    }
}
