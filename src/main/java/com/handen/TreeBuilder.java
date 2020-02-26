package com.handen;

import java.util.ArrayList;
import java.util.Stack;

class TreeBuilder {
    private ArrayList<String> lines;
    private ArrayList<String> staticFieldLines = new ArrayList<>();
    private ArrayList<Node> methodNodes = new ArrayList<>();

    public TreeBuilder(ArrayList<String> lines) {
        this.lines = lines;
    }

    public ArrayList<Node> getMethodTrees() {
        ArrayList<Node> methodNodes = new ArrayList<>();
        Node methodNode = null;
        Stack<NodeTypes> openedNodes = new Stack<>();
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(lineIsStaticField(line)) {
                staticFieldLines.add(line);
            }
            if(lineIsMethod(line)) {
                methodNode = new Node(line);
                openedNodes.add(NodeTypes.METHOD);
            }
            else {
                Node currentNode = null;
                if(line.contains("}")) {
                    NodeTypes lastOpenedNode = openedNodes.pop();
                    currentNode = getCorrespondingClosingNode(lastOpenedNode, line);
                }
                else {
                    if(line.contains("{")) {
                        currentNode = new Node(line);
                        openedNodes.push(currentNode.getType());
                    }
                    else {
                        currentNode = new Node(line, NodeTypes.STATEMENT);
                    }
                }

                if(currentNode.getType() == NodeTypes.METHOD_END)
                    methodNodes.add(methodNode);
                if(methodNode != null)
                    methodNode.recursivelyAddNode(currentNode);
            }
        }
        return methodNodes;
    }

    private Node getCorrespondingClosingNode(NodeTypes lastOpenedNode, String line) {
        switch(lastOpenedNode) {
            case LOOP: {
                return new Node(line, NodeTypes.LOOP_END);
            }
            case METHOD: {
                return new Node("конец", NodeTypes.METHOD_END);
            }
            case IF: {
                return new Node(line, NodeTypes.THEN_END);
            }
            case ELSE: {
                return new Node(line, NodeTypes.ELSE_END);
            }
            default: return new Node("getCorrespondingClosingNode", NodeTypes.EMPTY);
        }
    }

    private boolean isLineValid(String line) {
        return !line.contains("import") &&
                !line.trim().isEmpty();
    }

    private static boolean lineIsMethod(String line) {
        return (line.contains("public") || line.contains("private") && (line.contains("static") || line.contains("void")));
    }

    private static boolean lineIsStaticField(String line) {
        return (line.contains("private") || line.contains("public")) && line.contains(";");
    }
}