/*
 * $Id: WindowUtils.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.utils;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.RootPaneContainer;
import org.jdesktop.swing.utils.Spatial;

/**
 * Encapsulates various utilities for windows (ie: <code>Frame</code> and
 * <code>Dialog</code> objects and descendants, in particular).
 * @author Richard Bair
 */
public final class WindowUtils {
	
	/**
	 * Hide the constructor - don't wan't anybody creating an instance of this
	 */
	private WindowUtils() {
	}
	
	/**
	 * <p>
	 * Returns the <code>Point</code> at which a window should be placed to
	 * center that window on the screen.
	 * </p>
	 * <p>
	 * Some thought was taken as to whether to implement a method such as this,
	 * or to simply make a method that, given a window, will center it.  It was
	 * decided that it is better to not alter an object within a method.
	 * </p>
	 * @param window The window to calculate the center point for.  This object
	 * can not be null.
	 * @return the <code>Point</code> at which the window should be placed to
	 * center that window on the screen.
	 */
	public static Point getPointForCentering(Window window) {
		//assert window != null;
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for (GraphicsDevice device : devices) {
			Rectangle bounds = device.getDefaultConfiguration().getBounds();
			//check to see if the mouse cursor is within these bounds
			if (mousePoint.x >= bounds.x && mousePoint.y >= bounds.y
					&& mousePoint.x <= (bounds.x + bounds.width)
					&& mousePoint.y <= (bounds.y + bounds.height)) {
				//this is it
				int screenWidth = bounds.width;
				int screenHeight = bounds.height;
				int width = window.getWidth();
				int height = window.getHeight();
				Point p = new Point(((screenWidth - width) / 2) + bounds.x, ((screenHeight - height) / 2) + bounds.y);
				return p;
			}
		}
		return new Point(0,0);
	}
	
	/**
	 * <p>
	 * Returns the <code>Point</code> at which a window should be placed to
	 * center that window on the given desktop.
	 * </p>
	 * <p>
	 * Some thought was taken as to whether to implement a method such as this,
	 * or to simply make a method that, given a window, will center it.  It was
	 * decided that it is better to not alter an object within a method.
	 * </p>
	 * @param window The window (JInternalFrame) to calculate the center point
	 * for.  This object can not be null.
	 * @param desktop The JDesktopPane that houses this window.
	 * @return the <code>Point</code> at which the window should be placed to
	 * center that window on the given desktop
	 */
	public static Point getPointForCentering(JInternalFrame window, JDesktopPane desktop) {
		//assert window != null;
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for (GraphicsDevice device : devices) {
			Rectangle bounds = device.getDefaultConfiguration().getBounds();
			//check to see if the mouse cursor is within these bounds
			if (mousePoint.x >= bounds.x && mousePoint.y >= bounds.y
					&& mousePoint.x <= (bounds.x + bounds.width)
					&& mousePoint.y <= (bounds.y + bounds.height)) {
				//this is it
				int screenWidth = bounds.width;
				int screenHeight = bounds.height;
				int width = window.getWidth();
				int height = window.getHeight();
				Point p = new Point(((screenWidth - width) / 2) + bounds.x, ((screenHeight - height) / 2) + bounds.y);
				return p;
			}
		}
		return new Point(0,0);
	}

	/**
	 * Utility method used to load a GridBagConstraints object (param gbc) with the
	 * data in the other parameters.  This method saves code space over doing the
	 * assignments by hand, and also allows you to reuse the same GridBagConstraints
	 * object reducing temporary object creating (at the expense of a method call.
	 * Go figure).
	 */
	public static void setConstraints(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
	 double weightx, double weighty, int anchor, int fill, int top, int left, int bottom, int right) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.insets = new Insets(top, left, bottom, right);
	}
	
	/**
	 * Get a <code>Spatial</code> object representing the given window's position and
	 * magnitude in space.
	 * @param win The window to get a Spatial object for
	 * @return a Spatial object.  @see com.jgui.Spatial
	 */
	public static Spatial getSpatial(Window win) {
		Spatial spatial = new Spatial(win.getY(), win.getX(), win.getWidth(), win.getHeight());
		return spatial;
	}

	/**
	 * Get a <code>Spatial</code> object representing the given JComponent's position and
	 * magnitude in space.
	 * @param comp The JComponent to get a Spatial object for
	 * @return a Spatial object.  @see com.jgui.Spatial
	 */
	public static Spatial getSpatial(JComponent comp) {
		Spatial spatial = new Spatial(comp.getY(), comp.getX(), comp.getWidth(), comp.getHeight());
		return spatial;
	}
	
	/**
	 * Locates the RootPaneContainer for the given component
	 * @param c
	 * @return
	 */
	public static RootPaneContainer findRootPaneContainer(Component c) {
		if (c == null) {
			return null;
		} else if (c instanceof RootPaneContainer) {
			return (RootPaneContainer)c;
		} else {
			return findRootPaneContainer(c.getParent());
		}
	}

	/**
	 * Locates the JFrame for the given component
	 * @param c
	 * @return
	 */
	public static JFrame findJFrame(Component c) {
		if (c == null) {
			return null;
		} else if (c instanceof RootPaneContainer) {
			return (JFrame)c;
		} else {
			return findJFrame(c.getParent());
		}
	}

	/**
	 * Locates the JDialog for the given component
	 * @param c
	 * @return
	 */
	public static JDialog findJDialog(Component c) {
		if (c == null) {
			return null;
		} else if (c instanceof JDialog) {
			return (JDialog)c;
		} else {
			return findJDialog(c.getParent());
		}
	}
}
