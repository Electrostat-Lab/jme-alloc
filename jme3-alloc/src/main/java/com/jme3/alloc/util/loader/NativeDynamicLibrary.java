package com.jme3.alloc.util.loader;

/**
 * Represents a native image domain.
 * 
 * @warning Internal use only
 * @author pavl_g
 */
public enum NativeDynamicLibrary {
    LINUX_x86_64("libs/linux/x86-64", "lib" + LibraryInfo.LIBRARY.getBaseName() + ".so"),
    LINUX_x86("libs/linux/x86", "lib"+ LibraryInfo.LIBRARY.getBaseName() + ".so"),
    MAC_x86_64("libs/macos/x86-64", "lib"+ LibraryInfo.LIBRARY.getBaseName() + ".dylb"),
    MAC_x86("libs/macos/x86", "lib"+ LibraryInfo.LIBRARY.getBaseName() + ".dylb"),
    WIN_x86("libs/windows/x86", "lib"+ LibraryInfo.LIBRARY.getBaseName() + ".dll"),
    WIN_x86_64("libs/windows/x86-64", "lib"+ LibraryInfo.LIBRARY.getBaseName() + ".dll");

    private final String library;
    private final String directory;
    public static String LIBRARY_BASE_NAME = "jmealloc";

    NativeDynamicLibrary(final String directory, final String library) {
        this.directory = directory;
        this.library = library;
    }

    public String getDirectory() {
        return directory;
    }

    public String getLibrary() {
        return library;
    }

    public String getAbsoluteLibraryLocation() {
        return directory + "/" + library;
    }
}
