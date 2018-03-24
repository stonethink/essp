/*
 * $Id: Application.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing;

import java.applet.Applet;
import java.applet.AppletContext;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Window;

import java.beans.Beans;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.JApplet;
import javax.swing.JRootPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.jdesktop.swing.event.SelectionListener;

import org.jdesktop.swing.actions.ActionManager;

import org.jdesktop.swing.utils.SwingWorker;
/**
 * <p>
 * Class which represents central state and properties for a single client
 * application which can be either a standalone Java application (typically
 * initiated using Java WebStart) or a set of one or more Java applets which
 * share the same code base.  There should only be a single Application instance
 * per client application.</p>
 * <p>
 * This class also encapsulates any functionality which has variable API
 * between applets and Java WebStart applications so that UI components can
 * reliably talk to a single interface for such services.</p>
 *
 * @author Amy Fowler
 * @version 1.0
 */
public class Application {

    // Index values for toplevels array
    private static final int WINDOWS = 0;
    private static final int APPLETS = 1;

    private static Map appMap = new Hashtable();
    private static Map imageCache = new HashMap();

    private ActionMap actionMap;

    // The ActionManager instance
    // TODO: investigate if this introduces a circular dependency.
    private ActionManager manager;

    private String title = "JDNC Application";
    private String versionString = "";

    private Image splashImage = null;
    private Image titleBarImage = null;

    private URL baseURL;
    private Vector toplevel[] = new Vector[2];

    private List selectionListeners;

    /**
     * Private constructor so that an Application can't be directly instantated.
    private Application() {
    }

    /**
     * Factory method for obtaining the Application instance associated with this
     * application.  The Application object will be instantiated if it does not
     * already exist. This method is intended for use by standalone applications
     * where there may be one and only one JDNCapp instance.
     * @return Application instance for application
     */
    public static Application getInstance() {
        return getInstance("theone");
    }

    /**
     * Factory method for obtaining the Application instance associated with the
     * application designated by the specified key.  The Application object will
     * be instantiated if it does not already exist. This method is intended
     * for use by applets, where there may be multiple Application instances in
     * a running VM.
     * @param key object designating the application
     * @return Application instance for application
     */
    public static Application getInstance(Object key) {
        Application app = null;
        synchronized(appMap) {
            app = (Application) appMap.get(key);
            if (app == null) {
		try {
		    ClassLoader cl = Thread.currentThread().getContextClassLoader();
		    app = (Application)Beans.instantiate(cl,
							 "org.jdesktop.swing.Application");
		    appMap.put(key, app);
		} catch (Exception ex) {
		    // XXX eception
		    ex.printStackTrace();
		}
		//REMIND(aim): memory leak - when to remove the app entry for applets
            }
        }
        return app;
    }

    /**
     * Convenience method for getting the JDNCapp instance given
     * a component instance. The component instance must be contained in
     * a containent hierarchy which has either a Window or an Applet instance
     * as the root.
     *
     * @param c the ui component
     * @return Application instance for the specified component's application
     */
    public static Application getApp(Component c) {
        Application app = null;
        Container parent = c instanceof Container? (Container)c : c.getParent();
        while (parent != null) {
            if (parent instanceof Window) {
                app = findApp(WINDOWS, parent);
                break;
            }
            else if (parent instanceof Applet) {
                app = findApp(APPLETS, parent);
                break;
            }
            else {
                parent = parent.getParent();
            }
        }
	// There is no app associated with this component.
	// Create one and register it with the root container
	if (app == null) {
	    Component p = SwingUtilities.getRoot(c);
	    if (p != null) {
		app = Application.getInstance(p);
		if (p instanceof Applet) {
		    app.registerApplet((Applet)p);
		} else {
		    app.registerWindow((Window)p);
		}
	    }
	}
        return app;
    }

    private static Application findApp(int type, Component c) {
        Application app = null;
        Iterator apps = appMap.values().iterator();
        while (app == null && apps.hasNext()) {
            Application a = (Application) apps.next();
            if (a.toplevel[type] != null) {
                if (a.toplevel[type].contains(c)) {
                    app = a;
                    break;
                }
            }
        }
        return app;
    }

    /**
     * Return the action manager for this application.
     *
     * @return the action manager instance
     */
    public ActionManager getActionManager() {
	if (manager == null) {
	    manager = new ActionManager();
	}
	return manager;
    }

