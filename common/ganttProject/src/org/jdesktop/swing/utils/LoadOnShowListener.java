/*
 * $Id: LoadOnShowListener.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.utils;

import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import java.io.IOException;

// XXX msd: could be a circular dependencie
import org.jdesktop.swing.data.DefaultTableModelExt;

/**
 * Listener which may be registered on a user interface component
 * so that the specified data model object will start loading its
 * data when the component is first made visible on the screen.
 * @author Amy Fowler
 * @version 1.0
 */
public class LoadOnShowListener implements HierarchyListener {
    private DefaultTableModelExt data;
    public LoadOnShowListener(DefaultTableModelExt data) {
        this.data = data;
    }
    public void hierarchyChanged(HierarchyEvent event) {
        Component component = (Component)event.getComponent();
        if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) > 0 &&
            component.isShowing()) {
            try {
                data.startLoading();
            } catch (IOException e) {
                //REMIND(aim): invoke app exception handler
                System.out.println("could not load data: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
