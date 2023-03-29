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