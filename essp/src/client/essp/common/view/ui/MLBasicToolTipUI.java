package client.essp.common.view.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JToolTip;


/**
 *
 * <p>Title:��Tooltip����ʵ�ֹ��ʻ� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Run
 * @version 1.0
 */
public class MLBasicToolTipUI extends BasicToolTipUI {
    private final static MLBasicToolTipUI ML_TOOLTIP_UI = new
            MLBasicToolTipUI();

    /**
     * �������ظ÷�������ΪUIManager����ø÷��������ʵ�������Բ���д�����丸���ʵ��
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_TOOLTIP_UI;
    }

    /**
     * ����ToolTip��pain�������������� �������һ�£����˹��ʻ���Ҫ��ʾ���ִ�
     * @param g Graphics
     * @param b AbstractButton
     * @param textRect Rectangle
     * @param text String
     */
    public void paint(Graphics g, JComponent c) {
    	resetMLTipText(c);
    	super.paint(g, c);
    }
    
    /**
     * ���ʻ��ǳ���ҲҪ����
     */
    public Dimension getPreferredSize(JComponent c) {
    	resetMLTipText(c);
    	return super.getPreferredSize(c);
    }
    
    /**
     * ������ʻ����TipText
     * @param c JComponent
     */
    private void resetMLTipText(JComponent c) {
    	JToolTip comp = (JToolTip) c;
    	String tipText = comp.getTipText();
    	String newTipText = MessageUtil.getMessage(tipText);
    	if(tipText != null && newTipText != null 
    			&& newTipText.equals(tipText) == false) {
    		comp.setTipText(newTipText);
    	}
    }
}
