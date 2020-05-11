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
        String text = "";
        if(line.contains("for") && !line.contains(":")) {
            //for
            int lastSemicolonIndex = line.lastIndexOf(';');
            String action = line.substring(lastSemicolonIndex + 1, line.lastIndexOf(")"));
            text = "\n" + action;
        }
        return text;
    }

    @Override
    public String getText() {
        String text = "\n";
        if(line.contains("for") && !line.contains(":")) {
            //for
            int lastSemicolonIndex = line.lastIndexOf(';');
            text += line.substring(line.indexOf("(") + 1, lastSemicolonIndex);
        }
        else {
            //while, foreach
            text += line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        }
        if(line.contains(":")) {
            //foreach
            text = text.replace("\n", "\t");
        }

        return text;
    }

    @Override
    public void draw(Context context) {
        String loopLabel = context.getLoopLabelText();
        context.drawStrategy(new DrawOpenLoopPolygonStrategy());

        String openingText = loopLabel + getText();

        context.drawStrategy(new DrawTextStrategy(openingText));

        context.drawStrategy(new DrawArrowStrategy());

        for(Node node : getChildren()) {
            node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
        }

        context.drawStrategy(new DrawCloseLoopPolygonStrategy());
        String closingText = loopLabel + getClosingBlockText();
        context.drawStrategy(new DrawTextStrategy(closingText));
    }
}