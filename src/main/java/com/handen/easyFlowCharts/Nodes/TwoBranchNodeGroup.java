package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.Context;
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
    public int measureHeight() {
        int firstBranchHeight = 0;
        for(AbstractNode node: firstBranch) {
            firstBranchHeight += node.measureHeight();
        }
        if(firstBranch.size() > 0) {
            firstBranchHeight += (firstBranch.size() - 1) * AbstractNode.ARROW_LENGTH;
        }

        int secondBranchHeight = 0;
        for(AbstractNode node: secondBranch) {
            secondBranchHeight += node.measureHeight();
        }
        if(secondBranch.size() > 0) {
            secondBranchHeight += (secondBranch.size() - 1) * AbstractNode.ARROW_LENGTH;
        }

        int childrenHeight = Math.max(firstBranchHeight, secondBranchHeight);

        return AbstractNode.BLOCK_HEIGHT + AbstractNode.ARROW_LENGTH + childrenHeight + AbstractNode.ARROW_LENGTH + AbstractNode.BLOCK_HEIGHT;
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
            sum += (nodes.size() - 1) * AbstractNode.ARROW_LENGTH;
        }
        return sum;
    }
}