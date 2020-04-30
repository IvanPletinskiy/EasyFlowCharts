package com.handen.utils;

import java.io.File;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class FileTreeIterator implements Iterable {

    private String startPath;
    private File startFile;


    public FileTreeIterator(String startPath) {
        this.startPath = startPath;
        startFile = new File(startPath);
        if(startFile.isDirectory()) {
            startFile.listFiles();
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return null;
            }
        };
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
