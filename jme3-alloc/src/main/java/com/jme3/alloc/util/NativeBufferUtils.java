package com.jme3.alloc.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.jme3.alloc.util.loader.NativeImageLoader;

/**
 * A jvm low-level implementation to the native GNU-Standard-Library [stdlib].
 * 
 * @author pavl_g
 */
public final class NativeBufferUtils {

    static {
        try {
            /* extracts and loads the system specific library */
            NativeImageLoader.loadLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NativeBufferUtils() {
    }

    /**
     * Allocates a buffer of [size] bytes using [void* malloc(size_t)] provided by GNU stdlib.
     * Note: Maximum size is 32-bit for 32-bit architectures and 64-bit for 64-bit architectures.
     * 
     * @param size the size of the newly created buffer in bytes 
     * @return a newly created un-initialized {@link java.nio.ByteBuffer} object
     * @see NativeBufferUtils#clearAlloc(long)
     */
    public static native ByteBuffer memoryAlloc(final long size);

    /**
     * Allocates a buffer block of [size] bytes using [void* calloc(size_t)] provided by GNU stdlib, and initializes its elements to zero.
     * Note: Maximum size is 32-bit for 32-bit architectures and 64-bit for 64-bit architectures.
     * 
     * @param size the size of the byte buffer in bytes units
     * @return a newly created zeroed {@link java.nio.ByteBuffer} with the [size] bytes
     * @see NativeBufferUtils#memoryAlloc(long)
     */
    public static native ByteBuffer clearAlloc(final long size);

    /**
     * Copies the value [value] into [size] byte elements starting from the [buffer] address.
     * 
     * @param buffer a {@link java.nio.ByteBuffer} to set its elements
     * @param value an 8-bit value to set the memory blocks (buffer elements) with, starting from the buffer address
     * @param size the number of byte elements to set
     * @return the java-nio byte buffer for chained invocations
     * @see NativeBufferUtils#memoryMove(ByteBuffer, ByteBuffer, long)
     */
    public static native ByteBuffer memorySet(final ByteBuffer buffer, final int value, final long size); 

    /**
     * Manipulates the values of byte elements [size] starting from the buffers addresses, from a bytebuffer to another.
     * 
     * @param to the destination {@link java.nio.ByteBuffer}
     * @param from the source {@link java.nio.ByteBuffer}
     * @param size the number of elements to move
     * @see NativeBufferUtils#memorySet(ByteBuffer, int, long) 
     */
    public static native void memoryMove(final ByteBuffer to, final ByteBuffer from, final long size);

    /**
     * Frees a buffer previously allocated by {@link NativeBufferUtils#memoryAlloc(long)} and destroys (nullifies) the memory reference. 
     * 
     * @param buffer a {@link java.nio.ByteBuffer} to destroy
     * @see NativeBufferUtils#memoryAlloc(long)
     * @see NativeBufferUtils#clearAlloc(long)
     */
    public static native void destroy(final ByteBuffer buffer);

    /**
     * Retrieves a memory address of a buffer previously allocated by {@link NativeBufferUtils#memoryAlloc(long)} in integers.
     * 
     * @param buffer a {@link java.nio.ByteBuffer} to retrieve its memory address
     * @return a 32-bit or 64-bit integer (depending on the architecture) representing the memory address of the specified buffer
     */
    public static native long getMemoryAdress(final ByteBuffer buffer);
}