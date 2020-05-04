package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.Context;
import com.handen.easy_flow_charts.strategies.DrawRectangleStrategy;
import com.handen.easy_flow_charts.strategies.DrawTextStrategy;

public class StatementNode extends SingleNode {

    public StatementNode(String line) {
        super(line);
    }

    @Override
    public String getText() {
        return line.trim().replace(";", "");
    }

    @Override
    public Context draw(Context context) {
        context.setStrategy(new DrawRectangleStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();
        return context;
    }
}