package org.scijava.headless.peer;

import java.awt.*;
import java.awt.peer.DialogPeer;
import java.util.List;

public class HeadlessDialogPeer extends HeadlessWindowPeer implements DialogPeer {
	public HeadlessDialogPeer(Dialog target) {
		super(target);
	}

	@Override
	public void setTitle(String title) {
	}

	@Override
	public void setResizable(boolean resizable) {
	}

	@Override
	public void blockWindows(List<Window> windows) {

	}

	@Override
	public void setModalBlocked(Dialog blocker, boolean blocked) {

	}
}
