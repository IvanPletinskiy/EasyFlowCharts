package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawInnerRectStrategy;
import com.handen.easyFlowCharts.strategies.DrawRectangleStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

public class MethodCallNode extends com.handen.easyFlowCharts.Nodes.SingleNode {

    public MethodCallNode(String line) {
        super(line);
    }

    @Override
    public String getOpeningBlockText() {
        return line.trim().substring(0, line.length() - 1);
    }

    @Override
    public Context draw(Context context) {
        context.drawStrategy(new DrawRectangleStrategy());

        context.drawStrategy(new DrawInnerRectStrategy());

        context.drawStrategy(new DrawTextStrategy(getOpeningBlockText()));

        return context;
    }
}