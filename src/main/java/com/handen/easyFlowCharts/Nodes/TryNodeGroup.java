package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.flowchart.DrawConstants;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawConnectBranchesArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawDiamondStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;
import com.handen.easyFlowCharts.strategies.DrawThenArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawVerticalLineStrategy;
import com.handen.easyFlowCharts.utils.Point;

@SuppressWarnings("Duplicates")
public class TryNodeGroup extends TwoBranchNodeGroup {

    private String exception = "";

    public TryNodeGroup(String line) {
        super(line);
    }

    @Override
    public String getOpeningBlockText() {
        return exception;
    }

    @Override
    public void addNode(AbstractNode node) {
        if(node.line.contains("catch") && node.line.contains("{")) {
            setIsFirstBranch(false);
            int bracketIndex = node.line.indexOf("(");
            exception = node.line.substring(bracketIndex + 1, node.line.indexOf(" ", bracketIndex + 2));
        }
        else {
            super.addNode(node);
        }
    }

    @Override
    public Context draw(Context context) {
        for(AbstractNode node: super.getFirstBranch()) {
            context = node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
        }

        Point diamondPoint = new Point(context.getCurrentPoint());

        //Draw diamond
        context.drawStrategy(new DrawDiamondStrategy());
        context.drawStrategy(new DrawTextStrategy(getOpeningBlockText()));

        context.drawStrategy(new DrawArrowStrategy());

        Point leftBranchEndpoint = new Point(context.getCurrentPoint());

        //draw right branch
        context.setCurrentPoint(diamondPoint);
        context.drawStrategy(new DrawThenArrowStrategy());
        drawBranch(context, getSecondBranch());

        context.drawStrategy(new DrawConnectBranchesArrowStrategy());

        context.setCurrentPoint(new Point(leftBranchEndpoint));

        int diff = measureBranchHeight(getSecondBranch());
        context.setCurrentPoint(leftBranchEndpoint);
        context.drawStrategy(new DrawVerticalLineStrategy(diff + DrawConstants.ARROW_LENGTH));

        context.drawStrategy(new DrawVerticalLineStrategy(DrawConstants.BLOCK_HEIGHT));
        Point currentPoint = context.getCurrentPoint();

        currentPoint.y -= DrawConstants.BLOCK_HEIGHT;
        context.setCurrentPoint(currentPoint);

        return context;
    }
}