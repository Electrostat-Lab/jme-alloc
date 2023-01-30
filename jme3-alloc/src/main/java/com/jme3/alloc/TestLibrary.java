package com.jme3.alloc;

import java.nio.ByteBuffer;

import com.jme3.alloc.util.NativeBufferUtils;

public final class TestLibrary {

    public static void main(String[] args) {
        final ByteBuffer buffer0 = (ByteBuffer) NativeBufferUtils.clearAlloc(8);
        final ByteBuffer buffer1 = (ByteBuffer) NativeBufferUtils.clearAlloc(8);
        buffer0.putInt(0xFF);
        System.out.println(buffer0.getInt(0));
        System.out.println(buffer1.getInt(0));

        final ByteBuffer newBuffer = (ByteBuffer) NativeBufferUtils.memorySet(buffer1, 'A', 8);
        System.out.println(buffer0.getInt(0));
        System.out.println(buffer1.get(0));
        System.out.println(newBuffer.get(0));
        
        while (true);
    }
}
