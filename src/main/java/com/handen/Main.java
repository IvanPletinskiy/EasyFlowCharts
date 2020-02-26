package com.handen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static String path = "C:\\Projects\\Block3\\src\\com\\handen\\Main.java";

    public static void main(String[] args) throws IOException {
	// write your code here
        ArrayList<String> lines = parseFile(new File(path));
        ArrayList<Node> methodNodes = new TreeBuilder(lines).getMethodTrees();
    }

    private static ArrayList<String> parseFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;
        ArrayList<String> lines = new ArrayList<>();
        while((s = reader.readLine()) != null) {
            lines.add(s);
        }
        return lines;
    }
}
