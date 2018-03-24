package client.essp.common.view.ui;

import com.sun.java.swing.plaf.windows.*;
import javax.swing.AbstractButton;
import javax.swing.plaf.ComponentUI;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.Graphics;
/**
 *
 * <p>Title: 对RadioButton内容实现国际化</p>
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
public class MLWindowsRadioButtonUI extends WindowsRadioButtonUI {
    private final static MLWindowsRadioButtonUI ML_WINDOWS_RABUTTON_UI = new
            MLWindowsRadioButtonUI();

    /**
     * 必须重载该方法，因为UIManager会调用该方法获得其实例，所以不重写会获得其父类的实例
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        return ML_WINDOWS_RABUTTON_UI;
    }

    /**
     * 重载写checkbox上的字串方法,以text为Key 查找其国际化对应字串
     * @param g Graphics
     * @param b AbstractButton
     * @param textRect Rectangle
     * @param text String
     */
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect,
                             String text) {
        super.paintText(g, b, textRect, MessageUtil.getMessage(b.getText()));
    }

}
