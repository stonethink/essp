package client.essp.common.view.ui;

import com.sun.java.swing.plaf.windows.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import java.awt.Rectangle;
import java.awt.Graphics;
/**
 *
 * <p>Title:��Checkbox����ʵ�ֹ��ʻ� </p>
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
public class MLWindowsCheckBoxUI extends WindowsCheckBoxUI {
    private final static MLWindowsCheckBoxUI ML_WINDOWS_CHECKBOX_UI = new
            MLWindowsCheckBoxUI();

    /**
     * �������ظ÷�������ΪUIManager����ø÷��������ʵ�������Բ���д�����丸���ʵ��
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_WINDOWS_CHECKBOX_UI;
    }
    /**
     * ����дcheckbox�ϵ��ִ�����,��textΪKey ��������ʻ���Ӧ�ִ�
     * @param g Graphics
     * @param b AbstractButton
     * @param textRect Rectangle
     * @param text String
     */
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
        super.paintText( g,  b,  textRect,  MessageUtil.getMessage(b.getText())) ;
    }
}
