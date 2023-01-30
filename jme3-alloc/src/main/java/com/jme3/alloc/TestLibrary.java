package com.jme3.alloc;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jme3.alloc.util.NativeBufferUtils;

public final class TestLibrary {

    public static void main(String[] args) {
        final ByteBuffer buffer0 = (ByteBuffer) NativeBufferUtils.clearAlloc(100);
        buffer0.put((byte) 100);
        System.out.println(buffer0.get(0));
        NativeBufferUtils.destroy(buffer0);
        System.out.println(buffer0.get(0));
        
        while (true);
    }
}
