package client.essp.common.view.ui;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import client.essp.common.view.SideCompTabbedPane;

import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;

/**
 *
 * <p>Title: 对TabPane实现国际化</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MLWindowsTabbedPaneUI extends WindowsTabbedPaneUI {
    private final static MLWindowsTabbedPaneUI ML_WINDOWS_TABBEDPANE_UI = new
            MLWindowsTabbedPaneUI();

    /**
     * 必须重载该方法，因为UIManager会调用该方法获得其实例，所以不重写会获得其父类的实例
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_WINDOWS_TABBEDPANE_UI;
    }
 
    /**
     * title为Tab标题Key，将其国际化，让后调用其父类的方法
     */
    public void paint(Graphics g, JComponent c) {
    	resetMLTipText(c);
    	super.paint(g, c);
    }
    
    /**
     * 重设国际化后的TipText
     * @param c JComponent
     */
    private void resetMLTipText(JComponent c) {
    	JTabbedPane comp = (JTabbedPane) c;
    	for(int i = 0; i < comp.getTabCount(); i ++) {
	    	String tipText = comp.getTitleAt(i);
	    	String newTipText = MessageUtil.getMessage(tipText);
	    	if(tipText != null && newTipText != null 
	    			&& newTipText.equals(tipText) == false) {
	    		comp.setTitleAt(i, newTipText);
	    	}
    	}
    } 
    
    protected LayoutManager createLayoutManager() {
        return new SideCompTabbedLayout();
    }
    class SideCompTabbedLayout extends BasicTabbedPaneUI.TabbedPaneLayout {

        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);

            SideCompTabbedPane sideCompTabbedPane = (SideCompTabbedPane) parent;
            java.awt.Dimension conDim = sideCompTabbedPane.getSize();
            java.awt.Rectangle rect = new Rectangle();
            java.awt.Dimension subDim = sideCompTabbedPane.getSideComp().getPreferredSize();
            //rect.x = conDim.width - subDim.width - 1;
            //为了使肩上的组件与下面的Tabbed组件对齐，向右移动几个象素。
            if (sideCompTabbedPane.getSideComp() instanceof JPanel) {
                rect.x = conDim.width - subDim.width ;
            } else {
                rect.x = conDim.width - subDim.width ;
            }
            rect.y = sideCompTabbedPane.getInsets().top -0;
            rect.width = subDim.width;
            int iH = calculateMaxTabHeight(0) + 0;
            rect.height = iH;
            sideCompTabbedPane.getSideComp().setBounds(rect);
            //sideCompTabbedPane.sideComp.setBounds(200,0,66,20);
            //sideCompTabbedPane.sideComp.setVisible(true);
        }

    }
}
