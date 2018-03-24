package client.essp.pms.wbs.pbsAndFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.pbs.PmsPbsNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import java.awt.event.*;
import javax.swing.JButton;

public class VwAssignPbsList extends VWTreeTableWorkArea {
    private String actionIdList = "FPbsListAction";

    /**
     * define common data (globe)
     */
    private Long acntRid;

    final Object[][] configs = new Object[][] {
                                      {"Product Name", "productName", VMColumnConfig.EDITABLE, null},
                                      {"Product Code", "productCode",VMColumnConfig.UNEDITABLE, new VWJText()},
                                      {"Status", "pbsStatus", VMColumnConfig.EDITABLE, new VWJText()},
    };
    static final String treeColumnName = "productName";

    public VwAssignPbsList() {
        try {
           super.jbInit(configs, treeColumnName,
                        DtoPbsAssign.class, new PmsPbsNodeViewManager());
       } catch (Exception e) {
           e.printStackTrace();
       }

       addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionPerformedLoad(event);
            }
        });

        JButton btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand(e);
            }
        });
    }

    public void actionPerformedExpand(ActionEvent e) {
        this.getTreeTable().expandsRow();
    }

    private void actionPerformedLoad(ActionEvent event) {
        resetUI();
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get("acntRid"));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( acntRid == null ){
            this.getTreeTable().setRoot(null);
            return;
        }

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("inAcntRid", acntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj("root");

            this.getTreeTable().setRoot(root);
        }
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwAssignPbsList();
        Parameter param = new Parameter();
        param.put("acntRid", "7");
        workArea.setParameter(param);
        workArea.refreshWorkArea();

        VWWorkArea workArea0 = new VWWorkArea();
        workArea0.addTab("Wbs", workArea);
        TestPanel.show(workArea0);
    }

}
