package org.scijava.headless;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;

/**
 * Custom GraphicsConfiguration for headless operation
 */
public class HeadlessGraphicsConfiguration extends GraphicsConfiguration {
    private final HeadlessGraphicsDevice device;

    public HeadlessGraphicsConfiguration(HeadlessGraphicsDevice device) {
        this.device = device;
    }

    @Override
    public GraphicsDevice getDevice() {
        return device;
    }

    @Override
    public ColorModel getColorModel() {
        return ColorModel.getRGBdefault();
    }

    @Override
    public ColorModel getColorModel(int transparency) {
        return ColorModel.getRGBdefault();
    }

    @Override
    public AffineTransform getDefaultTransform() {
        return new AffineTransform();
    }

    @Override
    public AffineTransform getNormalizingTransform() {
        return new AffineTransform();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, device.width, device.height);
    }
}
