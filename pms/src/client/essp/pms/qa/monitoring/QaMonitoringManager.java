package client.essp.pms.qa.monitoring;

import javax.swing.Icon;
import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Color;
import client.image.ComImage;
import javax.swing.ImageIcon;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckAction;

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
public class QaMonitoringManager extends NodeViewManager {
    public DtoQaMonitoring getDataBean() {
        if (getNode() != null) {
            return (DtoQaMonitoring) getNode().getDataBean();
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
        } else if (getDataBean().getType().equals(DtoKey.TYPE_WBS)) {
            return new ImageIcon(ComImage.getImage("wbs.png"));
        } else if (getDataBean().getType().equals(DtoKey.TYPE_ACTIVITY)) {
            return new ImageIcon(ComImage.getImage("activity.png"));
        } else if (getDataBean().getType().equals(DtoQaCheckPoint.
                                                  DTO_PMS_CHECK_POINT_TYPE)) {
            return new ImageIcon(ComImage.getImage("qaCheckPoint.png"));
        } else if (getDataBean().getType().equals(DtoQaCheckAction.
                                                  DTO_PMS_CHECK_ACTION_TYPE)) {
            return new ImageIcon(ComImage.getImage("qaCheckAction.png"));
        } else {
            return super.getIcon();
        }
    }
}
