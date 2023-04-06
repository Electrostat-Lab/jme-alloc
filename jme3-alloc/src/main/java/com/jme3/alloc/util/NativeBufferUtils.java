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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jme3.alloc.util.loader.NativeBinaryLoader;

/**
 * A jvm low-level implementation to the native GNU-Standard-Library [stdlib].
 * 
 * @author pavl_g
 */
public final class NativeBufferUtils {

    static {
        NativeBinaryLoader.loadLibraryIfEnabled();
    }

    private NativeBufferUtils() {
    }

    /**
     * Allocates a buffer of [size] bytes using [void* malloc(size_t)] provided by GNU stdlib.
     * Note: Maximum size is (2^32) for 32-bit architectures and (2^64) for 64-bit architectures.
     * 
     * @param size the size of the newly created buffer in bytes 
     * @return a newly created un-initialized {@link java.nio.ByteBuffer} object
     * @see NativeBufferUtils#clearAlloc(long)
     */
    public static native ByteBuffer memoryAlloc(long size);

    /**
     * Allocates a buffer block of [size] bytes using [void* calloc(size_t)] provided by GNU stdlib, and initializes its elements to zero.
     * Note: Maximum size is (2^32) for 32-bit architectures and (2^64) for 64-bit architectures.
     * 
     * @param size the size of the byte buffer in bytes units
     * @return a newly created zeroed {@link java.nio.ByteBuffer} with the [size] bytes
     * @see NativeBufferUtils#memoryAlloc(long)
     */
    public static native ByteBuffer clearAlloc(long size);

    /**
     * Copies the value [value] into [size] byte elements starting from the [buffer] address.
     * 
     * @param buffer a {@link java.nio.ByteBuffer} to set its elements
     * @param value an 8-bit value to set the memory blocks (buffer elements) with, starting from the buffer address
     * @param size the number of byte elements to set
     * @return the java-nio byte buffer for chained invocations
     */
    public static native ByteBuffer memorySet(ByteBuffer buffer, int value, long size); 

    /**
     * Manipulates the values of byte elements [size] starting from the buffers addresses, from a bytebuffer to another.
     * 
     * @param to the destination {@link java.nio.ByteBuffer}
     * @param from the source {@link java.nio.ByteBuffer}
     * @param size the number of elements to move
     * @see NativeBufferUtils#memoryCopy(ByteBuffer, ByteBuffer, long)
     */
    public static native void memoryMove(ByteBuffer to, ByteBuffer from, long size);

    /**
     * Copies the values of byte elements [size] starting from the buffers addresses, from a bytebuffer to another.
     * 
     * @param to the destination {@link java.nio.ByteBuffer}
     * @param from the source {@link java.nio.ByteBuffer}
     * @param size the number of elements to move
     * @see NativeBufferUtils#memoryMove(ByteBuffer, ByteBuffer, long)
     */
    public static native void memoryCopy(ByteBuffer to, ByteBuffer from, long size);

    /**
     * Frees a buffer previously allocated by {@link NativeBufferUtils#memoryAlloc(long)} and destroys (nullifies) the memory reference. 
     * 
     * @param buffer a {@link java.nio.Buffer} to destroy
     * @see NativeBufferUtils#memoryAlloc(long)
     * @see NativeBufferUtils#clearAlloc(long)
     */
    public static native void destroy(Buffer buffer);

    /**
     * Frees a buffer memory previously allocated by {@link NativeBufferUtils#memoryAlloc(long)} and destroys (nullifies) the memory reference. 
     * 
     * @param memoryAddress a {@link java.nio.Buffer} memory address to destroy
     * @see NativeBufferUtils#memoryAlloc(long)
     * @see NativeBufferUtils#clearAlloc(long)
     */
    public static native void destroy(long memoryAddress);

    /**
     * Retrieves a memory address of a buffer previously allocated by {@link NativeBufferUtils#memoryAlloc(long)} in integers.
     * 
     * @param buffer a {@link java.nio.Buffer} to retrieve its memory address
     * @return a 32-bit or 64-bit integer (depending on the architecture) representing the memory address of the specified buffer
     */
    public static native long getMemoryAddress(Buffer buffer);
}