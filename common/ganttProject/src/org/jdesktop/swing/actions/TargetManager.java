/*
 * $Id: TargetManager.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.actions;

import java.awt.Component;

import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.FocusManager;

import org.jdesktop.swing.Application;


/**
 * The target manager dispatches commands to {@link Targetable} objects
 * that it manages. This design of this class is based on the <i>Chain of
 * Responsiblity</i> and <i>Mediator</i> design patterns. The target manager
 * acts as a mediator between {@link TargetableAction}s and the intended targets.
 * This allows Action based components to invoke commands on components
 * without explicitly binding the user Action to the component action.
 * <p>
 * The target manager maintains a reference to a current
 * target and a target list.
 * The target list is managed using the <code>addTarget</code> and
 * <code>removeTarget</code> methods. The current target is managed using the
 * <code>setTarget</code> and <code>getTarget</code> methods.
 * <p>
 * Commands are dispatched to the Targetable objects in the <code>doCommand</code>
 * method in a well defined order. The doCommand method on the Targetable object
 * is called and if it returns true then the command has been handled and
 * command dispatching will stop. If the Targetable doCommand method returns
 * false then the
 * <p>
 * If none of the Targetable objects can handle the command then the default
 * behaviour is to retrieve an Action from the {@link javax.swing.ActionMap} of
 * the permanent focus owner with a key that matches the command key. If an
 * Action can be found thenthe <code>actionPerformed</code>
 * method is invoked using an <code>ActionEvent</code> that was constructed
 * using the command string.
 * <p>
 * If the Action is not found on the focus order then the ActionMaps of the ancestor
 * hierarchy of the focus owner is searched until a matching Action can be found.
 *  Finally, if none
 * of the components can handle the command then it is dispatche to the ActionMap
 * of the current Application instance.
 * <p>
 * The order of command dispatch is as follows:
 * <ul>
 *    <li>Current Targetable object invoking doCommand method
 *    <li>List order of Targetable objects invoking doCommand method
 *    <li>ActionMap entry of the permanent focus owner invoking actionPerfomed
 *    <li>ActionMap entry of the ancestor hierarchy of the permanent focus owner
 *    <li>ActionMap entry of the current Application instance
 * </ul>
 *
 * @see Targetable
 * @see TargetableAction
 * @author Mark Davidson
 */
public class TargetManager {

    private static TargetManager INSTANCE;
    private List targetList;
    private Targetable target;
    private PropertyChangeSupport propertySupport;

    /**
     * Create a target manager. Use this constructor if the application
     * may support many target managers. Otherwise, using the getInstance method
     * will return a singleton.
     */
    public TargetManager() {
        propertySupport = new PropertyChangeSupport(this);
    }

