/*
 * $Id: DefaultTableModelExt.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import org.jdesktop.swing.data.MetaData;

import org.jdesktop.swing.event.MessageEvent;
import org.jdesktop.swing.event.MessageListener;
import org.jdesktop.swing.event.ProgressEvent;
import org.jdesktop.swing.event.ProgressListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.InputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


/**
 * Class used to represent a tabular data model which holds 2 dimensional
 * data.  This class provides a simple alternative to <code>javax.sql.RowSet</code> and
 * is intended for use when the program is not obtaining its data directly
 * from a JDBC data source.  Programs which are interfacing with databases using
 * JDBC should use the <code>RowSet</code> API instead.</p>
 * <p>
 * A tabular data model is structured with a fixed number of columns where
 * each column is represented by an integer index.  The first
 * column's index is 0 and the last column's index is columnCount - 1.  Each
 * column in the tabular data model has a <code>MetaData</code> instance which describes the
 * column's data-type and edit constraints.</p>
 * <p>
 * The structure of the model (number of columns and associated column meta-data
 * which describes those columns) must be initialized before the rows of data
 * are loaded into the model.  The structure can be obtained directly
 * from the data source (if set) or it can be set explicitly by the program.
 * The following example configures the table's structure explicitly:
 * <pre><code>
 *     DefaultTableModelExt data = new DefaultTableModelExt();
 *     data.setColumnCount(3);
 *
 *     MetaData columnMetaData = data.getColumnMetaData(0);
 *     columnMetaData.setName("firstname");
 *     columnMetaData = data.getColumnMetaData(1);
 *     columnMetaData.setName("lastname");
 *     columnMetaData = data.getColumnMetaData(2);
 *     columnMetaData.setName("employeeid");
 *     columnMetaData.setElementClass(Integer.class);
 * </code></pre>
 * <p>
 * If the meta-data properties for each column are not explicitly set, the type
 * of each column will default to <code>java.lang.String</code>.</p>
 * <p>
 * The data for the model may be loaded either by explicitly invoking the
 * <code>addRows</code> or <code>insertRows</code> method, or by setting the &quot;source&quot; property, in
 * which case the tabular data model will handle streaming the data in from that
 * source when <code>startLoading</code> is called.  The format of the data at that
 * source URL must be readible by the DataLoader instance set as the &quot;loader&quot;
 * property.  By default the loader is set to a <code>TableModelExtTextLoader</code> instance,
 * which can read the data in simple ascii format where the columns are
 * separated by configurable regular expression delimeters.  Examples of this format are
 * tab-separated-values (TSV) and comma-separated-values (CSV).  If the data at the
 * source is coming in an alternate format, the caller is responsible for setting
 * an appropriate loader object before invoking <code>startLoading</code>.</p>
 * <p>
 * If the data for the above example was available from a tsv file, the tabular
 * data model could be loaded with the following code:
 * <pre><code>
 *     data.setSource("file:///D:/acme/employees.tsv");
 *     data.startLoading();
 * </code></pre>
 * </p>
 * <p>
 * It is often desirable to determine the structure of the tabular data model
 * (number of columns, types, etc) from the source rather than setting it explicitly.
 * The amount of structural information available from a source will depend on
 * the format of the data.  The plain text formats do not typically contain meta-data
 * information beyond optional column names encoded as the first row, and thus
 * it is desirable to explicitly configure the column meta-data for these simple
 * formats.</p>
 * <p>
 * Once the data has been loaded into the model, values at a specific row and
 * column may be retreived or modified.   For example:
 * <pre><code>
 *     int rowCount = data.getRowCount();
 *     for (int i = 0; i < rowCount; i++) {
 *         <b>Float payrate = (Float)data.getValueAt(i, 4);</b>
 *         if (payrate < 10.00) {
 *             // underpaid - give raise
 *             <b>data.setValueAt(i, 4, new Float(payrate*.05));</b>
 *         }
 *     }
 * </code></pre>
 * </p>
 * Additionally, rows can be inserted or deleted.  Example:
 * <pre><code>
 *     // hired
 *     Object employee[] = new Object[4];
 *     employee[0] = "Einstein";
 *     employee[1] = "Albert";
 *     employee[2] = new Integer(430);
 *     employee[3] = new Float(12.50);
 *     <b>data.insertRow(5, employee);</b>
 *
 *     // fired
 *     <b>data.deleteRow(6);</b>
 * </code></pre>
 *
 * <p>
 * This class is also an instance of <code>javax.swing.table.TableModel</code> and thus
 * can be set as the model for any <code>javax.swing.JTable</code> component instance.</p>
 * <p>
 * DefaultTableModelExt generates table model events when either its structure or
 * values are modified. To listen for these events, register a <code>TableModelListener</code>
 * instance. Example:
 * <pre><code>
 *     DefaultTableModelExt data = new DefaultTableModelExt("http://www.foo.com/barQuery");
 *     <b>data.addTableModelListener(new TableModelListener() {
 *         public void tableChanged(TableModelEvent e) {
 *              // handle tabular data model change
 *         }
 *     });</b>
 * </code></pre>
 * </p>
 *
 * @see MetaData
 * @see javax.swing.table.TableModel
 * @see javax.swing.event.TableModelEvent
 * @see javax.swing.event.TableModelListener
 * @see TableModelExtTextLoader
 *
 * @author Amy Fowler
 * @version 1.0
 */
