package client.essp.timesheet.account.common;

import java.awt.Component;

import javax.swing.*;

import c2s.essp.timesheet.account.IAccountStyle;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJTableRender;
import client.image.ComImage;
import java.awt.Color;
import client.framework.model.VMTable2;
import java.awt.BorderLayout;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWJIconAccountTableRender extends VWJTableRender {

    public VWJIconAccountTableRender() {
        super(null);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        VWJLabel comp = new VWJLabel();
        comp.setText(value.toString());

        if (row % 2 == 0) {
            comp.setBackground(this.getNodeViewManager().getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(this.getNodeViewManager().getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {
            comp.setForeground(this.getNodeViewManager().getSelectForeground());
            comp.setBackground(this.getNodeViewManager().getSelectBackground());
        } else {
            comp.setForeground(this.getNodeViewManager().getForeground());
        }

        if (comp instanceof JComponent) {
            ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
        }
        //add by XR,设置字体
        comp.setFont(this.getNodeViewManager().getTextFont());
        if (table instanceof VWJTable) {
            VWJTable vwjtable = (VWJTable) table;
            Object data = ((VMTable2) vwjtable.getModel()).getRow(row);
            if (data instanceof IAccountStyle) {
                comp.setIcon(this.getIcon((IAccountStyle) data));
            }
        }
        JPanel panel = new JPanel();
        panel.setLayout( new BorderLayout());
        comp.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(comp, BorderLayout.CENTER);
        panel.setBackground(comp.getBackground());
        return panel;
    }

    /**
     * 根据acnt判断Account类型，返回图标
     * @return Icon
     */
    private static Icon getIcon(IAccountStyle acnt) {
        if (acnt == null) {
            return null;
        } else if (acnt.isDept()) {
            return new ImageIcon(ComImage.getImage("deptAccount.png"));
        } else if (acnt.isP3Adapted()) {
            return new ImageIcon(ComImage.getImage("P3Account.png"));
        } else {
            return new ImageIcon(ComImage.getImage("projectAccount.png"));
        }
    }



}
