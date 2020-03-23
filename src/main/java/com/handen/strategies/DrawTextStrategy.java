package com.handen.strategies;

import com.handen.utils.Point;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

import static com.handen.Nodes.AbstractNode.BLOCK_HEIGHT;
import static com.handen.Nodes.AbstractNode.BLOCK_WIDTH;

public class DrawTextStrategy implements DrawStrategy {

    private static final int MAX_SYMBOLS_IN_LINE = 20;
    private static final int LINE_MARGIN = 2;
    public static final int FONT_SIZE = 12;
    private String text;

    public DrawTextStrategy(String text) {
        this.text = text;
    }

    @Override
    public Point draw(GraphicsContext gc, Point p) {
        ArrayList<String> lines = new ArrayList<>();
        if(text != null) {
            boolean isDividable = true;
            while(text.length() > MAX_SYMBOLS_IN_LINE && isDividable) {
                int dividerIndex = Math.max(text.lastIndexOf(" ", MAX_SYMBOLS_IN_LINE - 1), text.lastIndexOf(".", MAX_SYMBOLS_IN_LINE));
                if(dividerIndex > 0) {
                    lines.add(text.substring(0, dividerIndex));
                    text = text.substring(dividerIndex);
                }
                else {
                    //Нет пробелов до максимального количества символов в строке, найдём наименьший после этого
                    int spaceIdx = text.indexOf(" ", MAX_SYMBOLS_IN_LINE - 1);
                    int dotIdx = text.indexOf(".", MAX_SYMBOLS_IN_LINE);
                    if(spaceIdx > 0 && spaceIdx < dotIdx) {
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

            int totalHeight = FONT_SIZE * lines.size() + (lines.size() - 1) * LINE_MARGIN;
            int top = (BLOCK_HEIGHT - totalHeight) / 2;
            int currentY = p.y + top + (FONT_SIZE / 2);
            for(String line : lines) {
                gc.fillText(line, p.x + BLOCK_WIDTH / 2, currentY, BLOCK_WIDTH);
                currentY += LINE_MARGIN + FONT_SIZE;
            }
        }
        return p;
    }
}