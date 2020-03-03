package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawArrowStrategy;
import com.handen.strategies.DrawCloseLoopPolygonStrategy;
import com.handen.strategies.DrawOpenLoopPolygonStrategy;
import com.handen.strategies.DrawTextStrategy;

public class LoopNodeGroup extends  OneBranchNodeGroup {

    public LoopNodeGroup(String line) {
        super(line);
    }

    @Override
    String getClosingBlockText() {
        //TODO
        return null;
    }

    @Override
    public String getText() {
        String text = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        return text;
    }

    @Override
    public Context draw(Context context) {
        context.setStrategy(new DrawOpenLoopPolygonStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        context.setStrategy(new DrawArrowStrategy());
        context.drawCurrentStrategy();

        for(AbstractNode node : getChildren()) {
            node.draw(context);
            context.setStrategy(new DrawArrowStrategy());
            context.drawCurrentStrategy();
        }

        context.setStrategy(new DrawCloseLoopPolygonStrategy());
        context.drawCurrentStrategy();
        context.setStrategy(new DrawTextStrategy(getClosingBlockText()));
        context.drawCurrentStrategy();
        return context;
    }
}