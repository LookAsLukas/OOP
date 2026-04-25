package ru.nsu.nmashkin.task241.service; // Замените на ваш пакет

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * .
 */
public class GitService {
    /**
     * .
     *
     * @param repoUrl .
     * @param targetDir .
     * @param branch .
     * @return .
     * @throws IOException .
     * @throws InterruptedException .
     */
    public Path cloneRepository(String repoUrl, Path targetDir, String branch) throws IOException, InterruptedException {
        if (!Files.exists(targetDir)) Files.createDirectories(targetDir);

        String[] branchesToTry = branch.equalsIgnoreCase("default")
                ? new String[]{"main", "master"}
                : new String[]{branch};

        for (String b : branchesToTry) {
            Path cloneDir = targetDir.resolve("repo");
            if (tryClone(repoUrl, b, cloneDir)) {
                return cloneDir;
            }

            cleanup(cloneDir);
        }
        throw new IOException("Failed to clone repo. Neither 'main' nor 'master' found or accessible.");
    }

    private boolean tryClone(String url, String branch, Path dir) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "git", "clone", "--depth", "1", "--single-branch",
                    "--branch", branch, url, dir.toString()
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while (br.readLine() != null);
            }
            return p.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * .
     *
     * @param repoPath .
     * @param start .
     * @param end .
     * @return .
     * @throws IOException .
     * @throws InterruptedException .
     */
    public boolean hasCommitBetween(Path repoPath, LocalDateTime start, LocalDateTime end) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "git", "-C", repoPath.toString(), "log",
                "--pretty=format:%H",
                "--since", start.toString(),
                "--until", end.toString()
        );
        Process p = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            boolean hasCommit = br.readLine() != null;
            p.waitFor(5, TimeUnit.SECONDS);
            return hasCommit;
        }
    }

    /**
     * .
     *
     * @param repoPath .
     * @throws IOException .
     */
    public void cleanup(Path repoPath) throws IOException {
        if (repoPath == null || !Files.exists(repoPath)) return;
        try (var stream = Files.walk(repoPath)) {
            stream.sorted(java.util.Comparator.reverseOrder())
                    .forEach(p -> {
                        try { Files.delete(p); } catch (IOException ignored) {}
                    });
        }
    }
}