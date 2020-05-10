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
        }
        return text;
    }

    @Override
    public String getOpeningBlockText() {
        String text;
        if(line.contains("for") && !line.contains(":")) {
            //for
            int firstSemicolonIndex = line.indexOf(';');
            String initialization = line.substring(line.indexOf("(") + 1, firstSemicolonIndex);
            int secondSemicolonIndex = line.indexOf(';', firstSemicolonIndex + 1);
            String condition = line.substring(secondSemicolonIndex + 1, line.lastIndexOf(";"));
            text = initialization + condition;
        }
        else {
            //while, foreach
            text = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        }

        return text;
    }

    @Override
    public Context draw(Context context) {
        context.drawStrategy(new DrawOpenLoopPolygonStrategy());

        String openingText = context.getLoopLabelText() + getOpeningBlockText();
        context.drawStrategy(new DrawTextStrategy(openingText));

        context.drawStrategy(new DrawArrowStrategy());

        for(AbstractNode node : getChildren()) {
            node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
        }

        context.drawStrategy(new DrawCloseLoopPolygonStrategy());
        String closingText = context.getLoopLabelText() + getClosingBlockText();
        context.drawStrategy(new DrawTextStrategy(closingText));
        return context;
    }
}