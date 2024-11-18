package org.scijava.headless;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Custom GraphicsDevice for headless operation
 */
class HeadlessGraphicsDevice extends GraphicsDevice {
    private final HeadlessGraphicsConfiguration config;
    
    public HeadlessGraphicsDevice() {
        this.config = new HeadlessGraphicsConfiguration(this);
    }
    
    @Override
    public int getType() {
        return GraphicsDevice.TYPE_RASTER_SCREEN;
    }
    
    @Override
    public String getIDstring() {
        return "HeadlessDisplay";
    }
    
    @Override
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { config };
    }
    
    @Override
    public GraphicsConfiguration getDefaultConfiguration() {
        return config;
    }
}