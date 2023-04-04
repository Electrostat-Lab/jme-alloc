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
package com.jme3.alloc.gc;

import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.alloc.util.NativeBufferUtils;

/**
 * A direct buffer allocator utility which allocates and registers direct buffers to be reclaimed by
 * the java garbage collector as a part of postmortem actions or as a result of an explicit call to 
 * {@link System#gc()}.
 * 
 * @author pavl_g
 */
public class GarbageCollectibleBufferAllocator {
    private static final Logger logger = Logger.getLogger(GarbageCollectibleBufferAllocator.class.getName());
    private final Map<Long, GarbageCollectibleBuffer> collectibles = new HashMap<>();
    private final ReferenceQueue<Buffer> referenceQueue = new ReferenceQueue<>();
    
    /**
     * Instantiates a collection of direct buffers that will be registered to be GC'ed.
     * To start the cleaner thread use {@link GarbageCollectibleBufferAllocator#()}.
     * 
     * @see GarbageCollectibleBufferAllocator#register(Buffer)
     */
    public GarbageCollectibleBufferAllocator() {
        startMemoryScavenger(this);
    }

    /**
     * Creates a new direct byte buffer explicitly with a specific [capacity] in bytes and registers
     * it to be reclaimed by the GC as a part of postmortem actions or an explicit call to GC.
     *
     * @param capacity the buffer capacity in bytes units
     * @return a new direct byte buffer object of capacity [capacity] in bytes
     * @see com.jme3.alloc.util.NativeBufferUtils#clearAlloc(long)
     */
    public ByteBuffer allocate(final long capacity) {
        final ByteBuffer buffer = NativeBufferUtils.clearAlloc(capacity);
        register(buffer);
        return buffer;
    }

    /**
     * Registers a direct buffer as a {@link GarbageCollectibleBuffer} to the reference queue {@link GarbageCollectibleBufferAllocator#referenceQueue}
     * to be GC'ed as a part of postmortem actions or as a result of an explicit call to the GC.
     * 
     * @param buffer a buffer to register to the GC reference queue
     */
    public void register(Buffer buffer) {
        GarbageCollectibleBuffer collectibleBuffer = GarbageCollectibleBuffer.from(buffer, referenceQueue);
        collectibles.put(collectibleBuffer.getMemoryAddress(), collectibleBuffer);
    }

    /**
     * De-allocates a direct buffer using its reference and removes its address 
     * from the list.
     * 
     * @param buffer the buffer memory address
     */
    public void deallocate(Buffer buffer) {
        if (!buffer.isDirect()) {
            throw new UnSupportedBufferException("Buffer isn't a direct buffer!");
        }
        long bufferAddress = NativeBufferUtils.getMemoryAddress(buffer);
        deallocate(bufferAddress);
    }

    /**
     * De-allocates a direct buffer using its memory address and removes it 
     * from the list.
     * 
     * @param bufferAddress the buffer memory address
     */
    public synchronized void deallocate(long bufferAddress) {
        /* return if the buffer is not in the list of the referenceQueue */
        GarbageCollectibleBuffer collectible = collectibles.get(bufferAddress);
        if (collectible == null) {
            if (!(Thread.currentThread().getName().equals(MemoryScavenger.class.getName()))) {
                logger.log(Level.SEVERE, "Buffer " + bufferAddress + " is not found! ");
            }
            return;
        }
        NativeBufferUtils.destroy(bufferAddress);
        collectible.setMemoryAddress(0);
        collectibles.remove(bufferAddress);
    }

    /**
     * De-allocates a direct buffer using a {@link GarbageCollectibleBuffer} reference.
     * 
     * @param collectible the GarbageCollectibleBuffer instance
     */
    protected synchronized void deallocate(GarbageCollectibleBuffer collectible) {
        long address = collectible.getMemoryAddress();
        if (address == 0) {
            return;
        }
        NativeBufferUtils.destroy(address);
        collectible.setMemoryAddress(0);
        collectibles.remove(address);
    }

    /**
     * Starts the memory scavenger with a buffer allocator.
     *
     * @param allocator the buffer allocator.
     */
    protected void startMemoryScavenger(GarbageCollectibleBufferAllocator allocator) {
        MemoryScavenger.start(allocator, referenceQueue);
    }
}