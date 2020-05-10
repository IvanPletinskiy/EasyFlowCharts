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
    public void addNode(Node node) {
        if(node.getText().contains("else ") && node.getText().contains("{")) {
            setIsFirstBranch(false);
        }
        else {
            super.addNode(node);
        }
    }

    @Override
    public Context draw(Context context) {
        drawDiamond(context);
        Point diamondPoint = new Point(context.getCurrentPoint());

        drawLeftBranch(context);
        Point leftBranchEndpoint = new Point(context.getCurrentPoint());

        //Comeback to diamond
        context.setCurrentPoint(diamondPoint);

        drawRightBranch(context);

        connectBranches(context, leftBranchEndpoint);

        context.drawStrategy(new DrawVerticalLineStrategy(DrawConstants.BLOCK_HEIGHT));
        Point currentPoint = context.getCurrentPoint();

        currentPoint.y -= DrawConstants.BLOCK_HEIGHT;
        context.setCurrentPoint(currentPoint);

        return context;
    }

    private void drawDiamond(Context context) {
        context.drawStrategy(new DrawDiamondStrategy());
        context.drawStrategy(new DrawTextStrategy(getText()));
    }

    private void drawRightBranch(Context context) {
        int leftBranchWidth = measureBranchWidth(getSecondBranch());
        context.drawStrategy(new DrawThenArrowStrategy(leftBranchWidth));
        drawBranch(context, getFirstBranch());
    }

    private void drawLeftBranch(Context context) {
        context.drawStrategy(new DrawArrowStrategy());
        drawBranch(context, getSecondBranch());
    }

    private void connectBranches(Context context, Point leftBranchEndpoint) {
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
    }
}