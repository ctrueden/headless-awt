package org.scijava.headless;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class HeadlessAgent implements ClassFileTransformer {
	@Override
	public byte[] transform(ClassLoader loader,
		String className,
		Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain,
		byte[] classfileBuffer)
	{
		if (className.equals("java/awt/GraphicsEnvironment")) {
			try {
				ClassPool cp = ClassPool.getDefault();
				CtClass cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

				// Find and modify the checkHeadless method
				CtMethod m = cc.getDeclaredMethod("checkHeadless");
				m.setBody("{ return; }"); // No-op instead of throwing

				// Or alternatively patch isHeadless() to always return false
				m = cc.getDeclaredMethod("isHeadless");
				m.setBody("{ return false; }");

				return cc.toBytecode();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		// ... similar patches for Window, Component etc if needed ...
		return null;
	}
}
