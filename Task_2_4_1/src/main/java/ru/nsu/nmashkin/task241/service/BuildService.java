package ru.nsu.nmashkin.task241.service;

import java.nio.file.Path;

/**
 * .
 */
public class BuildService {
    /**
     * .
     *
     * @param taskDir .
     * @return .
     */
    public boolean compileJava(Path taskDir) {
        try {
            GradleExecutor.run(taskDir, "compileJava", 0);
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Build failed: " + e.getMessage());
            return false;
        }
    }
}