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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Helper utility for loading native binaries.
 * 
 * @author pavl_g.
 */
public final class NativeBinaryLoader {
    
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final int EOF = -1;
    private static boolean isLoaded = false;
	
    private NativeBinaryLoader() {
    }

    public static void loadLibrary() throws IOException {
        if (NativeVariant.NAME.getData().contains(NativeVariant.Linux)) {
            loadLinux();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Windows)) {
            loadWindows();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Mac)) {
            loadMac();
        }
    }

    private static void loadLinux() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractBinary(NativeDynamicLibrary.LINUX_x86_64);
        } else {
            extractBinary(NativeDynamicLibrary.LINUX_x86);
        }
    }

    private static void loadWindows() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractBinary(NativeDynamicLibrary.WIN_x86_64);
        } else {
            extractBinary(NativeDynamicLibrary.WIN_x86);
        }
    }

    private static void loadMac() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractBinary(NativeDynamicLibrary.MAC_x86_64);
        } else {
            extractBinary(NativeDynamicLibrary.MAC_x86);
        }
    }

    /**
     * Extracts an appropriate system-based native binary from sources and loads the binary using {@link System#load(String)}.
     * 
     * @param library the library to extract to the user directory and load
     */
    private static void extractBinary(final NativeDynamicLibrary library) throws IOException {        
        if (isLoaded) {
            return;
        }

        LOCK.lock();
        
        /* CRITICAL SECTION STARTS */

        final String workingDirectory = System.getProperty("user.dir");
        final InputStream nativeLib = NativeBinaryLoader.class.getClassLoader().getResourceAsStream(library.getAbsoluteLibraryLocation());
        final String extractionLocation = workingDirectory + "/" + library.getLibrary();
        final FileOutputStream fos = new FileOutputStream(extractionLocation);  
        try {
            // extract the shipped native files
            final byte[] buffer = new byte[nativeLib.available()];
            for (int bytes = 0; bytes != EOF; bytes = nativeLib.read(buffer)) {
                /* use the bytes as the buffer length to write valid data */
                fos.write(buffer, 0, bytes);
            }
            System.load(extractionLocation);
            isLoaded = true;
        } finally {
            nativeLib.close();
            fos.close();
            LOCK.unlock();
            /* CRITICAL SECTION ENDS */
        }
    }
}