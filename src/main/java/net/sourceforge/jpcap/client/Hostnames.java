/*
 * Copyright (C) 2026, jpcap modernised fork
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Async reverse-DNS resolver shared by all {@link HostRenderer} instances.
 * <p>
 * Lookups are pooled and memoised: each unique IP is resolved at most once
 * per process lifetime. The result is fed back via a callback so callers can
 * trigger a repaint without blocking on DNS from the AWT thread.
 */
final class Hostnames {

    private static final ConcurrentMap<String, String> cache =
        new ConcurrentHashMap<>();

    private static final ExecutorService pool = Executors.newFixedThreadPool(
        4,
        r -> {
            Thread t = new Thread(r, "jpcap-dns-resolver");
            t.setDaemon(true);
            return t;
        });

    private Hostnames() {
    }

    /**
     * Resolve {@code ip} to a host name. If it has been resolved before the
     * cached value is returned immediately and {@code onResolved} is not
     * invoked. Otherwise the IP is returned synchronously, a background
     * lookup is scheduled, and {@code onResolved} fires (on a worker thread)
     * with the resolved name -- only if the lookup actually changes the
     * display name. Failed lookups are cached as the literal IP so we don't
     * retry endlessly.
     */
    static String resolve(String ip, Consumer<String> onResolved) {
        String cached = cache.get(ip);
        if (cached != null) {
            return cached;
        }
        // populate immediately with the IP so concurrent callers see a value
        // and don't double-schedule; the worker may overwrite with the name.
        cache.putIfAbsent(ip, ip);
        pool.submit(() -> {
            String resolved = lookup(ip);
            cache.put(ip, resolved);
            if (!resolved.equals(ip)) {
                onResolved.accept(resolved);
            }
        });
        return ip;
    }

    private static String lookup(String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            String name = addr.getCanonicalHostName();
            // getCanonicalHostName() returns the IP unchanged when reverse
            // resolution fails -- in that case stay with the literal IP.
            return name == null || name.equals(ip) ? ip : name;
        } catch (UnknownHostException e) {
            return ip;
        }
    }
}
