package org.scijava.headless.peer;

import java.awt.*;
import java.awt.peer.WindowPeer;

public class HeadlessWindowPeer extends HeadlessContainerPeer implements WindowPeer {
	public HeadlessWindowPeer(Window target) {
		super(target);
	}

	@Override
	public void toFront() {
	}

	@Override
	public void toBack() {
	}

	@Override
	public void updateAlwaysOnTopState() {
	}

	@Override
	public void updateFocusableWindowState() {
	}

	@Override
	public void setModalBlocked(Dialog blocker, boolean blocked) {
	}

	@Override
	public void updateMinimumSize() {
	}

	@Override
	public void updateIconImages() {
	}

	@Override
	public void setOpacity(float opacity) {
	}

	@Override
	public void setOpaque(boolean isOpaque) {
	}

	@Override
	public void updateWindow() {
	}

	@Override
	public void repositionSecurityWarning() {
	}
}
