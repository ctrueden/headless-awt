package org.scijava.headless;

import java.awt.Button;
import java.awt.Frame;

public class HeadlessAWTExample {
	public static void main(String[] args) {
		Frame frame = new Frame("Headless Window");
		frame.add(new Button("Hello"));
		frame.setSize(800, 600);
		frame.setVisible(true);

		System.out.println("Successfully 'displayed' AWT Window in headless environment");

		frame.dispose();
	}
}
