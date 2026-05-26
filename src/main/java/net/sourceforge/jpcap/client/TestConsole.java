/*
 * Copyright (C) 2026, jpcap modernised fork
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 *
 * Minimal local replacement for net.ultrametrics.console.TestConsole.
 * Provides a small set of built-in commands (help, memory, gc, properties,
 * exit, halt) suitable for derived consoles such as
 * {@link CaptureConsole} to extend.
 */
package net.sourceforge.jpcap.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class TestConsole implements CommandLineable {

    @Override
    public String doCommand(String command, String[] args) {
        String c = command.toLowerCase(Locale.ROOT);
        return switch (c) {
            case "help", "?"        -> command_help(new HashSet<>());
            case "memory", "mem"    -> command_memory();
            case "gc"               -> command_gc();
            case "properties"       -> command_properties();
            case "halt"             -> command_halt(args);
            case "exit", "quit"     -> command_exit(args);
            default                 -> "unrecognised command: " + command
                + "  (try 'help')";
        };
    }

    @Override
    public String command_help(Collection<String> suppressed) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("commands:\n");
        if (!suppressed.contains("help")) {
            buffer.append("\thelp                    - show this help\n");
        }
        if (!suppressed.contains("memory")) {
            buffer.append("\tmemory                  - print JVM memory stats\n");
        }
        if (!suppressed.contains("gc")) {
            buffer.append("\tgc                      - request garbage collection\n");
        }
        if (!suppressed.contains("properties")) {
            buffer.append("\tproperties              - dump system properties\n");
        }
        if (!suppressed.contains("exit")) {
            buffer.append("\texit                    - leave the console\n");
        }
        if (!suppressed.contains("halt")) {
            buffer.append("\thalt [code]             - terminate the JVM\n");
        }
        return buffer.toString();
    }

    protected String command_memory() {
        Runtime rt = Runtime.getRuntime();
        long free = rt.freeMemory();
        long total = rt.totalMemory();
        long max = rt.maxMemory();
        return String.format(
            "memory: used=%dKB free=%dKB total=%dKB max=%dKB",
            (total - free) / 1024, free / 1024, total / 1024, max / 1024);
    }

    protected String command_gc() {
        System.gc();
        return "gc requested";
    }

    protected String command_properties() {
        Properties props = System.getProperties();
        Map<String, String> sorted = new TreeMap<>();
        for (String key : props.stringPropertyNames()) {
            sorted.put(key, props.getProperty(key));
        }
        StringBuilder buffer = new StringBuilder();
        sorted.forEach((k, v) -> buffer.append(k).append('=').append(v).append('\n'));
        return buffer.toString();
    }

    protected String command_exit(String[] args) {
        return null;
    }

    protected String command_halt(String[] args) {
        int code = 0;
        if (args.length > 0) {
            try {
                code = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
                return "halt: argument must be numeric";
            }
        }
        System.exit(code);
        return null;
    }
}
