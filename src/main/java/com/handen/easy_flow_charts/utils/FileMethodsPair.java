package com.handen.easy_flow_charts.utils;

import com.handen.easy_flow_charts.Nodes.MethodNodeGroup;

import java.io.File;
import java.util.List;

public class FileMethodsPair {
    public File file;
    public List<MethodNodeGroup> methods;

    public FileMethodsPair(File file, List<MethodNodeGroup> methods) {
        this.file = file;
        this.methods = methods;
    }

    public String getFileName() {
        int dotIndex = file.getName().indexOf(".");
        String fileName = file.getName().substring(0, dotIndex);
        return fileName;
    }
}
