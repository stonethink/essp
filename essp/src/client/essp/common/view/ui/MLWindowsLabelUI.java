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
 * <p>Title: 对Window风格Label实现国际化</p>
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
     * 必须重载该方法，因为UIManager会调用该方法获得其实例，所以不重写会获得其父类的实例
     * @param c JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent c) {
        //因为树上的Cell和下拉框中显示也用了Label控件，但其无需国际化，返回其父类的实例
        if(c instanceof TreeCellRenderer ||c instanceof ListCellRenderer){
            return new WindowsLabelUI();
        }
        return ML_WINDOWSLLABEL_UI;
    }

    /**
     * 在格式化Label的文本前，将其国际化，再调用父类的格式文本方法，
     * 若在这之后重载其他方法以实现国际化会不正确，因为已格式化的文本可能跟原来不一样
     * 比如过长的文体会用...代替
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
