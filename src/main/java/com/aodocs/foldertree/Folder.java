package com.aodocs.foldertree;

import java.util.List;

public class Folder implements Comparable<Folder> {
    private final String name;
    private List<Folder> childs;

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Folder o) {
        return this.name.compareTo(o.getName());
    }

    public String getName() {
        return name;
    }

    public List<Folder> getChilds() {
        return childs;
    }

    public void setChilds(List<Folder> childs) {
        this.childs = childs;
    }
}
