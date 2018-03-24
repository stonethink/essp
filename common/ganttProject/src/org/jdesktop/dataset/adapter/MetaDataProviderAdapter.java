/*
 * MetaDataProviderAdapter.java
 *
 * Created on February 24, 2005, 7:17 AM
 */

package org.jdesktop.dataset.adapter;

import java.util.List;
import org.jdesktop.dataset.DataColumn;
import org.jdesktop.dataset.DataTable;
import org.jdesktop.dataset.event.DataTableListener;
import org.jdesktop.dataset.event.RowChangeEvent;
import org.jdesktop.dataset.event.TableChangeEvent;
import org.jdesktop.swing.data.MetaData;
import org.jdesktop.swing.data.MetaDataProvider;

/**
 *
 * @author rbair
 */
public class MetaDataProviderAdapter implements MetaDataProvider {
    private DataTable table;
    private MetaData[] cachedMetaData;
    private String[] cachedFieldNames;
    
    /** Creates a new instance of MetaDataProviderAdapter */
    public MetaDataProviderAdapter(DataTable table) {
        assert table != null;
        this.table = table;
        initMetaData();
        //add listener to table for column change events
        table.addDataTableListener(new DataTableListener(){
            public void rowChanged(RowChangeEvent evt) {
                //null op
            }

            public void tableChanged(TableChangeEvent evt) {
                //reload the metaData
                initMetaData();
            }
        });
    }

    public int getFieldCount() {
        return cachedMetaData == null ? 0 : cachedMetaData.length;
    }

    public final String[] getFieldNames() {
        return cachedFieldNames ==  null ? new String[0] : cachedFieldNames;
    }

    public final MetaData[] getMetaData() {
        return cachedMetaData == null ? new MetaData[0] : cachedMetaData;
    }

    public MetaData getMetaData(String dataID) {
        if (cachedMetaData == null) {
            return new MetaData(dataID);
        }
        for (MetaData md : cachedMetaData) {
            if (md.getName().equals(dataID)) {
                return md;
            }
        }
        return null;
    }
    
    private void initMetaData() {
        List<DataColumn> cols = table.getColumns();
        cachedMetaData = new MetaData[cols.size()];
        cachedFieldNames = new String[cachedMetaData.length];
        for (int i=0; i<cachedMetaData.length; i++) {
            DataColumn col = cols.get(i);
            //TODO if the column name changes, my cache is invalidated!!!
            cachedMetaData[i] = new MetaData(col.getName(), col.getType());
            cachedFieldNames[i] = col.getName();
        }
    }
}