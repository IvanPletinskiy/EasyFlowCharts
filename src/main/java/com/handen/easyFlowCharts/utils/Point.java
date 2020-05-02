package com.handen.easyFlowCharts.utils;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}