package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.ARROW_LENGTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.THEN_ARROW_LENGTH;

public abstract class TwoBranchNodeGroup extends NodeGroup {
    private boolean isFirstBranch = true;
    private ArrayList<Node> firstBranch = new ArrayList<>();
    private ArrayList<Node> secondBranch = new ArrayList<>();

    @Override
    public void addNode(Node node) {
        if(isFirstBranch) {
            firstBranch.add(node);
        }
        else {
            secondBranch.add(node);
        }
    }

    protected ArrayList<Node> getFirstBranch() {
        return firstBranch;
    }

    protected ArrayList<Node> getSecondBranch() {
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
        int leftWidth = measureBranchWidth(firstBranch);
        int rightWidth = measureBranchWidth(secondBranch);

        int width = leftWidth + THEN_ARROW_LENGTH - (BLOCK_WIDTH / 2) + rightWidth;
        return width;
    }

    @Override
    public int measureHeight() {
        int firstBranchHeight = 0;
        for(Node node : firstBranch) {
            firstBranchHeight += node.measureHeight();
        }
        if(firstBranch.size() > 0) {
            firstBranchHeight += (firstBranch.size() - 1) * ARROW_LENGTH;
        }

        int secondBranchHeight = 0;
        for(Node node : secondBranch) {
            secondBranchHeight += node.measureHeight();
        }
        if(secondBranch.size() > 0) {
            secondBranchHeight += (secondBranch.size() - 1) * ARROW_LENGTH;
        }

        int childrenHeight = Math.max(firstBranchHeight, secondBranchHeight);

        return BLOCK_HEIGHT + ARROW_LENGTH + childrenHeight + ARROW_LENGTH + BLOCK_HEIGHT;
    }

    Context drawBranch(Context context, ArrayList<Node> nodes) {
        for(Node node : nodes) {
            node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
        }
        return context;
    }

    int measureBranchHeight(ArrayList<Node> nodes) {
        int sum = 0;
        for(Node node : nodes) {
            sum += node.measureHeight();
        }

        if(!nodes.isEmpty()) {
            sum += (nodes.size() - 1) * ARROW_LENGTH;
        }
        return sum;
    }

    protected int measureBranchWidth(List<Node> branch) {
        int width = BLOCK_WIDTH;
        for(Node node : branch) {
            if(node.measureWidth() > width) {
                width = node.measureWidth();
            }
        }
        return width;
    }
}