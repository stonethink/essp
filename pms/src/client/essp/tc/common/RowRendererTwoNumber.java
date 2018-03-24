package client.essp.tc.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

public class RowRendererTwoNumber implements TableCellRenderer {
    Color selectBgColor = null;
    NodeViewManager nodeViewManager = new NodeViewManager();

    public RowRendererTwoNumber() {
        this(null);
    }

    public RowRendererTwoNumber(Color selectBgColor) {
        this.selectBgColor = selectBgColor;
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = null;

        //value
        if (value != null) {
            if (value instanceof BigDecimal[]) {
                BigDecimal array[] = (BigDecimal[]) value;
                if (array.length == 1) {
                    comp = getOne(array[0]);
                } else if (array.length == 2) {
                    comp = getTwo(array[0], array[1]);
                }
            } else if (value instanceof BigDecimal) {
                comp = getOne((BigDecimal) value);
            }
        }

        if (comp == null) {
            VWJText text = new VWJText();
            text.setText(value == null ? null : value.toString());
            comp = text;
        }

        if (isSelected) {
            comp.setBackground(new Color(200, 200, 200));
        } else {
            comp.setBackground(table.getBackground());
        }

        comp.setFont(table.getFont());
        ((JComponent) comp).setBorder(null);

        if (row % 2 == 0) {
            comp.setBackground(nodeViewManager.getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(nodeViewManager.getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {

            if (this.selectBgColor == null) {
                comp.setForeground(nodeViewManager.getSelectForeground());
                comp.setBackground(nodeViewManager.getSelectBackground());
            } else {
                comp.setForeground(Color.black);
                comp.setBackground(this.selectBgColor);
            }
        } else {
            comp.setForeground(nodeViewManager.getForeground());
        }

        //使显示的text偏离网格几个象素
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(2, 2));
        lbl.setBackground(comp.getBackground());
        panel.setBackground(comp.getBackground());
        panel.add(lbl, BorderLayout.EAST);
        panel.add(comp, BorderLayout.CENTER);

        return panel;
    }

    private Component getOne(BigDecimal oneValue) {
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        real.setCanNegative(true);
        if(oneValue == null)
            real.setUICValue(new BigDecimal(0));
        else
            real.setUICValue(oneValue);
        return real;
    }

    private Component getTwo(BigDecimal oneValue, BigDecimal twoValue) {
        if (oneValue == null) {
            return getOne(twoValue);
        } else if (twoValue == null) {
            return getOne(oneValue);
        } else {
            if (oneValue.equals(twoValue)) {
                return getOne(oneValue);
            }
        }

        String oneStr = oneValue.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        String twoStr = twoValue.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        VWJText text = new VWJText();
        text.setHorizontalAlignment(text.RIGHT);
        text.setText(oneStr + "/" + twoStr);
        return text;
    }
}
