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
package com.jme3.alloc.util;

import java.io.IOException;
import com.jme3.alloc.util.loader.NativeBinaryLoader;

/**
 * Provides the jme3-alloc api with getters to track thrown native errors.
 * 
 * @author pavl_g
 */
public final class NativeErrno {

    static {
        try {
            /* extracts and loads the system specific library if it's not been loaded before */
            NativeBinaryLoader.loadLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NativeErrno() {
    }

    /**
     * Tests whether the system is out of memory (cannot allocate more buffers).
     * 
     * @return true if ENOMEM is thrown, false otherwise
     */
    public static boolean isOutOfMemory() {
        return NativeErrno.getErrno() == NativeErrno.getErrorNoMemory();
    }

    /**
     * Tests whether the system has thrown a ENINVAL native error.
     * 
     * @return true if EINVAL is thrown, false otherwise
     */
    public static boolean isInvalidValue() {
        return NativeErrno.getErrno() == NativeErrno.getErrorInvalidValue();
    }

    /**
     * Retrieves the system specific [ENOMEM] from the GNU [errno.h].
     * 
     * @return an integer value representing the no memory error
     */
    public static native int getErrorNoMemory();

    /**
     * Retrieves the system specific [EINVAL] from the GNU [errno.h].
     * 
     * @return an integer value representing the invalid value error
     */
    public static native int getErrorInvalidValue();

    /**
     * Retrieves the native thrown error code.
     * 
     * @return an integer representing the native error code
     */
    public static native int getErrno();
}
