package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Config
import ru.nsu.nmashkin.task241.core.Task

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TasksBlock {
    private final Config config
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    TasksBlock(Config config) { this.config = config }

    void task(String id, String name, Integer maxPoints,
              String softDeadline, String hardDeadline) {
        config.addTask(new Task(id, name, maxPoints,
            LocalDate.parse(softDeadline, DF), LocalDate.parse(hardDeadline, DF)))
    }

    def methodMissing(String name, args) {
        if (args.length == 4) {
            config.addTask(new Task(name, args[0] as String, args[1] as int,
                    LocalDate.parse(args[2] as String, DF), LocalDate.parse(args[3] as String, DF)))
        }
    }
}