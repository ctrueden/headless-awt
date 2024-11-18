package org.scijava.headless;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.awt.GraphicsDevice;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class HeadlessAgent {
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Adding transformer...");
		inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
			if (className.startsWith("sun/java2d/")) System.out.println("Checking " + className + "...");
			if (className.equals("sun/java2d/HeadlessGraphicsEnvironment")) {
				System.out.println("Hacking sun.java2d.HeadlessGraphicsEnvironment...");
				/*
				public GraphicsDevice[] getScreenDevices() throws HeadlessException
				public GraphicsDevice getDefaultScreenDevice() throws HeadlessException
				public Point getCenterPoint() throws HeadlessException
				public Rectangle getMaximumWindowBounds() throws HeadlessException
				*/
				try {
					ClassPool cp = ClassPool.getDefault();
					CtClass cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
					cc.getDeclaredMethod("getScreenDevices").setBody("{ return new java.awt.GraphicsDevice[] { getDefaultScreenDevice() }; }");
					cc.getDeclaredMethod("getDefaultScreenDevice").setBody("{ return new org.scijava.headless.HeadlessGraphicsDevice(); }");
					cc.getDeclaredMethod("getCenterPoint").setBody("{ return new java.awt.Point(1920 / 2, 1080 / 2); }");
					cc.getDeclaredMethod("getMaximumWindowBounds").setBody("{ return new java.awt.Rectangle(0, 0, 1920, 1080); }");

					byte[] bytecode = cc.toBytecode();
					cc.detach(); // Important to prevent memory leaks
					return bytecode;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (className.equals("java/awt/GraphicsEnvironment")) {
				try {
					ClassPool cp = ClassPool.getDefault();
					CtClass cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

					// Find and modify the checkHeadless method
					cc.getDeclaredMethod("checkHeadless").setBody("{ return; }"); // No-op instead of throwing

					// Or alternatively patch isHeadless() to always return false
					// m = cc.getDeclaredMethod("isHeadless");
					// m.setBody("{ return false; }");

					byte[] bytecode = cc.toBytecode();
					cc.detach(); // Important to prevent memory leaks
					return bytecode;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		});
	}
}
