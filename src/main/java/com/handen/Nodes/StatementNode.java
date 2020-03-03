package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawRectangleStrategy;
import com.handen.strategies.DrawTextStrategy;

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