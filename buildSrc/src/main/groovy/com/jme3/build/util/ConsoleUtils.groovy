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
package com.jme3.build.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Process;

/**
 * Some useful console io utils to provide log errors and info.
 * 
 * @author pavl_g
 */
public final class ConsoleUtils {
    private ConsoleUtils() {
    }

    /**
     * Prints the process input-stream to the [stdout].
     * 
     * @param process a {@link java.lang.Process} process
     */
    public static void printConsoleInput(final Process process) {
        try {
            printInputStream(process.getInputStream(), System.out);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the process input-stream to the [stderr].
     * 
     * @param process a {@link java.lang.Process} process
     */
    public static void printConsoleError(final Process process) {
        try {
            printInputStream(process.getErrorStream(), System.err);
        } catch(IOException e) {
            e.printStackTrace();
        }    
    }

    /**
     * Prints an input-stream content to a stream [e.g: stdout, stderr].
     * 
     * @param in an input-stream to print its content
     * @param stream an output-stream to print on it
     */
    public static int printInputStream(final InputStream in, final PrintStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            for (String out = null; (out = reader.readLine()) != null;) {
                stream.println(out);
            }
        } finally {
            reader.close();
            in.close();
            reader = null;
        }
        return 0;
    }
}
