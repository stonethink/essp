/*
 * $Id: CollapseAction.java,v 1.1 2006/10/10 03:50:57 mingxingzhang Exp $
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
 * Contains the visible properties of a collape action which
 * would be invoked on a tree like structure.
 *
 * @author Ramesh Gupta
 */
public class CollapseAction extends TargetableAction {
    private final static Icon defaultIcon;

    static {
        URL url = CollapseAction.class.getResource("resources/collapseAll.gif");
        defaultIcon = url == null ? null : new ImageIcon(url);
    }

    public CollapseAction() {
        this("Collapse All", "collapse-all", defaultIcon);
    }

    public CollapseAction(String name, String id, Icon icon) {
        super(name, id, icon);
        putValue(Action.SHORT_DESCRIPTION, name);
    }
}
