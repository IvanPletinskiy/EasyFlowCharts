package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawArrowStrategy;
import com.handen.strategies.DrawConnectBranchesArrowStrategy;
import com.handen.strategies.DrawDiamondStrategy;
import com.handen.strategies.DrawTextStrategy;
import com.handen.strategies.DrawThenArrowStrategy;
import com.handen.strategies.DrawVerticalLineStrategy;
import com.handen.utils.Point;

@SuppressWarnings("Duplicates")
public class TryNodeGroup extends TwoBranchNodeGroup {

    private String exception = "";

    public TryNodeGroup(String line) {
        super(line);
    }

    @Override
    public String getText() {
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

        Point diamondPoint = new Point(context.getCurrentPoint());

        //Draw diamond
        context.setStrategy(new DrawDiamondStrategy());
        context.drawCurrentStrategy();
        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        context.setStrategy(new DrawArrowStrategy());
        context.drawCurrentStrategy();

        Point leftBranchEndpoint = new Point(context.getCurrentPoint());

        //draw right branch
        context.setCurrentPoint(diamondPoint);
        context.setStrategy(new DrawThenArrowStrategy());
        context.drawCurrentStrategy();
        drawBranch(context, getSecondBranch());

        context.setStrategy(new DrawConnectBranchesArrowStrategy());
        context.drawCurrentStrategy();

        context.setCurrentPoint(new Point(leftBranchEndpoint));

        int diff = measureBranchHeight(getSecondBranch());
        context.setCurrentPoint(leftBranchEndpoint);
        context.setStrategy(new DrawVerticalLineStrategy(diff + ARROW_LENGTH));
        context.drawCurrentStrategy();

        //FIXME костыль
        context.setStrategy(new DrawVerticalLineStrategy(BLOCK_HEIGHT));
        context.drawCurrentStrategy();
        Point currentPoint = context.getCurrentPoint();

        currentPoint.y -= BLOCK_HEIGHT;
        context.setCurrentPoint(currentPoint);

        return context;
    }
}