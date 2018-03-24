/*
 * $Id: ExpandAction.java,v 1.1 2006/10/10 03:50:57 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.jdnc.actions;

import java.net.URL;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.jdesktop.swing.actions.TargetableAction;

/**
 * Contains the visible properties of an expand action which
 * would be invoked on a tree like structure.
 */
public class ExpandAction extends TargetableAction {
    private final static Icon defaultIcon;

    static {
        URL url = ExpandAction.class.getResource("resources/expandAll.gif");
        defaultIcon = url == null ? null : new ImageIcon(url);
    }

    public ExpandAction() {
        this("Expand All", "expand-all", defaultIcon);
    }

    public ExpandAction(String name, String id, Icon icon) {
        super(name, id, icon);
        putValue(Action.SHORT_DESCRIPTION, name);
    }
}
