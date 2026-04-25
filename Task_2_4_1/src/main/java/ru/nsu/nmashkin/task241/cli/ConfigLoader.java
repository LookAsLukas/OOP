package ru.nsu.nmashkin.task241.cli;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.nmashkin.task241.core.Config;
import ru.nsu.nmashkin.task241.dsl.ConfigBuilder;

/**
 * .
 */
public class ConfigLoader {
    /**
     * .
     *
     * @param configFile .
     * @return .
     * @throws IOException .
     */
    public static Config load(File configFile) throws IOException {
        if (!configFile.exists()) {
            throw new RuntimeException("Config not found: " + configFile.getAbsolutePath());
        }

        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell shell = new GroovyShell(
                ConfigBuilder.class.getClassLoader(),
                new groovy.lang.Binding(), cc);

        DelegatingScript script = (DelegatingScript) shell.parse(configFile);

        ConfigBuilder builder = new ConfigBuilder();
        script.setDelegate(builder);

        script.run();

        return builder.build();
    }
}