package client.essp.common.security.role;

import client.framework.view.vwcomp.NodeViewManager;
import c2s.essp.common.menu.DtoMenuItem;
import java.awt.Color;

public class MenuNodeViewManager  extends NodeViewManager {
    public DtoMenuItem getDataBean() {
        if (getNode() != null) {
            return (DtoMenuItem) getNode().getDataBean();
        } else {
            return null;
        }
    }

    public Color getForeground() {
        return super.getForeground();
    }

}
