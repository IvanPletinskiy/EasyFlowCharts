package com.handen.easyFlowCharts;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.Nodes.Node;
import com.handen.easyFlowCharts.Nodes.NodeGroup;
import com.handen.easyFlowCharts.Nodes.TwoBranchNodeGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class GraphBuilder {
    private File file;
    private Stack<NodeGroup> openedNodeGroups;
    private List<MethodNodeGroup> methodAbstractNodes;
    private LineParser lineParser;

    public GraphBuilder(File file) {
        this.file = file;
        openedNodeGroups = new Stack<>();
        methodAbstractNodes = new LinkedList<>();
        lineParser = new LineParser();
    }

    public List<MethodNodeGroup> parseFile() {
        List<String> lines = readFile();
        addAllMethodNames(lines);

        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if(isLineValid(line)) {
                if(line.equals("}")) {
                    if(i != lines.size() - 1 && !openedNodeGroups.isEmpty()) {
                        String nextLine = lines.get(i + 1);
                        handleEndBracket(nextLine);
                    }
                }
                else {
                    addNode(line);
                }
            }
        }
        return methodAbstractNodes;
    }

    private void handleEndBracket(String nextLine) {
        if(openedNodeGroups.peek() instanceof TwoBranchNodeGroup) {
            TwoBranchNodeGroup twoBranchNodeGroup = (TwoBranchNodeGroup) openedNodeGroups.peek();
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

    private void addNode(String line) {
        Node node = lineParser.nextNode(line);
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

    private List<String> readLines() throws IOException {
        FileReader fileReader = null;
        List<String> lines = new LinkedList<>();
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
        return lines;
    }

    private List<String> readFile() {
        List<String> lines = Collections.emptyList();
        try {
            lines = readLines();
        }
        catch(IOException e) {
            System.err.println("Cannot read from file: " + file.toString());
            e.printStackTrace();
        }

        return lines;
    }

    private void addAllMethodNames(List<String> lines) {
        for(String line : lines) {
            line = line.trim();
            if(isLineValid(line) && !line.contains("}")) {
                Node node = lineParser.nextNode(line);
                if(node instanceof MethodNodeGroup) {
                    lineParser.addMethodName(node.getText());
                }
            }
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
                !line.isEmpty() && !line.contains("@") && !line.contains("interface") && !(line.contains("void") && line.contains(";")) && !line.contains("/*") && !line.contains("*/");
    }
}