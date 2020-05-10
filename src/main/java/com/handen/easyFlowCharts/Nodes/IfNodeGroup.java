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
/**
 * This NodeGroup represents an if statement with two branches. getFirstBranch returns right branch(then branch),
 *  getSecondBranch returns left branch(else branch).
 */
public class IfNodeGroup extends TwoBranchNodeGroup {

    public IfNodeGroup(String line) {
        super(line);
    }

    @Override
    public String getText() {
        String text = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        return text;
    }

    @Override
    public void addNode(AbstractNode node) {
        if(node.getText().contains("else ") && node.getText().contains("{")) {
            setIsFirstBranch(false);
        }
        else {
            super.addNode(node);
        }
    }

    @Override
    public Context draw(Context context) {
        //TODO handle else branch width

        context.drawStrategy(new DrawDiamondStrategy());

        context.drawStrategy(new DrawTextStrategy(getText()));

        Point diamondPoint = new Point(context.getCurrentPoint());


        context.drawStrategy(new DrawArrowStrategy());
        //draw left branch
        drawBranch(context, getSecondBranch());
        Point leftBranchEndpoint = new Point(context.getCurrentPoint());

        //draw right branch
        context.setCurrentPoint(diamondPoint);

        context.drawStrategy(new DrawThenArrowStrategy());
        drawBranch(context, getFirstBranch());

        int leftBranchHeight = measureBranchHeight(getSecondBranch());
        int rightBranchHeight = measureBranchHeight(getFirstBranch());

        int diff = Math.abs(leftBranchHeight - rightBranchHeight);

        if(leftBranchHeight > rightBranchHeight) {
            context.drawStrategy(new DrawVerticalLineStrategy(diff));

            context.drawStrategy(new DrawConnectBranchesArrowStrategy());
            context.setCurrentPoint(leftBranchEndpoint);
        }
        else {
            if(leftBranchHeight == rightBranchHeight) {
                context.drawStrategy(new DrawConnectBranchesArrowStrategy());
                context.setCurrentPoint(leftBranchEndpoint);
            }
            else {
                context.drawStrategy(new DrawConnectBranchesArrowStrategy());
                context.setCurrentPoint(leftBranchEndpoint);
                var strategy = new DrawVerticalLineStrategy(diff + DrawConstants.ARROW_LENGTH);
                context.drawStrategy(strategy);
            }
        }

        context.drawStrategy(new DrawVerticalLineStrategy(DrawConstants.BLOCK_HEIGHT));
        Point currentPoint = context.getCurrentPoint();

        currentPoint.y -= DrawConstants.BLOCK_HEIGHT;
        context.setCurrentPoint(currentPoint);

        return context;
    }
}