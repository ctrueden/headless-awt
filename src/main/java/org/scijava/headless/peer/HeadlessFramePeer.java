package org.scijava.headless.peer;

import java.awt.*;
import java.awt.peer.FramePeer;

public class HeadlessFramePeer extends HeadlessWindowPeer implements FramePeer {
	public HeadlessFramePeer(Frame target) {
		super(target);
	}

	@Override
	public void setTitle(String title) {
	}

	@Override
	public void setMenuBar(MenuBar mb) {
	}

	@Override
	public void setResizable(boolean resizable) {
	}

	@Override
	public void setState(int state) {
	}

	@Override
	public int getState() {
		return Frame.NORMAL;
	}

	@Override
	public void setMaximizedBounds(Rectangle bounds) {
	}

	@Override
	public void setBoundsPrivate(int x, int y, int w, int h) {
	}

	@Override
	public Rectangle getBoundsPrivate() {
		return new Rectangle(x, y, width, height);
	}

	@Override
	public void emulateActivation(boolean activate) {

	}
}
