package com.handen;

import com.handen.Nodes.AbstractNode;
import com.handen.Nodes.IfNodeGroup;
import com.handen.Nodes.LoopNodeGroup;
import com.handen.Nodes.MethodCallNode;
import com.handen.Nodes.MethodNodeGroup;
import com.handen.Nodes.ReturnNode;
import com.handen.Nodes.SoutNode;
import com.handen.Nodes.StatementNode;
import com.handen.Nodes.TryNodeGroup;

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
            return new SoutNode(line);
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
        boolean isMethodCall = line.contains("(") && line.contains(");") && line.contains("=");
        if(isMethodCall) {
            final String methodName = line.substring(line.indexOf("=") + 1, line.lastIndexOf("(")).trim();
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
