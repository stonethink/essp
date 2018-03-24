/*
 * $Id: SQLDataProvider.java,v 1.1 2006/10/10 03:50:56 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset.provider.sql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdesktop.dataset.DataColumn;
import org.jdesktop.dataset.DataProvider;
import org.jdesktop.dataset.DataRow;
import org.jdesktop.dataset.DataTable;
import org.jdesktop.dataset.event.TableChangeEvent;
import org.jdesktop.dataset.provider.LoadTask;
import org.jdesktop.dataset.provider.SaveTask;

/**
 * SQL based DataProvider for a JDNC DataSet. This implementation handles
 * retrieving values from a database table, and persisting changes back
 * to the table.
 * 
 * @author rbair
 */
public class SQLDataProvider extends DataProvider {
    private TableCommand tableCommand;
    private JDBCDataConnection conn;
    
    /** 
     * Creates a new instance of SQLDataProvider 
     */
    public SQLDataProvider() {
    }
    
    public SQLDataProvider(String tableName) {
        tableCommand = new TableCommand(tableName);
    }
    
    public SQLDataProvider(String tableName, String whereClause) {
        tableCommand = new TableCommand(tableName, whereClause);
    }
    
    public void setTableCommand(TableCommand command) {
        this.tableCommand = command;
        //TODO should have javabeans property on this...
    }

    public TableCommand getTableCommand() {
        return tableCommand;
    }
    
    public void setDataConnection(JDBCDataConnection c) {
        this.conn = c;
    }
    
    public JDBCDataConnection getDataConnection() {
        return conn;
    }
    
    /**
     * @inheritDoc
     */
    protected LoadTask createLoadTask(DataTable[] tables) {
        return new LoadTask(tables) {
            protected void readData(DataTable[] tables) throws Exception {
                if (conn == null) {
                    //no connection, short circuit
                    return;
                }
                if (tableCommand == null) {
                    //there isn't any command to run, so short circuit the method
                    return;
                }
                //TODO when selectCommand exists, add it to the check here
                
                //set the progess count
                setMinimum(0);
                setMaximum(tables.length);
                //construct and execute a resultset for each table in turn.
                //as each table is finished, call scheduleLoad.
                for (DataTable table : tables) {
                    try {
                        PreparedStatement stmt = tableCommand.getSelectStatement(conn);
                        ResultSet rs = stmt.executeQuery();

                        //collect the column names from the result set so that
                        //I can retrieve the data from the result set into the
                        //column based on matching column names
                        ResultSetMetaData md = rs.getMetaData();
                        List<String> names = new ArrayList<String>();
                        List<DataColumn> columns = table.getColumns();
                        for (int i=0; i<columns.size(); i++) {
                            String name = columns.get(i).getName();
                            for (int j=0; j<md.getColumnCount(); j++) {
                                if (name.equalsIgnoreCase(md.getColumnName(j+1))) {
                                    names.add(name);
                                }
                            }
                        }
                        
                        //iterate over the result set. Every 50 items, schedule a load
                        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(60);
                        while (rs.next()) {
                            if (rows.size() >= 50) {
                                LoadItem item = new LoadItem<List<Map<String,Object>>>(table, rows);
                                scheduleLoad(item);
                                rows = new ArrayList<Map<String,Object>>(60);
                            }
                            //create a row
                            Map<String,Object> row = new HashMap<String,Object>();
                            for (String name : names) {
                                row.put(name, rs.getObject(name));
                            }
                            rows.add(row);
                        }
                        //close the result set
            			rs.close();
                        //load the remaining items
                        LoadItem item = new LoadItem<List<Map<String,Object>>>(table, rows);
                        scheduleLoad(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setProgress(getProgress() + 1);
                }
                setProgress(getMaximum());
            }
            
            /**
             * @inheritDoc
             */
            protected void loadData(LoadItem[] items) {
                for (LoadItem<List<Map<String,Object>>> item : items) {
                    for (Map<String,Object> row : item.data) {
                        DataRow r  = item.table.appendRowNoEvent();
                        for (String col : row.keySet()) {
                            r.setValue(col, row.get(col));
                        }
                        r.setStatus(DataRow.DataRowStatus.UNCHANGED);
                    }
                    item.table.fireDataTableChanged(new TableChangeEvent(item.table));
                }
            }
        };
    }

    /**
     * @inheritDoc
     */
    protected SaveTask createSaveTask(DataTable[] tables) {
        return new SaveTask(tables) {
            protected void saveData(DataTable[] tables) throws Exception {
                if (conn == null) {
                    //no connection, short circuit
                    return;
                }
                if (tableCommand == null) {
                    //there isn't any command to run, so short circuit the method
                    return;
                }
                //TODO when selectCommand exists, add it to the check here

                //set the progess count
                setMinimum(0);
                setMaximum(tables.length);
                for (DataTable table : tables) {
                    //fetch the set of rows from the table
                    List<DataRow> rows = table.getRows();
                    //for each row, either insert it, update it, delete it, or
                    //ignore it, depending on the row flag
                    for (DataRow row : rows) {
                        PreparedStatement stmt = null;
                        switch (row.getStatus()) {
                            case UPDATED:
                                stmt = tableCommand.getUpdateStatement(conn, row);
                                break;
                            case INSERTED:
                                stmt = tableCommand.getInsertStatement(conn, row);
                                break;
                            case DELETED:
                                stmt = tableCommand.getDeleteStatement(conn, row);
                                break;
                            default:
                                //do nothing
                                break;
                        }
                        
                        if (stmt != null) {
                            conn.executeUpdate(stmt);
                        }
                    }
                    setProgress(getProgress() + 1);
                }
                setProgress(getMaximum());
            }
        };
    }
    
}
