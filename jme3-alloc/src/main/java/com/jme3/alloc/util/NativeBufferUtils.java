package com.jme3.alloc.util;

import java.io.IOException;
import java.nio.Buffer;
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
     * Allocates a buffer of size bytes.
     * 
     * @param size the size of the newly created buffer in bytes (max = 32-bit or 4 bytes)
     * @return a new java-nio buffer object
     */
    public static native Buffer memoryAlloc(final long size);

    /**
     * Copies the value [value] into each of the first [size] bytes of the object beginning at the 
     * memory address of [buffer].
     * 
     * @param buffer a buffer block to start from
     * @param value the value to set the memory blocks with starting from the buffer
     * @param size the size to extend after the buffer address
     * @return the buffer block
     */
    public static native void memorySet(final Buffer buffer, final int value, final long size); 

    /**
     * 
     * @param to
     * @param from
     * @param size
     * @return
     */
    public static native void memoryMove(final Buffer to, final Buffer from, final long size);

    /**
     * Allocates a buffer block of size = [size] bytes using {@link NativeBufferUtils#malloc(long)}, and sets its contents to zero.
     * 
     * @param size the size of the single memory block
     * @return a newly created zeroed java-nio buffer with the size of [count * size]
     */
    public static native Buffer clearAlloc(final long size);

    /**
     * Make a buffer block previously allocated by {@link NativeBufferUtils#malloc(long)} larger or smaller, possibly by 
     * copying it to a new location.
     * 
     * @param buffer a java nio buffer to re-size it
     * @param size a new size to assign to the buffer
     * @return a newly re-sized java-nio buffer
     */
    public static native Buffer reAlloc(final Buffer buffer, final long size);

    /**
     * Change the size of buffer blocks previously allocated by {@link NativeBufferUtils#calloc(long, long)} 
     * to [count * size] bytes as with {@link NativeBufferUtils#realloc(Buffer, long)}.
     * 
     * @param buffer a java-nio buffer to re-size it
     * @param count the number of memory blocks to re-allocate
     * @param size the new size to assign to each memory block
     * @return a newly re-sized java-nio buffer
     */
    public static native Buffer reAllocArray(final Buffer buffer, final long count, final long size);

    /**
     * Allocates a block of size bytes whose address is a multiple of alignment. 
     * The alignment must be a power of two and size must be a multiple of alignment.
     * 
     * @param alignment
     * @param size
     * @return
     */
    public static native Buffer alignedAlloc(final long alignment, final long size);

    public static native int memoryAllocInfo(final int options, final String outputPath);

    /**
     * Frees a buffer previously allocated by {@link NativeBufferUtils#malloc(long)} and destroys (nullifies) the memory reference. 
     * 
     * @param buffer a java nio buffer to destroy
     */
    public static native void destroy(final Buffer buffer);

    
    public static native long getBufferCapacity(final Buffer buffer);

    public static native long getBufferSize(final Buffer buffer);

    /**
     * Retrieves a memory address of a buffer previously allocated by {@link NativeBufferUtils#malloc(long)} in integers.
     * 
     * @param buffer a java nio buffer to retrieve its memory address
     * @return an integer representing the memory address of the specified buffer
     */
    public static native long getMemoryAdress(final Buffer buffer);

    public static native int mallocStats();
}