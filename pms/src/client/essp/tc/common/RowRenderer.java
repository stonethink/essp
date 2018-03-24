package client.essp.tc.common;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.NodeViewManager;


public class RowRenderer implements TableCellRenderer {
    public final static int real = 0;
    public final static int text = 1;

    public final static Color color = new Color(200, 200, 200);
    public final static Color noColor = new Color(255, 255, 255);
    NodeViewManager nodeViewManager = new NodeViewManager();
    int type = 0;
    Color selectedBackgroundColor = null;

    public RowRenderer(int type) {
        this( type, color );
    }
    public RowRenderer(int type, Color selectedBackgroundColor) {
        this.type = type;
        selectedBackgroundColor = selectedBackgroundColor;
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = null;
        if (type == real) {
            VWJReal realComp = new VWJReal();
            realComp.setMaxInputIntegerDigit(4);
            realComp.setMaxInputDecimalDigit(2);
            realComp.setUICValue(value);
            comp = realComp;
        } else {
            VWJText txtComp = new VWJText();
            txtComp.setUICValue(value);
            comp = txtComp;
        }
        ((JComponent) comp).setBorder(null);

        comp.setFont(table.getFont());
        ((JComponent) comp).setBorder(null);
        if (row % 2 == 0) {
            comp.setBackground(nodeViewManager.getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(nodeViewManager.getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {
            if (this.selectedBackgroundColor == null) {
                comp.setForeground(nodeViewManager.getSelectForeground());
                comp.setBackground(nodeViewManager.getSelectBackground());
            } else {
                comp.setForeground(Color.black);
                comp.setBackground(this.selectedBackgroundColor);
            }
        } else {
            comp.setForeground(nodeViewManager.getForeground());
        }

        return ((JComponent) comp);
    }

    public void setSelectedBackgroundColor(Color color) {
        this.selectedBackgroundColor = color;
    }
}
