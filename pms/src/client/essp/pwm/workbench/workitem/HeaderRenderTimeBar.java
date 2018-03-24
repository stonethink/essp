package client.essp.pwm.workbench.workitem;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.uic.timeBar.PanelTimeBar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class HeaderRenderTimeBar extends DefaultTableCellRenderer {

    //java.awt.Color timeBarForeColor = Color.BLUE;
    //java.awt.Color timeBarBackColor = Color.WHITE;

    protected com.uic.timeBar.PanelTimeBar panelTimeBar = new PanelTimeBar();

    public HeaderRenderTimeBar() {
        panelTimeBar.getTimeBar().setShowBar(false);

        panelTimeBar.getJTextFieldBeginTime().setText("begin");
        panelTimeBar.getJTextFieldEndTime().setText("end");
        panelTimeBar.getJTextFieldBeginTime().setPreferredSize(new Dimension(32,22));
        panelTimeBar.getJTextFieldEndTime().setPreferredSize(new Dimension(30,22));
        panelTimeBar.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        panelTimeBar.getTimeBar().setBackground(table.getTableHeader().getBackground());
        return panelTimeBar;
    }
    public com.uic.timeBar.PanelTimeBar getPanelTimeBar() {
        return panelTimeBar;
    }

}
