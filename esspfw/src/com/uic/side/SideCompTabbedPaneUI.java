package com.uic.side;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Xuxi.Chen
 * @version 1.0
 */

//public class SideCompTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {
public class SideCompTabbedPaneUI extends com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI {
    public SideCompTabbedPaneUI() {
    }

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

    }

//    protected void installComponents() {
//      super.installComponents();
//      //tabPane.add(((SideCompTabbedPane)tabPane).getSideComp());
//
//    }

    protected LayoutManager createLayoutManager() {
        return new SideCompTabbedLayout();
    }

    class SideCompTabbedLayout extends BasicTabbedPaneUI.TabbedPaneLayout {

        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);

            SideCompTabbedPane sideCompTabbedPane = (SideCompTabbedPane) parent;
            java.awt.Dimension conDim = sideCompTabbedPane.getSize();
            java.awt.Rectangle rect = new Rectangle();
            java.awt.Dimension subDim = sideCompTabbedPane.sideComp.getPreferredSize();
            //rect.x = conDim.width - subDim.width - 1;
            //为了使肩上的组件与下面的Tabbed组件对齐，向右移动几个象素。
            if (sideCompTabbedPane.sideComp instanceof JPanel) {
                rect.x = conDim.width - subDim.width ;
            } else {
                rect.x = conDim.width - subDim.width ;
            }
            rect.y = sideCompTabbedPane.getInsets().top -0;
            rect.width = subDim.width;
            int iH = calculateMaxTabHeight(0) + 0;
            rect.height = iH;
            sideCompTabbedPane.sideComp.setBounds(rect);
            //sideCompTabbedPane.sideComp.setBounds(200,0,66,20);
            //sideCompTabbedPane.sideComp.setVisible(true);
        }

    }


}
