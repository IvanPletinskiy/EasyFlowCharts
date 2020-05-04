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
    public String getText() {
        return line.trim().substring(0, line.length() - 1);
    }

    @Override
    public Context draw(Context context) {
        context.setStrategy(new DrawRectangleStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawInnerRectStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        return context;
    }
}