package ru.nsu.nmashkin.task241.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * .
 */
public class Config {
    private final Map<String, Task> tasks = new HashMap<>();
    private final Map<String, Group> groups = new HashMap<>();
    private final List<Checkpoint> checkpoints = new ArrayList<>();
    private final List<Check> checks = new ArrayList<>();
    private final Map<String, Integer> bonusPoints = new HashMap<>();
    private final Map<Integer, String> gradeCriteria = new TreeMap<>(Collections.reverseOrder());

    private long testTimeoutSeconds = 30;
    private boolean skipAuthCheck = false;

    /**
     * .
     *
     * @param task .
     */
    public void addTask(Task task) {
        tasks.put(task.id(), task);
    }

    /**
     * .
     *
     * @param group .
     */
    public void addGroup(Group group) {
        groups.put(group.name(), group);
    }

    /**
     * .
     *
     * @param cp .
     */
    public void addCheckpoint(Checkpoint cp) {
        checkpoints.add(cp);
    }

    /**
     * .
     *
     * @param cmd .
     */
    public void addCheckCommand(Check cmd) {
        checks.add(cmd);
    }

    /**
     * .
     *
     * @param studentNick .
     * @param points .
     */
    public void addBonus(String studentNick, int points) {
        bonusPoints.put(studentNick, points);
    }

    /**
     * .
     *
     * @param minScore .
     * @param grade .
     */
    public void addGradeCriteria(int minScore, String grade) {
        gradeCriteria.put(minScore, grade);
    }

    /**
     * .
     *
     * @param timeoutSeconds .
     */
    public void setTestTimeoutSeconds(long timeoutSeconds) {
        testTimeoutSeconds = timeoutSeconds;
    }

    /**
     * .
     *
     * @return .
     */
    public Map<String, Task> getTasks() {
        return tasks;
    }

    /**
     * .
     *
     * @return .
     */
    public Map<String, Group> getGroups() {
        return groups;
    }

    /**
     * .
     *
     * @return .
     */
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    /**
     * .
     *
     * @return .
     */
    public List<Check> getChecks() {
        return checks;
    }

    /**
     * .
     *
     * @return .
     */
    public Map<String, Integer> getBonusPoints() {
        return bonusPoints;
    }

    /**
     * .
     *
     * @return .
     */
    public Map<Integer, String> getGradeCriteria() {
        return gradeCriteria;
    }

    /**
     * .
     *
     * @return .
     */
    public long getTestTimeoutSeconds() {
        return testTimeoutSeconds;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean skipAuthCheck() {
        return skipAuthCheck;
    }

    /**
     * .
     *
     * @param skipAuthCheck .
     */
    public void setSkipAuthCheck(boolean skipAuthCheck) {
        this.skipAuthCheck = skipAuthCheck;
    }

    /**
     * .
     *
     * @param nick .
     * @return .
     */
    public Student getStudent(String nick) {
        return groups.values().stream()
                .flatMap(g -> g.students().stream())
                .filter(s -> s.nick().equals(nick))
                .findAny().orElse(null);
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n=== Parsed Configuration ===\n");
        sb.append("Tasks: ").append(tasks.keySet()).append("\n");
        sb.append("Groups: ").append(groups.keySet()).append("\n");
        sb.append("Checkpoints: ").append(checkpoints).append("\n");
        sb.append("Checks: ").append(checks.size()).append(" command(s)\n");
        for (Check c : checks) {
            sb.append("  -> ").append(c.taskId()).append(" / ")
                    .append(c.studentNick()).append("\n");
        }
        sb.append("Bonuses: ").append(bonusPoints).append("\n");
        sb.append("Criteria: ").append(gradeCriteria).append("\n");
        return sb.toString();
    }
}