package com.jme3.build;

import org.gradle.api.tasks.TaskAction;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import com.jme3.build.actions.Permissioning;
import com.jme3.build.actions.ScriptExecutor;

public class UnixScriptRunner extends DefaultTask {

    @Input
    protected String script = "";

    @Input
    protected String javaHome = System.getProperty("java.home");

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

    public void setScript(final String script) {
        this.script = script;
    }

    public void setJavaHome(final String javaHome) {
        this.javaHome = javaHome;
    }

    public final String getScript() {
        return script;
    }

    public final String getJavaHome() {
        return javaHome;
    }
}
