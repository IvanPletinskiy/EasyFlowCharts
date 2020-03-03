package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawArrowStrategy;
import com.handen.strategies.DrawOvalStrategy;
import com.handen.strategies.DrawTextStrategy;

public class MethodNodeGroup extends OneBranchNodeGroup {

    public MethodNodeGroup(String line) {
        super(line);
    }

    @Override
    String getClosingBlockText() {
        return "Конец";
    }

    @Override
    public String getText() {
        String line = super.line.trim();
        int methodNameStartIdx = line.substring(0, line.indexOf("(")).lastIndexOf(" ") + 1;
        return line.substring(methodNameStartIdx).replace("{", "");
    }

    @Override
    public Context draw(Context context) {
        context.setStrategy(new DrawOvalStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getText()));
        context.drawCurrentStrategy();

        context.setStrategy(new DrawArrowStrategy());
        context.drawCurrentStrategy();

        for(AbstractNode node: getChildren()) {
            context = node.draw(context);
            context.setStrategy(new DrawArrowStrategy());
            context.drawCurrentStrategy();
        }

        context.setStrategy(new DrawOvalStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getClosingBlockText()));
        context.drawCurrentStrategy();

        return context;
    }
}