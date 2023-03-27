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

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.alloc.util.NativeBufferUtils;

/**
 * Provides a quick implementation to the base direct buffer allocator api.
 * Buffer memory is automatically released when the Buffer object gets
 * garbage collected.
 * 
 * @see com.jme3.alloc.util.NativeBufferUtils
 * @author pavl_g
 * @author Ali-RS
 */
public final class NativeBufferAllocator {

    private static final Logger LOGGER = Logger.getLogger(NativeBufferAllocator.class.getName());

    /**
     * The reference queue.
     */
    private static final ReferenceQueue<Buffer> REFERENCE_QUEUE = new ReferenceQueue<>();

    /**
     * The cleaner thread.
     */
    private static final Thread CLEAN_THREAD = new Thread(NativeBufferAllocator::freeByteBuffers);

    /**
     * The map with created deallocators.
     */
    private static final Map<Long, Deallocator> DEALLOCATORS = new ConcurrentHashMap<>();

    static {
        CLEAN_THREAD.setDaemon(true);
        CLEAN_THREAD.setName("Buffer Deallocator");
        CLEAN_THREAD.start();
    }

    /**
     * Creates a new direct byte buffer explicitly with a specific [size] in bytes.
     *
     * @param size the buffer capacity in bytes units
     * @return a new direct byte buffer object of size [size] in bytes
     * @see com.jme3.alloc.util.NativeBufferUtils#clearAlloc(long)
     */
    public static ByteBuffer allocate(final long size) {
        final ByteBuffer byteBuffer = NativeBufferUtils.clearAlloc(size);
        final Long address = NativeBufferUtils.getMemoryAdress(byteBuffer);
        DEALLOCATORS.put(address, createDeallocator(address, byteBuffer));
        return byteBuffer;
    }

    /**
     * Releases the memory of a direct buffer using a buffer object reference.
     *
     * @param buffer the buffer reference to release its memory.
     * @see com.jme3.alloc.util.NativeBufferUtils#destroy(java.nio.Buffer)
     */
    public static void release(final Buffer buffer) {

        final long address = getAddress(buffer);

        if (address == -1) {
            LOGGER.warning("Not found address of the " + buffer);
            return;
        }

        // disable deallocator
        final Deallocator deallocator = DEALLOCATORS.remove(address);

        if (deallocator == null) {
            LOGGER.warning("Not found a deallocator for address " + address);
            return;
        }

        deallocator.setAddress(null);

        NativeBufferUtils.destroy(buffer);
    }

    /**
     * The byte buffer deallocator.
     */
    static class Deallocator extends PhantomReference<ByteBuffer> {

        /**
         * The address of byte buffer.
         */
        volatile Long address;

        Deallocator(final ByteBuffer referent, final ReferenceQueue<? super ByteBuffer> queue, final Long address) {
            super(referent, queue);
            this.address = address;
        }

        /**
         * @param address the address of byte buffer.
         */
        void setAddress(final Long address) {
            this.address = address;
        }

        /**
         * Free memory.
         */
        void free() {
            if (address == null) return;
            freeMemory();
            DEALLOCATORS.remove(address);
        }

        void freeMemory() {
            //TODO: NativeBufferUtils.destroy(address);
        }
    }

    /**
     * Free unnecessary byte buffers.
     */
    static void freeByteBuffers() {
        try {
            for (;;) {
                final Deallocator deallocator = (Deallocator) REFERENCE_QUEUE.remove();
                deallocator.free();
            }
        } catch (final InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error freeing byte buffers", e);
        }
    }

    /**
     * Get memory address of the buffer.
     *
     * @param buffer the buffer.
     * @return the address or -1.
     */
    static long getAddress(final Buffer buffer) {

        // TODO: NativeBufferUtils.getMemoryAdress(buffer);

        return -1;
    }

    static Deallocator createDeallocator(final Long address, final ByteBuffer byteBuffer) {
        return new Deallocator(byteBuffer, REFERENCE_QUEUE, address);
    }
}