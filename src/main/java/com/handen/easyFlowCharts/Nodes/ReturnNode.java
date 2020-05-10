package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawParallelogramStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

public class ReturnNode extends SingleNode {

    public ReturnNode(String line) {
        super(line);
    }

    @Override
    public String getOpeningBlockText() {
        return line.replace(";", "");
    }

    @Override
    public Context draw(Context context) {
        context.drawStrategy(new DrawParallelogramStrategy());

        context.drawStrategy(new DrawTextStrategy(getOpeningBlockText()));

        return context;
    }
}