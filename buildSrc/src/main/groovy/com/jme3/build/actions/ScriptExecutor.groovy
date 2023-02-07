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
package com.jme3.build.actions;

import org.gradle.api.resources.MissingResourceException;
import com.jme3.build.util.ConsoleUtils;
import com.jme3.build.UnixScriptRunner;
import java.lang.Process;
import org.gradle.api.Task;
import java.io.IOException;
import java.lang.InterruptedException;

/**
 * A task that executes a script and print its stream. 
 *
 * @author pavl_g 
 */
public final class ScriptExecutor {
   
    public void execute(Task task) {
        final UnixScriptRunner runner = ((UnixScriptRunner) task);
        if (runner.getScript() == null || runner.getScript().equals("")) {
            throw new MissingResourceException("Cannot find a script to execute !");
        }
        try {
            executeScript(runner.getScript(), runner.getJavaHome());
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeScript(final String script, final String javaHome) throws IOException, InterruptedException {
        String command = "bash";

        if (System.getProperty("os.name").contains("Windows")) {
            command = "C:\\Program Files\\Git\\bin\\bash.exe";
        }
        
        Process run = Runtime.getRuntime().exec(new String[] { command, script, javaHome });
        
        if (run.waitFor() == 1) {
            System.out.println("Run Failed !");
        }

        ConsoleUtils.printConsoleInput(run);

        /* release resources */
        run.destroy();
        run = null;
        command = null;
    }
}
