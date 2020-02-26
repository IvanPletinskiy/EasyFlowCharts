package com.handen.Nodes;

public class StatementNode extends SingleNode {

    public StatementNode(String line) {
        super(line);
    }

    @Override
    public String getText() {
        return line;
    }
}
