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
    
    public static GarbageCollectibleBuffer allocate(final long size) {
          final GarbageCollectibleBuffer collectible = GarbageCollectibleBuffer.allocateDirect(size, COLLECTIBLES);
          return collectible;
    }
    
    public static GarbageCollectibleBuffer register(final ByteBuffer buffer) {
          if (!buffer.isDirect()) {
                 throw new UnSupportedBufferException("Buffer isn't a direct buffer!");
          }
          final GarbageCollectibleBuffer collectible = GarbageCollectibleBuffer.from(buffer, COLLECTIBLES);
          return collectible;
    }

    public static void deallocate(final Buffer buffer) {
          NativeBufferUtils.destroy(buffer);
    }
    
    public static MemoryScavenger startMemoryScavenger() {
        return MemoryScavenger.start(COLLECTIBLES);
    }
}
