package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Config

class BonusBlock {
    private final Config config

    BonusBlock(Config config) { this.config = config }

    def methodMissing(String name, args) {
        if (args.length == 1 && args[0] instanceof Number) {
            config.addBonus(name, args[0] as int)
        }
    }
}