package client.essp.common.view.ui;

import com.sun.java.swing.plaf.windows.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JMenuItem;
/**
 *
 * <p>Title: 对Button内容实现国际化</p>
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
public class MLWindowsButtonUI extends WindowsButtonUI {
    private final static MLWindowsButtonUI ML_WINDOWS_BUTTON_UI = new
            MLWindowsButtonUI();

    /**
     * 必须重载该方法，因为UIManager会调用该方法获得其实例，所以不重写会获得其父类的实例
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_WINDOWS_BUTTON_UI;
    }
    
    @Override
    public void paint(Graphics g, JComponent c) {
    	AbstractButton b = (AbstractButton)c;
    	String text = b.getText();
//    	resetMultiTipText(b); MLBasicToolTipUI问题已解决
    	String newText = MessageUtil.getMessage(text);
    	if(text != null && newText != null && !text.equals(newText)) {
    		b.setText(newText);
    	}
    	super.paint(g, c);
    }
    
//    /**
//     * 获得Tooltip控件的国际化信息
//     * @param b AbstractButton
//     */
//    private void resetMultiTipText(AbstractButton b){
//        String tipText = b.getToolTipText();
//        if(tipText == null) {
//        	return;
//        }
//        String newTipText = MessageUtil.getMessage(tipText);
//        if(newTipText != null && newTipText.equals(tipText) == false) {
//        	b.setToolTipText(newTipText);
//        	
//        }
//    }
}
