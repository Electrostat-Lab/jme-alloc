package com.jme3.alloc.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jme3.alloc.util.NativeBufferUtils;

final class GarbageCollectibleBuffer extends PhantomReference<Buffer> {
    private long memoryAddress;
    
    private GarbageCollectibleBuffer(ByteBuffer referent, ReferenceQueue<Buffer> queue) {
        super(referent, queue);
        this.memoryAddress = NativeBufferUtils.getMemoryAdress(referent);
    }

    public static GarbageCollectibleBuffer from(ByteBuffer buffer, ReferenceQueue<Buffer> queue) {
        if (!buffer.isDirect()) {
            throw new UnSupportedBufferException("Target Buffer isnot a direct Buffer!");
        }
        return new GarbageCollectibleBuffer(buffer, queue);
    }
    
    public long getMemoryAddress() {
         return memoryAddress;
    }
}