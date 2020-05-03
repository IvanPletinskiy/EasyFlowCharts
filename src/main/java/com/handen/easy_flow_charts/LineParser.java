package com.handen.easy_flow_charts;

import com.handen.easy_flow_charts.Nodes.AbstractNode;
import com.handen.easy_flow_charts.Nodes.IfNodeGroup;
import com.handen.easy_flow_charts.Nodes.LoopNodeGroup;
import com.handen.easy_flow_charts.Nodes.MethodCallNode;
import com.handen.easy_flow_charts.Nodes.MethodNodeGroup;
import com.handen.easy_flow_charts.Nodes.ReturnNode;
import com.handen.easy_flow_charts.Nodes.PrintNode;
import com.handen.easy_flow_charts.Nodes.StatementNode;
import com.handen.easy_flow_charts.Nodes.TryNodeGroup;

import java.util.ArrayList;

class LineParser {

    private ArrayList<String> methodNames = new ArrayList<>();

    public AbstractNode nextNode(String line) {
        if(lineIsMethod(line)) {
            return new MethodNodeGroup(line);
        }
        if(lineIsIf(line)) {
            return new IfNodeGroup(line);
        }
        if(lineIsLoop(line)) {
            return new LoopNodeGroup(line);
        }
        if(lineIsSout(line)) {
            return new PrintNode(line);
        }
        if(lineIsReturn(line)) {
            return new ReturnNode(line);
        }
        if(lineIsMethodCall(line)) {
            return new MethodCallNode(line);
        }
        if(lineIsStatement(line)) {
            return new StatementNode(line);
        }
        if(lineIsTry(line)) {
            return new TryNodeGroup(line);
        }
        if(lineIsElse(line)) {
            return new StatementNode(line);
        }
        if(lineIsCatch(line)) {
            return new StatementNode(line);
        }
        throw new IllegalArgumentException("Can't recognize node inside line: \n" + line);
    }

    private boolean lineIsCatch(String line) {
        return line.contains("catch") && line.contains("{");
    }

    private boolean lineIsElse(String line) {
        return line.contains("else") && line.contains("{");
    }

    private boolean lineIsIf(String line) {
        return line.contains("if") && line.contains("(") && line.contains(")");
    }

    private boolean lineIsMethodCall(String line) {
        boolean isMethodCall = line.contains("(") && line.contains(");");
        if(isMethodCall) {
            String methodName;
            if(line.contains("=")) {
                methodName = line.substring(line.indexOf("=") + 1, line.lastIndexOf("(")).trim();
            }
            else {
                methodName = line.trim().substring(0, line.lastIndexOf("("));
            }
            isMethodCall = methodNames.stream().anyMatch(name -> name.contains(methodName));
        }

        return isMethodCall;
    }

    private boolean lineIsReturn(String line ) {
        return line.contains("return ");
    }

    private boolean lineIsTry(String line) {
        return line.contains("try") && line.contains("{");
    }

    private boolean lineIsSout(String line) {
        return line.contains("System.out.print");
    }

    private boolean lineIsLoop(String line) {
        return line.contains("for") || line.contains("while");
    }

    private boolean lineIsMethod(String line) {
        return line.contains("{") && (line.contains("public") || line.contains("private") || line.contains("static") || line.contains("void") || line.contains("boolean"));
    }

    private boolean lineIsStatement(String line) {
        return line.contains(";");
    }

    public void addMethodName(String methodName) {
        methodNames.add(methodName);
    }
/*
    private boolean lineIsStaticField(String line) {
        return (line.contains("private") || line.contains("public")) && line.contains(";");
    }

 */
}
