/*
 * $Id: ImagePanelBinding.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.jdesktop.swing.data.DataModel;

import org.jdesktop.swing.JXImagePanel;

public class ImagePanelBinding extends AbstractBinding {

    private JXImagePanel imagePanel;

    public ImagePanelBinding(JXImagePanel imagePanel,
			     DataModel model, String fieldName) {
        super(imagePanel, model, fieldName, AbstractBinding.AUTO_VALIDATE_NONE);
    }

    public JComponent getComponent() {
	return imagePanel;
    }

    public void setComponent(JComponent component) {
	this.imagePanel = (JXImagePanel)component;
    }

    protected Object getComponentValue() {
	Class klazz = metaData.getElementClass();
	if (klazz == Image.class) {
	    return imagePanel.getImage();
	}
	else if (klazz == Icon.class) {
	    return imagePanel.getIcon();
	}
	// default?
	return null;
    }

    protected void setComponentValue(Object value) {
        Class klazz = metaData.getElementClass();
        if (klazz == Image.class) {
            imagePanel.setImage((Image)value);
        } else if (klazz == Icon.class) {
            imagePanel.setIcon((Icon)value);
        } else if (klazz == String.class) {
            try {
                imagePanel.setIcon(new ImageIcon(new URL((String)value)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
