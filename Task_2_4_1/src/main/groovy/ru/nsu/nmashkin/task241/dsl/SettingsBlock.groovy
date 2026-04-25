package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Config

class SettingsBlock {
    private final Config config

    SettingsBlock(Config config) { this.config = config; }

    void methodMissing(String name, String... args) {
        if (name.equals("testTimeout")) {
            if (args[1].endsWith('s')) {
                config.setTestTimeoutSeconds(args[1].dropRight(1).toLong())
            }
            if (args[1].endsWith('m')) {
                config.setTestTimeoutSeconds(args[1].dropRight(1).toLong() * 60)
            }
        }
        if (name.equals("skipAuthCheck")) {
            config.setSkipAuthCheck(args[1].toBoolean());
        }
    }
}
