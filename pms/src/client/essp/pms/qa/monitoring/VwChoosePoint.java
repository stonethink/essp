package client.essp.pms.qa.monitoring;

import client.framework.view.vwcomp.VWJComboBox;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJLabel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import java.util.List;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import client.framework.model.VMComboBox;

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
public class VwChoosePoint  extends VWWorkArea implements
    IVWPopupEditorEvent {
    private VWJComboBox cmbPoints = new VWJComboBox();
    VwMonitoringList mList;
    VWJLabel lbAttention = new VWJLabel();
    DtoQaMonitoring currentDataBean;
    JLabel jLabel1 = new JLabel();

    public VwChoosePoint(VwMonitoringList mList) {
        this.mList = mList;
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
        }
    }
    private void jbInit() throws Exception {
        this.setLayout(null);
        lbAttention.setText("Please choose a check point");
        lbAttention.setBounds(new Rectangle(60, 33, 181, 33));
        cmbPoints.setBounds(new Rectangle(60, 77, 164, 19));
        jLabel1.setText("jLabel1");
        this.add(cmbPoints);
        this.add(lbAttention);
    }
    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        ITreeNode currentNode = mList.getSelectedNode();
        currentDataBean = (DtoQaMonitoring) currentNode.
                                              getDataBean();
        List pointList = currentDataBean.getPointList();
        if(pointList == null) {
            return;
        }
        Object[] values = pointList.toArray();
        String[] names = new String[pointList.size()];
        for(int i = 0; i < pointList.size(); i++) {
            DtoQaCheckPoint point = (DtoQaCheckPoint) pointList.get(i);
            names[i] = point.getName();
        }
        cmbPoints.setModel(VMComboBox.toVMComboBox(names,values));
        if(cmbPoints.getItemCount()>0) {
            cmbPoints.setSelectedIndex(0);
        }

    }


    public boolean onClickOK(ActionEvent e) {
        DtoQaMonitoring newAction = new DtoQaMonitoring();
        DtoQaCheckPoint point = (DtoQaCheckPoint) cmbPoints.getUICValue();
        newAction.setAcntRid(point.getAcntRid());
        newAction.setBelongRid(point.getRid());
        newAction.setName(point.getName());
        newAction.setMethod(point.getMethod());
        newAction.setRemark(point.getRemark());
        newAction.setCpRid(point.getRid());
        this.mList.actionPerformedAddAction(newAction);
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