    /**
     * Return the singleton instance.
     */
    public static TargetManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TargetManager();
        }
        return INSTANCE;
    }

    /**
     * Add a target to the target list. Will be appended
     * to the list by default. If the prepend flag is true then
     * the target will be added at the head of the list.
     * <p>
     * Targets added to the head of the list will will be the first
     * to handle the command.
     *
     * @param target the targeted object to add
     * @param prepend if true add at the head of the list; false append
     */
    public void addTarget(Targetable target, boolean prepend) {
        if (targetList == null) {
            targetList = new ArrayList();
        }
        if (prepend) {
            targetList.add(0, target);
        } else {
            targetList.add(target);
        }
        // Should add focus listener to the component.
    }

    /**
     * Appends the target to the target list.
     * @param target the targeted object to add
     */
    public void addTarget(Targetable target) {
        addTarget(target, false);
    }

    /**
     * Remove the target from the list
     */
    public void removeTarget(Targetable target) {
        if (targetList != null) {
            targetList.remove(target);
        }
    }

    /**
     * Returns an array of managed targets that were added with the
     * <code>addTarget</code> methods.
     *
     * @return all the <code>Targetable</code> added or an empty array if no
     *         targets have been added
     */
    public Targetable[] getTargets() {
        Targetable[] targets;
        if (targetList == null) {
            targets = new Targetable[0];
        } else {
            targets = new Targetable[targetList.size()];
            targets = (Targetable[])targetList.toArray(new Targetable[targetList.size()]);
        }
        return targets;
    }

    /**
     * Gets the current targetable component. May or may not
     * in the target list. If the current target is null then
     * the the current targetable component will be the first one
     * in the target list which can execute the command.
     *
     * This is a bound property and will fire a property change event
     * if the value changes.
     *
     * @param newTarget the current targetable component to set or null if
     *       the TargetManager shouldn't have a current targetable component.
     */
    public void setTarget(Targetable newTarget) {
        Targetable oldTarget = target;
        if (oldTarget != newTarget) {
            target = newTarget;
            propertySupport.firePropertyChange("target", oldTarget, newTarget);
        }
    }

    /**
     * Return the current targetable component. The curent targetable component
     * is the first place where commands will be dispatched.
     *
     * @return the current targetable component or null
     */
    public Targetable getTarget() {
        return target;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    /**
     * Listens to changes in the target property, disables managed actions
     *
     class TargetListener implements PropertyChangeListener {
     public void propertyChange(PropertyChangeEvent evt) {
     ActionManager manager = ActionManager.getInstance();

     // Disable old commands.
     Targetable target = (Targetable)evt.getOldValue();
     if (target != null) {
     String[] commands = target.getCommands();
     for (int i = 0; i < commands.length; i++) {
     manager.setEnabled(commands[i], false);
     }
     }

     // Enable new commands
     target = (Targetable)evt.getNewValue();
     if (target != null) {
     String[] commands = target.getCommands();
     for (int i = 0; i < commands.length; i++) {
     manager.setEnabled(commands[i], true);
     }
     }
     }
     }*/

    /**
     * Executes the command on the current targetable component.
     * If there isn't current targetable component then the list
     * of targetable components are searched and the first component
     * which can execute the command. If none of the targetable
     * components handle the command then the ActionMaps of the
     * focused components are searched.
     *
     * @param command the key of the command
     * @param value the value of the command; depends on context
     * @return true if the command has been handled otherwise false
     */
    public boolean doCommand(Object command, Object value) {
        // Try to invoked the explicit target.
        if (target != null) {
            if (target.hasCommand(command) && target.doCommand(command, value)) {
                return true;
            }
        }

        // The target list has the next chance to handle the command.
        if (targetList != null) {
            Iterator iter = targetList.iterator();
            while (iter.hasNext()) {
                Targetable target = (Targetable)iter.next();
                if (target.hasCommand(command) &&
                    target.doCommand(command, value)) {
                    return true;
                }
            }
        }

        ActionEvent evt = null;
        if (value instanceof ActionEvent) {
            evt = (ActionEvent)value;
        }

        // Fall back behavior. Get the component which has focus and search the
        // ActionMaps in the containment hierarchy for matching action.
        Component comp = FocusManager.getCurrentManager().getPermanentFocusOwner();
        while (comp != null) {
            if (comp instanceof JComponent) {
                ActionMap map = ((JComponent)comp).getActionMap();
                Action action = map.get(command);
                if (action != null) {
                    if (evt == null) {
                        evt = new ActionEvent(comp, 0, command.toString());
                    }
                    action.actionPerformed(evt);

                    return true;
                }
            }
            comp = comp.getParent();
        }

        Application app = Application.getInstance();
        if (app != null) {
            ActionMap map = app.getActionMap();
            Action action = map.get(command);
            if (action != null) {
                if (evt == null) {
                    evt = new ActionEvent(comp, 0, command.toString());
                }
                action.actionPerformed(evt);
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the TargetManager.
     * This method is package private and for testing purposes only.
     */
    void reset() {
        if (targetList != null) {
            targetList.clear();
            targetList = null;
        }
        target = null;

        PropertyChangeListener[] listeners = propertySupport.getPropertyChangeListeners();
        for (int i = 0; i < listeners.length; i++) {
            propertySupport.removePropertyChangeListener(listeners[i]);
        }
        INSTANCE = null;
    }

}