public class DefaultTableModelExt extends AbstractTableModel implements MetaDataProvider {

    private URL sourceURL;

    //private DataLoader loader;
    private DataLoader loader;

    private MetaData[] columnMetaData;
    private Vector rows;

    private boolean loading = false;
    private boolean readOnly = false;

    private PropertyChangeListener columnMetaDataListener;

    private PropertyChangeSupport pcs;

    /**
     * Creates a new tabular data model with 0 columns.
     */
    public DefaultTableModelExt() {
        this(0);
    }

    /**
     * Creates a new tabular data model with the specified number of
     * columns.
     * @throws IllegalArgumentException if columnCount < 0
     * @param columnCount integer indicating the number of columns in this
     *        tabular data model
     */
    public DefaultTableModelExt(int columnCount) {
        init();
        setColumnCount(columnCount);
    }

    /**
     * Creates a new tabular data model configured to obtain its data
     * in plain text format from the specified URL.
     * @param urlName String containing the name of the URL which provides the
     *        data for this tabular data model
     * @throws MalformedURLException if urlName cannot be converted to a valid URL
     */
    public DefaultTableModelExt(String urlName) throws MalformedURLException {
        this();
        setSource(urlName);
    }

    /**
      * Create a new tabular data model configured to obtain its data
      * in plain text format from the specified URL.
      * @param sourceURL URL which provides the data for this tabular data model
      */
     public DefaultTableModelExt(URL sourceURL) {
         this();
         setSource(sourceURL);
     }

     /**
      * Creates a new tabular data model configured to obtain its data
      * in plain text format from the specified URL.  The number of columns
      * will be initialized to the specified column count.
      * @param urlName String containing the name of the URL which provides the
      *        data for this tabular data model
      * @param columnCount integer indicating the number of columns in this
      *        tabular data model
      * @throws IllegalArgumentException if columnCount < 0
      * @throws MalformedURLException if urlName cannot be converted to a valid URL
      */
     public DefaultTableModelExt(String urlName, int columnCount) throws MalformedURLException {
         this(columnCount);
         setSource(urlName);
     }

     private void init() {
         columnMetaDataListener = new MetaDataChangeListener();
         pcs = new PropertyChangeSupport(this);
         setLoader(new TableModelExtTextLoader());
     }

    /**
     * Sets the &quot;source&quot; URL property after converting the URL string
     * to a <code>URL</code> instance.
     * @see #getSource
     * @param urlName String containing the name of the URL which provides the
     *        data for this tabular data model
     * @throws MalformedURLException if urlName cannot be converted to a valid URL
     */
    public void setSource(String urlName) throws MalformedURLException {
        setSource(new URL(urlName));
    }

