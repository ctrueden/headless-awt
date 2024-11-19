package org.scijava.headless;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class HeadlessAgent {
	public static void premain(String agentArgs, Instrumentation inst) {
		// Add this component to the bootstrap classpath.
		try {
			String jarPath = HeadlessAgent.class.getProtectionDomain()
				.getCodeSource().getLocation().toURI().getPath();
			inst.appendToBootstrapClassLoaderSearch(new JarFile(jarPath));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Modify headless-related classes to have a fake screen instead of throwing HeadlessException.
		final int w = 1920, h = 1080;
		inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
			CtClass cc = null;
			try {
				switch (className) {
					case "sun/java2d/HeadlessGraphicsEnvironment": {
						ClassPool cp = ClassPool.getDefault();
						cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

						cc.getDeclaredMethod("getScreenDevices").setBody(
							"{ return new java.awt.GraphicsDevice[] { getDefaultScreenDevice() }; }");
						cc.getDeclaredMethod("getDefaultScreenDevice").setBody(
							"{ return new org.scijava.headless.HeadlessGraphicsDevice(" + w + ", " + h + "); }");
						cc.getDeclaredMethod("getCenterPoint").setBody(
							"{ return new java.awt.Point(" + w + " / 2, " + h + " / 2); }");
						cc.getDeclaredMethod("getMaximumWindowBounds").setBody(
							"{ return new java.awt.Rectangle(0, 0, " + w + ", " + h + "); }");
						break;
					}
					case "sun/awt/HeadlessToolkit": {
						ClassPool cp = ClassPool.getDefault();
						cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

						// Override needed methods from java.awt.Toolkit.
						CtMethod m = CtMethod.make(
							"protected java.awt.peer.LightweightPeer createComponent(java.awt.Component target) {" +
								"    return new org.scijava.headless.peer.HeadlessComponentPeer(target);" +
								"}", cc);
						cc.addMethod(m);

						// Modify already-overridden methods.
						cc.getDeclaredMethod("createWindow").setBody(
							"{ return new org.scijava.headless.peer.HeadlessWindowPeer($1); }");
						cc.getDeclaredMethod("createFrame").setBody(
							"{ return new org.scijava.headless.peer.HeadlessFramePeer($1); }");
						cc.getDeclaredMethod("createDialog").setBody(
							"{ return new org.scijava.headless.peer.HeadlessDialogPeer($1); }");

						// public ButtonPeer createButton(Button target)
						// public TextFieldPeer createTextField(TextField target)
						// public ChoicePeer createChoice(Choice target)
						// public LabelPeer createLabel(Label target)
						// public ListPeer createList(List target)
						// public CheckboxPeer createCheckbox(Checkbox target)
						// public ScrollbarPeer createScrollbar(Scrollbar target)
						// public ScrollPanePeer createScrollPane(ScrollPane target)
						// public TextAreaPeer createTextArea(TextArea target)
						// public FileDialogPeer createFileDialog(FileDialog target)
						// public MenuBarPeer createMenuBar(MenuBar target)
						// public MenuPeer createMenu(Menu target)
						// public PopupMenuPeer createPopupMenu(PopupMenu target)
						// public MenuItemPeer createMenuItem(MenuItem target)
						// public CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target)
						// public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge)
						// public RobotPeer createRobot(Robot target, GraphicsDevice screen)
						// public KeyboardFocusManagerPeer getKeyboardFocusManagerPeer()

						// public TrayIconPeer createTrayIcon(TrayIcon target)
						// public SystemTrayPeer createSystemTray(SystemTray target)
						// public GlobalCursorManager getGlobalCursorManager()
						// protected void loadSystemColors(int[] systemColors)
						// public ColorModel getColorModel()
						// public int getScreenResolution()
						// public Map mapInputMethodHighlight(InputMethodHighlight highlight)
						// public int getMenuShortcutKeyMask()
						// public boolean getLockingKeyState(int keyCode)
						// public void setLockingKeyState(int keyCode, boolean on)
						// public Cursor createCustomCursor(Image cursor, Point hotSpot, String name)
						// public Dimension getBestCursorSize(int preferredWidth, int preferredHeight)
						// public int getMaximumCursorColors()

						cc.getDeclaredMethod("getScreenHeight").setBody("{ return " + h + "; }");
						cc.getDeclaredMethod("getScreenWidth").setBody("{ return " + w + "; }");
						cc.getDeclaredMethod("getScreenSize").setBody("{ return new java.awt.Dimension(" + w + ", " + h + "); }");
						cc.getDeclaredMethod("getScreenInsets").setBody("{ return new java.awt.Insets(0, 0, 0, 0); }");

						// public void setDynamicLayout(boolean dynamic)
						// protected boolean isDynamicLayoutSet()
						// public boolean isDynamicLayoutActive()
						// public Clipboard getSystemClipboard()
						// public PrintJob getPrintJob(Frame frame, String jobtitle, JobAttributes jobAttributes, PageAttributes pageAttributes)
						// public PrintJob getPrintJob(Frame frame, String doctitle, Properties props)
						// public DesktopPeer createDesktopPeer(Desktop target)
						// public boolean areExtraMouseButtonsEnabled()
						break;
					}
					case "java/awt/GraphicsEnvironment": {
						ClassPool cp = ClassPool.getDefault();
						cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
						cc.getDeclaredMethod("checkHeadless").setBody("{ return; }"); // No-op instead of throwing
						break;
					}
					case "java/awt/Window": {
						ClassPool cp = ClassPool.getDefault();
						cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
						cc.getDeclaredMethod("closeSplashScreen").setBody("{ return; }"); // Avoid native code.
						break;
					}
				}

				if (cc == null) return null;
				return cc.toBytecode();
			}
			catch (Exception e) {
				System.err.println("FAILED to patch " + className + ":");
				e.printStackTrace();
			}
			finally {
				if (cc != null) cc.detach();
			}
			return null;
		});
	}
}
