package client.framework.view.vwcomp;

import java.awt.event.MouseEvent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IVWJTreeTableListener {
    void onMouseClick(MouseEvent e);

    void onSelectionChange(int preRowId, int currRowId);
}
