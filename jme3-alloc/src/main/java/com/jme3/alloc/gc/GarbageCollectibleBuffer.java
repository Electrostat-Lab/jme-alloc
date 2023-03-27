package com.jme3.alloc.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jme3.alloc.util.NativeBufferUtils;

public class GarbageCollectibleBuffer extends PhantomReference<Buffer> {
    private long memoryAddress;
    
    private GarbageCollectibleBuffer(final ByteBuffer referent, final ReferenceQueue<? super Buffer> queue) {
        super(referent, queue);
        this.memoryAddress = NativeBufferUtils.getMemoryAdress(referent);
    }

    public static ByteBuffer from(final ByteBuffer buffer, final ReferenceQueue<? super Buffer> queue) {
        if (!buffer.isDirect()) {
                throw new UnSupportedBufferException("Target Buffer isnot a direct Buffer!");
        }
        new GarbageCollectibleBuffer(buffer, queue);
        return buffer;
    }
    
    public static ByteBuffer allocateDirect(final long size, final ReferenceQueue<? super Buffer> queue) {
        final ByteBuffer buffer = NativeBufferUtils.clearAlloc(size);
        return GarbageCollectibleBuffer.from(buffer, queue);       
    }
    
    public void deallocate() {
        NativeBufferUtils.destroy(memoryAddress);
        memoryAddress = 0;
    }
    
    public long getMemoryAddress() {
         return memoryAddress;
    }
}
