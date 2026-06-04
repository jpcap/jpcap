/*
 * Copyright (C) 2026, jpcap modernised fork
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 *
 * Minimal local replacement for com.fooware.util.CommandLine. Reads lines
 * from standard input, splits them on whitespace and dispatches to a
 * CommandLineable handler. Loop exits when handler returns {@code null}
 * or stdin reaches EOF.
 */
package net.sourceforge.jpcap.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CommandLine {

    private final CommandLineable handler;
    private String prompt = "> ";

    public CommandLine(CommandLineable handler) {
        this.handler = handler;
    }

    public void setPromptString(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Spawn a daemon REPL thread and return immediately. The original
     * {@code com.fooware.util.CommandLine.start()} also returned at once so
     * its caller could finish initialising the GUI before the prompt began.
     */
    public void start() {
        Thread t = new Thread(this::runLoop, "PromptThread");
        t.setDaemon(true);
        t.start();
    }

    private void runLoop() {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8));
        try {
            while (true) {
                System.out.print(prompt);
                System.out.flush();
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+");
                String command = parts[0];
                String[] args = parts.length > 1
                    ? Arrays.copyOfRange(parts, 1, parts.length)
                    : new String[0];
                String result = handler.doCommand(command, args);
                if (result == null) {
                    System.exit(0);
                }
                if (!result.isEmpty()) {
                    System.out.println(result);
                }
            }
        } catch (IOException e) {
            System.err.println("CommandLine: " + e.getMessage());
        }
    }
}
