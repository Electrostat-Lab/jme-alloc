package com.jme3.alloc.util.loader;

public enum LibraryInfo {
    LIBRARY("jmealloc");

    private String baseName;

    LibraryInfo(final String baseName) {
        this.baseName = baseName;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(final String baseName) {
        this.baseName = baseName;
    }
}
