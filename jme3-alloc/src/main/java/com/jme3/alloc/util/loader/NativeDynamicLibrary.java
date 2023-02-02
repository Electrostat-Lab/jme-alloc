/*
 * Copyright (c) 2009-2023 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.alloc.util.loader;

/**
 * Represents a native binary domain with a {@link NativeDynamicLibrary#directory} and a {@link NativeDynamicLibrary#library}.
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
