package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.flowchart.Context;
import com.handen.easyFlowCharts.strategies.DrawArrowStrategy;
import com.handen.easyFlowCharts.strategies.DrawCircleStrategy;
import com.handen.easyFlowCharts.strategies.DrawOvalStrategy;
import com.handen.easyFlowCharts.strategies.DrawTextStrategy;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.ARROW_LENGTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_BOTTOM_OFFSET;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.LIST_HEIGHT;

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
    public void draw(Context context) {
        context.drawStrategy(new DrawOvalStrategy());
        context.drawStrategy(new DrawTextStrategy(getText()));
        context.drawStrategy(new DrawArrowStrategy());

        int height = 0;
        int width = BLOCK_WIDTH;

        for(Node node : getChildren()) {
            if(height + node.measureHeight() + ARROW_LENGTH + BLOCK_HEIGHT > LIST_HEIGHT - LIST_BOTTOM_OFFSET) {
                drawReference(context, width);
                height = 0;
            }
            node.draw(context);
            context.drawStrategy(new DrawArrowStrategy());
            height += node.measureHeight();
            height += ARROW_LENGTH;
            width = (node.measureWidth() > width) ? node.measureWidth() : width;
        }

        context.drawStrategy(new DrawOvalStrategy());

        context.drawStrategy(new DrawTextStrategy(getClosingBlockText()));
    }

    private void drawReference(Context context, int columnWidth) {
        context.drawStrategy(new DrawCircleStrategy());
        String referenceLabel = context.getReferenceLabel();
        context.drawStrategy(new DrawTextStrategy(referenceLabel));
        context.goToNextColumn(columnWidth);
        context.drawStrategy(new DrawCircleStrategy());
        context.drawStrategy(new DrawTextStrategy(referenceLabel));
        context.drawStrategy(new DrawArrowStrategy());
    }
}