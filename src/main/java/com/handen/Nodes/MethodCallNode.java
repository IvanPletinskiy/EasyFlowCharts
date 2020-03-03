package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawInnerRectStrategy;
import com.handen.strategies.DrawRectangleStrategy;
import com.handen.strategies.DrawTextStrategy;

public class MethodCallNode extends SingleNode {

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