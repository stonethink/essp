package client.framework.view.vwcomp;

import java.awt.event.ActionEvent;

/**
 * <p>Title: </p>
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
public interface IVWWizardEditorEvent {

        /**
         *
         * @param e ActionEvent
         * @return boolean
         */
    public boolean onClickBack(ActionEvent e);
    public boolean onClickNext(ActionEvent e);
    public boolean onClickFinish(ActionEvent e);
    public boolean onClickCancel(ActionEvent e);
    public void refresh();

}
