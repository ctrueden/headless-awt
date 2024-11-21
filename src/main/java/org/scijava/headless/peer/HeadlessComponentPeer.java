package org.scijava.headless.peer;

import sun.java2d.pipe.Region;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.PaintEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.awt.peer.ContainerPeer;

// First, create our dummy peer implementations
public class HeadlessComponentPeer implements ComponentPeer {
	protected Component target;
	protected int x, y, width, height;
	protected boolean visible;
	protected Color background, foreground;
	protected Font font;
	protected Cursor cursor;
	protected boolean enabled = true;

	public HeadlessComponentPeer(Component target) {
		this.target = target;
	}

	@Override
	public void dispose() {}

	@Override
	public boolean isObscured() {
		return false;
	}

	@Override
	public boolean canDetermineObscurity() {
		return false;
	}

	@Override
	public void setVisible(boolean v) { this.visible = v; }
	@Override
	public void setEnabled(boolean e) { this.enabled = e; }
	@Override
	public void paint(Graphics g) {}
	@Override
	public void print(Graphics g) {}

	@Override
	public void setBounds(int x, int y, int width, int height, int op) {
		// FIXME: respect op value
		this.x = x; this.y = y;
		this.width = width; this.height = height;
	}

	@Override
	public void handleEvent(AWTEvent e) {

	}

	@Override
	public void coalescePaintEvent(PaintEvent e) {

	}

	public boolean handleEvent(Event e) { return false; }
	public Point getLocationOnScreen() { return new Point(x, y); }
	public Dimension getPreferredSize() { return new Dimension(width, height); }
	public Dimension getMinimumSize() { return new Dimension(10, 10); }
	public ColorModel getColorModel() { return ColorModel.getRGBdefault(); }
	public Graphics getGraphics() { return new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB).createGraphics(); }
	public FontMetrics getFontMetrics(Font f) { return new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics(f); }
	public void setForeground(Color c) { this.foreground = c; }
	public void setBackground(Color c) { this.background = c; }
	public void setFont(Font f) { this.font = f; }

	@Override
	public void updateCursorImmediately() {

	}

	@Override
	public boolean requestFocus(
		Component lightweightChild,
		boolean temporary,
		boolean focusedWindowChangeAllowed,
		long time,
		FocusEvent.Cause cause)
	{
		return false;
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	public Image createImage(int width, int height) {
		return null;
	}

	@Override
	public VolatileImage createVolatileImage(int width, int height) {
		return null;
	}

	@Override
	public GraphicsConfiguration getGraphicsConfiguration() {
		return null;
	}

	@Override
	public boolean handlesWheelScrolling() {
		return false;
	}

	@Override
	public void createBuffers(int numBuffers, BufferCapabilities caps) throws AWTException {

	}

	@Override
	public Image getBackBuffer() {
		return null;
	}

	@Override
	public void flip(int x1, int y1, int x2, int y2, BufferCapabilities.FlipContents flipAction) {

	}

	@Override
	public void destroyBuffers() {

	}

	@Override
	public void reparent(ContainerPeer newContainer) {

	}

	@Override
	public boolean isReparentSupported() {
		return false;
	}

	@Override
	public void layout() {

	}

	@Override
	public void applyShape(Region region) {

	}

	@Override
	public void setZOrder(ComponentPeer above) {

	}

	@Override
	public boolean updateGraphicsData(GraphicsConfiguration gc) {
		return false;
	}
}
