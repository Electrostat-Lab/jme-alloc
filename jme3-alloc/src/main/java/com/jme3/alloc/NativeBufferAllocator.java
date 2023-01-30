package com.jme3.alloc;

import java.nio.ByteBuffer;
import com.jme3.alloc.util.NativeBufferUtils;

/**
 * Provides a quick implementation to the base direct buffer allocator api.
 * 
 * @see com.jme3.alloc.util.NativeBufferUtils
 * @author pavl_g
 */
public final class NativeBufferAllocator {
    
    private NativeBufferAllocator() {
    }

    /**
     * Creates a new direct byte buffer explicitly with a specific [capacity] in bytes.
     *
     * @param size the buffer size in bytes units
     * @return a new direct byte buffer object of size [capacity] in bytes
     * @see com.jme3.alloc.util.NativeBufferUtils#clearAlloc(long)
     */
    public static ByteBuffer createDirectByteBuffer(final long capacity) {
        return (ByteBuffer) NativeBufferUtils.clearAlloc(capacity);
    }
    
    /**
     * Releases the memory of a direct buffer using a buffer object reference.
     *
     * @param buffer the buffer reference to release its memory.
     * @see com.jme3.alloc.util.NativeBufferUtils#destroy(java.nio.Buffer)
     */
    public static void releaseDirectByteBuffer(final ByteBuffer buffer) {
        NativeBufferUtils.destroy(buffer);
    }
}