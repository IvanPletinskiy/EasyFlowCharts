package com.handen.Nodes;

class LoopNodeGroup extends  OneBranchNodeGroup{

    public LoopNodeGroup(String line) {
        super(line);
    }

    @Override
    String getClosingBlockText() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }
}
