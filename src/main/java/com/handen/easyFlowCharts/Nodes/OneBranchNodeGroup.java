package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.DrawConstants;

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
    public int measureWidth() {
        int width = 0;
        for(AbstractNode node: children) {
            if(node.measureWidth() > width) {
                width = node.measureWidth();
            }
        }

        return width;
    }

    @Override
    public int measureHeight() {
        int sum = 0;
        for(AbstractNode child : getChildren()) {
            sum += child.measureHeight();
        }
        if(children.size() > 0) {
            sum += (children.size() - 1) * DrawConstants.ARROW_LENGTH;
        }
        return DrawConstants.BLOCK_HEIGHT + DrawConstants.ARROW_LENGTH + sum +  DrawConstants.ARROW_LENGTH + DrawConstants.BLOCK_HEIGHT;
    }
}
