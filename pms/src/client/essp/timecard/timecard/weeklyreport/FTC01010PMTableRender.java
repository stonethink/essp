package client.essp.timecard.timecard.weeklyreport;

import client.framework.view.vwcomp.VWJTableRender;

import java.awt.Color;
import java.awt.Component;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


public class FTC01010PMTableRender extends VWJTableRender {
    private Component comp = null;

    public FTC01010PMTableRender(Component comp) {
        super(comp);
    }

    public Component getTableCellRendererComponent(JTable  table,
                                                   Object  value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int     row,
                                                   int     column) {
        comp = super.getTableCellRendererComponent(table, value, isSelected,
                                                   hasFocus, row, column);

        if (row == (table.getRowCount() - 1)) {
            comp.setForeground(Color.white);
            comp.setBackground(Color.gray);
        }

        return comp;
    }

    public static void setRender(JTable table) {
        TableModel model = table.getModel();

        if (model instanceof client.framework.model.VMTable2) {
            client.framework.model.VMTable2 newModel = (client.framework.model.VMTable2) model;
            List                            cfgs = newModel.getColumnConfigs();

            int                             columnCount = table.getColumnCount();
            TableColumnModel                columnModel = table.getColumnModel();

            for (int i = 0; i < columnCount; i++) {
                TableColumn                           column = columnModel
                                                               .getColumn(i);
                client.framework.model.VMColumnConfig columnConfig = (client.framework.model.VMColumnConfig) cfgs
                                                                     .get(i);

                if ((columnConfig.getComponent() != null)
                        && columnConfig.getComponent() instanceof client.framework.view.vwcomp.IVWComponent) {
                    //System.out.println("set column"+table.getColumnName(i)+" on "+vwComp);
                    FTC01010PMTableRender render = new FTC01010PMTableRender(columnConfig
                                                                             .getComponent());
                    column.setCellRenderer(render);
                }
            }
        }
    }
}
