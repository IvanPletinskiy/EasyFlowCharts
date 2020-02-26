package com.handen.Nodes;

abstract class TwoBranchNodeGroup extends AbstractNode {
    private SimpleNodeGroup childrenLeft = new SimpleNodeGroup();
    private SimpleNodeGroup childrenRight = new SimpleNodeGroup();

    public TwoBranchNodeGroup(String line) {
        super(line);
    }

    OneBranchNodeGroup getLeftNodes() {
        return childrenLeft;
    }

    OneBranchNodeGroup getRightNodes() {
        return childrenRight;
    }

    void addNode(AbstractNode node, boolean isLeftBranch) {
        if(isLeftBranch) {
            childrenLeft.addNode(node);
        }
        else {
            childrenRight.addNode(node);
        }
    }

    @Override
    int measureHeight() {
        return Math.max(getLeftNodes().measureHeight(), getRightNodes().measureHeight());
    }
}