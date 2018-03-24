package client.essp.tc.common;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


public class RowHeaderRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.setForeground(Color.black);
        super.setBackground(UIManager.getColor("TableHeader.background"));

        setFont(table.getFont());
        setBorder(noFocusBorder);
        setValue(value);
        setHorizontalAlignment(SwingConstants.RIGHT);

        return this;
    }
}
