/*
 * $Id: DataLoader.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import org.jdesktop.swing.data.ConversionException;

import org.jdesktop.swing.event.MessageSourceSupport;
import org.jdesktop.swing.event.MessageEvent;
import org.jdesktop.swing.event.MessageListener;
import org.jdesktop.swing.event.MessageSource;
import org.jdesktop.swing.event.ProgressEvent;
import org.jdesktop.swing.event.ProgressListener;
import org.jdesktop.swing.event.ProgressSource;

import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.SwingUtilities;


/**
 * Base class for implementing objects which asynchronously load data from an
 * input stream into a model, ensuring that the potentially lengthy operation
 * does not block the user-interface.
 * <p>
 * Swing requires that all operations which directly affect the user-interface
 * component hierarchy execute on a single thread, the event dispatch thread (
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">
 * Swing's single-threaded GUI rule</a>).  Because of this, the task of streaming
 * data (potentially over the network) should not be performed on the event
 * dispatch thread because it would cause the user-interface to freeze and
 * become unresponsive.  And yet, while the reading of the data should be
 * off-loaded to a separate thread, it is desirable to incrementally load
 * portions of that data into the model as it's read so that the user can see
 * more immediate results than if he/she had to wait for the entire data
 * stream to be read before seeing any data at all.</p>
 * <p>
 * This class implements all the required thread and object synchronization
 * to support this asynchronous, incremental load operation.  It does this by
 * splitting the operation into three distinct steps:
 * <ol>
 * <li>read-meta-data: obtain any available meta-data from the stream which
 *                may provide structural and type information about the data.
 *                Meta-data is typically encoded in the beginning of the stream
 *                and is format-dependent.  This should be called only from
 *                the event dispatch thread since it will modify properties on
 *                the model and possibly generate events which affect user-interface
 *                components.</li>
 * <li>read-data: reading the data from the input-stream and loading it into
 *                a Java data structure which is not connected to the data model.
 *                This step is typically the most time consuming, hence it is
 *                performed in its own &quot;reader&quot; thread which is created by the
 *                <code>startLoading</code> method.</li>
 * <li>load-data: taking the contents of the disconnected data structure and adding it to
 *                the data model.  This step must be performed on the event
 *                dispatch thread because it has the potential to generate
 *                events which affect the user-interface components.</li>
 * </ol>
 * A concrete subclass should implement the 3 methods corresponding to these
 * steps:
 * <pre><code>
 *     public void loadMetaData(Object model, InputStream is)
 *     public void readData(Object model, InputStream is)
 *     public void loadData(Object model)
 * </code></pre>
 * <p>
 * An application using a DataLoader instance may only invoke
 * <code>initializeMetaData</code> and <code>startLoading</code> directly.
 * <code>startLoading</code> will cause <code>readData</code> and
 * <code>loadData</code> to invoked during the load operation.</p>
 *
 * @author Amy Fowler
 * @version 1.0
 */
public abstract class DataLoader implements ProgressSource, MessageSource {
    
    public static final String READER_PRIORITY_KEY = "swingx.readpriority";
    private LoadNotifier loadNotifier;
    private MessageSourceSupport mss;

    protected DataLoader() {
        mss = new MessageSourceSupport(this);
    }

    /**
     * Adds the specified progress listener to this data loader.
     * @param l progress listener to be notified as data is loaded or errors occur
     */
    public void addProgressListener(ProgressListener l) {
        mss.addProgressListener(l);
    }

    /**
     * Removes the specified progress listener from this data loader.
     * @param l progress listener to be notified as data is loaded or errors occur
     */
    public void removeProgressListener(ProgressListener l) {
        mss.removeProgressListener(l);
    }

    /**
     *
     * @return array containing all progress listeners registered on this data loader
     */
    public ProgressListener[] getProgressListeners() {
        return mss.getProgressListeners();
    }

    /**
     * Adds the specified message listener to this data loader.
     * @param l message listener to be notified as data is loaded or errors occur
     */
    public void addMessageListener(MessageListener l) {
        mss.addMessageListener(l);
    }

    /**
     * Removes the specified message listener from this data loader.
     * @param l message listener to be notified as data is loaded or errors occur
     */
    public void removeMessageListener(MessageListener l) {
        mss.removeMessageListener(l);
    }

    /**
     *
     * @return array containing all message listeners registered on this data loader
     */
    public MessageListener[] getMessageListeners() {
        return mss.getMessageListeners();
    }

    /**
     * Initializes the model with any meta-data available in the input stream.
     * The amount of meta-data available from the input stream is format-dependent.
     * This method is synchronous and may be invoked from the event dispatch thread.
     * @param model the data model being loaded from the input stream
     * @param is the input stream containing the meta-data
     * @throws IOException
     */
    public abstract void loadMetaData(Object model, InputStream is)
        throws IOException;

