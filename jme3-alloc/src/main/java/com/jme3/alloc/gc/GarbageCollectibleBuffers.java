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
 * A collection utility of GC-registered-direct buffers, the collection list is provided through
 * the registered direct buffers addresses, direct buffers are added to a ReferenceQueue that is 
 * registered to the GC as a part of post-mortem actions.
 * 
 * @author pavl_g
 */
public final class GarbageCollectibleBuffers {
    private final Logger LOGGER = Logger.getLogger(GarbageCollectibleBuffers.class.getName());
    private final Map<Long, GarbageCollectibleBuffer> BUFFER_ADDRESSES = new HashMap<>();
    private final ReferenceQueue<Buffer> COLLECTIBLES = new ReferenceQueue<>();
    
    /**
     * Instantiates a collection of direct buffers that will be registered to be GC'ed.
     * To start the cleaner thread use {@link GarbageCollectibleBuffers#startMemoryScavenger()}.
     * 
     * @see GarbageCollectibleBuffers#register(ByteBuffer)
     */
    public GarbageCollectibleBuffers() {
    }
    
    /**
     * Registers a direct buffer as a {@link GarbageCollectibleBuffer} to the reference queue {@link GarbageCollectibleBuffers#COLLECTIBLES} 
     * to be GC'ed as a part of post-mortem actions.
     * 
     * @param buffer a buffer to register to the GC reference queue
     */
    public void register(Buffer buffer) {
        GarbageCollectibleBuffer collectibleBuffer = GarbageCollectibleBuffer.from(buffer, COLLECTIBLES);
        BUFFER_ADDRESSES.put(collectibleBuffer.getMemoryAddress(), collectibleBuffer);
    }

    /**
     * De-allocates a direct buffer using its reference and removes its address 
     * from the list.
     * 
     * @param buffer the buffer memory address
     * @param isScavenger true if the stack-caller is the {@link MemoryScavenger} thread 
     *                    associated with this collection, false otherwise.
     */
    public void deallocate(Buffer buffer, boolean isScavenger) {
        if (!buffer.isDirect()) {
            throw new UnSupportedBufferException("Buffer isn't a direct buffer!");
        }
        long bufferAddress = NativeBufferUtils.getMemoryAdress(buffer);
        deallocate(bufferAddress, isScavenger);
    }

    /**
     * De-allocates a direct buffer using its memory address and removes it 
     * from the list.
     * 
     * @param bufferAddress the buffer memory address
     * @param isScavenger true if the stack-caller is the {@link MemoryScavenger} thread 
     *                    associated with this collection, false otherwise.
     */
    public void deallocate(long bufferAddress, boolean isScavenger) {
        /* return if the buffer is not in the list of the collectibles */
        if (!BUFFER_ADDRESSES.containsKey(bufferAddress)) {
            log(Level.SEVERE, "Buffer " + bufferAddress + " is not found!", isScavenger);
            return;
        }
        NativeBufferUtils.destroy(bufferAddress);
        BUFFER_ADDRESSES.remove(bufferAddress);
    }
    
    /**
     * Starts the cleaner thread that is blocked until a buffer reference is available at the queue 
     * {@link GarbageCollectibleBuffers#COLLECTIBLES} by the GC as a part of post-morterm actions to 
     * deallocate a direct buffer.
     */
    public void startMemoryScavenger() {
        MemoryScavenger.start(this, COLLECTIBLES);
    }

    private void log(Level level, String msg, boolean disabled) {
        if (disabled) {
            return;
        }
        LOGGER.log(level, msg);
    }
}
