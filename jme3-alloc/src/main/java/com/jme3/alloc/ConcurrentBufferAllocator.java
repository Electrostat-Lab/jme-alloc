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
package com.jme3.alloc;

import com.jme3.alloc.gc.GarbageCollectibleBufferAllocator;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread-safe direct buffer allocator through using critical sections and mutexes.  
 * 
 * @author pavl_g
 */
public class ConcurrentBufferAllocator extends GarbageCollectibleBufferAllocator {
    
    /**
     * A reentrant mutual exclusion Lock with the same basic behavior and semantics 
     * as the implicit monitor lock accessed using synchronized.
     */
    protected final ReentrantLock reentrantLock = new ReentrantLock();
    
    /**
     * Instantiates a thread-safe buffer allocator with the buffer collections.
     * 
     * @see ConcurrentBufferAllocator#allocate(long)
     * @see ConcurrentBufferAllocator#deallocate(long, boolean)
     * @see ConcurrentBufferAllocator#deallocate(Buffer, boolean)
     */
    public ConcurrentBufferAllocator() {
        super();
    }
    
    @Override
    public ByteBuffer allocate(long capacity) {
        reentrantLock.lock();
        try {
            /* CRITICAL-SECTION STARTS*/
            return super.allocate(capacity);
        } finally {
            reentrantLock.unlock();
            /* CRITICAL-SECTION ENDS*/
        }
    }

    @Override
    public void deallocate(Buffer buffer) {
        reentrantLock.lock();
        try {
            /* CRITICAL-SECTION STARTS*/
            super.deallocate(buffer);
        } finally {
            reentrantLock.unlock();
            /* CRITICAL-SECTION ENDS*/
        }
    }
}
