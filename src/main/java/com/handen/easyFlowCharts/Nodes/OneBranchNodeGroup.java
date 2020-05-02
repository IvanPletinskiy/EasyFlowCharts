package com.handen.easyFlowCharts.Nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class OneBranchNodeGroup extends NodeGroup {

    private ArrayList<AbstractNode> children = new ArrayList<>();

    public OneBranchNodeGroup(String line) {
        super(line);
    }

    @Override
    public void addNode(AbstractNode node) {
        children.add(node);
    }

    List<AbstractNode> getChildren() {
        return children;
    }

    abstract String getClosingBlockText();

    @Override
    public int measureHeight() {
        int sum = 0;
        for(AbstractNode child : getChildren()) {
            sum += child.measureHeight();
        }
        if(children.size() > 0) {
            sum += (children.size() - 1) * AbstractNode.ARROW_LENGTH;
        }
        return AbstractNode.BLOCK_HEIGHT + AbstractNode.ARROW_LENGTH + sum +  AbstractNode.ARROW_LENGTH + AbstractNode.BLOCK_HEIGHT;
    }
}
