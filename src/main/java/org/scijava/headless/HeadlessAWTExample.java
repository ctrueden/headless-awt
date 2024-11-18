package org.scijava.headless;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Example usage demonstrating how to override the system property
 * and create AWT components in a headless environment
 */
public class HeadlessAWTExample {
    public static void main(String[] args) {
        // Set system property to use our custom GraphicsEnvironment
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            System.setProperty("java.awt.graphicsenv", 
                             HeadlessGraphicsEnvironment.class.getName());
            return null;
        });
        
        try {
            // Create and show a window
            Frame frame = new Frame("Headless Window");
            frame.setSize(800, 600);
            frame.setVisible(true); // This would normally throw HeadlessException
            
            System.out.println("Successfully created AWT Window in headless environment");
            
            // Clean up
            frame.dispose();
        } catch (Exception e) {
            System.err.println("Failed to create window: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
