package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.Context;
import com.handen.easy_flow_charts.strategies.DrawInnerRectStrategy;
import com.handen.easy_flow_charts.strategies.DrawRectangleStrategy;
import com.handen.easy_flow_charts.strategies.DrawTextStrategy;

public class MethodCallNode extends com.handen.easy_flow_charts.Nodes.SingleNode {

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