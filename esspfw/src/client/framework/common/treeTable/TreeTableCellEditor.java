package client.framework.common.treeTable;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import javax.swing.JComponent;
import javax.swing.JTree;
import java.awt.Rectangle;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.Icon;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableModel;
import client.framework.model.VMTreeTableModelAdapter;
import c2s.dto.ITreeNode;
import c2s.dto.DtoBase;
import client.framework.model.VMTreeTableModel;

/**
 * TreeTableCellEditor implementation. Component returned is the
 * JTree.
 */
public class TreeTableCellEditor extends DefaultCellEditor implements
        TableCellEditor {
    private JTreeTable treeTable;
    public TreeTableCellEditor(JTreeTable treeTable) {
        super(new TreeTableTextField());
        this.treeTable = treeTable;
    }

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int r, int c) {
//        return treeTable.tree;
        Component comp = super.getTableCellEditorComponent(table, value,
                isSelected, r, c);
        JTree t = treeTable.getTree();
//        boolean rv = t.isRootVisible();
        int offsetRow = r;
        Rectangle bounds = t.getRowBounds(offsetRow);
        int offset = bounds.x;
//        System.out.println("当前节点的偏移量为(不包括ICON的宽度)："+offset);
        TreeCellRenderer tcr = t.getCellRenderer();
        if (tcr instanceof DefaultTreeCellRenderer) {
            Object node = t.getPathForRow(offsetRow).
                          getLastPathComponent();
            Icon icon;
            if (t.getModel().isLeaf(node)) {
                icon = ((DefaultTreeCellRenderer) tcr).getLeafIcon();
            } else if (t.isExpanded(offsetRow)) {
                icon = ((DefaultTreeCellRenderer) tcr).getOpenIcon();
            } else {
                icon = ((DefaultTreeCellRenderer) tcr).getClosedIcon();
            }
            if (icon != null) {
                offset += ((DefaultTreeCellRenderer) tcr).getIconTextGap() +
                        icon.getIconWidth();
            }
        }
        ((TreeTableTextField) getComponent()).offset = offset;
        return comp;
//        return component;
//        System.out.println("当前节点的偏移量为："+offset);
//        TreeTableTextField textField=new TreeTableTextField();

//        textField.offset=offset;
//        if(value instanceof String){
//            textField.setText(value.toString());
//        }else{
//            textField.setText("");
//        }
//        return textField;
    }

    /**
     * Overridden to return false, and if the event is a mouse event
     * it is forwarded to the tree.<p>
     * The behavior for this is debatable, and should really be offered
     * as a property. By returning false, all keyboard actions are
     * implemented in terms of the table. By returning true, the
     * tree would get a chance to do something with the keyboard
     * events. For the most part this is ok. But for certain keys,
     * such as left/right, the tree will expand/collapse where as
     * the table focus should really move to a different column. Page
     * up/down should also be implemented in terms of the table.
     * By returning false this also has the added benefit that clicking
     * outside of the bounds of the tree node, but still in the tree
     * column will select the row, whereas if this returned true
     * that wouldn't be the case.
     * <p>By returning false we are also enforcing the policy that
     * the tree will never be editable (at least by a key sequence).
     */
    public boolean isCellEditable(EventObject e) {
//        if (e instanceof MouseEvent) {
//            for (int counter = treeTable.getColumnCount() - 1; counter >= 0;
//                 counter--) {
//                if (treeTable.getColumnClass(counter) == TreeTableModel.class) {
//                    MouseEvent me = (MouseEvent)e;
//                    MouseEvent newME = new MouseEvent(treeTable.tree, me.getID(),
//                               me.getWhen(), me.getModifiers(),
//                               me.getX() - treeTable.getCellRect(0, counter, true).x,
//                               me.getY(), me.getClickCount(),
//                               me.isPopupTrigger());
//                    treeTable.tree.dispatchEvent(newME);
//                    break;
//                }
//            }
//        }
        if (e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) e;
            // If the modifiers are not 0 (or the left mouse button),
            // tree may try and toggle the selection, and table
            // will then try and toggle, resulting in the
            // selection remaining the same. To avoid this, we
            // only dispatch when the modifiers are 0 (or the left mouse
            // button).
//            System.out.println("鼠标点击的位置为:"+me.getX()+"___"+me.getY());
            int rowHeight = this.treeTable.getRowHeight();
            int row = me.getY() / rowHeight;
//            System.out.println("-----得到的行数是："+row);
            if (me.getModifiers() == 0 ||
                me.getModifiers() == InputEvent.BUTTON1_MASK) {
                for (int counter = treeTable.getColumnCount() - 1; counter >= 0;
                                   counter--) {
                    if (treeTable.getColumnClass(counter) == TreeTableModel.class) {
                        MouseEvent newME = new MouseEvent
                                           (treeTable.tree, me.getID(),
                                            me.getWhen(), me.getModifiers(),
                                            me.getX() -
                                            treeTable.getCellRect(0, counter, true).
                                            x,
                                            me.getY(), me.getClickCount(),
                                            me.isPopupTrigger());
                        treeTable.tree.dispatchEvent(newME);
//                        System.out.println("MouseEvent的X和Y的值分别为："+newME.getX()+"__"+newME.getY());
                        break;
                    }
                }
            }
            if (me.getClickCount() >= 2) {
                TableModel model = this.treeTable.getModel();
                if (model instanceof VMTreeTableModelAdapter) {
                    VMTreeTableModelAdapter treeTableModel = (
                            VMTreeTableModelAdapter) model;
                    if (treeTableModel.getTreeTableModel() instanceof
                        VMTreeTableModel) {
                        //如果没有设定树表的树那一列可编辑的话，则不可编辑
                        VMTreeTableModel realModel = (VMTreeTableModel)
                                treeTableModel.getTreeTableModel();
                        int selIndex = this.treeTable.getSelectedColumn();
                        if (realModel.getColumnDataName(selIndex).equals(
                                realModel.getTreeColumnName())) {
                            if (!realModel.isTreeColumnEditable()) {
                                return false;
                            }
                        }
                    }
                    ITreeNode node = (ITreeNode) treeTableModel.nodeForRow(row);
                    DtoBase dto = (DtoBase) node.getDataBean();
                    if (dto.isReadonly()) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        } //响应鼠标事件

        return false;
    }
}
