package com.handen.Nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Branch without opening and closing block, i.e. left or right branch of TwoBranchNodeGroup
 */
class SimpleNodeGroup extends OneBranchNodeGroup {
    private ArrayList<AbstractNode> children = new ArrayList<>();

    public SimpleNodeGroup() {
        super("");
    }

    private SimpleNodeGroup(String line) {
        super(line);
    }

    @Override
    List<AbstractNode> getChildren() {
        return children;
    }

    @Override
    void addNode(AbstractNode node) {
        children.add(node);
    }

    @Override
    String getClosingBlockText() {
        return "";
    }

    @Override
    public String getText() {
        return "";
    }
}
