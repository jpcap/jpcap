/*
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann
 * Distributed under the Mozilla Public License
 *   https://www.mozilla.org/MPL/MPL-1.1.txt
 */
package net.sourceforge.jpcap.client;

import java.awt.Component;
import java.awt.Graphics;

/**
 * A rendering of a host on a network.
 * <p>
 * The renderer holds <em>relative</em> position (x, y in [0,1]); absolute
 * pixel coordinates are derived from the current canvas size each paint, so
 * the visualisation reflows when the window is resized.
 */
public class HostRenderer {

    public HostRenderer(Component canvas, String ipAddress) {
        this.canvas = canvas;
        this.ipAddress = ipAddress;
        this.hostName = Settings.ENABLE_NAMES
            ? Hostnames.resolve(ipAddress, this::setHostName)
            : ipAddress;

        x = Math.random();
        y = Math.random();
    }

    int getRenderedX() {
        return xpos;
    }

    int getRenderedY() {
        return ypos;
    }

    public void setPos(int xn, int yn) {
        xpos = xn;
        ypos = yn;
        int w = Math.max(1, canvas.getWidth());
        int h = Math.max(1, canvas.getHeight());
        x = (double) xpos / w;
        y = (double) ypos / h;
    }

    boolean isInside(int px, int py) {
        return px > xpos - Settings.ICON_WIDTH
            && px < xpos + Settings.ICON_WIDTH
            && py > ypos - Settings.ICON_HEIGHT
            && py < ypos + Settings.ICON_HEIGHT;
    }

    void paint(Graphics g) {
        xpos = (int) (x * canvas.getWidth());
        ypos = (int) (y * canvas.getHeight());

        g.setColor(Settings.COLOR_HOST_ICON);
        int rx = xpos - Settings.ICON_WIDTH / 2;
        int ry = ypos - Settings.ICON_HEIGHT / 2;
        if (selected) {
            g.fillRect(rx, ry, Settings.ICON_WIDTH, Settings.ICON_HEIGHT);
        } else {
            g.drawRect(rx, ry, Settings.ICON_WIDTH, Settings.ICON_HEIGHT);
        }
        g.setColor(Settings.COLOR_HOST_TEXT);
        g.drawString(hostName, xpos + Settings.ICON_WIDTH, ypos);
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "[HostRenderer: " + hostName + "]";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    protected void setHostName(String hostName) {
        if (hostName == null || hostName.equals(this.hostName)) {
            return;
        }
        this.hostName = hostName;
        canvas.repaint();
    }

    /** Relative x in [0,1]. */
    double x;

    /** Last painted absolute x (used for hit-testing). */
    int xpos;

    /** Relative y in [0,1]. */
    double y;

    /** Last painted absolute y. */
    int ypos;

    public String ipAddress;
    public String hostName;

    private final Component canvas;
    private boolean selected = false;
}
