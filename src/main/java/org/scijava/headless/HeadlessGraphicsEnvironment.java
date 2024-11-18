package org.scijava.headless;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * Custom GraphicsEnvironment that allows headless operation
 */
public class HeadlessGraphicsEnvironment extends GraphicsEnvironment {
    private static final BufferedImage DUMMY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D DUMMY_GRAPHICS = DUMMY_IMAGE.createGraphics();
    
    @Override
    public GraphicsDevice[] getScreenDevices() throws HeadlessException {
        return new GraphicsDevice[] { new HeadlessGraphicsDevice() };
    }
    
    @Override
    public GraphicsDevice getDefaultScreenDevice() throws HeadlessException {
        return new HeadlessGraphicsDevice();
    }
    
    @Override
    public Graphics2D createGraphics(BufferedImage img) {
        return DUMMY_GRAPHICS;
    }
    
    @Override
    public Font[] getAllFonts() {
        return new Font[0];
    }
    
    @Override
    public String[] getAvailableFontFamilyNames() {
        return new String[0];
    }
    
    @Override
    public String[] getAvailableFontFamilyNames(Locale l) { return new String[0]; }
}
