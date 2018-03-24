package client.essp.pms.pbs;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import c2s.essp.pms.pbs.DtoAssignWbs;
import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import client.framework.view.vwcomp.NodeViewManager;
import client.image.ComImage;

public class AssignWbsNodeViewManager extends NodeViewManager {

    public DtoAssignWbs getDataBean() {
        if (getNode().getDataBean() instanceof DtoAssignWbs) {
            return (DtoAssignWbs) getNode().getDataBean();
        } else {
            return null;
        }
    }

    public Icon getIcon() {
        if (getDataBean() == null) {
            return super.getIcon();
        } else if (getDataBean().getJoinType().equals(DtoPmsPbsAssignment.JOINTYPEWBS)) {
            return new ImageIcon(ComImage.getImage("wbs.png"));
        } else if (getDataBean().getJoinType().equals(DtoPmsPbsAssignment.JOINTYPEACT)) {
            return new ImageIcon(ComImage.getImage("activity.png"));
        } else {
            return super.getIcon();
        }
    }

}
