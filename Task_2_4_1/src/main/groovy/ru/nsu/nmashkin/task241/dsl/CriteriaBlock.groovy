package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Config

class CriteriaBlock {
    private final Config config

    CriteriaBlock(Config config) { this.config = config }

    def methodMissing(String name, args) {
        config.addGradeCriteria(args[0] as int, name);
    }
}