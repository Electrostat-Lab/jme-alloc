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

import com.jme3.alloc.util.NativeBufferUtils;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.InterruptedException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Tests dirty multi-threading, the work is distributed among 3 threads, an allocation thread, an initialize thread and a destroy thread.
 * Note: to run type: └──╼ $./gradlew :jme3-alloc-examples:TestDirtyMultithreading :jme3-alloc-examples:run
 * 
 * @author pavl_g
 */
public final class TestDirtyMultithreading {
    
    private static final Logger LOGGER = Logger.getLogger(TestDirtyMultithreading.class.getName());
    private static final ReentrantLock lock = new ReentrantLock();
    private static final int BUFFER_COUNT = 20;
    private static final long BUFFER_SIZE = 2000000L;
    private static final long BUFFER_DATA = 3000L;
    private static final ByteBuffer[] buffers = new ByteBuffer[BUFFER_COUNT];
    
    /**
     * Allocates 20 ByteBuffers with each of size [2000000] bytes
     */
    private static final Thread allocateThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                lock.lock();
                LOGGER.log(Level.FINE, Thread.currentThread().getName());
                for (int i = 0; i < buffers.length; i++) {
                    buffers[i] = NativeBufferUtils.clearAlloc(BUFFER_SIZE);
                }
            } finally {
                lock.unlock();
            }
           
        }
        
    });
    
    /**
     * Fills 20 ByteBuffers with a long data = 3000
     */
    private static final Thread initThread = new Thread(new Runnable() {
        @Override
        public void run() {
            LOGGER.log(Level.FINE, Thread.currentThread().getName());
            try {
                lock.lock();
                /* Test initializing and filling */
                for (int i = 0; i < buffers.length; i++) {
                    buffers[i].putLong(BUFFER_DATA);
                }
                printInfo(buffers);
            } finally {
                lock.unlock();
            }
        }
    });

    /**
     * Destroys the previously allocated 20 ByteBuffers
     */
    private static final Thread destroyThread = new Thread(new Runnable() {
        @Override
        public void run() {
            LOGGER.log(Level.FINE, Thread.currentThread().getName());
            try {
                lock.lock();
                for (int i = 0; i < buffers.length; i++) {
                    NativeBufferUtils.destroy(buffers[i]);
                }
                /* Note?: Activating this line will fire a jvm crash; because those use buffers are now destroyed 
                   and the current buffer objects point to other system data which may not be thread-safe ! */
                // printInfo(buffers);
            } finally {
                lock.unlock();   
            }
        }
        
    });
    
    public static void main(String[] args) throws InterruptedException {
        LOGGER.log(Level.INFO, "**************** " + TestDirtyMultithreading.class.getName() + "****************");
        allocateThread.start();
        Thread.sleep(500);
        initThread.start();
        Thread.sleep(500);
        destroyThread.start();
        LOGGER.log(Level.INFO, "**************** " + TestDirtyMultithreading.class.getName() + "****************");
    }   

    private static void printInfo(final ByteBuffer[] buffers) {
        for (int i = 0; i < buffers.length; i++) {
            System.out.println(buffers[i]);
            System.out.println("Buffer Data: " + buffers[i].getLong(0));
        }
    }
}
