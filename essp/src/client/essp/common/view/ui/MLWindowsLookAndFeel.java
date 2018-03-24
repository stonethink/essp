package client.essp.common.view.ui;

import com.sun.java.swing.plaf.windows.*;
import javax.swing.UIDefaults;

/**
 *
 * <p>Title: ʵ��Window�������������</p>
 *
 * <p>Description: ָ��Label,Button,Panel��������ʻ���UI���</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Run
 * @version 1.0
 */
public class MLWindowsLookAndFeel extends WindowsLookAndFeel {
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        String mlPackage = "client.essp.common.view.ui.";
        Object[] classes = {
                           "LabelUI", mlPackage + "MLWindowsLabelUI",
                           "TabbedPaneUI", mlPackage + "MLWindowsTabbedPaneUI",
                           "ButtonUI", mlPackage + "MLWindowsButtonUI",
                           "CheckBoxUI", mlPackage + "MLWindowsCheckBoxUI",
                           "RadioButtonUI", mlPackage + "MLWindowsRadioButtonUI",
                           "ToolTipUI", mlPackage + "MLBasicToolTipUI",
        };
        table.putDefaults(classes);
    }

}
