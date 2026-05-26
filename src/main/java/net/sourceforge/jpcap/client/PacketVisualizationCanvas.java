/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * A graphical view of captured packets.
 * <p>
 * Renderers ({@link HostRenderer}, {@link CommRenderer}) hold logical state
 * (relative positions, counters); the canvas draws them inside
 * {@link #paintComponent(Graphics)} so resizing and overdraw work through
 * Swing's normal paint pipeline instead of the old immediate-mode
 * {@code canvas.getGraphics()} approach.
 */
public class PacketVisualizationCanvas extends JPanel {

    private final HashMap hostMap;
    private final HashMap commMap;

    public PacketVisualizationCanvas(HashMap hostMap, HashMap commMap) {
        super();
        this.hostMap = hostMap;
        this.commMap = commMap;
        setBackground(Settings.COLOR_BG);
        setOpaque(true);
    }

    public HashMap getHostMap() {
        return hostMap;
    }

    public HashMap getCommMap() {
        return commMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Comm edges first so host icons render on top.
            try {
                for (Object o : commMap.values()) {
                    ((CommRenderer) o).paint(g2);
                }
            } catch (ConcurrentModificationException ignored) {
                // packets arriving on the capture thread can mutate the map
                // mid-paint; the next repaint will redraw whatever was missed.
            }

            try {
                for (Object o : hostMap.values()) {
                    ((HostRenderer) o).paint(g2);
                }
            } catch (ConcurrentModificationException ignored) {
                // see comment above
            }
        } finally {
            g2.dispose();
        }
    }
}
