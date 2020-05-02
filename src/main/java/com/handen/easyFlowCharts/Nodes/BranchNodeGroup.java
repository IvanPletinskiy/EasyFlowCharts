package com.handen.easyFlowCharts.Nodes;

import com.handen.easyFlowCharts.Context;

import java.util.List;

/**
 * Branch without opening and closing block, i.e. left or right branch of TwoBranchNodeGroup
 */
//TODO delete
public class BranchNodeGroup extends OneBranchNodeGroup {

    public BranchNodeGroup() {
        super("");
    }

    private BranchNodeGroup(String line) {
        super(line);
    }

    @Override
    List<AbstractNode> getChildren() {
        return null;
    }

    @Override
    String getClosingBlockText() {
        return "";
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public Context draw(Context context) {

        return context;
    }

    @Override
    public int measureHeight() {


        return 0;
    }
}