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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a thread that is synchronized with the GC to de-allocate a previously 
 * registered {@link GarbageCollectibleBuffer}, the thread blocks until the buffer 
 * reference is added to the queue by the GC as a part of post-mortem actions, then it 
 * acts upon the reference to de-allocate the original native memory from the native-heap.
 * 
 * @author pavl_g
 */
final class MemoryScavenger extends Thread {
    private final Logger LOGGER = Logger.getLogger(MemoryScavenger.class.getName());
    private final GarbageCollectibleBuffers collectibles;
    private final ReferenceQueue<Buffer> queue;
    
    private MemoryScavenger(GarbageCollectibleBuffers collectibles, ReferenceQueue<Buffer> queue) {
         super(MemoryScavenger.class.getName());
         setDaemon(true);
         this.collectibles = collectibles;
         this.queue = queue;
    }
    
    public static MemoryScavenger start(GarbageCollectibleBuffers collectibles, ReferenceQueue<Buffer> queue) { 
         final MemoryScavenger scavenger = new MemoryScavenger(collectibles, queue);
         scavenger.start();
         return scavenger;
    }
    
    @Override
    public void run() {
         for (;;) {
             // blocks until an object is available in the queue before returning and removing it
             // object references are added to the queue by the GC as a part of post-mortem actions
            try {
                GarbageCollectibleBuffer collectible = (GarbageCollectibleBuffer) queue.remove();
                // de-allocate the direct buffer and removes its address from the [BUFFER_ADDRESSES]
                // Make a scavenger call
                collectibles.deallocate(collectible.getMemoryAddress(), true);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Operation interrupted!", e);
            }
         }
    }
}