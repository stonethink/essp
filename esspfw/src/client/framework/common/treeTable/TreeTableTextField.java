package client.framework.common.treeTable;

import javax.swing.JTextField;

/**
 * Component used by TreeTableCellEditor. The only thing this does
 * is to override the <code>reshape</code> method, and to ALWAYS
 * make the x location be <code>offset</code>.
 */
public class TreeTableTextField extends JTextField {
    public int offset;

    public void reshape(int x, int y, int w, int h) {
        int newX = Math.max(x, offset);
        super.reshape(newX, y, w - (newX - x), h);
    }
}
