package ru.nsu.nmashkin.task241.dsl

import org.codehaus.groovy.control.CompilerConfiguration
import ru.nsu.nmashkin.task241.core.Config
import ru.nsu.nmashkin.task241.core.Group

class ConfigBuilder {
    private final Config config = new Config()

    void tasks(Closure closure) {
        def block = new TasksBlock(config)
        closure.delegate = block
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void group(String name, Closure closure) {
        def group = new Group(name)
        def block = new GroupBlock(group)
        closure.delegate = block
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
        config.addGroup(group)
    }

    void checkpoints(Closure closure) {
        closure.delegate = new CheckpointsBlock(config)
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void points(Closure closure) {
        closure.delegate = new BonusBlock(config)
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void criteria(Closure closure) {
        closure.delegate = new CriteriaBlock(config)
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void check(Closure closure) {
        closure.delegate = new CheckBlock(config)
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void settings(Closure closure) {
        closure.delegate = new SettingsBlock(config)
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void importConfig(String path) {
        def file = new File(path)
        if (!file.exists()) {
            System.err.println("[ERROR] Config file not found: $path")
            return
        }

        def cc = new CompilerConfiguration()
        cc.setScriptBaseClass(DelegatingScript.class.getName())

        def shell = new GroovyShell(this.class.classLoader, new Binding(), cc)

        DelegatingScript script = (DelegatingScript) shell.parse(file)
        script.setDelegate(this)
        script.run()
    }

    Config build() {
        return config
    }
}