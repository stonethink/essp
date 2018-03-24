package client.essp.common.TableUitl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import client.framework.model.IColumnConfig;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJTableHeaderRender;
import client.framework.view.vwcomp.VWJTreeTable;
import client.framework.view.vwcomp.VWUtil;
import client.image.ComImage;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class TableColumnChooseDisplay {
    Object[] selectColumn;
    Object[] allColumn;
    Component parentPanel;
    AbstractTableModel tableModel;
    JTable table;

    JButton btnDisplay;

    /**
     * @param AbstractTableModel tableModel   -- table model whose column is choosed to display
     *         tableModel must be instanceof IColumnConfig interface.
     * @param parentPanel Component -- the choose dialog's parent panel
     */
    public TableColumnChooseDisplay(JTable table, Component parentPanel) {
        this.table = table;
        this.tableModel = (AbstractTableModel) table.getModel();
        this.parentPanel = parentPanel;

        if (tableModel instanceof IColumnConfig) {
            IColumnConfig config = (IColumnConfig) tableModel;

            //select column
            List columnConfigs = config.getColumnConfigs();
            this.selectColumn = new String[columnConfigs.size()];
            for (int i = 0; i < columnConfigs.size(); i++) {
                selectColumn[i] = ((VMColumnConfig) columnConfigs.get(i)).
                                  getName();
            }

            //all column
            List columnMap = config.getColumnMap();
            this.allColumn = new String[columnMap.size()];
            for (int i = 0; i < columnMap.size(); i++) {
                allColumn[i] = ((VMColumnConfig) columnMap.get(i)).getName();
            }
//            Set entySet = columnMap.entrySet();
//            int i = 0;
//            for (Iterator it = entySet.iterator(); it.hasNext(); ) {
//                Map.Entry entry = (Map.Entry) it.next();
//                allColumn[i] = ((VMColumnConfig) entry.getValue()).getName();
//                i++;
//            }
        }
    }

    public JButton getDisplayButton() {
        btnDisplay = new JButton();
        btnDisplay.setIcon(new ImageIcon(ComImage.getImage("column.png")));
        btnDisplay.setMaximumSize(new Dimension(20, 20));
        btnDisplay.setMinimumSize(new Dimension(20, 20));
        btnDisplay.setPreferredSize(new Dimension(20, 20));
        btnDisplay.setText("");
        btnDisplay.setToolTipText("rsid.common.display");

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showChooseDialog(TableColumnChooseDisplay.this.parentPanel) == true) {
                    setColumnConfigOfModel();
                }
            }
        });

        return this.btnDisplay;
    }

    public Object[] getAllColumn() {
        return this.allColumn;
    }

    public Object[] getSelectColumn() {
        return this.selectColumn;
    }

    public void setSelectColumn(Object[] selectColumn) {
        this.selectColumn = selectColumn;
    }

    private boolean showChooseDialog(Component parentPanel) {
        ChoosePanel choosePanel = new ChoosePanel(allColumn, selectColumn);
        if (this.getAllColumn().length > 18) {
            choosePanel.setPreferredSize(new Dimension(280, 400));
        } else {
            choosePanel.setPreferredSize(new Dimension(280, 200));
        }
        if (JOptionPane.showConfirmDialog(parentPanel, choosePanel,
                                          "Select Columns To Display",
                                          JOptionPane.OK_CANCEL_OPTION)
            == JOptionPane.OK_OPTION) {
            this.setSelectColumn(choosePanel.getShowObjects());
            return true;
        } else {
            return false;
        }
    }


    protected void setColumnConfigOfModel() {
        if (tableModel instanceof IColumnConfig) {
            int selectedIndex = table.getSelectedRow();
            IColumnConfig configModel = (IColumnConfig) tableModel;
            List columnConfigs = new ArrayList();

            for (int i = 0; i < getSelectColumn().length; i++) {
                String columnName = (String) getSelectColumn()[i];
                VMColumnConfig columnConfig = null;
//                (VMColumnConfig) configModel.getColumnMap().get(columnName);
                List colList = configModel.getColumnMap();
                for (int j = 0; j < colList.size(); j++) {
                    VMColumnConfig columnConfigTemp = (VMColumnConfig) colList.
                            get(j);
                    if (columnConfigTemp.getName().equals(columnName)) {
                        columnConfig = columnConfigTemp;
                        break;
                    }
                }
                if (columnConfig == null) {
                    columnConfig = new VMColumnConfig(new Object[] {columnName});
                    configModel.getColumnMap().add(columnConfig);
                }

                columnConfigs.add(columnConfig);
            }

            configModel.setColumnConfigs(columnConfigs, false);

            tableModel.fireTableStructureChanged();

            //set cell render
            VWUtil.setTableRender(table);
            VWUtil.setTableEditor(table);

            boolean isConfigSetColumnWidth = false;
            for (int i = 0; i < table.getColumnCount(); i++) {
                String columnName = table.getColumnName(i);
                TableColumn column = table.getColumn(columnName);

                //set header render
                column.setHeaderRenderer(new VWJTableHeaderRender(((
                        IColumnConfig) tableModel)));

                //column width
                VMColumnConfig columnConfig = null;
                // (VMColumnConfig) configModel.getColumnMap().get(columnName);
                List colList = configModel.getColumnMap();
                for (int j = 0; j < colList.size(); j++) {
                    VMColumnConfig columnConfigTemp = (VMColumnConfig) colList.
                            get(j);
                    if (columnConfigTemp.getName().equals(columnName)) {
                        columnConfig = columnConfigTemp;
                        break;
                    }
                }

                if (columnConfig.getPreferWidth() > 0) {
                    column.setPreferredWidth(columnConfig.getPreferWidth());
                    isConfigSetColumnWidth = true;
                }
            }

            if (isConfigSetColumnWidth == false) {
                TableColumnWidthSetter.set(table);
            }

            //keep the selected row
            if (table instanceof VWJTreeTable) {
                ((VWJTreeTable) table).setSelectedRow(selectedIndex);
            } else if (table instanceof VWJTable) {
                ((VWJTable) table).setSelectRow(selectedIndex);
            }
        }

    }

}
