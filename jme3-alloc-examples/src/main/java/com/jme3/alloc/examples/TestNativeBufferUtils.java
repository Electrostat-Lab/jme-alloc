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
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Tests the {@link com.jme3.alloc.util.NativeBufferUtils} basic functionalities including: clear memory allocation, destruction
 * and some logging stuff.
 * Note: to run type: └──╼ $./gradlew :jme3-alloc-examples:TestNativeBufferUtils :jme3-alloc-examples:run
 * 
 * @author pavl_g
 */
public final class TestNativeBufferUtils {

    static {
        NativeBinaryLoader.setAutoLoad(false);
    }

    private static final Logger LOGGER = Logger.getLogger(TestNativeBufferUtils.class.getName());
    
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "**************** " + TestNativeBufferUtils.class.getName() + "****************");

        ByteBuffer buffer = NativeBufferUtils.clearAlloc(1000);
        buffer.putInt(200);
        printInfo(buffer);
        NativeBufferUtils.destroy(buffer);
        /* Note: data printed from here is not the buffer's anymore, this buffer's memory address has been destructed */
        /* Warning: the printed data is another buffers data ! */
        /* Note: writing on this buffer again will fire a jvm crash with a crash-log file; because the current buffer may not be thread-safe */
        printInfo(buffer);
        buffer = null;

        LOGGER.log(Level.INFO, "**************** " + TestNativeBufferUtils.class.getName() + "****************");
    }    

    private static void printInfo(final ByteBuffer buffer) {
        System.out.println(buffer);
        System.out.println("Buffer Data: " + buffer.getInt(0));
    }
}
