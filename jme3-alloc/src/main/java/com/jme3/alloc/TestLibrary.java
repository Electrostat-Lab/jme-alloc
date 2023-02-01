package com.jme3.alloc;

import java.nio.ByteBuffer;

import com.jme3.alloc.util.NativeBufferUtils;

public class TestLibrary {
    public static void main(String[] args) {
        final ByteBuffer buffer = NativeBufferUtils.memoryAlloc(2000);
        buffer.putInt(100);
        System.out.println(buffer.getInt(0));
        System.out.println(buffer);
        NativeBufferUtils.destroy(buffer);
        
    }
}
