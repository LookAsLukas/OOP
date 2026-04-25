package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Check
import ru.nsu.nmashkin.task241.core.Config

class CheckBlock {
    private final Config config

    CheckBlock(Config config) { this.config = config }

    def methodMissing(String name, args) {
        if (args.length == 1) {
            config.addCheckCommand(new Check(args[0], name))
        }
    }
}
