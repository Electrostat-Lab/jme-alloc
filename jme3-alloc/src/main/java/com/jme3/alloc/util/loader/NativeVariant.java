package com.jme3.alloc.util.loader;

/**
 * Represents a native variant (OS + ARCH).
 * 
 * @warning Internal use only
 * @author pavl_g
 */
public enum NativeVariant {
    NAME(System.getProperty("os.name")),
    ARCH(System.getProperty("os.arch"));
    
    public static final String Linux = "Linux";
    public static final String Windows = "Windows";
    public static final String Mac = "Mac";

    private final String data;

    NativeVariant(final String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
    
    /** WIP */

    public static boolean is_x86_64(final String arch) {
        return arch.equals("amd64");
    }
    public static boolean is_x86(final String arch) {
        return arch.equals("x86");
    }
}
