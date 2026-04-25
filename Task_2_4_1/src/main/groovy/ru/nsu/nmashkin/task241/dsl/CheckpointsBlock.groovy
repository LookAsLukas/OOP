package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Checkpoint
import ru.nsu.nmashkin.task241.core.Config
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CheckpointsBlock {
    private final Config config
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    CheckpointsBlock(Config config) { this.config = config }

    def methodMissing(String name, args) {
        if (args.length == 1 && args[0] instanceof String) {
            config.addCheckpoint(new Checkpoint(name,
                    LocalDate.parse(args[0] as CharSequence, DF)))
        }
    }
}