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
 * Tests {@link NativeBufferUtils#memoryCopy(ByteBuffer, ByteBuffer, long)} between 2 buffers, where buffer1 copies all the elements
 * from buffer0.
 * Note: to run type: └──╼ $./gradlew :jme3-alloc-examples:TestMemoryCopy :jme3-alloc-examples:run
 * 
 * @author pavl_g
 */
public class TestMemoryCopy {
    private static final Logger LOGGER = Logger.getLogger(TestMemoryCopy.class.getName());

    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "**************** " + TestMemoryCopy.class.getName() + "****************");
        final long BYTES = 2000000000L;
        ByteBuffer buffer0 = NativeBufferUtils.clearAlloc(BYTES);
        ByteBuffer buffer1 = NativeBufferUtils.clearAlloc(BYTES);
        buffer0.putLong(200000L);
        printInfo(buffer0);
        printInfo(buffer1);
        /* Deep copy all bytes from [buffer0] to [buffer1] */
        NativeBufferUtils.memoryCopy(buffer1, buffer0, BYTES);
        printInfo(buffer1);

        /* Destroy the original buffer and test the cloned buffer */
        NativeBufferUtils.destroy(buffer0);
        /* Destroy the java reference too */
        buffer0 = null;
        
        printInfo(buffer1);
        NativeBufferUtils.destroy(buffer1);
        

        LOGGER.log(Level.INFO, "**************** " + TestMemoryCopy.class.getName() + "****************");
    }    

    private static void printInfo(final ByteBuffer buffer) {
        System.out.println(buffer);
        System.out.println("Buffer Data: " + buffer.getLong(0));
    }
}
