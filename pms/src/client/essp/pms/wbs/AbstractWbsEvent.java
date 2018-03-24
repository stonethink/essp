package client.essp.pms.wbs;

import java.awt.PopupMenu;

import com.wits.util.Parameter;
import client.framework.view.vwcomp.IVWTreeTablePopupEvent;

public abstract class AbstractWbsEvent implements IVWTreeTablePopupEvent {
    public final static String ACCOUNT_RID="ACCOUNT_RID";
    private Parameter param=new Parameter();

    public AbstractWbsEvent() {
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
