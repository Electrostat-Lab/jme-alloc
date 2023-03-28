package com.jme3.alloc.gc;

import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import com.jme3.alloc.gc.memory.MemoryScavenger;
import com.jme3.alloc.util.NativeBufferUtils;

public final class GarbageCollectibleBuffers {
    private static final List<Long> BUFFER_ADDRESSES = new ArrayList<>();
    private static final ReferenceQueue<Buffer> COLLECTIBLES = new ReferenceQueue<>();
    
    private GarbageCollectibleBuffers() {
    }
    
    /**
     * Registers a direct buffer as a {@link GarbageCollectibleBuffer} to the reference queue {@link GarbageCollectibleBuffers#COLLECTIBLES} 
     * to be GC'ed as a part of the post-mortem actions.
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
            return;
        }
        NativeBufferUtils.destroy(bufferAddress);
        BUFFER_ADDRESSES.remove(bufferAddress);
    }
    
    public static MemoryScavenger startMemoryScavenger() {
        return MemoryScavenger.start(COLLECTIBLES);
    }
}
