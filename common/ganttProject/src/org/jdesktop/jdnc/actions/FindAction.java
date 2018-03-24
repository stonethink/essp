/*
 * $Id: FindAction.java,v 1.1 2006/10/10 03:50:57 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.jdnc.actions;

import java.net.URL;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.jdesktop.swing.actions.TargetableAction;

public class FindAction extends TargetableAction {

    private final static Icon defaultIcon;

    static {
	// Uses JLF graphics.
        URL url = FindAction.class.getResource("/toolbarButtonGraphics/general/Find24.gif");
        defaultIcon = url == null ? null : new ImageIcon(url);
    }

    public FindAction() {
        this("Find", "find", defaultIcon);
    }

    public FindAction(String name, String id, Icon icon) {
        super(name, id, icon);
        putValue(Action.SHORT_DESCRIPTION, name);
	putValue(Action.MNEMONIC_KEY, new Integer('F'));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
    }
}
