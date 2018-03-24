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
 * <p>Title: ��TabPaneʵ�ֹ��ʻ�</p>
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
     * �������ظ÷�������ΪUIManager����ø÷��������ʵ�������Բ���д�����丸���ʵ��
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_WINDOWS_TABBEDPANE_UI;
    }
 
    /**
     * titleΪTab����Key��������ʻ����ú�����丸��ķ���
     */
    public void paint(Graphics g, JComponent c) {
    	resetMLTipText(c);
    	super.paint(g, c);
    }
    
    /**
     * ������ʻ����TipText
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
            //Ϊ��ʹ���ϵ�����������Tabbed������룬�����ƶ��������ء�
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
