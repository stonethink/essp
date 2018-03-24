/*
 * $Id: DataProvider.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.dataset;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jdesktop.dataset.provider.LoadTask;
import org.jdesktop.dataset.provider.SaveTask;
import org.jdesktop.dataset.provider.Task;

/**
 * Provides a basic implementation of DataProvider that handles all of the
 * threading issues normally associated with writing a DataProvider.
 *
 * @author rbair
 */
public abstract class DataProvider {
    /**
     * thread pool from which to get threads to execute the loader.
     */
    private static final Executor EX = Executors.newCachedThreadPool();

    public void load(DataTable[] tables) {
        Task task = createLoadTask(tables);
        runTask(task);
    }
    
	public void load(DataTable t) {
        load(new DataTable[]{t});
    }
    
	public void save(DataTable t) {
        save(new DataTable[]{t});
    }
    
    public void save(DataTable[] tables) {
        Task task = createSaveTask(tables);
        runTask(task);
    }
    
    /**
     * Creates a task that saves data from an array of DataTables to the
     * data store. All of these tables will be saved serially on the same
     * background thread.
     */
    protected abstract SaveTask createSaveTask(DataTable[] tables);
    
    /**
     * Creates a Task that loads data from the data store into one or more
     * DataTables. All of these tables will be loaded serially using the same
     * background thread.
     */
    protected abstract LoadTask createLoadTask(DataTable[] tables);
    
    /**
     * Invoked by the <code>load</code> or <code>save</code> methods.
     * This method will be called on the EventDispatch thread, and therefore
     * must not block. This method is provided to allow concrete subclasses to
     * provide a custom thread creation/scheduling implementation.
     * 
     * @param runner
     */
    protected void runTask(Task runner) {
//      Application.getInstance().getProgressManager().addProgressable(runner);
        EX.execute(runner);
    }
}