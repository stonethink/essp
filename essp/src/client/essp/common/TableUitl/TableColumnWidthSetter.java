package client.essp.common.TableUitl;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import client.framework.common.treeTable.TreeTableModel;

public class TableColumnWidthSetter {
    public final static int PREFER_TABLE_WIDTH = 800;
    public final static int MIN_TABLE_COLUMN_WIDTH = 70;
    public final static int MIN_TREE_TABLE_COLUMN_WIDTH = 160;

    public static void set(JTable table){
        set(table, PREFER_TABLE_WIDTH);
    }

    public static void set(JTable table, int preferTableWidth) {
        int tableWidth = table.getWidth();
        if( tableWidth < preferTableWidth ){
            tableWidth = preferTableWidth;
        }

        int columnCount = table.getColumnCount();
        int treeColumn = findTreeColumn(table);

        int minTotalWidth = 0;
        if( treeColumn >= 0 ){
            minTotalWidth = MIN_TREE_TABLE_COLUMN_WIDTH + (columnCount-1)*MIN_TABLE_COLUMN_WIDTH;
        }else{
            minTotalWidth = columnCount*MIN_TABLE_COLUMN_WIDTH;
        }

        if( minTotalWidth > tableWidth ){
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        }

        for (int i = 0; i < columnCount; i++) {
            TableColumn column = table.getColumn(table.getColumnName(i));

            if( i == treeColumn ){
                column.setPreferredWidth(MIN_TREE_TABLE_COLUMN_WIDTH);
            }else{
                column.setPreferredWidth(MIN_TABLE_COLUMN_WIDTH);
            }
        }
    }

    static int findTreeColumn(JTable table){
        for (int i = 0; i < table.getColumnCount(); i++) {
            if( table.getColumnClass(i) == TreeTableModel.class ){
                return i;
            }
        }

        return -1;
    }
}
