package client.framework.view.vwcomp;

import javax.swing.plaf.basic.BasicComboBoxEditor;

public class VWJComboBoxEditor extends BasicComboBoxEditor{

    public VWJComboBoxEditor() {
        editor = new VWJText();
        editor.setBorder(null);
    }

}
