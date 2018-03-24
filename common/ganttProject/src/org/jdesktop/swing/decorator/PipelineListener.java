/*
 * $Id: PipelineListener.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.decorator;

import java.util.EventListener;

/**
 * PipelineListener
 *
 * @author Ramesh Gupta
 */
public interface PipelineListener extends EventListener {
    /**
     * Sent when the pipeline has changed in any way.
     *
     * @param e  a <code>PipelineEvent</code> encapsulating the
     *    event information
     */
    void contentsChanged(PipelineEvent e);
}

