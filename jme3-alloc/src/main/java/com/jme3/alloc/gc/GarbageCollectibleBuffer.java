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

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import com.jme3.alloc.util.NativeBufferUtils;

/**
 * A direct buffer wrapper that registers a direct buffer to be GC'ed by adding 
 * it to a reference queue. 
 * 
 * @author pavl_g.
 */
final class GarbageCollectibleBuffer extends PhantomReference<Buffer> {
    private long memoryAddress;
    
    private GarbageCollectibleBuffer(Buffer referent, ReferenceQueue<Buffer> queue) {
        super(referent, queue);
        this.memoryAddress = NativeBufferUtils.getMemoryAddress(referent);
    }

    /**
     * Instantiates an object from a direct buffer and a GC reference queue.
     * 
     * @param buffer the buffer to register.
     * @param queue the reference queue that the buffer is added to once GC starts.
     * @return a new object of type [GarbageCollectibleBuffer].
     * @throws UnSupportedBufferException if the buffer is not a direct buffer.
     */
    public static GarbageCollectibleBuffer from(Buffer buffer, ReferenceQueue<Buffer> queue) {
        if (!buffer.isDirect()) {
            throw new UnSupportedBufferException("Target Buffer isnot a direct Buffer!");
        }
        return new GarbageCollectibleBuffer(buffer, queue);
    }
    
    /**
     * Retrieves the memory address of this direct buffer.
     * 
     * @return direct buffer memory address in long format.
     */
    public long getMemoryAddress() {
         return memoryAddress;
    }

    /**
     * Sets the memory address for this buffer.
     * 
     * @param memoryAddress the memory address in long.
     */
    public void setMemoryAddress(long memoryAddress) {
        this.memoryAddress = memoryAddress;
    }
}