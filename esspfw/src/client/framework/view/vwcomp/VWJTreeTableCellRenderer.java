package client.framework.view.vwcomp;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import c2s.dto.ITreeNode;
import client.framework.common.treeTable.TreeTableCellRenderer;

public class VWJTreeTableCellRenderer extends TreeTableCellRenderer {
    private VWJTreeTable treeTable;
    public VWJTreeTableCellRenderer(TreeModel model,VWJTreeTable treeTable) {
        super(model,treeTable);
        this.treeTable=treeTable;
    }

    /**
     * TreeCellRenderer method. Overridden to update the visible row.
     */
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int column) {
        TreePath treepathByRow = this.getPathForRow(row);
        if (treepathByRow != null) {
            ITreeNode node = (ITreeNode) (treepathByRow.
                                          getLastPathComponent());
            if (node != null && node instanceof ITreeNode) {
                ITreeNode treeNode = (ITreeNode) node;

                treeTable.nodeViewManager.setNode(treeNode);
                treeTable.nodeViewManager.setDataValue(value);
                this.setForeground(treeTable.nodeViewManager.
                                   getForeground());
                this.setBackground(treeTable.nodeViewManager.
                                   getBackground());

                if (isSelected) {
                    setForeground(treeTable.nodeViewManager.
                                  getSelectForeground());
                    setBackground(treeTable.nodeViewManager.
                                  getSelectBackground());
                }

                this.setFont(treeTable.nodeViewManager.getTextFont());
            }
        }
        //this.setBackground(java.awt.Color.GREEN);

//        DefaultTreeCellRenderer render=(DefaultTreeCellRenderer)treeTable.getTree().getCellRenderer();
//        render.setBackground(java.awt.Color.yellow);
        //this.setBackground(java.awt.Color.green);
//        Dimension dim=render.getPreferredSize();
//        dim.setSize(1024,30);
//        render.setPreferredSize(dim);
//        render.setSize(dim);
//        this.setPreferredSize(dim);
//        this.setSize(dim);

        visibleRow = row;
        return this;
    }

}
