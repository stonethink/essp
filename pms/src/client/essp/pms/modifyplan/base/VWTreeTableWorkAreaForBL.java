package client.essp.pms.modifyplan.base;

import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.view.vwcomp.VWJTreeTable;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.model.VMTreeTableModel;
import java.awt.Dimension;
import client.framework.common.treeTable.TreeTableModel;
import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.table.TableCellEditor;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import client.essp.pms.wbs.VwWbsGeneralBase;
import javax.swing.JScrollPane;

public class VWTreeTableWorkAreaForBL extends VWTreeTableWorkArea {
    public VWTreeTableWorkAreaForBL() {
        super();
    }

    protected void jbInit(Object[][] configs, String treeColumnName,
                          Class dtoClass) throws Exception {
        jbInit(configs, treeColumnName, dtoClass, new NodeViewManager());
    }

    protected void jbInit(Object[][] configs, String treeColumnName,
                          Class dtoClass, NodeViewManager nodeViewManager) throws
        Exception {
        this.setPreferredSize(new Dimension(680, 240));

        //tree table
        model = new VMTreeTableModelForBL(null, configs, treeColumnName);
        model.setDtoType(dtoClass);
        treeTable = new PlanningTreeTable(model, nodeViewManager);
        TableColumnWidthSetter.set(treeTable);

        JScrollPane pane = treeTable.getScrollPane();
        pane.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.add(pane);
    }

    class PlanningTreeTable extends VWJTreeTable {
        public PlanningTreeTable(TreeTableModel treeTableModel,
                                 NodeViewManager nodeViewManager) {
            super(treeTableModel, nodeViewManager);
        }

        public Component prepareEditor(TableCellEditor editor, int row,
                                       int column) {
            Object value = getValueAt(row, column);
            boolean isSelected = isCellSelected(row, column);
            Component comp = editor.getTableCellEditorComponent(this, value,
                isSelected, row, column);
            if (comp instanceof JComponent) {
                JComponent jComp = (JComponent) comp;
                if (jComp.getNextFocusableComponent() == null) {
                    jComp.setNextFocusableComponent(this);
                }
            }

            //如果是完工比例计算方法那一列的话，就根据不同的情况来改变其Model
            try {
                ITreeNode node = this.getSelectedNode();
                Object o = node.getDataBean();
                DtoWbsActivity dto = null;
                String seletedColName = model.getColumnName(this.
                    getSelectedColumn());
                if (o instanceof DtoWbsActivity) {
                    dto = (DtoWbsActivity) o;
                }
                if (dto != null && seletedColName.equals("Complete Method") &&
                    comp instanceof VWJComboBox) {
                    VWJComboBox methodCombo = (VWJComboBox) comp;
                    if (dto.isWbs()) {
                        methodCombo.setModel(VMComboBox.toVMComboBox(
                            VwWbsGeneralBase.completeRateCompMethodTitle,
                            VwWbsGeneralBase.completeRateCompMethod));
                    } else if (dto.isActivity()) {
                        methodCombo.setModel(VMComboBox.toVMComboBox(
                            DtoWbsActivity.
                            ACTIVITY_COMPLETE_METHOD_TITLE,
                            DtoWbsActivity.ACTIVITY_COMPLETE_METHOD));
                    }
                    return methodCombo;
                }
            } catch (Exception ex) {

            }

            return comp;
        }

    }
}
