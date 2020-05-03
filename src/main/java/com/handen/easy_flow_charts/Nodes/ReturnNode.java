package com.handen.easy_flow_charts.Nodes;

import com.handen.easy_flow_charts.flowchart.Context;
import com.handen.easy_flow_charts.strategies.DrawParallelogramStrategy;
import com.handen.easy_flow_charts.strategies.DrawTextStrategy;

public class ReturnNode extends SingleNode {

    public ReturnNode(String line) {
        super(line);
    }

    @Override
    public String getText() {
        return line.replace(";", "");
    }

    @Override
    public Context draw(Context context) {
        context.setStrategy(new DrawParallelogramStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        return context;
    }
}