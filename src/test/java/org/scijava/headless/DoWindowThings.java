package org.scijava.headless;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DoWindowThings {
  public static void main(String... args) throws InterruptedException {
    System.out.println("Creating window...");
    JFrame f = new JFrame("Spiffy");
    JPanel p = new JPanel();
    f.setContentPane(p);
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    p.add(new JButton("Push me"));
    p.add(new JCheckBox("Check me"));
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.pack();
    f.setVisible(true);
    System.out.println("Window is up; waiting 5 seconds...");
    Thread.sleep(5000);
    f.dispose();
    System.out.println("Window is disposed.");
  }
}
