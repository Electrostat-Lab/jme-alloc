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
package com.jme3.alloc.examples;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.alloc.ConcurrentBufferAllocator;

/**
 * Tests the concurrent buffer allocator.
 * 
 * @author pavl_g.
 */
public final class TestConcurrentBufferAllocator {

    private static final Logger LOGGER = Logger.getLogger(TestConcurrentBufferAllocator.class.getName());
    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();
    private static final ConcurrentBufferAllocator allocator = new ConcurrentBufferAllocator();
    private static ByteBuffer buffer;

    private static final Thread FORCE_GC = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                REENTRANT_LOCK.lock();
                /* wait 30 seconds before starting GC! */
                Thread.sleep(30000);
                System.out.println("Started GC!");
                /* Force GC */
                System.gc();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                REENTRANT_LOCK.unlock();
            }
        };
    });

    private static final Thread ALLOCATOR = new Thread(new Runnable() {

        @Override
        public void run() {
            /* Allocate some buffers */
            try {
                REENTRANT_LOCK.lock();
                System.out.println("Started allocating memory!");
                /* allocate 2GBs * 20 = 40GBs direct buffers and register it to GC */
                for (int i = 0; i < 20; i++) {
                    allocator.allocate(2000000000L);
                }
            } finally {
                REENTRANT_LOCK.unlock();
            }
        };
    });

    public static void main(String[] args) throws InterruptedException {
        LOGGER.log(Level.INFO, "**************** " + TestConcurrentBufferAllocator.class.getName() + "****************");
        ALLOCATOR.start();
        FORCE_GC.start();

        Thread.sleep(20000);
        LOGGER.log(Level.INFO, "**************** " + TestConcurrentBufferAllocator.class.getName() + "****************");
    }
}
