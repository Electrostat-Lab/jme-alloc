package com.jme3.alloc.gc;

import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jme3.alloc.gc.memory.MemoryScavenger;
import com.jme3.alloc.util.NativeBufferUtils;

public final class GarbageCollectibleBuffers {
    private static ReferenceQueue<Buffer> COLLECTIBLES = new ReferenceQueue<>();
    
    private GarbageCollectibleBuffers() {
    }
    
    public static ByteBuffer allocate(final long size) {
        return GarbageCollectibleBuffer.allocateDirect(size, COLLECTIBLES);
    }
    
    public static ByteBuffer register(final ByteBuffer buffer) {
          if (!buffer.isDirect()) {
                 throw new UnSupportedBufferException("Buffer isn't a direct buffer!");
          }
          GarbageCollectibleBuffer.from(buffer, COLLECTIBLES);
          return buffer;
    }

    public static void deallocate(final Buffer buffer) {
        NativeBufferUtils.destroy(buffer);
    }
    
    public static MemoryScavenger startMemoryScavenger() {
        return MemoryScavenger.start(COLLECTIBLES);
    }
}