    /**
     * Sets the &quot;source&quot; property.
     * @see #getSource
     * @param sourceURL URL which provides the data for this tabular data model
     */
    public void setSource(URL sourceURL) {
        URL oldSource = this.sourceURL;
        this.sourceURL = sourceURL;
        pcs.firePropertyChange("source", oldSource, sourceURL);
    }

    /**
     * @see #setSource
     * @return URL which provides the data for this tabular data model
     */
    public URL getSource() {
        return sourceURL;
    }

    /**
     * Sets the data loader object to be used to stream in the data from the source URL.
     * @see #getLoader
     * @param loader DataLoader object which can read data in the format provided
     *        from the source
     */
    public void setLoader(DataLoader loader) {
        DataLoader oldLoader = this.loader;
        this.loader = loader;
        pcs.firePropertyChange("loader", oldLoader, loader);
    }

    /**
     * Returns a TableModelExtTextLoader instance by default, which can read
     * data in plain text format where rows are separated by newlines and
     * columns within each row are separated by the tab character (delimeters
     * are configurable).
     * @see TableModelExtTextLoader
     * @see #setLoader
     * @return DataLoader object which can read data in the format provided
     *         from the source
     */
    public DataLoader getLoader() {
        return loader;
    }

    /**
     * Returns the number of columns in this tabular data model.
     * If the data source is non-null and the number of columns has not yet
     * been initialized, this method will attempt to initialize the number
     * of columns by reading any available meta-data from the source.
     * @return integer indicating the number of columns in this
     *         tabular data model
     */
    public int getColumnCount() {
        synchronized(this) {
            ensureMetaDataInitialized();
            return columnMetaData.length;
        }
    }

    /**
     * Initializes the number of columns in this tabular data model and
     * creates new instances of column MetaData objects for each column.
     * Each column meta-data object will default to type String and will
     * have a column name &quot;column+&lt;columnIndex&gt;&quot;.
     * This method will also cause any existing rows of data to be cleared
     * so that rowCount will be 0.
     * @throws IllegalArgumentException if columnCount < 0
     * @param columnCount integer indicating the number of columns in this
     *        tabular data model
     */
    public void setColumnCount(int columnCount) {
        if (columnCount < 0) {
            throw new IllegalArgumentException("column count, " + columnCount +
                                               " is < 0");
        }
        int oldColumnCount = columnMetaData != null ? columnMetaData.length :
                0;
        synchronized(this) {
            if (columnMetaData != null) {
                for (int i = 0; i < columnMetaData.length; i++) {
                    columnMetaData[i].removePropertyChangeListener(
                        columnMetaDataListener);
                }
            }
            columnMetaData = new MetaData[columnCount];
            for (int i = 0; i < columnMetaData.length; i++) {
                columnMetaData[i] = new MetaData("column" + i);
                columnMetaData[i].addPropertyChangeListener(
                    columnMetaDataListener);
            }
            rows = new Vector();
        }
        fireTableStructureChanged();
        pcs.firePropertyChange("columnCount", oldColumnCount, columnCount);
    }

    /**
     * Returns the meta-data for the specified column.
     * If the data source is non-null and the number of columns has not yet
     * been initialized, this method will attempt to initialize the number
     * of columns by reading any available meta-data from the source.
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount
     * @param columnIndex integer index indicating column in tabular data model
     * @return MetaData object describing the data element at the specified
     *         column
     */
    public MetaData getColumnMetaData(int columnIndex) {
        synchronized(this) {
            ensureMetaDataInitialized();
            return columnMetaData[columnIndex];
        }
    }

//-------------------------- implementing MetaDataProvider    
    /**
     *
     * @param columnName String containing the id for the column
     * @return MetaData object describing the data element at the specified column
     */
    public MetaData getMetaData(String columnName) {
        int columnIndex = getColumnIndex(columnName);
        return getColumnMetaData(columnIndex);
    }

    /**
     * If the data source is non-null and the number of columns has not yet
     * been initialized, this method will attempt to initialize the number
     * of columns by reading any available meta-data from the source.
     *
     * @return array containing the MetaData objects for each column in the
     *         tabular data model
     */
    public MetaData[] getMetaData() {
        ensureMetaDataInitialized();
        MetaData metaDataCopy[] = new MetaData[columnMetaData.length];
        System.arraycopy(columnMetaData, 0, metaDataCopy, 0, columnMetaData.length);
        return metaDataCopy;
    }


