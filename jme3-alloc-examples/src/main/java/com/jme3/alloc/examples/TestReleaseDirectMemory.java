package com.jme3.alloc.examples;

import com.jme3.alloc.ConcurrentBufferAllocator;
import com.jme3.app.SimpleApplication;
import com.jme3.util.BufferAllocator;
import com.jme3.util.BufferAllocatorFactory;
import com.jme3.util.BufferUtils;
import com.jme3.util.LWJGLBufferAllocator;
import java.nio.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.jme3.alloc.gc.GarbageCollectibleBufferAllocator;

/**
 * Stress-test for the direct memory alloction V.S. direct memory release manually and through the
 * GC MemoryScavenger.
 * 
 * @author Ali-RS
 * @author pavl_g 
 */
public class TestReleaseDirectMemory extends SimpleApplication {

    public static void main(String[] args){
        System.setProperty(BufferAllocatorFactory.PROPERTY_BUFFER_ALLOCATOR_IMPLEMENTATION, TestAllocator.class.getName());
        TestReleaseDirectMemory app = new TestReleaseDirectMemory();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        System.out.println("Allocator=" + System.getProperty(BufferAllocatorFactory.PROPERTY_BUFFER_ALLOCATOR_IMPLEMENTATION));

        final Queue<Buffer> bufferQueue = new ConcurrentLinkedQueue<>();

        Thread allocator1 = new Thread(() -> {
            System.out.println("Running buffer allocator 1");
            while (true) {
                FloatBuffer buffer = BufferUtils.createFloatBuffer(524288);
                for (int i = 0; i < 100000; i++) {
                    buffer.put(Float.MAX_VALUE);
                }

                bufferQueue.add(buffer);
            }
        });
        allocator1.setName("Allocator-1");
        allocator1.setDaemon(true);
        allocator1.start();

        Thread allocator2 = new Thread(() -> {
            System.out.println("Running buffer allocator 2");
            while (true) {
                ShortBuffer buffer = BufferUtils.createShortBuffer(524288);
                for (int i = 0; i < 100000; i++) {
                    buffer.put(Short.MAX_VALUE);
                }

                bufferQueue.add(buffer);
            }
        });
        allocator2.setName("Allocator-2");
        allocator2.setDaemon(true);
        allocator2.start();

        Thread deallocator = new Thread(() -> {
            System.out.println("Running buffer deallocator");
            while (true) {
                Buffer buffer = bufferQueue.poll();
                if (buffer != null) {
                    BufferUtils.destroyDirectBuffer(buffer);
                }
            }
        });
        deallocator.setName("Deallocator");
        deallocator.setDaemon(true);
        deallocator.start();
    }

    @Override
    public void simpleUpdate(float tpf) {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(524288);
        for (int i = 0; i < 300000; i++) {
            buffer.put(Double.MAX_VALUE);
        }

        System.gc();
    }

    public static class TestAllocator implements BufferAllocator {

        enum AllocatorType {
            Default, // Use ByteBuffer.allocateDirect(size)
            Lwjgl,   // Use jme3-lwjgl3 LWJGLBufferAllocator
            JmeAlloc // Use jme3-alloc
        }

        final AllocatorType allocatorType = AllocatorType.JmeAlloc;

        final GarbageCollectibleBufferAllocator jmeAlloc = new ConcurrentBufferAllocator();
        final LWJGLBufferAllocator lwjglAlloc = new LWJGLBufferAllocator.ConcurrentLWJGLBufferAllocator();

        @Override
        public void destroyDirectBuffer(Buffer toBeDestroyed) {
            if (allocatorType == AllocatorType.Lwjgl) {
                lwjglAlloc.destroyDirectBuffer(toBeDestroyed);
            } else if (allocatorType == AllocatorType.JmeAlloc) {
                jmeAlloc.deallocate(toBeDestroyed);
            }
        }

        @Override
        public ByteBuffer allocate(int size) {
            if (allocatorType == AllocatorType.Lwjgl) {
                return lwjglAlloc.allocate(size);
            } else if (allocatorType == AllocatorType.JmeAlloc) {
                return jmeAlloc.allocate(size);
            } else {
                return ByteBuffer.allocateDirect(size);
            }
        }
    }
}