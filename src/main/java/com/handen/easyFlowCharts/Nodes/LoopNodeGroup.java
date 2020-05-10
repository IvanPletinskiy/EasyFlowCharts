package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawCloseLoopPolygonStrategy;
import com.handen.easyFlowCharts.strategies.DrawOpenLoopPolygonStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

public class LoopNodeGroup extends OneBranchNodeGroup {

    public LoopNodeGroup(String line) {
        super(line);
    }

    @Override
    String getClosingBlockText() {
        //TODO
        return null;
    }

    @Override
    public String getText() {
        String text = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        return text;
    }

    @Override
    public Context draw(Context context) {
        context.drawStrategy(new DrawOpenLoopPolygonStrategy());

        context.drawStrategy(new DrawTextStrategy(getText()));

        context.drawStrategy(new DrawArrowStrategy());

        for(AbstractNode node : getChildren()) {
            node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
        }

        context.drawStrategy(new DrawCloseLoopPolygonStrategy());
        context.drawStrategy(new DrawTextStrategy(getClosingBlockText()));
        return context;
    }
}