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
package com.jme3.alloc.util.loader;

/**
 * Represents a native variant (OS + ARCH).
 * 
 * @warning Internal use only
 * @author pavl_g
 */
public enum NativeVariant {
    NAME(System.getProperty("os.name")),
    ARCH(System.getProperty("os.arch")),
    VM(System.getProperty("java.vm.name"));
    
    public static final String Linux = "Linux";
    public static final String Windows = "Windows";
    public static final String Mac = "Mac";
    public static final String Dalvik = "Dalvik";

    private final String data;

    NativeVariant(final String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }

    public static boolean isLinux() {
        return NativeVariant.NAME.getData().contains(NativeVariant.Linux);
    }

    public static boolean isWindows() {
        return NativeVariant.NAME.getData().contains(NativeVariant.Windows);
    }

    public static boolean isMac() {
        return NativeVariant.NAME.getData().contains(NativeVariant.Mac);
    }

    public static boolean isAndroid() {
        return VM.getData().contains(NativeVariant.Dalvik);
    }

    public static boolean isX86_64() {
        return ARCH.getData().contains("64");
    }

    public static boolean isX86() {
        return ARCH.getData().equals("x86");
    }
}
