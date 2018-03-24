package client.framework.view.vwcomp;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import client.framework.model.*;


public class VWJTableRender implements TableCellRenderer {

    private DefaultTableCellRenderer defaultRender = new DefaultTableCellRenderer();
    private Component comp = null;

    //add by xh
    private NodeViewManager nodeViewManager = null;

    public VWJTableRender(Component comp) {
        this(comp, new NodeViewManager());
    }

    public VWJTableRender(Component comp, NodeViewManager nodeViewManager) {
        this.comp = comp;
        if (nodeViewManager == null) {
            this.nodeViewManager = new NodeViewManager();
        } else {
            this.nodeViewManager = nodeViewManager;
        }
    }

    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
        //System.out.println("row="+row+",column="+column+",value="+value);
    	
    	/**
    	 * modified by lipengxu at 2008-06-03
    	 * for nodeViewManager dataName and dataVelue
    	**/
    	nodeViewManager.setDataValue(value);
        if( table.getModel() instanceof VMTable2 ){
        	VMTable2 model = (VMTable2) table.getModel();
            nodeViewManager.setNode(model.getRow(row));
            java.util.List configList = model.getColumnConfigs();
            if(column >= 0 && configList != null && configList.size() > column) {
            	VMColumnConfig config = (VMColumnConfig) configList.get(table.convertColumnIndexToModel(column));
            	nodeViewManager.setDataName(config.getDataName());
            }
        } else if(table.getModel() instanceof VMTreeTableModelAdapter) {
        	VMTreeTableModelAdapter model = (VMTreeTableModelAdapter) table.getModel();
        	java.util.List configList = model.getColumnConfigs();
        	if(column >= 0 && configList != null && configList.size() > column) {
            	VMColumnConfig config = (VMColumnConfig) configList.get(table.convertColumnIndexToModel(column));
            	nodeViewManager.setDataName(config.getDataName());
            }
        }

        int horizontalAlignment = SwingConstants.CENTER;
        if (comp == null || !(comp instanceof IVWComponent)) {
            comp = defaultRender.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        } else {
            IVWComponent vwComp = (IVWComponent) comp;
            vwComp.setUICValue(value);
            horizontalAlignment = vwComp.getHorizontalAlignment();
        }

        /*
        if (comp instanceof VWJText || comp instanceof VWJTextArea) {
            ((JComponent)comp).setToolTipText(((IVWComponent)comp).getUICValue().toString());
        }
        */

        if (row % 2 == 0) {
            comp.setBackground(nodeViewManager.getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(nodeViewManager.getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {
            comp.setForeground(nodeViewManager.getSelectForeground());
            comp.setBackground(nodeViewManager.getSelectBackground());
        } else {
            comp.setForeground(nodeViewManager.getForeground());
        }

        if (comp instanceof JComponent) {
            ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
        }
        //add by XR,设置字体
        comp.setFont(nodeViewManager.getTextFont());
        
        //使显示的text偏离网格几个象素
        JPanel panel = new JPanel();
        panel.setLayout( new BorderLayout());

        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(2,2));
//      设置图标，如果可以
        Icon icon = nodeViewManager.getIcon();
        if(icon != null) {
        	lbl.setIcon(icon);
        	lbl.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        
        lbl.setBackground(comp.getBackground());
        panel.setBackground(comp.getBackground());
        if( horizontalAlignment == SwingConstants.RIGHT ){
            panel.add(lbl, BorderLayout.EAST);
        }else if( horizontalAlignment == SwingConstants.LEFT ){
            panel.add(lbl, BorderLayout.WEST);
        }
        panel.add(comp,BorderLayout.CENTER);

        return panel;
    }

    public NodeViewManager getNodeViewManager() {
        return nodeViewManager;
    }

    public void setNodeViewManager(NodeViewManager nodeViewManager) {
        this.nodeViewManager = nodeViewManager;
    }
}
