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
    public String getOpeningBlockText() {
        String line = super.line.trim();
        int methodNameStartIdx = line.substring(0, line.indexOf("(")).lastIndexOf(" ") + 1;
        return line.substring(methodNameStartIdx).replace("{", "");
    }

    @Override
    public Context draw(Context context) {
        context.drawStrategy(new DrawOvalStrategy());

        context.drawStrategy(new DrawTextStrategy(getOpeningBlockText()));

        context.drawStrategy(new DrawArrowStrategy());

        int height = 0;

        for(AbstractNode node : getChildren()) {
            //TODO перенести эту логику в FlowchartDrawer
            if(height + node.measureHeight() + DrawConstants.ARROW_LENGTH + DrawConstants.BLOCK_HEIGHT > DrawConstants.LIST_HEIGHT - DrawConstants.LIST_BOTTOM_OFFSET) {
                drawTransition(context);
                height = 0;
            }
            context = node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
            height += node.measureHeight();
            height += DrawConstants.ARROW_LENGTH;
        }

        context.drawStrategy(new DrawOvalStrategy());

        context.drawStrategy(new DrawTextStrategy(getClosingBlockText()));

        return context;
    }

    private void drawTransition(Context context) {
        page++;

        context.drawStrategy(new DrawCircleStrategy());

        context.goToNextPage(page);

        context.drawStrategy(new DrawCircleStrategy());
        context.drawStrategy(new DrawArrowStrategy());
    }
}