package com.handen;

import com.handen.Nodes.AbstractNode;
import com.handen.Nodes.MethodNodeGroup;
import com.handen.Nodes.NodeGroup;
import com.handen.Nodes.TwoBranchNodeGroup;

import java.util.ArrayList;
import java.util.Stack;

class TreeBuilder {
    private ArrayList<String> lines;
    private Stack<NodeGroup> openedNodeGroups;
    private ArrayList<MethodNodeGroup> methodAbstractNodes;
    private LineParser lineParser;

    public TreeBuilder(ArrayList<String> lines) {
        this.lines = lines;
        openedNodeGroups = new Stack<>();
        methodAbstractNodes = new ArrayList<>();
        lineParser = new LineParser();
    }

    public ArrayList<MethodNodeGroup> getMethodTrees() {
        addAllMethodNames();

        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if(isLineValid(line)) {
                if(line.equals("}")) {
                    if(i != lines.size() - 1 && !openedNodeGroups.isEmpty()) {
                        if(openedNodeGroups.peek() instanceof TwoBranchNodeGroup) {
                            TwoBranchNodeGroup twoBranchNodeGroup = (TwoBranchNodeGroup) openedNodeGroups.peek();
                            String nextLine = lines.get(i + 1);
                            if(isBracketClosingBranch(nextLine) && twoBranchNodeGroup.isFirstBranch()) {
                                twoBranchNodeGroup.setIsFirstBranch(false);
                            }
                            else {
                                closeNodeGroup();
                            }
                        }
                        else {
                            closeNodeGroup();
                        }
                    }
                }
                else {
                    addNode(line);
                }
            }
        }
        return methodAbstractNodes;
    }

    private void addAllMethodNames() {
        for(String line : lines) {
            line = line.trim();
            if(isLineValid(line) && !line.contains("}")) {
                AbstractNode node = lineParser.nextNode(line);
                if(node instanceof MethodNodeGroup) {
                    lineParser.addMethodName(node.getText());
                }
            }
        }
    }

    private void addNode(String line) {
        AbstractNode node = lineParser.nextNode(line);
        if(node instanceof NodeGroup) {
            if(!openedNodeGroups.isEmpty()) {
                openedNodeGroups.peek().addNode(node);
            }
            openedNodeGroups.push((NodeGroup) node);
        }
        else {
            openedNodeGroups.peek().addNode(node);
        }
    }

    private boolean isBracketClosingBranch(String nextLine) {
        return (nextLine.contains("else ") || nextLine.contains("catch"));
    }

    private void closeNodeGroup() {
        NodeGroup endedNodeGroup = openedNodeGroups.pop();
        if(openedNodeGroups.isEmpty()) {
            methodAbstractNodes.add((MethodNodeGroup) endedNodeGroup);
        }
    }

    private boolean isLineValid(String line) {
        return !line.contains("import") && !line.contains(" class ") && !line.contains("package") && !line.contains("@") &&
                !line.isEmpty() && !line.contains("@");
    }
}