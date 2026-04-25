package ru.nsu.nmashkin.task241.service;

import java.util.Map;
import ru.nsu.nmashkin.task241.core.Config;
import ru.nsu.nmashkin.task241.core.Task;

/**
 * .
 */
public class ScoreCalculator {

    /**
     * .
     *
     * @param numericScore .
     * @param grade .
     * @param bonusApplied .
     */
    public record ScoreResult(double numericScore, String grade, int bonusApplied) {}

    /**
     * .
     *
     * @param task .
     * @param verification .
     * @param studentNick .
     * @param config .
     * @return .
     */
    public ScoreResult calculate(Task task, TaskVerificationResult verification,
                                 String studentNick, Config config) {

        if (!verification.buildOk() || !verification.docsOk() || !verification.styleOk()) {
            return new ScoreResult(0.0, "0", 0);
        }

        double baseScore = 0.0;
        TestService.TestResult testRes = verification.tests();
        if (testRes != null) {
            int total = testRes.passed() + testRes.failed() + testRes.skipped();
            if (total > 0) {
                baseScore = ((double) testRes.passed() / total) * task.maxScore();
            }
        }

        int bonus = config.getBonusPoints().getOrDefault(studentNick, 0);
        double finalScore = Math.max(0.0, baseScore + bonus);

        String grade = mapToGrade(finalScore, config.getGradeCriteria());

        return new ScoreResult(finalScore, grade, bonus);
    }

    private String mapToGrade(double score, Map<Integer, String> criteria) {
        String result = "2";
        int maxMetCriteria = 0;
        for (var entry : criteria.entrySet()) {
            if (entry.getKey() >= maxMetCriteria && score >= entry.getKey()) {
                maxMetCriteria = entry.getKey();
                result = entry.getValue();
            }
        }
        return result;
    }
}