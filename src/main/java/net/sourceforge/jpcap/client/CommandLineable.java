/*
 * Copyright (C) 2026, jpcap modernised fork
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 *
 * Minimal local replacement for com.fooware.util.CommandLineable.
 */
package net.sourceforge.jpcap.client;

import java.util.Collection;

/**
 * Anything that can respond to a parsed command line.
 */
public interface CommandLineable {

    /**
     * Execute a parsed command.
     *
     * @param command name of the command (the first token on the line)
     * @param args    arguments following the command
     * @return human-readable result, or {@code null} to indicate the REPL
     *         should exit.
     */
    String doCommand(String command, String[] args);

    /**
     * Produce a help string. Implementations should call
     * {@code super.command_help(suppressed)} where appropriate so help text
     * from parent consoles is included.
     */
    String command_help(Collection<String> suppressed);
}
