package ru.nsu.nmashkin.task241.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * .
 */
public class GradleExecutor {

    /**
     * .
     *
     * @param projectDir .
     * @param task .
     * @param timeoutSec .
     * @return .
     * @throws IOException .
     * @throws InterruptedException .
     */
    public static boolean run(Path projectDir,
                              String task,
                              long timeoutSec) throws IOException, InterruptedException {
        List<String> command = buildGradleCommand(projectDir, task);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(projectDir.toFile());
        pb.redirectErrorStream(true);

        Process proc = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        boolean finished = timeoutSec > 0
            ? proc.waitFor(timeoutSec, TimeUnit.SECONDS)
            : proc.waitFor() == 0;
        if (!finished) {
            proc.destroyForcibly();
            System.err.println("[ERROR] Task \"" + task + "\" timed out after " + timeoutSec + "s");
            return true;
        }

        if (proc.exitValue() != 0) {
            System.err.println("[ERROR] Task \"" + task
                    + "'\" failed with code " + proc.exitValue() + "\n" + output);
        }
        return false;
    }

    private static List<String> buildGradleCommand(Path dir, String task) {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        Path wrapper = dir.resolve(isWindows ? "gradlew.bat" : "gradlew");

        List<String> cmd = new ArrayList<>();

        if (Files.exists(wrapper)) {
            if (isWindows) {
                cmd.addAll(List.of("cmd", "/c", "gradlew.bat"));
            } else {
                cmd.add("./gradlew");
            }
        } else {
            cmd.add("gradle");
        }

        cmd.addAll(List.of(task, "--rerun-tasks", "--no-daemon", "--console=plain", "-q"));
        return cmd;
    }
}