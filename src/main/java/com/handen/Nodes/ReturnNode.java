package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawParallelogramStrategy;
import com.handen.strategies.DrawTextStrategy;

public class ReturnNode extends SingleNode{

    public ReturnNode(String line) {
        super(line);
    }

    @Override
    public String getText() {

        return line.trim().substring(line.indexOf("return") + 6,).replace(";", "");
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