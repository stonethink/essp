package client.framework.view.vwcomp;

import java.awt.event.ActionEvent;

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
public interface IVWPopupEditorEvent {
    /**
     *
     * @param e
     * @return true: close dialog ; false: no action
     */
    public boolean onClickOK(ActionEvent e);

    public boolean onClickCancel(ActionEvent e);
}
