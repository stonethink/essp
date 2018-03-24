package client.essp.pms.wbs;

import client.framework.view.vwcomp.NodeViewManager;
import c2s.essp.pms.wbs.IDtoWbsActivity;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import client.image.ComImage;
import java.awt.Color;

public class WbsNodeViewManager extends NodeViewManager {
    public IDtoWbsActivity getDataBean() {
        if (getNode() != null) {
            return (IDtoWbsActivity) getNode().getDataBean();
        } else {
            return null;
        }
    }

    public Color getForeground() {
//        if (getDataBean() != null) {
//            if (getDataBean().isWbs()) {
//                return super.getForeground();
//            } else if (getDataBean().isActivity()) {
//                return super.getForeground();
//            }
//        }
        return super.getForeground();
    }

    public Icon getIcon() {
        if (getDataBean() == null) {
            return super.getIcon();
        } else if (getDataBean().isMilestone() != null &&
                   getDataBean().isMilestone().booleanValue()) {
            return new ImageIcon(ComImage.getImage("activityRed.gif"));
        } else if (getDataBean().isWbs()) {
            return new ImageIcon(ComImage.getImage("wbs.png"));
        } else if (getDataBean().isActivity()) {
            return new ImageIcon(ComImage.getImage("activity.png"));
        } else {
            return super.getIcon();
        }
    }
}
