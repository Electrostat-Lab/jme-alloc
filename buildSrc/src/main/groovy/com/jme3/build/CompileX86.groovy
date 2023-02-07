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
package com.jme3.build;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;

/**
 * Compiles a x86 binary to a specific system.
 *
 * @author pavl_g
 */
public class CompileX86 extends UnixScriptRunner {

    /** Placeholders for gradle properties */
    @Input
    protected String compileWindowsX86 = "";
    
    @Input
    protected String compileMacX86 = "";
    
    @Input
    protected String compileLinuxX86 = "";

    protected final boolean isWindows = System.getProperty("os.name").contains("Windows");
    protected final boolean isMacos = System.getProperty("os.name").contains("Mac");
    protected final boolean isLinux = System.getProperty("os.name").contains("Linux");

    @Override
    protected void scriptExecution() {
        boolean isCompileWindowsX86 = getConditionFromValue(compileWindowsX86);
        boolean isCompileMacX86 = getConditionFromValue(compileMacX86);
        boolean isCompileLinuxX86 = getConditionFromValue(compileLinuxX86);

        if (isWindows && !isCompileWindowsX86) {
            /* terminate when on windows if the compile flag is disabled */
            return;    
        } 
        
        if (isMacos && !isCompileMacX86) {
            /* terminate when on MacOS if the compile flag is disabled */
            return;    
        } 
        
        if (isLinux && !isCompileLinuxX86) {
            /* terminate when on linux if the compile flag is disabled */
            return;
        }

        super.scriptExecution();
    }

    private boolean getConditionFromValue(final String value) {
        return value.equals("true");
    }

    public String getCompileLinuxX86() {
        return compileLinuxX86;
    }

    public String getCompileWindowsX86() {
        return compileWindowsX86;
    }

    public String getCompileMacX86() {
        return compileMacX86;
    }

}
