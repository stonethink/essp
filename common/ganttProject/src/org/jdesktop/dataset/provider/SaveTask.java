/*
 * $Id: SaveTask.java,v 1.1 2006/10/10 03:50:55 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset.provider;

import javax.swing.Icon;
import org.jdesktop.dataset.DataSet;
import org.jdesktop.dataset.DataTable;

/**
 *
 * @author rbair
 */
public abstract class SaveTask extends AbstractTask {
    private DataTable[] tables;

    public SaveTask(DataTable[] tables) {
        this.tables = tables == null ? new DataTable[0] : tables;
    }

    public void run() {
        setIndeterminate(true);
        try {
            saveData(tables);
            setProgress(getMaximum());
        } catch (Exception e) {
            final Throwable error = e;
            e.printStackTrace();
            setProgress(getMaximum());
        }
    }

    protected abstract void saveData(DataTable[] tables) throws Exception;

    /**
     * @inheritDoc
     */
    public String getDescription() {
        return "<html><h3>Saving data</h3></html>";
    }

    /**
     * @inheritDoc
     */
    public Icon getIcon() {
        return null;
    }

    /**
     * @inheritDoc
     */
    public String getMessage() {
        return "Saving item " + (getProgress() + 1) + " of " + getMaximum();
    }

    /**
     * @inheritDoc
     */
    public boolean cancel() throws Exception {
        return false;
    }
}