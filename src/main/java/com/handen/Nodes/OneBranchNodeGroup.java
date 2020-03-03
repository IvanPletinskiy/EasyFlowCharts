package com.handen.Nodes;

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
            sum += (children.size() - 1) * ARROW_LENGTH;
        }
        return BLOCK_HEIGHT + ARROW_LENGTH + sum +  ARROW_LENGTH + BLOCK_HEIGHT;
    }
}
