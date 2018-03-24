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
 * <p>Title: ��Button����ʵ�ֹ��ʻ�</p>
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
     * �������ظ÷�������ΪUIManager����ø÷��������ʵ�������Բ���д�����丸���ʵ��
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
//    	resetMultiTipText(b); MLBasicToolTipUI�����ѽ��
    	String newText = MessageUtil.getMessage(text);
    	if(text != null && newText != null && !text.equals(newText)) {
    		b.setText(newText);
    	}
    	super.paint(g, c);
    }
    
//    /**
//     * ���Tooltip�ؼ��Ĺ��ʻ���Ϣ
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
