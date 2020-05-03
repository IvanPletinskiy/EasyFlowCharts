package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.flowchart.DrawConstants;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawCircleStrategy;
import com.handen.easyFlowCharts.strategies.DrawOvalStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

public class  MethodNodeGroup extends OneBranchNodeGroup {

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

        for(AbstractNode node : getChildren()) {
            //TODO перенести эту логику в FlowchartDrawer
            if(height + node.measureHeight() + DrawConstants.ARROW_LENGTH + DrawConstants.BLOCK_HEIGHT > DrawConstants.LIST_HEIGHT - DrawConstants.LIST_BOTTOM_OFFSET) {
                drawTransition(context);
                height = 0;
            }
            context = node.draw(context);
            context.setStrategy(new DrawArrowStrategy());
            context.drawCurrentStrategy();
            height += node.measureHeight();
            height += DrawConstants.ARROW_LENGTH;
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