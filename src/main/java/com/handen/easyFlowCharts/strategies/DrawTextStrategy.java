package com.handen.easyFlowCharts.strategies;

import com.handen.easyFlowCharts.flowchart.DrawConstants;
import com.handen.easyFlowCharts.utils.Point;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_HEIGHT;
import static com.handen.easyFlowCharts.flowchart.DrawConstants.BLOCK_WIDTH;

public class DrawTextStrategy implements DrawStrategy {

    private String text;

    public DrawTextStrategy(String text) {
        this.text = text;
    }

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        ArrayList<String> lines = new ArrayList<>();
        if(text.contains("\n")) {
            String line =  text.substring(0, text.indexOf("\n"));
            lines.add(line);
            text = text.substring(text.indexOf("\n") + 1);
        }
        boolean isDividable = true;
        while(text.length() > DrawConstants.MAX_SYMBOLS_IN_LINE && isDividable) {
            int spaceIndex = text.lastIndexOf(" ", DrawConstants.MAX_SYMBOLS_IN_LINE - 1);
            int dotIndex = text.lastIndexOf(".", DrawConstants.MAX_SYMBOLS_IN_LINE);
            int bracketIndex = text.lastIndexOf("(", DrawConstants.MAX_SYMBOLS_IN_LINE);
            int dividerIndex = Math.max(spaceIndex, dotIndex);
            dividerIndex = Math.max(dividerIndex, bracketIndex);
            if(dividerIndex > 0) {
                lines.add(text.substring(0, dividerIndex));
                text = text.substring(dividerIndex);
            }
            else {
                //Нет пробелов до максимального количества символов в строке, найдём наименьший после этого
                int spaceIdx = text.indexOf(" ", DrawConstants.MAX_SYMBOLS_IN_LINE - 1);
                int dotIdx = text.indexOf(".", DrawConstants.MAX_SYMBOLS_IN_LINE);
                if(spaceIdx > 0 && spaceIdx < dotIdx && dividerIndex >= 0) {
                    lines.add(text.substring(0, dividerIndex));
                    text = text.substring(dividerIndex);
                }

                if(dotIdx == -1 && spaceIdx == -1) {
                    dividerIndex = -1;
                }
                else {
                    if(dotIdx == -1) {
                        dividerIndex = spaceIdx;
                    }
                    else {
                        if(spaceIdx == -1) {
                            dividerIndex = dotIdx;
                        }
                        else {
                            dividerIndex = Math.min(dotIdx, spaceIdx);
                        }
                    }
                }
                if(dividerIndex > 0) {
                    lines.add(text.substring(0, dividerIndex));
                    text = text.substring(dividerIndex);
                }
                else {
                    isDividable = false;
                }
            }
        }
        lines.add(text);

        int totalHeight = DrawConstants.FONT_SIZE * lines.size() + (lines.size() - 1) * DrawConstants.LINE_MARGIN;
        int top = (BLOCK_HEIGHT - totalHeight) / 2;
        int currentY = p.y + top + (DrawConstants.FONT_SIZE / 2);
        for(String line : lines) {
            gc.fillText(line, p.x + BLOCK_WIDTH / 2, currentY, BLOCK_WIDTH);
            currentY += DrawConstants.LINE_MARGIN + DrawConstants.FONT_SIZE;
        }
        return p;
    }
}