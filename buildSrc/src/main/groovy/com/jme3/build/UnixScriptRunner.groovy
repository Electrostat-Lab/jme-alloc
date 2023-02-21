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

import org.gradle.api.tasks.TaskAction;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import com.jme3.build.actions.Permissioning;
import com.jme3.build.actions.ScriptExecutor;

/**
 * The abstract entity for running unix scripts in bash shell environments.
 * It consists of 2 sequential tasks: a [permissioning] task and a [script-executor] task.
 * 
 * @author pavl_g
 */
public class UnixScriptRunner extends DefaultTask {

    /**
     * The unix script to execute.
     */
    @Input
    protected String script = "";

    @Input
    protected String[] scriptArgs;

    @TaskAction
    protected void scriptExecution() {
        final ScriptExecutor executor = new ScriptExecutor();
        executor.execute(this);
    }

    @TaskAction
    protected void permissioning() {
        final Permissioning permissioningTask = new Permissioning();
        permissioningTask.execute(this);
    }

    /**
     * Sets the unix-script to run.
     *
     * @param script a string representing the absolute path to the script ending with the script name and file extension
     */
    public void setScript(final String script) {
        this.script = script;
    }

    public final String getScript() {
        return script;
    }

    public void setScriptArgs(final String[] scriptArgs) {
        this.scriptArgs = scriptArgs;
    }

    public String[] getScriptArgs() {
        return scriptArgs;
    }
}
