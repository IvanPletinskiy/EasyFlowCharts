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
    public void draw(Context context) {
        context.drawStrategy(new DrawParallelogramStrategy());

        context.drawStrategy(new DrawTextStrategy(getText()));
    }
}
