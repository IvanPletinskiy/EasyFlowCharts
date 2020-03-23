package com.handen.Nodes;

import com.handen.Context;

public abstract class AbstractNode {

    public static final int BLOCK_HEIGHT = 75;
    public static final int BLOCK_WIDTH = 150;
    public static final int ARROW_LENGTH = 25;
    public static final int LOOP_BLOCK_OFFSET = (int) (BLOCK_WIDTH * 0.2);
    public static final int THEN_ARROW_LENGTH = BLOCK_WIDTH;

    protected String line;

    public AbstractNode(String line) {
        this.line = line;
    }

    public abstract String getText();

    public abstract int measureHeight();

    public abstract Context draw(Context context);
}