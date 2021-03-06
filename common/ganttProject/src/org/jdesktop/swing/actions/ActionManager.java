/*
 * $Id: ActionManager.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.actions;

import java.beans.PropertyChangeListener;

import java.io.PrintStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

/**
 * The ActionManager manages sets of <code>javax.swing.Action</code>s for an
 * application. There are convenience methods for getting and setting the state
 * of the action.
 * All of these elements have a unique id tag which is used by the ActionManager
 * to reference the action. This id maps to the <code>Action.ACTION_COMMAND_KEY</code>
 * on the Action.
 * <p>
 * The ActionManager may be used to conveniently register callback methods
 * on BoundActions.
 * <p>
 * A typical use case of the ActionManager is:
 * <p>
 *  <pre>
 *   ActionManager manager = ActionManager.getInstance();
 *
 *   // load Actions
 *   manager.addAction(action);
 *
 *   // Change the state of the action:
 *   manager.setEnabled("new-action", newState);
 * </pre>
 *
 * The ActionManager also supports Actions that can have a selected state
 * associated with them. These Actions are typically represented by a
 * JCheckBox or similar widget. For such actions the registered method is
 * invoked with an additional parameter indicating the selected state of
 * the widget. For example, for the callback handler:
 *<p>
 * <pre>
 *  public class Handler {
 *      public void stateChanged(boolean newState);
 *   }
 * </pre>
 * The registration method would look similar:
 * <pre>
 *  manager.registerCallback("select-action", new Handler(), "stateChanged");
 * </pre>
 *<p>
 * The stateChanged method would be invoked as the selected state of
 * the widget changed. Additionally if you need to change the selected
 * state of the Action use the ActionManager method <code>setSelected</code>.
 * <p>
 * The <code>ActionContainerFactory</code> uses the managed Actions in the
 * ActionManager to create
 * user interface components. For example, to create a JMenu based on an
 * action-list id:
 * <pre>
 * JMenu file = manager.getFactory().createMenu(list);
 * </pre>
 *
 * @see ActionContainerFactory
 * @see TargetableAction
 * @see BoundAction
 * @author Mark Davidson
 */
public class ActionManager {

    // Internal data structures which manage the actions.

    // key: value of ID_ATTR, value instanceof AbstractAction
    private Map actionMap;

    // Container factory instance for this ActionManager
    private ActionContainerFactory factory;

    /**
     * Shared instance of the singleton ActionManager.
     */
    private static ActionManager INSTANCE;

    // To enable debugging:
    //   Pass -Ddebug=true to the vm on start up.
    // or
    //   set System.setProperty("debug", "true"); before constructing this Object

    private static boolean DEBUG = false;

    /**
     * Creates the action manager. Use this constuctor if the application should
     * support many ActionManagers. Otherwise, using the getInstance method will
     * return a singleton.
     */
    public ActionManager() {
    }

    /**
     * Return the Action Container Factory associated with this ActionManager
     * instance. Will always return a factory instance.
     */
    public ActionContainerFactory getFactory() {
        if (factory == null) {
            factory = new ActionContainerFactory(this);
        }
        return factory;
    }

    /**
     * This method should be used to associate a subclassed ActionContainerFactory
     * with this ActionManager.
     */
    public void setFactory(ActionContainerFactory factory) {
        this.factory = factory;
    }

