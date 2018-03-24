package client.essp.pms.pbs;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import c2s.essp.pms.pbs.DtoPmsPbs;
import client.framework.view.vwcomp.NodeViewManager;
import client.image.ComImage;

public class PmsPbsNodeViewManager extends NodeViewManager {

    public DtoPmsPbs getDataBean() {
        if (getNode().getDataBean() instanceof DtoPmsPbs) {
            return (DtoPmsPbs) getNode().getDataBean();
        } else {
            return null;
        }
    }

    public Icon getIcon() {
        return new ImageIcon(ComImage.getImage("pbs.png"));
    }

}
