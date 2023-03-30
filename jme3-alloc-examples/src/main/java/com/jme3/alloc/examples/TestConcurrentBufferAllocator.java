package com.jme3.alloc.examples;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;
import com.jme3.alloc.ConcurrentBufferAllocator;

/**
 * Tests the concurrent buffer allocator.
 * 
 * @author pavl_g.
 */
public final class TestConcurrentBufferAllocator {

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
        ALLOCATOR.start();
        FORCE_GC.start();
    }
}
