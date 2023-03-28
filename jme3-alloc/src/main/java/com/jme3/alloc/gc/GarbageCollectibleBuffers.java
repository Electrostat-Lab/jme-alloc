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
    
    public GarbageCollectibleBuffers() {
    }
    
    /**
     * Registers a direct buffer as a {@link GarbageCollectibleBuffer} to the reference queue {@link GarbageCollectibleBuffers#COLLECTIBLES} 
     * to be GC'ed as a part of post-mortem actions.
     * 
     * @param buffer a buffer to register to the GC reference queue
     */
    public void register(ByteBuffer buffer) {
        GarbageCollectibleBuffer collectibleBuffer = GarbageCollectibleBuffer.from(buffer, COLLECTIBLES);
        BUFFER_ADDRESSES.put(collectibleBuffer.getMemoryAddress(), collectibleBuffer);
    }

    /**
     * De-allocates a direct buffer using its reference and removes its address 
     * from the list.
     * 
     * @param buffer the buffer memory address
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
    
    public MemoryScavenger startMemoryScavenger() {
        return MemoryScavenger.start(this, COLLECTIBLES);
    }

    private void log(Level level, String msg, boolean disabled) {
        if (disabled) {
            return;
        }
        LOGGER.log(level, msg);
    }
}
