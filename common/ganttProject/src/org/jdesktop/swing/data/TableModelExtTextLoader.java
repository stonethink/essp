/*
 * $Id: TableModelExtTextLoader.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import org.jdesktop.swing.event.ProgressEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javax.swing.SwingUtilities;


/**
 * Data loader class which reads data from a text input stream and loads
 * it into a DefaultTableModelExt instance.  Common examples of tabular text data
 * are comma-separated-values (CSV) and tab-separated-values (TSV) files.
 * <p>
 * Each line of text in the data stream is treated as a single row of data in
 * the tabular data model.  A line is considered to be terminated by any one
 * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
 * followed immediately by a linefeed.   Within each row, column values are
 * delimited by a regular expression string, which is a configurable
 * &quot;columnDelimiter&quot; property on the Loader class.  The default
 * column delimiter is a <code>tab ('\t')</code> character.</p>
 * <p>
 * Some text files encode the column names as the first row of data, thus
 * this loader class supports a configurable &quot;columnNamesInFirstRow&quot;
 * boolean property.</p>
 * <p>
 * This class supports loading the data into the model in increments so that
 * it may be displayed immediately, rather than waiting until the entire stream
 * has been read.  The number of rows in an increment can be specified via
 * the &quot;blockIncrementSize&quot; property.</p>
 * <p>
 * The following example configures a TableModelExtTextLoader to read CSV
 * formatted data at the URL location, &quot;http://myapp/employees.csv&quot;,
 * and load the data by roughly 75 line increments:
 * <pre><code>
 *     DefaultTableModelExt data = new DefaultTableModelExt("http://myapp/employees.csv");
 *     TableModelExtTextLoader loader = new TableModelExtTextLoader(",", false, 75);
 *     data.setLoader(loader);
 *     data.startLoading();
 * </code></pre>
 * </p>
 * <p>
 * Note that properties on a TableModelExtTextLoader instance should not be modified
 * while a load operation is executing, else synchronization errors will occur.
 * </p>
 */

public class TableModelExtTextLoader extends DataLoader {

    private String columnDelimiter;
    private boolean firstRowHeader;
    private int blockSize;

    private int columnCount = 0;
    private MetaData columnMetaData[];
    private List rows;
    private boolean complete = false;

    /**
     * Creates a TableModelExtTextLoader object with a default tab ('\t') column delimeter,
     * &quot;columnNameInFirstRow&quot; set to <code>false</code>, and
     * a default block increment size of 50.
     */
    public TableModelExtTextLoader() {
        this("\t", false, 50);
    }

    /**
     * Creates a TableModelExtTextLoader object with the specified column delimiter,
     * &quot;columnNamesInFirstRow&quot; value, and block increment size.
     * @param columnDelimiter regular expression string used to delimit column
     *        values within a row
     * @param isFirstRowHeader boolean indicating whether the first row
     *        should be treated as the column header names
     * @param blockIncrementSize integer indicating the number of rows to be read
     *        between incremental load requests
     */
    public TableModelExtTextLoader(String columnDelimiter,
                                 boolean isFirstRowHeader,
                                 int blockIncrementSize) {
        super();
        this.columnDelimiter = columnDelimiter;
        this.firstRowHeader = isFirstRowHeader;
        this.blockSize = blockIncrementSize;
    }

    /**
     *
     * @return regular expression string used to delimit column
     *        values within a row
     */
    public String getColumnDelimiter() {
        return columnDelimiter;
    }

    /**
     * Sets the &quot;columnDelimiter&quot; property.
     * @param regex regular expression string used to delimit column
     *        values within a row
     */
    public void setColumnDelimiter(String regex) {
        this.columnDelimiter = regex;
    }

    /**
     *
     * @return boolean indicating whether the first row should be treated
     *         as header column names
     */
    public boolean isFirstRowHeader() {
        return firstRowHeader;
    }

    /**
     * Sets the &quot;isFirstRowHeader&quot; property.
     * @param isFirstRowHeader boolean indicating whether the first row
     *        should be treated as header column names
     */
    public void setFirstRowHeader(boolean isFirstRowHeader) {
        this.firstRowHeader = isFirstRowHeader;
    }

    /**
     *
     * @return integer indicating the number of rows to be read
     *         between incremental load requests
     */
    public int getBlockIncrementSize() {
        return blockSize;
    }

