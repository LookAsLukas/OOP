package ru.nsu.nmashkin.task241.service;

import ru.nsu.nmashkin.task241.Main;
import ru.nsu.nmashkin.task241.core.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.Collectors;

public class HtmlReportGenerator {

    /**
     * Генерирует HTML-отчёт в требуемом формате:
     * одна таблица на группу, строки = (студент, задача) из checks.
     */
    public void generate(Config config, Set<Main.StudentData> results, Appendable output) throws IOException {
        output.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
                .append("<style>")
                .append("body{font-family:monospace;padding:20px;line-height:1.4}")
                .append("table{border-collapse:collapse;margin:15px 0;width:100%;max-width:1400px}")
                .append("th,td{border:1px solid #ccc;padding:6px;text-align:left}")
                .append("th{background:#f4f4f4;font-weight:bold;text-align:center}")
                .append(".pass{color:#2e7d32;font-weight:bold}.fail{color:#c62828;font-weight:bold}")
                .append("h2{border-bottom:2px solid #333;padding-bottom:5px;margin-top:30px}")
                .append("</style></head><body>\n");

        // Группируем результаты по группам
        Map<Group, List<Main.StudentData>> byGroup = results.stream()
                .collect(Collectors.groupingBy(
                        d -> config.getGroups().values().stream()
                                .filter(g -> g.students().contains(d.student()))
                                .findFirst()
                                .orElse(null),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        for (Map.Entry<Group, List<Main.StudentData>> entry : byGroup.entrySet()) {
            Group group = entry.getKey();
            if (group == null) continue;

            List<Main.StudentData> groupResults = entry.getValue();

            output.append("<h2>Group ").append(escapeHtml(group.name())).append("</h2>\n")
                    .append("<table><thead><tr>")
                    .append("<th>Student</th><th>Task</th><th>Build</th><th>Doc</th><th>Style</th><th>Tests</th><th>Total</th>")
                    .append("</tr></thead><tbody>\n");

            // Сортируем: сначала по студенту, потом по задаче (для читаемости)
            groupResults.stream()
                    .sorted(Comparator
                            .comparing((Main.StudentData d) -> d.student().name())
                            .thenComparing(d -> d.taskId()))
                    .forEach(data -> {
                        try {
                            Main.TaskCheckResult res = data.taskResult();
                            System.err.println(res);
                            String build = res.build() ? "ok" : "fail";
                            String docs = res.docs() ? "ok" : "fail";
                            String style = res.style() ? "ok" : "fail";
                            String tests = res.tests() != null ? res.tests().toString() : "-";
                            String total = String.format("%.1f / %d",
                                    res.score().numericScore(),
                                    config.getTasks().get(data.taskId()).maxScore());

                            output.append("<tr>")
                                    .append("<td>").append(escapeHtml(data.student().name())).append("</td>")
                                    .append("<td>").append(escapeHtml(data.taskId())).append("</td>")
                                    .append("<td class='").append(res.build() ? "pass" : "fail").append("'>").append(build).append("</td>")
                                    .append("<td class='").append(res.docs() ? "pass" : "fail").append("'>").append(docs).append("</td>")
                                    .append("<td class='").append(res.style() ? "pass" : "fail").append("'>").append(style).append("</td>")
                                    .append("<td>").append(tests).append("</td>")
                                    .append("<td><b>").append(total).append("</b></td>")
                                    .append("</tr>\n");
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });

            output.append("</tbody></table>\n");
        }

        output.append("</body></html>");
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}