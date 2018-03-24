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
 * <p>Title:对Tooltip内容实现国际化 </p>
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
     * 必须重载该方法，因为UIManager会调用该方法获得其实例，所以不重写会获得其父类的实例
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_TOOLTIP_UI;
    }

    /**
     * 重载ToolTip的pain方法，基本和其 父类操作一致，除了国际化其要显示的字串
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
     * 国际化是长度也要重设
     */
    public Dimension getPreferredSize(JComponent c) {
    	resetMLTipText(c);
    	return super.getPreferredSize(c);
    }
    
    /**
     * 重设国际化后的TipText
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
