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
     * Creates a new direct byte buffer explicitly with a specific size.
     *
     * @param size the byte buffer size used for allocating the buffer.
     * @return a new direct byte buffer object.
     * @see com.jme3.alloc.util.NativeBufferUtils#calloc(int)
     */
    public static ByteBuffer createDirectByteBuffer(final int capacity) {
        return (ByteBuffer) NativeBufferUtils.clearAlloc(capacity);
    }
    
    /**
     * Releases the memory of a direct buffer using a buffer object reference.
     *
     * @param buffer the buffer reference to release its memory.
     * @see com.jme3.alloc.util.NativeBufferUtils#free(java.nio.Buffer)
     */
    public static void releaseDirectByteBuffer(final ByteBuffer buffer) {
        NativeBufferUtils.destroy(buffer);
    }
}