    /**
     * Starts the asynchronous load operation by spinning up a separate thread
     * that will read the data from the input stream.  This method will
     * return immediately.  Prior to calling this method, a MessageListener
     * should be registered to be notified as data is loaded or errors occur.
     * If the data model being loaded requires meta-data to describe its
     * structure (such as the number of columns in a tabular data row), then
     * that meta-data must be initialized before <code>startLoading</code>
     * is called and must not be modified while the load operation executes,
     * otherwise synchronization errors will occur.
     * @see #addProgressListener
     * @param model the data model being loaded from the input stream
     * @param is the input stream containing the data
     */
    public void startLoading(final Object model, final InputStream is) {
        loadNotifier = new LoadNotifier(this, model);
        Runnable task = new Runnable() {
            public void run() {
                try {
                    readData(is);
                }
                catch (Exception e) {
                    final Throwable error = e;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            fireException(error);
                        }
                    });
                }
            }
        };
        Thread readerThread = new Thread(task);
        readerThread.setPriority(getReaderThreadPriority());
        fireProgressStarted(1,1); // indeterminate
        readerThread.start();
    }

    protected int getReaderThreadPriority() {
        String priority = System.getProperty(READER_PRIORITY_KEY);
        if (priority != null) {
            try {
                int prio = Integer.parseInt(priority);
                // PENDING: need to check upper bound?
                return Math.max(Thread.MIN_PRIORITY, prio);
            } catch (Exception ex) {
                // found some foul expression 
            }
            
        }
        return Thread.MIN_PRIORITY;
    }

    /**
     * Invoked by the <code>startLoading</code> method.  This method will be
     * called on a separate &quot;reader&quot; thread.  Subclasses must implement
     * this method
     * to read the data from the stream and place it in a data structure which
     * is <b>disconnected</b> from the data model.   When increments of data
     * are ready to be loaded into the model, this method should invoke
     * <code>scheduleLoad</code>, which will cause <code>loadData</code>
     * to be called on the event dispatch thread, where the model may be
     * safely updated.  Progress events must not be fired from this method.
     * @see #scheduleLoad
     * @param is the input stream containing the data
     * @throws IOException if errors occur while reading data from the input stream
     * @throws ConversionException if errors occur while converting data values from
     *         string to object
     */
    protected abstract void readData(InputStream is) throws IOException, ConversionException;

    /**
     * Invoked internally once the <code>readData</code> method calls
     * <code>scheduleLoad</code> to schedule the loading of an increment of
     * data to the model.  This method is called on the event dispatch
     * thread, therefore it is safe to mutate the model from this method.
     * Subclasses must implement this method to load the current contents of the
     * disconnected data structure into the data model.  Note that because
     * there is an unpredictable delay between the time <code>scheduleLoad</code>
     * is called from the &quot;reader&quot; thread and <code>loadData</code>
     * executes on the event dispatch thread, there may be more data available
     * for loading than was available when <code>scheduleLoad</code> was
     * invoked.  All available data should be loaded from this method.
     * <p>
     * This method should fire an appropriate progress event to notify progress
     * listeners when:
     * <ul>
     * <li>incremental load occurs(for determinate load operations)</li>
     * <li>load completes</li>
     * <li>exception occurs</li>
     * </ul>
     * </p>
     * @see #fireProgressStarted
     * @see #fireProgressEnded
     * @see #fireException
     *
     * @param model the data model being loaded from the input stream
     */
    protected abstract void loadData(Object model);

    /**
     * Invoked by the <code>readData</code> method from the &quot;reader&quot;
     * thread to schedule a subsequent call to <code>loadData</code> on the
     * event dispatch thread.  If <code>readData</code> invokes
     * <code>scheduleLoad</code> multiple times before <code>loadData</code>
     * has the opportunity to execute on the event dispatch thread, those
     * requests will be collapsed, resulting in only a single call to
     * <code>loadData</code>.
     * @see #readData
     * @see #loadData
     */
    protected void scheduleLoad() {
        synchronized (loadNotifier) {
            if (!loadNotifier.isPending()) {
                loadNotifier.setPending(true);
                SwingUtilities.invokeLater(loadNotifier);
            }
        }
    }

    /**
     * Fires event indicating that the load operation has started.
     * For a determinite progress operation, the minimum value should be less than
     * the maximum value. For inderminate operations, set minimum equal to maximum.
     *
     * @param minimum the minimum value of the progress operation
     * @param maximum the maximum value of the progress operation
     */
    protected void fireProgressStarted(int minimum, int maximum) {
        mss.fireProgressStarted(minimum, maximum);
    }

    /**
     * Fires event indicating that an increment of progress has occured.
     * @param progress total value of the progress operation. This
     *                 value should be between the minimum and maximum values
     */
    protected void fireProgressIncremented(int progress) {
        mss.fireProgressIncremented(progress);
    }

    /**
     * Fires event indicating that the load operation has completed
     */
    protected void fireProgressEnded() {
        mss.fireProgressEnded();
    }

    protected void fireException(Throwable t) {
        mss.fireException(t);
    }

    protected void fireMessage(String message) {
    mss.fireMessage(message);
    }

    private class LoadNotifier
        implements Runnable {
        private DataLoader loader;
        private Object model;
        private boolean pending = false;

        LoadNotifier(DataLoader loader, Object model) {
            this.loader = loader;
            this.model = model;
        }

        public synchronized void setPending(boolean pending) {
            this.pending = pending;
        }

        public synchronized boolean isPending() {
            return pending;
        }

        public void run() {
            synchronized (this) {
                loader.loadData(model);
                setPending(false);
            }
        }
    }

}