    /**
     * Sets the &quot;baseURL&quot; property of this application.
     * @param baseURL URL of codebase for this application
     */
    public void setBaseURL(URL baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * @return URL of codebase for this application
     */
    public URL getBaseURL() {
        return baseURL;
    }

    /**
     * Will retrieve the applet base url if this application is running in an
     * applet. Otherwise, it will try to retrieve the base URL of the
     * xml configuration file.
     */
    public static URL getBaseURL(Object obj) {
	URL url = null;
	if (obj instanceof Component) {
	    Container parent = SwingUtilities.getAncestorOfClass(JApplet.class,
								 (Component)obj);
	    if (parent != null) {
		JApplet applet = (JApplet)parent;
		url = applet.getDocumentBase();
	    } else {
		WebStartContext context = WebStartContext.getInstance();
		// FIXME: this should be spawned in a SwingWorker.
		url = context.getDocumentBase();
	    }
	    if (url == null) {
		Application app = Application.getApp((Component)obj);
		if (app != null) {
		    url = app.getBaseURL();
		}
	    }
	}
	if (url == null) {
	    url = Application.getInstance().getBaseURL();
	}
	return url;
    }

    /**
     * Fetches a url of a resource value using the clasloader and
     * relative path of obj.
     * Will first try to load from the classpath, then the direct url and then
     * look for a base url.
     */
    public static URL getURL(String value, Object obj) {
	URL url = getURLResource(value, obj);
	if (url == null) {
	    try {
		url = new URL(value);
	    } catch (MalformedURLException ex) {
		// fall through
	    }
	}
	if (url == null) {
	    URL base = Application.getBaseURL(obj);
	    if (base != null) {
		try {
		    url = new URL(base, value);
		} catch (MalformedURLException e) {
		    // fall through
		}
	    }
	}
	if (url == null) {
	    // System.err.println("getURL: no url for value: " + value + " obj: " + obj);
	}
	return url;
    }

    public static URL getURLResource(String value, Object obj) {
	return obj.getClass().getResource(value);
    }


    public static Image getImage(String name, Object obj) {
	Icon icon = getIcon(name, obj);
	if (icon != null) {
	    return ((ImageIcon)icon).getImage();
	} else {
	    return null;
	}
    }

    public static Icon getIcon(String name, Object obj) {
	Icon icon = null;

	if (name == null || (icon = (Icon)imageCache.get(name)) != null) {
	    return icon;
	}

	URL fileLoc = getURL(name, obj);
	if (fileLoc != null && (icon = new ImageIcon(fileLoc)) != null) {
	    imageCache.put(name, icon);
	}
	return icon;
    }

    /**
     * Display the document referenced by the url in a browser. 
     * <p>
     * If the application
     * is an Applet then the document will be shown from the browser from which it
     * has been launched. If the application has been launched from Java WebStart
     * then it will use the javax.jnlp.BasicService to show the document. If this
     * is a standalone application then it will use the platform browser to show
     * the document.
     * 
     * @param url an absolute URL giving locationto display
     * @param target indicates where to display the page
     * @see java.applet.AppletContext#showDocument(java.net.URL, java.lang.String)
     * @see javax.jnlp.BasicService#showDocument
     */
    public void showDocument(final URL url, final String target) {
	if (isRunningApplet()) {
	    // show the document in the first applet.
	    Iterator iter = getApplets();
	    if (iter != null) {
		// Get the first applet
		Applet applet = (Applet)iter.next();
		final AppletContext context = applet.getAppletContext();
		// if target is _self, _parent, _top, _blank, show the document in
		// a browser.
		SwingWorker worker = new SwingWorker() {
			public Object construct() {
			    context.showDocument(url, target);
			    return null;
			}
		    };
		worker.start();
	    }
	} else if (isRunningWebStart()) {
	    final WebStartContext context = WebStartContext.getInstance();
	    SwingWorker worker = new SwingWorker() {
		    public Object construct() {
			context.showDocument(url, target);
			return null;
		    }
		};
	    worker.start();
	} else {
	    // The application is running stand alone so use the platform browser
	    String[] command = null;
	    String os = System.getProperty("os.name").toLowerCase();
	    if (os.indexOf("os x") >= 0) {
		// Mac OSX
		command = new String[] { "open", url.toExternalForm() };
	    }
	    else if (os.indexOf("wind") >= 0) {
		// Windows variants
		command = new String[] { "rundll32 url.dll,FileProtocolHandler",
					 url.toExternalForm() };
	    }
	    else {
		// mozilla by default
		command = new String[] { "mozilla", "-remote",
					 "openurl(" + url.toExternalForm() + ")"};
	    }
	    executeCommand(command);
	}
    }

    /**
     * Executes the command in a new thread.
     */
    void executeCommand(final String[] command) {
	SwingWorker worker = new SwingWorker() {
		public Object construct() {
		    try {
			Runtime.getRuntime().exec(command);
		    } catch (Exception ex) {
		    }
		    return null;
		}
	    };
	worker.start();
    }

    /**
     * Sets the &quot;splashImage&quot; property of this application.
     * If set, this image will be rendered in the splash screen which
     * is displayed momentarily at application startup while the
     * application initializes.
     * @param splashImage image displayed in the application's splash screen
     */
    public void setSplashImage(Image splashImage) {
        this.splashImage = splashImage;
    }

    /**
     * @return Image displayed in the application's splash screen
     */
    public Image getSplashImage() {
        return splashImage;
    }

    /**
     * Sets the &quot;title&quot; property of this application.
     * @param title string containing the title of this application
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String containing the title of this application
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the &quot;titleBarImage&quot; property of this application.
     * If set, this image will be displayed in the titlebar of all UI
     * windows shown by this application.  The placement of this image
     * within the titlebar is Look and Feel dependent.
     * @param titleBarImage image displayed in titlebar of application's toplevel windows
     */
    public void setTitleBarImage(Image titleBarImage) {
        this.titleBarImage = titleBarImage;
    }

    /**
     * @return image displayed in titlebar of application's toplevel windows
     */
    public Image getTitleBarImage() {
        return titleBarImage;
    }

    /**
     * Sets the &quot;versionString&quot; property of this application.
     * @param versionString string containing the version of this application
     */
    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    /**
     * @return String containing the version of this application
     */
    public String getVersionString() {
        return versionString;
    }

    /**
     * Returns <code>true</code> if running as a standalone application and
     * returns <code>false</code> if running one or more applets.
     * @return true if this client is running as an application; 
     *         false if running as an applet
     */
    public boolean isStandAlone() {
        return getApplets() == null;
    }

    /**
     * Returns a boolean value indicating whether or not this application is
     * running in the security sandbox
     * 
     * @return true if the application is in a sandbox; otherwise false
     */
    public boolean isRunningInSandbox() {
        boolean inSandbox = false;
        try {
            new File(".");
        } catch (SecurityException e) {
            inSandbox = true;
        }
        return inSandbox;
    }

    /**
     * Returns a boolean value indicating if the application is in an applet.
     *
     * @return true if the app is in an applet; otherwise falsee
     */
    public boolean isRunningApplet() {
	return getApplets() != null;
    }

    /**
     * Returns a boolean value indicating if the application has been launched with
     * Java WebStart.
     *
     * @return true if running in web start; otherwise false
     */
    public boolean isRunningWebStart() {
	try {
	    // Use reflection so that we dont get classloader errors.
	    Class service = Class.forName("javax.jnlp.ServiceManager");
	    Method lookup = service.getMethod("lookup", new Class[] { String.class });
	    
	    Object basic = lookup.invoke(null, 
			 new Object[] { "javax.jnlp.BasicService" });
	    
	    // if we made it here then web start is running.
	    return true;
	} catch (Exception ex) {
	    // any exception means no
	}
	return false;
    }

    /**
     * Registers a window with the application instance.
     */
    public void registerWindow(Window window) {
        register(WINDOWS, window);
    }

    public void unregisterWindow(Window window) {
        unregister(WINDOWS, window);
    }

    public void registerApplet(Applet applet) {
        register(APPLETS, applet);
    }

    public void unregisterApplet(Applet applet) {
        unregister(APPLETS, applet);
    }

    /**
     * Return the action map associated with this application.
     * The action map holds all the global application actions.
     */
    public ActionMap getActionMap() { 
	if (actionMap == null) {
	    actionMap = new ActionMap();
	}
	return actionMap;
    }

    /**
     * @return iterator containing all applets registered with this app instance
     *         or null if the app was instantiated from a standalone application
     */
    public Iterator getApplets() {
        return getToplevels(APPLETS);
    }

    /**
     * @return iterator containing all windows registered with this app instance
     *         or null if there were no toplevel windows registered
     */
    public Iterator getWindows() {
        return getToplevels(WINDOWS);
    }

    public void addSelectionListener(SelectionListener l) {
        if (selectionListeners == null) {
            selectionListeners = new ArrayList();
        }
        selectionListeners.add(l);
    }

    public void removeSelectionListener(SelectionListener l) {
        if (selectionListeners != null) {
            selectionListeners.remove(l);
        }
    }

    public SelectionListener[] getSelectionListeners() {
        if (selectionListeners != null) {
            return (SelectionListener[])selectionListeners.toArray(
                             new SelectionListener[1]);
        }
        return new SelectionListener[0];
    }

    private void register(int type, Component c) {
        if (toplevel[type] == null) {
            toplevel[type] = new Vector();
        }
        toplevel[type].add(c);
    }

    private void unregister(int type, Component c) {
        if (toplevel[type] != null) {
            toplevel[type].remove(c);
        }
    }

    private Iterator getToplevels(int type) {
        if (toplevel[type] == null) {
            return null;
        }
        return toplevel[type].iterator();
    }
}
