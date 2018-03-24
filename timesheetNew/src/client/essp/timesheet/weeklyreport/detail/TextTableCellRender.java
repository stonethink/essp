package client.essp.timesheet.weeklyreport.detail;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import client.framework.view.vwcomp.NodeViewManager;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

/**
 * <p>Title: TextTableCellRender</p>
 *
 * <p>Description: ��ʱ����ϸTable���ı���Ԫ��Render</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TextTableCellRender extends DefaultTableCellRenderer {

    public final static TextTableCellRender DEFAULT = new TextTableCellRender();
    private NodeViewManager nodeViewManager;

    private TextTableCellRender() {
        nodeViewManager = new NodeViewManager();
    }

    public TextTableCellRender(NodeViewManager nodeViewManager) {
        this.nodeViewManager = nodeViewManager;
    }
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setValue(value);
        if (row % 2 == 0) {
            this.setBackground(nodeViewManager.getOddBackground()); //���������е�ɫ
        } else if (row % 2 == 1) {
            this.setBackground(nodeViewManager.getEvenBackground()); //����ż���е�ɫ
        }

        if (isSelected) {
            this.setForeground(nodeViewManager.getSelectForeground());
            this.setBackground(nodeViewManager.getSelectBackground());
        } else {
            this.setForeground(nodeViewManager.getForeground());
        }

        if (this instanceof JComponent) {
            ((JComponent) this).setBorder(BorderFactory.createEmptyBorder());
        }
        //add by XR,��������
        this.setFont(nodeViewManager.getTextFont());

        return this;
    }

}
