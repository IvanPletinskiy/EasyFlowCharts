package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.flowchart.DrawConstants;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;

import java.util.ArrayList;

public abstract class TwoBranchNodeGroup extends NodeGroup {
    private boolean isFirstBranch = true;
    private ArrayList<AbstractNode> firstBranch = new ArrayList<>();
    private ArrayList<AbstractNode> secondBranch = new ArrayList<>();

    @Override
    public void addNode(AbstractNode node) {
        if(isFirstBranch) {
            firstBranch.add(node);
        }
        else {
            secondBranch.add(node);
        }
    }

    protected ArrayList<AbstractNode> getFirstBranch() {
        return firstBranch;
    }

    protected ArrayList<AbstractNode> getSecondBranch() {
        return secondBranch;
    }

    public void setIsFirstBranch(boolean isFirstBranch) {
        this.isFirstBranch = isFirstBranch;
    }

    public boolean isFirstBranch() {
        return isFirstBranch;
    }

    public TwoBranchNodeGroup(String line) {
        super(line);
    }

    @Override
    public int measureWidth() {
        int leftWidth = 0;
        for(AbstractNode node : firstBranch) {
            if(node.measureWidth() > leftWidth) {
                leftWidth = node.measureWidth();
            }
        }

        int rightWidth = 0;
        for(AbstractNode node : secondBranch) {
            if(node.measureWidth() > rightWidth) {
                rightWidth = node.measureWidth();
            }
        }

        int width = leftWidth + DrawConstants.THEN_ARROW_LENGTH + rightWidth;
        return width;
    }

    @Override
    public int measureHeight() {
        int firstBranchHeight = 0;
        for(AbstractNode node: firstBranch) {
            firstBranchHeight += node.measureHeight();
        }
        if(firstBranch.size() > 0) {
            firstBranchHeight += (firstBranch.size() - 1) * DrawConstants.ARROW_LENGTH;
        }

        int secondBranchHeight = 0;
        for(AbstractNode node: secondBranch) {
            secondBranchHeight += node.measureHeight();
        }
        if(secondBranch.size() > 0) {
            secondBranchHeight += (secondBranch.size() - 1) * DrawConstants.ARROW_LENGTH;
        }

        int childrenHeight = Math.max(firstBranchHeight, secondBranchHeight);

        return DrawConstants.BLOCK_HEIGHT + DrawConstants.ARROW_LENGTH + childrenHeight + DrawConstants.ARROW_LENGTH + DrawConstants.BLOCK_HEIGHT;
    }

    protected Context drawBranch(Context context, ArrayList<AbstractNode> nodes) {
        for(AbstractNode node: nodes) {
            node.draw(context);
            context.setStrategy(new DrawArrowStrategy());
            context.drawCurrentStrategy();
        }
        return context;
    }

    protected int measureBranchHeight(ArrayList<AbstractNode> nodes) {
        int sum = 0;
        for(AbstractNode node: nodes) {
            sum += node.measureHeight();
        }

        if(!nodes.isEmpty()) {
            sum += (nodes.size() - 1) * DrawConstants.ARROW_LENGTH;
        }
        return sum;
    }
}