    /**
     * Return the instance of the ActionManger. If this has not been explicity
     * set then it will be created.
     *
     * @return the ActionManager instance.
     * @see #setInstance
     */
    public static ActionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActionManager();
        }
        return INSTANCE;
    }

    /**
     * Sets the ActionManager instance.
     */
    public static void setInstance(ActionManager manager) {
        INSTANCE = manager;
    }

    /**
     * Returns the ids for all the managed actions.
     * <p>
     * An action id is a unique idenitfier which can
     * be used to retrieve the corrspondng Action from the ActionManager.
     * This identifier can also
     * be used to set the properties of the action through the action
     * manager like setting the state of the enabled or selected flags.
     *
     * @return a set which represents all the action ids
     */
    public Set getActionIDs() {
        if (actionMap == null) {
            return null;
        }
        return actionMap.keySet();
    }

    public Action addAction(Action action) {
        return addAction(action.getValue(Action.ACTION_COMMAND_KEY), action);
    }

    /**
     * Adds an action to the ActionManager
     * @param id value of the action id - which is value of the ACTION_COMMAND_KEY
     * @param action Action to be managed
     * @return the action that was added
     */
    public Action addAction(Object id, Action action)  {
        if (actionMap == null) {
            actionMap = new HashMap();
        }
        actionMap.put(id, action);

        return action;
    }

    /**
     * Retrieves the action corresponding to an action id.
     *
     * @param id value of the action id
     * @return an Action or null if id
     */
    public Action getAction(Object id)  {
        if (actionMap != null) {
            return (Action)actionMap.get(id);
        }
        return null;
    }

    /**
     * Convenience method for returning the TargetableAction
     *
     * @param id value of the action id
     * @return the TargetableAction referenced by the named id or null
     */
    public TargetableAction getTargetableAction(Object id) {
        Action a = getAction(id);
        if (a instanceof TargetableAction) {
            return (TargetableAction)a;
        }
        return null;
    }

    /**
     * Convenience method for returning the BoundAction
     *
     * @param id value of the action id
     * @return the TargetableAction referenced by the named id or null
     */
    public BoundAction getBoundAction(Object id) {
        Action a = getAction(id);
        if (a instanceof BoundAction) {
            return (BoundAction)a;
        }
        return null;
    }

    /**
     * Convenience method for returning the ServerAction
     *
     * @param id value of the action id
     * @return the TargetableAction referenced by the named id or null
     */
    public ServerAction getServerAction(Object id) {
        Action a = getAction(id);
        if (a instanceof ServerAction) {
            return (ServerAction)a;
        }
        return null;
    }

    /**
     * Convenience method for returning the CompositeAction
     *
     * @param id value of the action id
     * @return the TargetableAction referenced by the named id or null
     */
    public CompositeAction getCompositeAction(Object id) {
        Action a = getAction(id);
        if (a instanceof CompositeAction) {
            return (CompositeAction)a;
        }
        return null;
    }

    /**
     * Convenience method for returning the StateChangeAction
     *
     * @param id value of the action id
     * @return the StateChangeAction referenced by the named id or null
     */
    private AbstractActionExt getStateChangeAction(Object id) {
        Action a = getAction(id);
        if (a != null && a instanceof AbstractActionExt) {
            AbstractActionExt aa = (AbstractActionExt)a;
            if (aa.isStateAction()) {
                return aa;
            }
        }
        return null;
    }

    /**
     * Enables or disables the state of the Action corresponding to the
     * action id. This method should be used
     * by application developers to ensure that all components created from an
     * action remain in synch with respect to their enabled state.
     *
     * @param id value of the action id
     * @param enabled true if the action is to be enabled; otherwise false
     */
    public void setEnabled(Object id, boolean enabled) {
        Action action = getAction(id);
        if (action != null) {
            action.setEnabled(enabled);
        }
    }


    /**
     * Returns the enabled state of the <code>Action</code>. When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     *
     * @param id value of the action id
     * @return true if this <code>Action</code> is enabled; false if the
     *         action doesn't exist or disabled.
     */
    public boolean isEnabled(Object id) {
        Action action = getAction(id);
        if (action != null) {
            return action.isEnabled();
        }
        return false;
    }

    /**
     * Sets the selected state of a toggle action. If the id doesn't
     * correspond to a toggle action then it will fail silently.
     *
     * @param id the value of the action id
     * @param selected true if the action is to be selected; otherwise false.
     */
    public void setSelected(Object id, boolean selected) {
        AbstractActionExt action = getStateChangeAction(id);
        if (action != null) {
            action.setSelected(selected);
        }
    }

    /**
     * Gets the selected state of a toggle action. If the id doesn't
     * correspond to a toggle action then it will fail silently.
     *
     * @param id the value of the action id
     * @return true if the action is selected; false if the action
     *         doesn't exist or is disabled.
     */
    public boolean isSelected(Object id) {
        AbstractActionExt action = getStateChangeAction(id);
        if (action != null) {
            return action.isSelected();
        }
        return false;
    }

    /**
     * A diagnostic which prints the Attributes of an action
     * on the printStream
     */
    static void printAction(PrintStream stream, Action action) {
        stream.println("Attributes for " + action.getValue(Action.ACTION_COMMAND_KEY));

        if (action instanceof AbstractAction) {
            Object[] keys = ((AbstractAction)action).getKeys();

            for (int i = 0; i < keys.length; i++) {
                stream.println("\tkey: " + keys[i] + "\tvalue: " +
                               action.getValue((String)keys[i]));
            }
        }
    }

    /**
     * Convenience method to register a callback method on a <code>BoundAction</code>
     *
     * @see BoundAction#registerCallback
     * @param id value of the action id - which is the value of the ACTION_COMMAND_KEY
     * @param handler the object which will be perform the action
     * @param method the name of the method on the handler which will be called.
     */
    public void registerCallback(Object id, Object handler, String method) {
        BoundAction action = getBoundAction(id);
        if (action != null) {
            action.registerCallback(handler, method);
        }
    }

    /**
     * A really ugly little hack which registers the text component on all the
     * ServerActions.
     * TODO: Should find a way of supporting generic components. Perhaps by
     *       defining a new interface like DataProvider which can
     *       be implemented by all ServerActions.
     *
     public void registerDataProvider(ServerActionListener listener) {
     if (actionMap != null) {
     Iterator iter = actionMap.values().iterator();
     while (iter.hasNext()) {
     AbstractAction action = (AbstractAction)iter.next();
     if (action instanceof ServerAction) {
     ((ServerAction)action).addServerActionListener(listener);
     }
     }
     }
     }*/

    //
    // Convenience methods for determining the type of action.
    //

    /**
     * Determines if the Action corresponding to the action id is a state changed
     * action (toggle, group type action).
     *
     * @param id value of the action id
     * @return true if the action id represents a multi state action; false otherwise
     */
    public boolean isStateAction(Object id) {
        Action action = getAction(id);
        if (action != null && action instanceof AbstractActionExt) {
            return ((AbstractActionExt)action).isStateAction();
        }
        return false;
    }

    /**
     * Test to determine if the action is a <code>TargetableAction</code>
     */
    public boolean isTargetableAction(Object id) {
        return (getTargetableAction(id) != null);
    }

    /**
     * Test to determine if the action is a <code>BoundAction</code>
     */
    public boolean isBoundAction(Object id) {
        return (getBoundAction(id) != null);
    }

    /**
     * Test to determine if the action is a <code>BoundAction</code>
     */
    public boolean isCompositeAction(Object id) {
        return (getCompositeAction(id) != null);
    }

    /**
     * Test to determine if the action is a <code>ServerAction</code>
     */
    public boolean isServerAction(Object id) {
        return (getServerAction(id) != null);
    }
}
