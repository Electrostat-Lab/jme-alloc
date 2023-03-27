package com.jme3.alloc.gc.memory;

import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import com.jme3.alloc.gc.GarbageCollectibleBuffer;

/**
 * @author pavl_g
 */
public final class MemoryScavenger extends Thread {
    private final ReferenceQueue<? super Buffer> queue;
     
    private MemoryScavenger(final ReferenceQueue<? super Buffer> queue) {
         super(MemoryScavenger.class.getName());
         setDaemon(true);
         this.queue = queue;
    }
    
    public static MemoryScavenger start(final ReferenceQueue<? super Buffer> queue) { 
         final MemoryScavenger scavenger = new MemoryScavenger(queue);
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
                collectible.deallocate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         }
    }
}