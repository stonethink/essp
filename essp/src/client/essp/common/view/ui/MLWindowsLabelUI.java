package client.essp.common.view.ui;

import com.sun.java.swing.plaf.windows.*;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.ListCellRenderer;

/**
 *
 * <p>Title: ��Window���Labelʵ�ֹ��ʻ�</p>
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
public class MLWindowsLabelUI extends WindowsLabelUI {
    private final static MLWindowsLabelUI ML_WINDOWSLLABEL_UI = new
            MLWindowsLabelUI();

    /**
     * �������ظ÷�������ΪUIManager����ø÷��������ʵ�������Բ���д�����丸���ʵ��
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        //��Ϊ���ϵ�Cell������������ʾҲ����Label�ؼ�������������ʻ��������丸���ʵ��
        if(c instanceof TreeCellRenderer ||c instanceof ListCellRenderer){
            return new WindowsLabelUI();
        }
        return ML_WINDOWSLLABEL_UI;
    }

    /**
     * �ڸ�ʽ��Label���ı�ǰ��������ʻ����ٵ��ø���ĸ�ʽ�ı�������
     * ������֮����������������ʵ�ֹ��ʻ��᲻��ȷ����Ϊ�Ѹ�ʽ�����ı����ܸ�ԭ����һ��
     * ����������������...����
     */
    protected String layoutCL(
            JLabel label,
            FontMetrics fontMetrics,
            String text,
            Icon icon,
            Rectangle viewR,
            Rectangle iconR,
            Rectangle textR) {
    	
    	String toolTip = label.getToolTipText();
    	String newText = MessageUtil.getMessage(text);
    	if(toolTip != null && text != null && toolTip.equals(text)) {
    		label.setToolTipText(newText);
    	}
            return super.layoutCL(
             label,
             fontMetrics,
             newText,
             icon,
             viewR,
             iconR,
             textR);
    }

}
