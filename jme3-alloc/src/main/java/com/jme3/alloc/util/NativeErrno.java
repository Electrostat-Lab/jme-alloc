package com.jme3.alloc.util;

import java.io.IOException;
import com.jme3.alloc.util.loader.NativeImageLoader;

public final class NativeErrno {

    static {
        try {
            /* extracts and loads the system specific library if it's not been loaded before */
            NativeImageLoader.loadLibrary();
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
