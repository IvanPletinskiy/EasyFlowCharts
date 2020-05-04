package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.Context;
import com.handen.easy_flow_charts.flowchart.DrawConstants;
import com.handen.easy_flow_charts.strategies.DrawArrowStrategy;
import com.handen.easy_flow_charts.strategies.DrawConnectBranchesArrowStrategy;
import com.handen.easy_flow_charts.strategies.DrawDiamondStrategy;
import com.handen.easy_flow_charts.strategies.DrawTextStrategy;
import com.handen.easy_flow_charts.strategies.DrawThenArrowStrategy;
import com.handen.easy_flow_charts.strategies.DrawVerticalLineStrategy;
import com.handen.easy_flow_charts.utils.Point;
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
        context.setStrategy(new DrawDiamondStrategy());
        context.drawCurrentStrategy();
        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        Point diamondPoint = new Point(context.getCurrentPoint());

        context.setStrategy(new DrawArrowStrategy());
        context.drawCurrentStrategy();
        //draw left branch
        drawBranch(context, getSecondBranch());
        Point leftBranchEndpoint = new Point(context.getCurrentPoint());

        //draw right branch
        context.setCurrentPoint(diamondPoint);
        context.setStrategy(new DrawThenArrowStrategy());
        context.drawCurrentStrategy();
        drawBranch(context, getFirstBranch());

        int leftBranchHeight = measureBranchHeight(getSecondBranch());
        int rightBranchHeight = measureBranchHeight(getFirstBranch());

        int diff = Math.abs(leftBranchHeight - rightBranchHeight);

        if(leftBranchHeight > rightBranchHeight) {
            context.setStrategy(new DrawVerticalLineStrategy(diff));
            context.drawCurrentStrategy();

            context.setStrategy(new DrawConnectBranchesArrowStrategy());
            context.drawCurrentStrategy();
            context.setCurrentPoint(leftBranchEndpoint);
        }
        else {
            if(leftBranchHeight == rightBranchHeight) {
                context.setStrategy(new DrawConnectBranchesArrowStrategy());
                context.drawCurrentStrategy();
                context.setCurrentPoint(leftBranchEndpoint);
            }
            else {
                context.setStrategy(new DrawConnectBranchesArrowStrategy());
                context.drawCurrentStrategy();
                context.setCurrentPoint(leftBranchEndpoint);
                context.setStrategy(new DrawVerticalLineStrategy(diff + DrawConstants.ARROW_LENGTH));
                context.drawCurrentStrategy();
            }
        }

        //FIXME костыль
        context.setStrategy(new DrawVerticalLineStrategy(DrawConstants.BLOCK_HEIGHT));
        context.drawCurrentStrategy();
        Point currentPoint = context.getCurrentPoint();

        currentPoint.y -= DrawConstants.BLOCK_HEIGHT;
        context.setCurrentPoint(currentPoint);

        return context;
    }
}