package client.essp.pms.activity;

import client.framework.view.vwcomp.IVWTreeTablePopupEvent;
import java.awt.PopupMenu;
import com.wits.util.Parameter;

public abstract class AbstractActivityEvent implements IVWTreeTablePopupEvent {
    public final static String ACCOUNT_RID="ACCOUNT_RID";
    private Parameter param=new Parameter();

    public AbstractActivityEvent() {
    }

    /**
     *
     * @return Long
     * @todo Implement this client.essp.pms.wbs.IVWTreeTablePopupEvent method
     */
    public Parameter getParameter() {
        return param;
    }

    /**
     * When mouse right click on a node, this action will be invoked.
     *
     * @return PopupMenu
     * @todo Implement this client.essp.pms.wbs.IVWTreeTablePopupEvent method
     */
    public PopupMenu getPopupMenu() {
        return null;
    }
}