    /**
     * Sets the &quot;blockIncrementSize&quot; property.
     * @param blockIncrementSize integer indicating the number of rows to be read
     *        between incremental load requests
     */
    public void setBlockIncrementSize(int blockIncrementSize) {
        this.blockSize = blockIncrementSize;
    }

    /**
     * Initializes the the number of columns in the tabular data model by
     * reading the first line in the stream and counting the columns.
     * If &quot;columnNamesInFirstRow&quot; is <code>true</code> then this
     * method will also initialize the column names for the tabular data model
     * with the column values contained in the first line of the stream.
     * This method is synchronous and may be invoked from the event dispatch thread.
     *
     * @param model the tabular data model being loaded from the input stream
     * @param is the input stream containing the meta-data
     * @throws IOException if errors occur while reading from the stream
     * @throws ClassCastException if the model is not an instance of DefaultTableModelExt
     */
    public void loadMetaData(Object model, InputStream is) throws IOException {
        DefaultTableModelExt dataModel = (DefaultTableModelExt)model;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String firstLine = reader.readLine();
        String columns[] = firstLine.split(columnDelimiter);
        dataModel.setColumnCount(columns.length);
        if (firstRowHeader) {
            for(int i = 0; i < columns.length; i++) {
                MetaData metaData = dataModel.getColumnMetaData(i);
                metaData.setName(columns[i]);
            }
        }
        reader.close();
    }

    /**
     * {@inheritDoc}
     */
    public void startLoading(final Object model, final InputStream is) {
        DefaultTableModelExt dataModel = (DefaultTableModelExt)model;
        columnCount = dataModel.getColumnCount();
        columnMetaData = dataModel.getMetaData();
        super.startLoading(model, is);
    }

  /**
    * Invoked by the <code>startLoading</code> method.  This method will be
    * called on a separate &quot;reader&quot; thread.  This method will invoke
    * <code>scheduleLoad</code> between reading each incremental block of rows.
    * This reader will use the &quot;elementClass&quot; and &quot;Converter&quot;
    * properties on a given column's meta-data object in order to convert the
    * data from string to object values.
    * @param is the input stream containing the data
    * @throws IOException if errors occur while reading data from the input stream
    * @throws ConversionException if errors occur while converting data values from
    *         string to object
    */
    protected void readData(InputStream is) throws IOException,
        ConversionException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer strbuf = new StringBuffer();
        if (columnCount == 0) {
            throw new IOException("cannot read data when column count is 0");
        }

        int colIndex = 0;
        int rowCount = 0;
        Object row[] = null;
        rows = Collections.synchronizedList(new ArrayList());

        String line = reader.readLine();
        if (firstRowHeader) {
            // throw away first line
            line = reader.readLine();
        }
        while (line != null) {
            String columns[] = line.split(columnDelimiter);
            row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                if (i < columns.length) {
                    row[i] = convertToValue(i, columns[i]);
                }
            }
            synchronized (rows) {
                rows.add(row);
                rowCount++;
                if (rowCount % blockSize == 0) {
                    scheduleLoad();
                }
            }
            line = reader.readLine();
        }
        complete = true;
        scheduleLoad();
        reader.close();
    }

    /**
     * Invoked internally once the <code>readData</code> method calls
     * <code>scheduleLoad</code> to schedule the loading of an increment of
     * data to the model.  This method is called on the event dispatch
     * thread, therefore it will add the current list of rows read to the
     * tabular data model.  After the data is added to the model, it will
     * fire a progress event to indicate whether the load operation is complete.
     *
     * @param model the data model being loaded from the input stream
     * @throws ClassCastException if model is not an instance of DefaultTableModelExt
     */
    protected void loadData(Object model) {
        DefaultTableModelExt dataModel = (DefaultTableModelExt)model;
        boolean done = false;
        synchronized (rows) {
            dataModel.loadRows(rows);
            rows.clear();
            done = complete;
        }
        if (done) {
            // don't want to hold lock while listeners fire
            fireProgressEnded();
        }
    }

    private Object convertToValue(int colIndex, String stringValue) throws ConversionException {
        Object value = null;
        if (stringValue.length() == 0) {
            return null;
        }
        Converter converter = columnMetaData[colIndex].getConverter();
        if (converter == null) {
            // defaults to String
            value = stringValue;
        } else {
            value = converter.decode(stringValue, columnMetaData[colIndex].getDecodeFormat());
        }
        return value;
    }
}
