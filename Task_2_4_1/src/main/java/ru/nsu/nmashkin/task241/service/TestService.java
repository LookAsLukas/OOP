package ru.nsu.nmashkin.task241.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * .
 */
public class TestService {

    /**
     * .
     *
     * @param passed  .
     * @param failed  .
     * @param skipped .
     * @param timeout .
     */
    public record TestResult(int passed, int failed, int skipped, boolean timeout) {
        @Override
        public String toString() {
            return String.format("%d/%d/%d", passed, failed, skipped);
        }
    }

    /**
     * .
     *
     * @param taskDir    .
     * @param timeoutSec .
     * @return .
     */
    public TestResult runTests(Path taskDir, long timeoutSec) {
        try {
            if (GradleExecutor.run(taskDir, "test", timeoutSec)) {
                System.out.println("[INFO] Test execution timed out");
                return new TestResult(0, 0, 0, true);
            }
            return parseHtmlReport(taskDir);
        } catch (Exception e) {
            System.err.println("[ERROR] Test execution failed");
            return new TestResult(0, 0, 0, false);
        }
    }

    private TestResult parseHtmlReport(Path taskDir) throws IOException {
        String html = Files.readString(taskDir.resolve("build/reports/tests/test/index.html"));

        int tests = extractBySelector(html, "id=\"tests\"");
        int failures = extractBySelector(html, "id=\"failures\"");
        int ignored = extractBySelector(html, "id=\"ignored\"");

        return new TestResult(Math.max(0, tests - failures - ignored), failures, ignored, false);
    }

    private int extractBySelector(String html, String infoBoxSelector) {
        int start = html.indexOf(infoBoxSelector);
        if (start == -1) {
            return 0;
        }

        int counterStart = html.indexOf("<div class=\"counter\">", start);
        if (counterStart == -1) {
            return 0;
        }

        int valueStart = counterStart + "<div class=\"counter\">".length();
        int valueEnd = html.indexOf("</div>", valueStart);
        if (valueEnd == -1) {
            return 0;
        }

        try {
            return Integer.parseInt(html.substring(valueStart, valueEnd).trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}