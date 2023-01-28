package com.jme3.alloc;

import java.nio.ByteBuffer;

public final class TestLibrary {

    public static void main(String[] args) {
        final ByteBuffer buffer = NativeBufferAllocator.createDirectByteBuffer(100000);
        System.out.println(buffer);
        System.out.println(buffer.capacity());
        buffer.put((byte) 100);
        System.out.println(buffer.get(0) + "");
    
        System.out.println(buffer);
        System.out.println(buffer.capacity());
        System.out.println(buffer.get(0) + "");
        buffer.put((byte) 12);
        System.out.println(buffer.get(0) + " ");
        System.out.println(buffer.get(1) + " ");
        System.out.println(buffer);
        NativeBufferAllocator.releaseDirectByteBuffer(buffer);
        System.out.println(buffer.get(0) + " ");
        System.out.println(buffer.get(1) + " ");
        System.out.println(buffer);
        
        while (true);
    }
}
