package ru.nsu.nmashkin.task241;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.nsu.nmashkin.task241.cli.ConfigLoader;
import ru.nsu.nmashkin.task241.core.Check;
import ru.nsu.nmashkin.task241.core.Config;
import ru.nsu.nmashkin.task241.core.Student;
import ru.nsu.nmashkin.task241.core.Task;
import ru.nsu.nmashkin.task241.service.BuildService;
import ru.nsu.nmashkin.task241.service.DocGenerator;
import ru.nsu.nmashkin.task241.service.GitService;
import ru.nsu.nmashkin.task241.service.HtmlReportGenerator;
import ru.nsu.nmashkin.task241.service.ScoreCalculator;
import ru.nsu.nmashkin.task241.service.StyleChecker;
import ru.nsu.nmashkin.task241.service.TaskVerificationResult;
import ru.nsu.nmashkin.task241.service.TestService;

/**
 * .
 */
public class Main {

    /**
     * .
     *
     * @param build .
     * @param docs .
     * @param style .
     * @param tests .
     * @param score .
     */
    public record TaskCheckResult(boolean build, boolean docs, boolean style,
                                  TestService.TestResult tests, ScoreCalculator.ScoreResult score) {
        static TaskCheckResult missing() {
            return new TaskCheckResult(false, false, false,
                    new TestService.TestResult(0, 0, 0, false),
                    new ScoreCalculator.ScoreResult(0.0, "0", 0));
        }
    }

    /**
     * .
     *
     * @param student .
     * @param taskId .
     * @param taskResult .
     * @param activityPercent .
     */
    public record StudentData(Student student, String taskId,
                              TaskCheckResult taskResult, double activityPercent) {}

    /**
     * .
     *
     * @param args .
     * @throws Exception .
     */
    public static void main(String[] args) throws Exception {
        String configPath = args.length > 0 ? args[0] : "config/test_check.groovy";
        System.err.println("[INFO] Loading config: " + new File(configPath).getAbsolutePath());
        Config config = ConfigLoader.load(new File(configPath));

        GitService git = new GitService();
        BuildService build = new BuildService();
        TestService test = new TestService();
        StyleChecker style = new StyleChecker();
        DocGenerator docs = new DocGenerator();
        ScoreCalculator scorer = new ScoreCalculator();

        Set<StudentData> results = new HashSet<>();
        Map<String, Path> gitCloned = new HashMap<>();

        for (Check check : config.getChecks()) {
            Student student = config.getStudent(check.studentNick());
            Task task = config.getTasks().get(check.taskId());

            System.err.println("[INFO] Processing: " + student.name() + " (" + task.name() + ")");
            Path tempDir;
            Path repoDir;

            try {
                tempDir = gitCloned.getOrDefault(student.nick(), null);
                if (tempDir == null) {
                    System.err.println("[INFO] Cloning repo \"" + student.url() + "\"");
                    tempDir = Files.createTempDirectory("oop-" + student.nick());
                    repoDir = git.cloneRepository(student.url(), tempDir, "default");
                    gitCloned.put(student.nick(), tempDir);
                } else {
                    repoDir = tempDir.resolve("repo");
                }

                Path taskDir = repoDir.resolve(task.id());

                if (!Files.exists(taskDir)) {
                    results.add(new StudentData(student, task.id(),
                            TaskCheckResult.missing(), 100));
                    System.err.println("[ERROR] Project not found: " + task.id());
                    continue;
                }

                System.err.println("[INFO] Building project: " + task.id());
                boolean buildOk = build.compileJava(taskDir);
                boolean docsOk = false;
                boolean styleOk = false;

                if (buildOk) {
                    System.err.println("[INFO] Generating doc: " + task.id());
                    docsOk = docs.generateDocs(taskDir);
                    System.err.println("[INFO] Checking style: " + task.id());
                    styleOk = style.checkStyle(taskDir);
                }

                TestService.TestResult testRes;
                if (buildOk && docsOk && styleOk) {
                    System.err.println("[INFO] Running tests: " + task.id());
                    testRes = test.runTests(taskDir, config.getTestTimeoutSeconds());
                } else {
                    testRes = new TestService.TestResult(0, 0, 0, false);
                }

                System.err.println("[INFO] Calculating score: " + task.id());
                ScoreCalculator.ScoreResult score = scorer.calculate(task,
                        new TaskVerificationResult(buildOk, docsOk, styleOk, testRes, null),
                        student.nick(), config);

                results.add(new StudentData(student, task.id(),
                                new TaskCheckResult(buildOk, docsOk, styleOk, testRes, score),
                                100));
                System.err.println("[INFO] Score: " + score.numericScore());

            } catch (Exception e) {
                System.err.println("[ERROR] Fatal error: " + e.getMessage());
            }

        }

        for (Path tempDir : gitCloned.values()) {
            git.cleanup(tempDir);
        }

        System.err.println("[INFO] Generating HTML report...");
        HtmlReportGenerator htmlReportGenerator = new HtmlReportGenerator();
        htmlReportGenerator.generate(config, results, System.out);
    }
}