    public String[] getFieldNames() {
        ensureMetaDataInitialized();
        String[] names = new String[getFieldCount()];
        for (int i = 0; i < names.length; i++) {
            names[i] = getColumnMetaData(i).getName();
        }
        return names;
    }
    
    public int getFieldCount() {
        return getColumnCount();
    }
    
    /**
     * Sets the meta data object for the specified column.
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount
     * @param columnIndex integer index indicating column in tabular data model
     * @param metaData object describing the data element at the specified
     *        column
     */
    public void setColumnMetaData(int columnIndex, MetaData metaData) {
        synchronized(this) {
            if (columnMetaData[columnIndex] != null) {
                columnMetaData[columnIndex].removePropertyChangeListener(
                    columnMetaDataListener);
            }
            columnMetaData[columnIndex] = metaData;
            metaData.addPropertyChangeListener(columnMetaDataListener);
        }
        fireTableStructureChanged();
    }

    private void ensureMetaDataInitialized() {
        if (columnMetaData == null || columnMetaData.length == 0) {
            initializeMetaData();
        }
    }

    private boolean initializeMetaData() {
        if (sourceURL != null && loader != null) {
            try {
                final InputStream is = sourceURL.openStream();
                loader.loadMetaData(this, is);
                is.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns the value of the specified column's meta-data &quot;elementClass&quot;
     * property.
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount
     * @see MetaData#getElementClass
     * @param columnIndex integer index indicating column in tabular data model
     * @return Class indicating column data element's type
     */
    public Class getColumnClass(int columnIndex) {
        ensureMetaDataInitialized();
        return columnMetaData[columnIndex].getElementClass();
    }

    /**
     * Returns the value of the specified column's meta-data &quot;label&quot;
     * property. Note: javax.swing.table.TableModel specifies this method returns
     * the header value, which is why this returns the label and not the name.
     * @see MetaData#getLabel
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount
     * @param columnIndex integer index indicating column in tabular data model
     * @return String containing the column's label
     */
    public String getColumnName(int columnIndex) {
        ensureMetaDataInitialized();
        return columnMetaData[columnIndex].getLabel();
    }

    /**
     * Returns the negation of the specified column's meta-data &quot;readOnly&quot;
     * property.
     * @see MetaData#isReadOnly
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount or
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     * @param columnIndex integer index indicating column in tabular data model
     * @return boolean indicating whether or not the tabular data element value
     *         at the specified row and column may be modified
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        ensureMetaDataInitialized();
        return !columnMetaData[columnIndex].isReadOnly();
    }

    /**
     * @return integer indicating the number of rows currently in this
     *         tabular data model
     */
    public int getRowCount() {
        return rows.size();
    }

    /**
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount or
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     * @param columnIndex integer index indicating column in tabular data model
     * @return Object containing value of tabular data element at the
     *         specified row and column
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object row[] = (Object[])rows.get(rowIndex);
        return row[columnIndex];
    }

    /**
     * Sets the value of the tabular data element at the specified row and column.
     * The caller is responsible for ensuring the object is an instance of the
     * column's class.
     * @see #getColumnClass
     * @see #isCellEditable
     * @throws RuntimeException if the column is not editable
     * @throws IndexOutOfBoundsException if
     *          columnIndex < 0 or columnIndex >= columnCount or
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param value Object containing value of tabular data element
     * @param rowIndex integer index indicating row in tabular data model
     * @param columnIndex integer index indicating column in tabular data model
     */
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (isCellEditable(rowIndex, columnIndex)) {
            synchronized(this.rows) {
                Object row[] = (Object[]) rows.get(rowIndex);
                row[columnIndex] = value;
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        } else {
            throw new RuntimeException("row " + rowIndex +" column " + columnIndex +
                                        " is not editable");
        }
    }

    /**
     * Deletes the row at the specified row index from the tabular data model.
     * @throws IndexOutOfBoundsException if
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     */
    public void deleteRow(int rowIndex) {
        synchronized(this.rows) {
            rows.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    /**
     * Deletes the contiguous set of rows in the specified range from
     * this tabular data model.
     * @throws IllegalArgumentException if lastRowIndex < firstRowIndex
     * @throws IndexOutOfBoundsException if
     *          firstRowIndex < 0 or firstRowIndex >= rowCount or
     *          lastRowIndex < 0 or lastRowIndex >= rowCount
     *
     * @param firstRowIndex integer index indicating first row in range
     * @param lastRowIndex integer index indicating last row in range
     */
    public void deleteRows(int firstRowIndex, int lastRowIndex) {
        if (lastRowIndex < firstRowIndex) {
            throw new IllegalArgumentException("lastRowIndex, " + lastRowIndex +
                                               " cannot be less than firstRowIndex, "+
                                               firstRowIndex);
        }
        synchronized(this.rows) {
            int deletedRowCount = lastRowIndex - firstRowIndex + 1;
            for(int i = 0; i < deletedRowCount; i++) {
                rows.remove(firstRowIndex);
            }
            fireTableRowsDeleted(firstRowIndex, lastRowIndex);
        }
    }

    /**@todo aim: run some tests to see if array copy bogs down on load */

    /**
     * Inserts a new row in the tabular data model at the specified row index.
     * The elements in the row array are copied into the tabular data model,
     * therefore subsequent changes to the content of the row parameter
     * will have no affect on the contents of the tabular data model.
     * @throws IndexOutOfBoundsException if
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     * @param row array containing the column elements for the row
     */
    public void insertRow(int rowIndex, Object row[]) {
        insertRowImpl(rowIndex, row);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    /**
     * Inserts a collection of new rows into the tabular data model at the
     * specified row index.  Each element in the collection must be an
     * array of objects with a length equal to the number of columns in this
     * tabular data model.
     * The elements in the row arrays are copied into the tabular data model,
     * therefore subsequent changes to the content of the rows parameter
     * will have no affect on the contents of the tabular data model.
     * @throws IndexOutOfBoundsException if
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     * @param rows list containing an object array for each row to be added
     */
    public void insertRows(int rowIndex, List rows) {
        synchronized(this.rows) {
            for (int i = 0; i < rows.size(); i++) {
                Object row[] = (Object[]) rows.get(i);
                insertRowImpl(rowIndex + i, row);
            }
            fireTableRowsInserted(rowIndex, rowIndex+rows.size()-1);
        }
    }

    /**
     * Adds a new row at the end of the tabular data model.
     * The elements in the row array are copied into the tabular data model,
     * therefore subsequent changes to the content of the row array parameter
     * will have no affect on the contents of the tabular data model.
     * @param row array containing the column elements for the row
     */
    public void addRow(Object row[]) {
        int rowIndex = rows.size();
        insertRowImpl(rowIndex, row);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    /**
     * Adds a collection of new rows at the end of the tabular data model.
     * Each element in the collection must be an array of objects with a
     * length equal to the number of columns in this tabular data model.
     * The elements in the rows list are copied into the tabular data model,
     * therefore subsequent changes to the content of the row parameter
     * will have no affect on the contents of the tabular data model.
     * @param rows list containing an object array for each row to be added
     */
    public void addRows(List rows) {
        insertRows(this.rows.size(), rows);
    }

    /* package-private method for loading row arrays without copying */
    void loadRows(List rows) {
        synchronized(this.rows) {
            int rowIndex = this.rows.size();
            this.rows.addAll(rows);
            fireTableRowsInserted(rowIndex, rowIndex+rows.size());
        }
    }

    private void insertRowImpl(int rowIndex, Object[] row) {
        int rowLength = getColumnCount();
        Object[] newRow = new Object[rowLength];
        System.arraycopy(row, 0, newRow, 0, rowLength);
        rows.insertElementAt(newRow, rowIndex);
    }


    /**
     * Starts loading the data asynchronously from the tabular data object's source.
     * If this tabular data object has existing data when this method is called,
     * it will be cleared before loading any new data.
     * This method will initiate a new thread to handle the loading and return
     * immediately, thus it is safe to call from the UI thread.
     * @throws IllegalStateException if the &quot;source&quot property is null
     * @throws IOException
     */
    public void startLoading() throws IOException {
        if (sourceURL == null) {
            throw new IllegalStateException(
                "data cannot be loaded when source URL is null");
        }
        if (isLoading()) {
            return;
        }
        ensureMetaDataInitialized();
        // make sure we can connect to source first...
        final InputStream is = sourceURL.openStream();

        setLoading(true);
        rows.clear();

        // listener will be called on event-dispatch-thread
        MessageAdapter a = new MessageAdapter(is);
        loader.addProgressListener(a);
        loader.addMessageListener(a);

        loader.startLoading(this, is);
    }

    private class MessageAdapter implements ProgressListener, MessageListener {

    private InputStream is;

    /**
     * This is a good way of doing this.
     */
    public MessageAdapter(InputStream is) {
        this.is = is;
    }

    public void progressStarted(ProgressEvent event) {
    }

    public void progressIncremented(ProgressEvent event) {
    }

    public void progressEnded(ProgressEvent event) {
        cleanup((DataLoader) event.getSource());
    }

    public void message(MessageEvent event) {
        if (event.getThrowable() != null) {
            cleanup((DataLoader) event.getSource());
        }
        // XXX - show messages to System.out for now. Move
        // to a message bus.
        System.out.println(event);

    }

    private void cleanup(DataLoader loader) {
        loader.removeProgressListener(this);
        setLoading(false);
        try {
        is.close();
        }
        catch (IOException e) {
        }
    }
    }

    /**
     * @see #startLoading
     * @return boolean indicating whether or not data is currently being loaded
     */
    public boolean isLoading() {
        return loading;
    }

    private void setLoading(boolean loading) {
        boolean oldLoading = this.loading;
        this.loading = loading;
        pcs.firePropertyChange("loading", oldLoading, loading);
    }

    /**
     * Returns the contents of the row at the specified row index.
     * The elements in the row are copied from the tabular data model,
     * therefore subsequent changes to the content of the returned row
     * array will have no affect on the contents of the tabular data model.
     * Use <code>setValueAt</code> to change the contents of the tabular data
     * model.
     * @see #setValueAt
     * @throws IndexOutOfBoundsException if
     *          rowIndex < 0 or rowIndex >= rowCount
     * @param rowIndex integer index indicating row in tabular data model
     * @return array containing the column element values for the row
     */
    public Object[] getRow(int rowIndex) {
        int columnCount = getColumnCount();
        Object row[] = new Object[columnCount];
        System.arraycopy((Object[])rows.get(rowIndex), 0,
                         row, 0, columnCount);
        return row;

    }

    /**
     * Note that the columnName parameter must be the &quot;name&quot; of the column
     * as stored in the column's MetaData and should not be confused with
     * the <code>getColumnName</code> method from TableModel, which actually
     * refers to the &quot;label&quot; property on the MetaData.
     * @see MetaData#getName
     * @throws IllegalArgumentException if the column name does not exist in
     *         this tabular data model
     * @param columnName String containing the name of the column
     * @return integer index of column in tabular data model which corresponds
     *         to the specified column name
     */
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columnMetaData.length; i++) {
            if (columnMetaData[i].getName().equals(columnName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("\""+columnName+"\" not found in tabular data object");
    }

    /**
     * Adds the specified property change listener to this tabular data model.
     * @param pcl PropertyChangeListener object to receive events when
     *        tabular data model properties change
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Removes the specified property change listener from this tabular data model.
     * @param pcl PropertyChangeListener object to receive events when
     *        tabular data model properties change
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    /**
     *
     * @return array containing the PropertyChangeListener objects registered
     *         on this tabular data model
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pcs.getPropertyChangeListeners();
    }

    private class MetaDataChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName.equals("elementClass") ||
                propertyName.equals("name")) {
                DefaultTableModelExt.this.fireTableStructureChanged();
            }
        }
    }

}
