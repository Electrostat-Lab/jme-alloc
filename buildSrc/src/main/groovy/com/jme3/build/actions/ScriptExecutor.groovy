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
        if (runner.getScript() == null) {
            throw new MissingResourceException("Cannot find a script to permissionize !");
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
