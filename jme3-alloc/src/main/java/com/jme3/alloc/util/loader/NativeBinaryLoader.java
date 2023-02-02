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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.UnsatisfiedLinkError;

/**
 * Helper utility for loading native binaries.
 * 
 * @author pavl_g.
 */
public final class NativeBinaryLoader {
    
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final int EOF = -1;
	
    private NativeBinaryLoader() {
    }

    /**
     * Extracts and loads the system and the architecture specific library from the output jar to the [user.dir].
     * 
     * @throws IOException if the library to extract is not present in the jar file
     */
    public static void loadLibrary() throws IOException {
        if (NativeVariant.NAME.getData().contains(NativeVariant.Linux)) {
            loadLinux();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Windows)) {
            loadWindows();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Mac)) {
            loadMac();
        }
    }

    /**
     * Extracts and loads the architecture specific library from [libs/linux] from the output jar to the [user.dir].
     * 
     * @throws IOException if the binary to extract is not present in the jar file
     * @see NativeDynamicLibrary#LINUX_x86
     * @see NativeDynamicLibrary#LINUX_x86_64
     */
    private static void loadLinux() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            incrementalExtractBinary(NativeDynamicLibrary.LINUX_x86_64);
        } else {
            incrementalExtractBinary(NativeDynamicLibrary.LINUX_x86);
        }
    }

    /**
     * Extracts and loads the architecture specific library from [libs/windows] from the output jar to the [user.dir].
     * 
     * @throws IOException if the binary to extract is not present in the jar file
     * @see NativeDynamicLibrary#WIN_x86
     * @see NativeDynamicLibrary#WIN_x86_64
     */
    private static void loadWindows() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            incrementalExtractBinary(NativeDynamicLibrary.WIN_x86_64);
        } else {
            incrementalExtractBinary(NativeDynamicLibrary.WIN_x86);
        }
    }

    /**
     * Extracts and loads the architecture specific library from [libs/macos] from the output jar to the [user.dir].
     * 
     * @throws IOException if the binary to extract is not present in the jar file
     * @see NativeDynamicLibrary#MAC_x86
     * @see NativeDynamicLibrary#MAC_x86_64
     */
    private static void loadMac() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            incrementalExtractBinary(NativeDynamicLibrary.MAC_x86_64);
        } else {
            incrementalExtractBinary(NativeDynamicLibrary.MAC_x86);
        }
    }

    /**
     * Retrieves the absolute path for the native library as supposed to be on the current user dir.
     * 
     * @param library the native library
     * @return the absolute path composed of the current user directory and the library name and system specific extension
     */
    private static String getAbsoluteLibraryDirectory(final NativeDynamicLibrary library) {
        return System.getProperty("user.dir") + System.getProperty("file.separator") + library.getLibrary();
    }

    /**
     * Tests whether the native library is extracted to the current user dir.
     * 
     * @param library the native library
     * @return true if the library has been extracted before, false otherwise
     */
    private static boolean isExtracted(final NativeDynamicLibrary library) {
        return new File(getAbsoluteLibraryDirectory(library)).exists();
    }

    /**
     * Loads a binary with a retry criteria.
     * 
     * @params library the native library to load
     * @params criteria a retry criteria, default is {@link RetryCriteria#RETRY_WITH_CLEAN_EXTRACTION}
     * @throws IOException in case the binary to be extracted is not found on the output jar
     */
    private static void loadBinary(final NativeDynamicLibrary library, final RetryCriteria criteria) throws IOException {
        try {
            System.load(getAbsoluteLibraryDirectory(library));
        } catch (final UnsatisfiedLinkError error) {
            switch (criteria) {
                case RETRY_WITH_INCREMENTAL_EXTRACTION:
                    incrementalExtractBinary(library);
                    break;
                
                default:
                    cleanExtractBinary(library);
                    break;
            }
        }
    }
    

    /**
     * Incrementally extracts and loads the native binary to the current [user.dir] with a retry binary load criteria.
     * The retry criteria utilizes the {@link java.lang.UnsatisfiedLinkError} to cleanly re-extract and re-load the binary in case of 
     * broken binaries.
     * 
     * @params library the native library to extract and load
     * @throws IOException in case the binary to be extracted is not found on the output jar
     */
    private static void incrementalExtractBinary(final NativeDynamicLibrary library) throws IOException {        
        if (isExtracted(library)) {
            loadBinary(library, RetryCriteria.RETRY_WITH_CLEAN_EXTRACTION);
            return;
        }
        cleanExtractBinary(library);
    }

    /**
     * Cleanly extracts and loads the native binary to the current [user.dir].
     * 
     * @params library the library to extract and load
     * @throws IOException in case the binary to be extracted is not found on the output jar
     */
    private static void cleanExtractBinary(final NativeDynamicLibrary library) throws IOException {
        /* CRITICAL SECTION STARTS */
        LOCK.lock();
        final InputStream nativeLib = NativeBinaryLoader.class.getClassLoader().getResourceAsStream(library.getAbsoluteLibraryLocation());
        final FileOutputStream fos = new FileOutputStream(getAbsoluteLibraryDirectory(library));  
        try {
            // extract the shipped native files
            final byte[] buffer = new byte[nativeLib.available()];
            for (int bytes = 0; bytes != EOF; bytes = nativeLib.read(buffer)) {
                /* use the bytes as the buffer length to write valid data */
                fos.write(buffer, 0, bytes);
            }
            loadBinary(library, RetryCriteria.RETRY_WITH_CLEAN_EXTRACTION);
        } finally {
            nativeLib.close();
            fos.close();
            LOCK.unlock();
            /* CRITICAL SECTION ENDS */
        }
    }
}