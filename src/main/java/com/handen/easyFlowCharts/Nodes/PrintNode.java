package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawParallelogramStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

public class PrintNode extends SingleNode {

    public PrintNode(String line) {
        super(line);
    }

    @Override
    public String getText() {
        String text = "sout(" + line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")) + ")";
        return text;
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
