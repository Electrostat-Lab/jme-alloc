package com.jme3.alloc.gc;

import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.alloc.gc.memory.MemoryScavenger;
import com.jme3.alloc.util.NativeBufferUtils;

/**
 * A collection utility of GC-registered-direct buffers, the collection is provided through
 * the registered direct buffers addresses, direct buffers are added to a ReferenceQueue that is 
 * registered to the GC as a part of post-mortem actions.
 * 
 * @author pavl_g
 */
public final class GarbageCollectibleBuffers {

    /**
     * Starts the memory-scavenger thread with the first item added to this collection.
     */
    static {
        GarbageCollectibleBuffers.startMemoryScavenger();
    }

    private static final Logger LOGGER = Logger.getLogger(MemoryScavenger.class.getName());
    private static final List<Long> BUFFER_ADDRESSES = new ArrayList<>();
    private static final ReferenceQueue<Buffer> COLLECTIBLES = new ReferenceQueue<>();
    
    private GarbageCollectibleBuffers() {
    }
    
    /**
     * Registers a direct buffer as a {@link GarbageCollectibleBuffer} to the reference queue {@link GarbageCollectibleBuffers#COLLECTIBLES} 
     * to be GC'ed as a part of post-mortem actions.
     * 
     * @param buffer a buffer to register to the GC reference queue
     */
    public static void register(ByteBuffer buffer) {
        if (!buffer.isDirect()) {
            throw new UnSupportedBufferException("Buffer isn't a direct buffer!");
        }
        GarbageCollectibleBuffer collectibleBuffer = GarbageCollectibleBuffer.from(buffer, COLLECTIBLES);
        BUFFER_ADDRESSES.add(collectibleBuffer.getMemoryAddress());
    }

    /**
     * De-allocates a direct buffer using its reference and removes its address 
     * from the list.
     * 
     * @param buffer the buffer memory address
     */
    public static void deallocate(Buffer buffer) {
        long bufferAddress = NativeBufferUtils.getMemoryAdress(buffer);
        deallocate(bufferAddress);
    }

    /**
     * De-allocates a direct buffer using its memory address and removes it 
     * from the list.
     * 
     * @param bufferAddress the buffer memory address
     */
    public static void deallocate(long bufferAddress) {
        /* return if the buffer is not in the list of the collectibles */
        if (!BUFFER_ADDRESSES.contains(bufferAddress)) {
            LOGGER.log(Level.SEVERE, "Buffer " + bufferAddress + " is not found!");
            return;
        }
        NativeBufferUtils.destroy(bufferAddress);
        BUFFER_ADDRESSES.remove(bufferAddress);
    }
    
    private static MemoryScavenger startMemoryScavenger() {
        return MemoryScavenger.start(COLLECTIBLES);
    }
}
