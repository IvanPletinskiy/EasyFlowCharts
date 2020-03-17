package com.handen.Nodes;

import com.handen.Context;
import com.handen.strategies.DrawArrowStrategy;
import com.handen.strategies.DrawCircleStrategy;
import com.handen.strategies.DrawOvalStrategy;
import com.handen.strategies.DrawTextStrategy;

import static com.handen.Context.PAGE_HEIGHT;

public class MethodNodeGroup extends OneBranchNodeGroup {

    int page = 0;

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

        int height = 0;

        for(AbstractNode node: getChildren()) {
            if(height + node.measureHeight()  + ARROW_LENGTH + BLOCK_HEIGHT> PAGE_HEIGHT) {
                drawTransition(context);
                height = 0;
            }
            context = node.draw(context);
            context.setStrategy(new DrawArrowStrategy());
            context.drawCurrentStrategy();
            height += node.measureHeight();
            height += ARROW_LENGTH;
        }

        context.setStrategy(new DrawOvalStrategy());
        context.drawCurrentStrategy();

        context.setStrategy(new DrawTextStrategy(getClosingBlockText()));
        context.drawCurrentStrategy();

        return context;
    }

    private void drawTransition(Context context) {
        page++;

        context.setStrategy(new DrawCircleStrategy());
        context.drawCurrentStrategy();

        context.goToNextPage(page);

        context.setStrategy(new DrawCircleStrategy());
        context.drawCurrentStrategy();
        context.setStrategy(new DrawArrowStrategy());
        context.drawCurrentStrategy();
    }
}