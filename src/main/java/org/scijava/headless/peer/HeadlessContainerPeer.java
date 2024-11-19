package org.scijava.headless.peer;

import java.awt.*;
import java.awt.peer.ContainerPeer;

public class HeadlessContainerPeer extends HeadlessComponentPeer implements ContainerPeer {

	public HeadlessContainerPeer(Component target) {
		super(target);
	}

	@Override
	public Insets getInsets() {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public void beginValidate() { }

	@Override
	public void endValidate() { }

	@Override
	public void beginLayout() { }

	@Override
	public void endLayout() { }
}
