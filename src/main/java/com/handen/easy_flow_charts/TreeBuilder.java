package com.handen.easy_flow_charts;

import com.handen.easy_flow_charts.Nodes.AbstractNode;
import com.handen.easy_flow_charts.Nodes.MethodNodeGroup;
import com.handen.easy_flow_charts.Nodes.NodeGroup;
import com.handen.easy_flow_charts.Nodes.TwoBranchNodeGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TreeBuilder {
    private File file;
    private List<String> lines;
    private Stack<NodeGroup> openedNodeGroups;
    private List<MethodNodeGroup> methodAbstractNodes;
    private LineParser lineParser;

    public TreeBuilder(File file) {
        this.file = file;
        this.lines = new LinkedList<>();
        openedNodeGroups = new Stack<>();
        methodAbstractNodes = new LinkedList<>();
        lineParser = new LineParser();
    }

    public List<MethodNodeGroup> parseFile() {
        readFile();
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

    private void readLines() throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fileReader != null) {
            BufferedReader reader = new BufferedReader(fileReader);
            String s;
            while((s = reader.readLine()) != null) {
                lines.add(s);
            }
        }
    }

    private void readFile() {
        try {
            readLines();
        }
        catch(IOException e) {
            System.err.println("Cannot read from file: " + file.toString());
            e.printStackTrace();
        }
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
        return !line.contains("import") && !line.contains("class ") && !line.contains("package") && !line.contains("@") &&
                !line.isEmpty() && !line.contains("@") && !line.contains("interface") && !(line.contains("void") && line.contains(";"));
    }
}