package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawParallelogramStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

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