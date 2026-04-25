package ru.nsu.nmashkin.task241.service;

import java.nio.file.Path;

/**
 * .
 */
public class DocGenerator {
    /**
     * .
     *
     * @param taskDir .
     * @return .
     */
    public boolean generateDocs(Path taskDir) {
        try {
            GradleExecutor.run(taskDir, "javadoc", 60);
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Javadoc generation failed");
            return false;
        }
    }
}
