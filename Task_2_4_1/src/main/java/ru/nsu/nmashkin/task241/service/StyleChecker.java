package ru.nsu.nmashkin.task241.service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

/**
 * .
 */
public class StyleChecker {

    private static final String CHECKSTYLE_JAR_URL =
            "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.17.0/checkstyle-10.17.0-all.jar";
    private static final String JAR_NAME = "checkstyle-10.17.0-all.jar";
    private final Path cacheDir;

    /**
     * .
     */
    public StyleChecker() {
        this.cacheDir = Path.of(System.getProperty("user.home"), ".oop-checker", "tools");
    }

    /**
     * .
     *
     * @param taskDir .
     * @return .
     */
    public boolean checkStyle(Path taskDir) {
        try {
            Path jarPath = downloadTool();
            Path configPath = extractConfigFromResources();
            Path src = taskDir.resolve("src");

            if (!Files.exists(src)) {
                System.err.println("[ERROR] \"src\" not found");
                return false;
            }

            return runCheckstyle(jarPath, configPath, src);
        } catch (Exception e) {
            System.err.println("[ERROR] Style check failed: " + e.getMessage());
            return false;
        }
    }

    private Path downloadTool() throws IOException {
        Files.createDirectories(cacheDir);
        Path jarPath = cacheDir.resolve(JAR_NAME);

        if (!Files.exists(jarPath)) {
            System.err.println("[INFO] Downloading checkstyle...");
            try (InputStream in = new URL(CHECKSTYLE_JAR_URL).openStream()) {
                Files.copy(in, jarPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        return jarPath;
    }

    private Path extractConfigFromResources() throws IOException, URISyntaxException {
        URL url = getClass().getClassLoader().getResource("google_style.xml");
        if (url == null) {
            throw new IOException("No google_style.xml found in resources");
        }

        String urlPath = url.toString();
        if (urlPath.contains("!")) {
            Path tempConfig = Files.createTempFile("checkstyle-config-", ".xml");
            try (InputStream in = url.openStream()) {
                Files.copy(in, tempConfig, StandardCopyOption.REPLACE_EXISTING);
            }
            tempConfig.toFile().deleteOnExit();
            return tempConfig;
        }
        return Path.of(url.toURI());
    }

    private boolean runCheckstyle(Path jarPath, Path configPath, Path srcDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", jarPath.toString(),
                "-c", configPath.toAbsolutePath().toString(),
                srcDir.toAbsolutePath().toString()
        );
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) output.append(line).append("\n");
        }

        boolean finished = proc.waitFor(60, TimeUnit.SECONDS);
        if (!finished) {
            proc.destroyForcibly();
            System.err.println("[ERROR] Checkstyle timed out after 60s");
            return false;
        }

        if (proc.exitValue() == 0) {
            System.err.println("[INFO] Checkstyle: PASSED");
            return true;
        }

        System.err.println("[WARNING] Style violations found:");
        String[] lines = output.toString().split("\n");
        int count = 0;
        for (String line : lines) {
            if (line.contains("[ERROR]") || line.contains("[WARN]")) {
                String clean = line.replaceFirst("^.+\\b(src[/\\\\]main[/\\\\]java)", "$1");
                System.err.println("  " + clean);
                count++;
            }
        }
        System.err.println("  Total violations: " + count);
        return false;
    }
}