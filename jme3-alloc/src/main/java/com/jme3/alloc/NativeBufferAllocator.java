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
package com.jme3.alloc;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jme3.alloc.gc.GarbageCollectibleBuffers;
import com.jme3.alloc.util.NativeBufferUtils;

/**
 * Provides a non-thread-safe quick implementation to the base direct buffer allocator api.
 * 
 * @see com.jme3.alloc.util.NativeBufferUtils
 * @author pavl_g
 */
public class NativeBufferAllocator {
    
    /**
     * A collection of direct {@link Buffer}s to register them to be GC'ed and deallocated as a part of 
     * post-mortem actions or by explicitly forcing GC.
     * 
     * @see NativeBufferAllocator#allocate(long) to allocate and register a new direct buffer.
     */
    protected final GarbageCollectibleBuffers collectibles = new GarbageCollectibleBuffers();

    /**
     * Instantiates a new allocator with a collection of {@link GarbageCollectibleBuffers}.
     * 
     * @see NativeBufferAllocator#allocate(long)
     * @see NativeBufferAllocator#release(Buffer)
     */
    public NativeBufferAllocator() {
        collectibles.startMemoryScavenger();
    }

    /**
     * Creates a new direct byte buffer explicitly with a specific [capacity] in bytes.
     *
     * @param capacity the buffer capacity in bytes units
     * @return a new direct byte buffer object of capacity [capacity] in bytes
     * @see com.jme3.alloc.util.NativeBufferUtils#clearAlloc(long)
     */
    public ByteBuffer allocate(final long capacity) {
        final ByteBuffer buffer = NativeBufferUtils.clearAlloc(capacity);
        collectibles.register(buffer);
        return buffer;
    }
    
    /**
     * Releases the memory of a direct buffer using a buffer object reference.
     *
     * @param buffer the buffer reference to release its memory.
     * @see com.jme3.alloc.util.NativeBufferUtils#destroy(java.nio.Buffer)
     */
    public void release(final Buffer buffer) {
        // Make a non-scavenger call 
        collectibles.deallocate(buffer, false);
    }
}